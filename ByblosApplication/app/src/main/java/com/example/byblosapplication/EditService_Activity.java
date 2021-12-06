package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class EditService_Activity extends AppCompatActivity {
    DatabaseReference databaseServices;
    public CheckBox licenseTypeEditCB;
    public CheckBox firstNameEditCB;
    public CheckBox lastNameEditCB;
    public CheckBox ageEditCB;
    public CheckBox homeAddressEditCB;
    public CheckBox emailEditCB;
    public CheckBox preferredCarTypeEditCB;
    public CheckBox pickupDateEditCB;
    public CheckBox returnDateEditCB;
    public CheckBox maxKmsEditCB;
    public CheckBox areaOfUseEditCB;
    public CheckBox startLocationEditCB;
    public CheckBox destinationEditCB;
    public CheckBox numberOfMoversEditCB;
    public CheckBox numberOfItemsEditCB;
    public String serviceID;
    public String serviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);
        databaseServices = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("services");
        licenseTypeEditCB = (CheckBox)findViewById(R.id.licenseEditCB);
        firstNameEditCB = (CheckBox)findViewById(R.id.firstNameEditCB);
        lastNameEditCB = (CheckBox)findViewById(R.id.lastNameEditCB);
        ageEditCB = (CheckBox)findViewById(R.id.ageEditCB);
        homeAddressEditCB = (CheckBox)findViewById(R.id.homeAddressEditCB);
        emailEditCB = (CheckBox)findViewById(R.id.emailEditCB);
        preferredCarTypeEditCB = (CheckBox)findViewById(R.id.preferredCarTypeEditCB);
        pickupDateEditCB= (CheckBox)findViewById(R.id.pickupDateEditCB);
        returnDateEditCB = (CheckBox)findViewById(R.id.returnDateEditCB);
        maxKmsEditCB = (CheckBox)findViewById(R.id.maximumNumOfKmsEditCB);
        areaOfUseEditCB = (CheckBox)findViewById(R.id.areaOfUseEditCB);
        startLocationEditCB = (CheckBox)findViewById(R.id.startLocationEditCB);
        destinationEditCB = (CheckBox)findViewById(R.id.destinationEditCB);
        numberOfMoversEditCB = (CheckBox)findViewById(R.id.numOfMoversEditCB);
        numberOfItemsEditCB = (CheckBox)findViewById(R.id.numOfItemsEditCB);

        Spinner serviceSpinner = (Spinner) findViewById(R.id.editServiceSpinner);
        ArrayList<String> serviceList = new ArrayList<String>();
         //Populate the spinner with services
        databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    String name = userSnapshot.child("name").getValue(String.class);
                    if (name != null)
                        serviceList.add(name);
                }
                ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(EditService_Activity.this, android.R.layout.simple_spinner_item, serviceList);
                serviceAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                serviceSpinner.setAdapter(serviceAdapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    licenseTypeEditCB.setChecked(false);
                    firstNameEditCB.setChecked(false);
                    lastNameEditCB.setChecked(false);
                    ageEditCB.setChecked(false);
                    homeAddressEditCB.setChecked(false);
                    emailEditCB.setChecked(false);
                    preferredCarTypeEditCB.setChecked(false);
                    pickupDateEditCB.setChecked(false);
                    returnDateEditCB.setChecked(false);
                    maxKmsEditCB.setChecked(false);
                    areaOfUseEditCB.setChecked(false);
                    startLocationEditCB.setChecked(false);
                    destinationEditCB.setChecked(false);
                    numberOfMoversEditCB.setChecked(false);
                    numberOfItemsEditCB.setChecked(false);
                databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        serviceID = "";
                        ArrayList<String> requirementList = new ArrayList<String>();
                        for (DataSnapshot userSnapshot: snapshot.getChildren()){
                            if (userSnapshot.child("name").getValue(String.class).equals(serviceSpinner.getSelectedItem().toString())){
                                serviceID = userSnapshot.child("id").getValue(String.class);
                                serviceName = userSnapshot.child("name").getValue(String.class);
                                EditText price = (EditText)findViewById(R.id.priceEditTV);
                                if (userSnapshot.child("servicePrice").getValue(Integer.class) != null)
                                   price.setText(userSnapshot.child("servicePrice").getValue(Integer.class).toString());
                            }
                        }
                        for (DataSnapshot userSnapshot: snapshot.child(serviceID).child("listOfRequirements").getChildren()){
                            requirementList.add(userSnapshot.getValue(String.class));
                        }
                        for (int i = 0; i < requirementList.size();i++) {
                            if (requirementList.get(i) != null) {
                                if (requirementList.get(i).equals("License"))
                                    licenseTypeEditCB.setChecked(true);
                                if (requirementList.get(i).equals("First Name"))
                                    firstNameEditCB.setChecked(true);
                                if (requirementList.get(i).equals("Last Name"))
                                    lastNameEditCB.setChecked(true);
                                if (requirementList.get(i).equals("Age"))
                                    ageEditCB.setChecked(true);
                                if (requirementList.get(i).equals("Home Address"))
                                    homeAddressEditCB.setChecked(true);
                                if (requirementList.get(i).equals("Email"))
                                    emailEditCB.setChecked(true);
                                if (requirementList.get(i).equals("Preferred Car Type"))
                                    preferredCarTypeEditCB.setChecked(true);
                                if (requirementList.get(i).equals("Pickup Date"))
                                    pickupDateEditCB.setChecked(true);
                                if (requirementList.get(i).equals("Return Date"))
                                    returnDateEditCB.setChecked(true);
                                if (requirementList.get(i).equals("Maximum Number of Kilometers"))
                                    maxKmsEditCB.setChecked(true);
                                if (requirementList.get(i).equals("Area of Use"))
                                    areaOfUseEditCB.setChecked(true);
                                if (requirementList.get(i).equals("Start Location"))
                                    startLocationEditCB.setChecked(true);
                                if (requirementList.get(i).equals("Destination"))
                                    destinationEditCB.setChecked(true);
                                if (requirementList.get(i).equals("Number of Movers"))
                                    numberOfMoversEditCB.setChecked(true);
                                if (requirementList.get(i).equals("Number of Items"))
                                    numberOfItemsEditCB.setChecked(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void EditService(View view){
        ArrayList<String> listOfRequirements = new ArrayList<String>();

        if (licenseTypeEditCB.isChecked())
            listOfRequirements.add("License");

        if (firstNameEditCB.isChecked())
            listOfRequirements.add("First Name");

        if (lastNameEditCB.isChecked())
            listOfRequirements.add("Last Name");

        if (ageEditCB.isChecked())
            listOfRequirements.add("Age");

        if (homeAddressEditCB.isChecked())
            listOfRequirements.add("Home Address");

        if (emailEditCB.isChecked())
            listOfRequirements.add("Email");

        if (preferredCarTypeEditCB.isChecked())
            listOfRequirements.add("Preferred Car Type");

        if (pickupDateEditCB.isChecked())
            listOfRequirements.add("Pickup Date");

        if (returnDateEditCB.isChecked())
            listOfRequirements.add("Return Date");

        if (maxKmsEditCB.isChecked())
            listOfRequirements.add("Maximum Number of Kilometers");

        if (areaOfUseEditCB.isChecked())
            listOfRequirements.add("Area of Use");


        if (startLocationEditCB.isChecked())
            listOfRequirements.add("Start Location");


        if (destinationEditCB.isChecked())
            listOfRequirements.add("Destination");


        if (numberOfMoversEditCB.isChecked())
            listOfRequirements.add("Number of Movers");


        if (numberOfItemsEditCB.isChecked())
            listOfRequirements.add("Number of Items");
        DatabaseReference serviceRef = databaseServices.child(serviceID).child("name");
        int servicePrice = Integer.parseInt(((EditText)findViewById(R.id.priceEditTV)).getText().toString().trim());
        Service service = new Service(serviceName, serviceID, servicePrice,listOfRequirements);
        serviceRef.removeValue();
        databaseServices.child(serviceID).setValue(service);
    }
}