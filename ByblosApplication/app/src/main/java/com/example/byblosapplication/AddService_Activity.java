package com.example.byblosapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class AddService_Activity extends AppCompatActivity {
    DatabaseReference databaseServices;
    public String serviceName;
    public ArrayList<String> listOfRequirements;
    public int servicePrice;
    public CheckBox licenseTypeCB;
    public CheckBox firstNameCB;
    public CheckBox lastNameCB;
    public CheckBox ageCB;
    public CheckBox homeAddressCB;
    public CheckBox emailCB;
    public CheckBox preferredCarTypeCB;
    public CheckBox pickupDateCB;
    public CheckBox returnDateCB;
    public CheckBox maxKmsCB;
    public CheckBox areaOfUseCB;
    public CheckBox startLocationCB;
    public CheckBox destinationCB;
    public CheckBox numberOfMoversCB;
    public CheckBox numberOfItemsCB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        databaseServices = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("services");

        licenseTypeCB = (CheckBox)findViewById(R.id.licenseCheckBox);
        firstNameCB = (CheckBox)findViewById(R.id.firstNameCheckBox);
        lastNameCB = (CheckBox)findViewById(R.id.lastNameCheckBox);
        ageCB = (CheckBox)findViewById(R.id.ageCheckBox);
        homeAddressCB = (CheckBox)findViewById(R.id.homeAddressCheckBox);
        emailCB = (CheckBox)findViewById(R.id.emailCheckBox);
        preferredCarTypeCB = (CheckBox)findViewById(R.id.pCTCheckBox);
        pickupDateCB= (CheckBox)findViewById(R.id.pickupDateCheckBox);
        returnDateCB = (CheckBox)findViewById(R.id.returnDateCheckBox);
        maxKmsCB = (CheckBox)findViewById(R.id.maxKmsCheckBox);
        areaOfUseCB = (CheckBox)findViewById(R.id.areaOfUseCheckBox);
        startLocationCB = (CheckBox)findViewById(R.id.startLocationCheckBox);
        destinationCB = (CheckBox)findViewById(R.id.destinationCheckBox);
        numberOfMoversCB = (CheckBox)findViewById(R.id.nOMCheckBox);
        numberOfItemsCB = (CheckBox)findViewById(R.id.numberOfItemsCheckBox);

    }

    public void AddService(View view){
        serviceName = ((EditText)findViewById(R.id.serviceName)).getText().toString().trim();

        if (serviceName.equals("") || ((EditText)findViewById(R.id.editTextPrice)).getText().toString().equals("")){
            Toast.makeText(AddService_Activity.this,"Missing Parameters", Toast.LENGTH_SHORT).show();
        }else{
            servicePrice = Integer.parseInt(((EditText)findViewById(R.id.editTextPrice)).getText().toString().trim());

            listOfRequirements = new ArrayList<String>();

            if (licenseTypeCB.isChecked())
                listOfRequirements.add("License");

            if (firstNameCB.isChecked())
                listOfRequirements.add("First Name");

            if (lastNameCB.isChecked())
                listOfRequirements.add("Last Name");

            if (ageCB.isChecked())
                listOfRequirements.add("Age");

            if (homeAddressCB.isChecked())
                listOfRequirements.add("Home Address");

            if (emailCB.isChecked())
                listOfRequirements.add("Email");

            if (preferredCarTypeCB.isChecked())
                listOfRequirements.add("Preferred Car Type");

            if (pickupDateCB.isChecked())
                listOfRequirements.add("Pickup Date");

            if (returnDateCB.isChecked())
                listOfRequirements.add("Return Date");

            if (maxKmsCB.isChecked())
                listOfRequirements.add("Maximum Number of Kilometers");

            if (areaOfUseCB.isChecked())
                listOfRequirements.add("Area of Use");


            if (startLocationCB.isChecked())
                listOfRequirements.add("Start Location");


            if (destinationCB.isChecked())
                listOfRequirements.add("Destination");


            if (numberOfMoversCB.isChecked())
                listOfRequirements.add("Number of Movers");


            if (numberOfItemsCB.isChecked())
                listOfRequirements.add("Number of Items");



            String id = databaseServices.push().getKey();
            Service service = new Service(serviceName,id,servicePrice,listOfRequirements);
            databaseServices.child(id).setValue(service);
            Toast.makeText(AddService_Activity.this,"Added Service", Toast.LENGTH_SHORT).show();
        }
    }
}