package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class ServiceRequest_Activity extends AppCompatActivity {
    DatabaseReference reviewsDatabase;
    DatabaseReference branchDatabase;
    String selected;
    Spinner requestSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_request);

        branchDatabase = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("branches");
        Bundle extras = getIntent().getExtras();
        String branchAddress = extras.getString("address");
        requestSpinner = (Spinner) findViewById(R.id.requestSpinner);

        ArrayList<String> listOfRequirements = new ArrayList<String>();

        branchDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String id = "";
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    if (userSnapshot.child("address").getValue(String.class).equals(branchAddress)){
                        id = userSnapshot.child("id").getValue(String.class);
                    }
                }
                DatabaseReference requestsDatabase = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("branches/" + id + "/requests");
                requestsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<String> requestList = new ArrayList<String>();
                        for (DataSnapshot userSnapshot: snapshot.getChildren()){
                            String customerName = userSnapshot.child("customerName").getValue(String.class);
                            requestList.add(customerName);
                        }
                        ArrayAdapter<String> requestAdapter = new ArrayAdapter<String>(ServiceRequest_Activity.this, android.R.layout.simple_spinner_item, requestList);
                        requestAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                        requestSpinner.setAdapter(requestAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void OnAccept(View view){
        Toast.makeText(ServiceRequest_Activity.this,"Accepted", Toast.LENGTH_SHORT).show();
    }

    public void OnReject(View view){
        Toast.makeText(ServiceRequest_Activity.this,"Rejected", Toast.LENGTH_SHORT).show();
    }
}