package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class DeleteService_Activity extends AppCompatActivity {
    DatabaseReference databaseServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_service);
        databaseServices = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("services");

        Spinner serviceSpinner = (Spinner) findViewById(R.id.servicesSpinner);
        ArrayList<String> serviceList = new ArrayList<String>();
        databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    String name = userSnapshot.child("name").getValue(String.class);
                    if (name != null)
                        serviceList.add(name);
                }
                ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(DeleteService_Activity.this, android.R.layout.simple_spinner_item, serviceList);
                serviceAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                serviceSpinner.setAdapter(serviceAdapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void DeleteService(View view){
        String serviceName = ((Spinner)findViewById(R.id.servicesSpinner)).getSelectedItem().toString();
        databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()){
                    if (userSnapshot.child("name").getValue(String.class).equals(serviceName)){
                        userSnapshot.getRef().removeValue();
                        Toast.makeText(DeleteService_Activity.this,"Deleted Service", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}