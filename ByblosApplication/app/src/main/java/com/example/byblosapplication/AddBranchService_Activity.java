package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class AddBranchService_Activity extends AppCompatActivity {
    DatabaseReference databaseServices;
    public String branchAddress;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_branch_service);

        databaseServices= FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("services");
        Bundle extras = getIntent().getExtras();
        branchAddress = extras.getString("address");


        //Add services to spinner
        databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<String> serviceList = new ArrayList<String>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    String name = userSnapshot.child("name").getValue(String.class);
                    if (name != null)
                        serviceList.add(name);
                }
                Spinner serviceSpinner = (Spinner) findViewById(R.id.serviceSpinner);
                ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(AddBranchService_Activity.this, android.R.layout.simple_spinner_item, serviceList);
                serviceAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                serviceSpinner.setAdapter(serviceAdapter);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void AddBranchService(View view){

        databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Service service;
                String serviceName = ((Spinner)findViewById(R.id.serviceSpinner)).getSelectedItem().toString();
                id = "";
                int servicePrice = 0;
                String name = "";
                ArrayList<String> listOfRequirements = new ArrayList<String>();

                //Get service
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    String snapShotName = userSnapshot.child("name").getValue(String.class);
                    if (snapShotName.equals(serviceName)){
                        name =  userSnapshot.child("name").getValue(String.class);
                        id = userSnapshot.child("id").getValue(String.class);
                        servicePrice = userSnapshot.child("servicePrice").getValue(int.class);

                    }
                }

                service = new Service(name,id,servicePrice,listOfRequirements);

                DatabaseReference databaseBranches= FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("branches");
                databaseBranches.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot userSnapshot: snapshot.getChildren()){
                            System.out.println("BranchAddress = " + userSnapshot.child("address"));
                            System.out.println("EmployeeBranchAddress = " + branchAddress);

                            if (userSnapshot.child("address").getValue().equals(branchAddress)){
                                String branchID = userSnapshot.child("id").getValue(String.class);
                                databaseBranches.child(branchID).child("services").child(id).setValue(service);
                                Toast.makeText(AddBranchService_Activity.this,"Service Added", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
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
}