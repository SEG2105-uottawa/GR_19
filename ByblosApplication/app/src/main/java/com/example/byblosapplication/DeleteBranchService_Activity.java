package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class DeleteBranchService_Activity extends AppCompatActivity {
    DatabaseReference databaseBranches;
    DatabaseReference databaseBranchServices;
    String branchID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_branch_service);

        Bundle extras = getIntent().getExtras();
        String branchAddress = extras.getString("address");
        databaseBranches = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("branches");

        //Get the branchID
        databaseBranches.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                branchID = "";
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    if (userSnapshot.child("address").getValue().equals(branchAddress)){
                        branchID = userSnapshot.child("id").getValue(String.class);
                    }
                }
                databaseBranchServices = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("branches/" + branchID +"/services");
                //Add services to spinner
                databaseBranchServices.addListenerForSingleValueEvent(new ValueEventListener() {
                    ArrayList<String> serviceList = new ArrayList<String>();
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot userSnapshot: snapshot.getChildren()){
                            String name = userSnapshot.child("name").getValue(String.class);
                            if (name != null)
                                serviceList.add(name);
                        }
                        Spinner serviceSpinner = (Spinner) findViewById(R.id.spinner);
                        ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(DeleteBranchService_Activity.this, android.R.layout.simple_spinner_item, serviceList);
                        serviceAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                        serviceSpinner.setAdapter(serviceAdapter);
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
        System.out.println("BranchID" + branchID);
    }

    public void DeleteBranchService(View view){
        databaseBranchServices.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String serviceName = ((Spinner)findViewById(R.id.spinner)).getSelectedItem().toString();
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    if (userSnapshot.child("name").getValue(String.class).equals(serviceName)){
                        userSnapshot.getRef().removeValue();
                        Toast.makeText(DeleteBranchService_Activity.this,"Removed Service", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}