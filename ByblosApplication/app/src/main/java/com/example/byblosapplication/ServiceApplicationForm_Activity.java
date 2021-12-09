package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class ServiceApplicationForm_Activity extends AppCompatActivity {
    DatabaseReference databaseServices;
    EditText firstNameEditText;
    EditText lastNameEditText;
    EditText ageEditText;
    EditText homeAddressEditText;
    EditText emailEditText;
    EditText preferredCarTypeEditText;
    EditText areaOfUseEditText;
    EditText startLocationEditText;
    EditText destinationEditText;
    EditText numberOfMoversEditText;
    EditText numberOfItemsEditText;
    TableLayout pickupDate;
    TableLayout returnDate;
    Spinner license;
    Spinner yearPickupSpinner;
    Spinner monthPickupSpinner;
    Spinner dayPickupSpinner;
    Spinner yearReturnSpinner;
    Spinner monthReturnSpinner;
    Spinner dayReturnSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_application_form);
        databaseServices = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("services");
        Bundle extras = getIntent().getExtras();
        String serviceName = extras.getString("serviceName");

        firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        ageEditText = (EditText) findViewById(R.id.ageEditText);
        homeAddressEditText = (EditText) findViewById(R.id.homeAddressEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);
        preferredCarTypeEditText = (EditText) findViewById(R.id.preferredCarTypeEditText);
        areaOfUseEditText = (EditText) findViewById(R.id.areaOfUseEditText);
        startLocationEditText = (EditText) findViewById(R.id.startLocationEditText);
        destinationEditText = (EditText) findViewById(R.id.destinationEditText);
        numberOfMoversEditText = (EditText) findViewById(R.id.numberOfMoversEditText);
        numberOfItemsEditText = (EditText) findViewById(R.id.numberOfItemsEditText);

        pickupDate = (TableLayout)findViewById(R.id.pickupLayout);
        returnDate = (TableLayout)findViewById(R.id.returnLayout);

        yearPickupSpinner = ((Spinner) findViewById(R.id.pickupYearSpinner));
        monthPickupSpinner = ((Spinner) findViewById(R.id.pickupMonthSpinner));
        dayPickupSpinner = ((Spinner) findViewById(R.id.pickupDaySpinner));
        yearReturnSpinner = ((Spinner) findViewById(R.id.returnYearSpinner));
        monthReturnSpinner = ((Spinner) findViewById(R.id.returnMonthSpinner));
        dayReturnSpinner = ((Spinner) findViewById(R.id.returnDaySpinner));

        license = (Spinner)findViewById(R.id.licenseSpinner);
        ArrayList<String> licenses = new ArrayList<String>();
        licenses.add("G1");
        licenses.add("G2");
        licenses.add("G");

        ArrayAdapter<String> licenseAdapter = new ArrayAdapter<String>(ServiceApplicationForm_Activity.this, android.R.layout.simple_spinner_item, licenses);
        licenseAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        license.setAdapter(licenseAdapter);

        ArrayList<Integer> years = new ArrayList<Integer>();
        ArrayList<Integer> months= new ArrayList<Integer>();
        ArrayList<Integer> days= new ArrayList<Integer>();
        for (int i = 2021; i <= 2022; i++){
            years.add(i);
        }

        for (int i = 1; i <= 12; i++){
            months.add(i);
        }

        for (int i = 1; i <= 31; i++)
            days.add(i);

        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<Integer>(ServiceApplicationForm_Activity.this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        yearPickupSpinner.setAdapter(yearAdapter);
        yearReturnSpinner.setAdapter(yearAdapter);

        ArrayAdapter<Integer> monthAdapter = new ArrayAdapter<Integer>(ServiceApplicationForm_Activity.this, android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        monthPickupSpinner.setAdapter(monthAdapter);
        monthReturnSpinner.setAdapter(monthAdapter);

        ArrayAdapter<Integer> dayAdapter = new ArrayAdapter<Integer>(ServiceApplicationForm_Activity.this, android.R.layout.simple_spinner_item, days);
        dayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        dayPickupSpinner.setAdapter(dayAdapter);
        dayReturnSpinner.setAdapter(dayAdapter);


        databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Service theService;
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    if (userSnapshot.child("name").getValue(String.class).equals(serviceName)){
                        String id = userSnapshot.child("id").getValue(String.class);
                        int servicePrice = userSnapshot.child("servicePrice").getValue(Integer.class);
                        ArrayList<String> listOfRequirements = new ArrayList<String>();
                        for (DataSnapshot requirements:userSnapshot.child("listOfRequirements").getChildren()){
                            listOfRequirements.add(requirements.getValue(String.class));
                            if (requirements.getValue(String.class).equals("License")){
                                license.setVisibility(View.VISIBLE);
                                ((TextView)findViewById(R.id.textView26)).setVisibility(View.VISIBLE);
                            }
                            if (requirements.getValue(String.class).equals("First Name"))
                                firstNameEditText.setVisibility(View.VISIBLE);

                            if (requirements.getValue(String.class).equals("Last Name"))
                                lastNameEditText.setVisibility(View.VISIBLE);
                            if (requirements.getValue(String.class).equals("Age"))
                                ageEditText.setVisibility(View.VISIBLE);
                            if (requirements.getValue(String.class).equals("Home Address"))
                                homeAddressEditText.setVisibility(View.VISIBLE);
                            if (requirements.getValue(String.class).equals("Email"))
                                emailEditText.setVisibility(View.VISIBLE);
                            if (requirements.getValue(String.class).equals("Preferred Car Type"))
                                preferredCarTypeEditText.setVisibility(View.VISIBLE);
                            if (requirements.getValue(String.class).equals("Pickup Date")){
                                pickupDate.setVisibility(View.VISIBLE);
                                ((TextView)findViewById(R.id.textView28)).setVisibility(View.VISIBLE);
                            }
                            if (requirements.getValue(String.class).equals("Return Date")) {
                                returnDate.setVisibility(View.VISIBLE);
                                ((TextView)findViewById(R.id.textView29)).setVisibility(View.VISIBLE);
                            }
                            if (requirements.getValue(String.class).equals("Area of Use"))
                                areaOfUseEditText.setVisibility(View.VISIBLE);
                            if (requirements.getValue(String.class).equals("Start Location"))
                                startLocationEditText.setVisibility(View.VISIBLE);
                            if (requirements.getValue(String.class).equals("Destination"))
                                destinationEditText.setVisibility(View.VISIBLE);
                            if (requirements.getValue(String.class).equals("Number of Movers"))
                                numberOfMoversEditText.setVisibility(View.VISIBLE);
                            if (requirements.getValue(String.class).equals("Number of Items"))
                                numberOfItemsEditText.setVisibility(View.VISIBLE);
                        }
                        theService = new Service(serviceName,id,servicePrice,listOfRequirements);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void submitRequest(View view){
        ArrayList<String> customerFields = new ArrayList<String>();
        ArrayList<String> requirements = new ArrayList<String>();
        if (firstNameEditText.isShown()){
            customerFields.add(firstNameEditText.getText().toString());
            requirements.add("First Name");
        }
        if (lastNameEditText.getVisibility() == View.VISIBLE){
            customerFields.add(lastNameEditText.getText().toString());
            requirements.add("Last Name");
        }
        if (ageEditText.getVisibility() == View.VISIBLE){
            customerFields.add(ageEditText.getText().toString());
            requirements.add("Age");
        }
        if (homeAddressEditText.getVisibility() == View.VISIBLE){
            customerFields.add(homeAddressEditText.getText().toString());
            requirements.add("Home Address");
        }
        if (emailEditText.getVisibility() == View.VISIBLE){
            customerFields.add(emailEditText.getText().toString());
            requirements.add("Email");
        }
        if (license.getVisibility() == View.VISIBLE){
            customerFields.add(license.getSelectedItem().toString());
            requirements.add("License");
        }
        if (preferredCarTypeEditText.getVisibility() == View.VISIBLE){
            customerFields.add(preferredCarTypeEditText.getText().toString());
            requirements.add("Preferred Car Type");
        }
        if (pickupDate.getVisibility() == View.VISIBLE) {
            String pickupDateS = (yearPickupSpinner.getSelectedItem().toString() + "/" + monthPickupSpinner.getSelectedItem().toString() + "/" + dayPickupSpinner.getSelectedItem().toString());
            customerFields.add(pickupDateS);
            requirements.add("Pickup Date");
        }
        if (returnDate.getVisibility() == View.VISIBLE){
            String returnDateS = (yearReturnSpinner.getSelectedItem().toString() + "/" + monthReturnSpinner.getSelectedItem().toString() + "/" + dayReturnSpinner.getSelectedItem().toString());
            customerFields.add(returnDateS);
            requirements.add("Return Date");
        }
        if (areaOfUseEditText.getVisibility() == View.VISIBLE){
            customerFields.add(areaOfUseEditText.getText().toString());
            requirements.add("Area of Use");
        }
        if (startLocationEditText.getVisibility() == View.VISIBLE){
            customerFields.add(startLocationEditText.getText().toString());
            requirements.add("Start Location");
        }
        if (destinationEditText.getVisibility() == View.VISIBLE){
            customerFields.add(destinationEditText.getText().toString());
            requirements.add("Destination");
        }
        if (numberOfMoversEditText.getVisibility() == View.VISIBLE){
            customerFields.add(numberOfMoversEditText.getText().toString());
            requirements.add("Number of Movers");
        }
        if (numberOfItemsEditText.getVisibility() == View.VISIBLE){
            customerFields.add(numberOfItemsEditText.getText().toString());
            requirements.add("Number of Items");
        }

        Bundle extras = getIntent().getExtras();
        String customerName = extras.getString("customerName");
        String branchId = extras.getString("branchId");
        DatabaseReference serviceRequest = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("branches/"+branchId + "/requests");
        String id = serviceRequest.push().getKey();
        String serviceName = extras.getString("serviceName");
        serviceRequest.child(id).child("requestType").setValue(serviceName);
        serviceRequest.child(id).child("customerName").setValue(customerName);
        serviceRequest.child(id).child("listOfRequirements").setValue(customerFields);
        serviceRequest.child(id).child("requirementNames").setValue(requirements);
        Toast.makeText(ServiceApplicationForm_Activity.this,"Submitted Service Request", Toast.LENGTH_SHORT).show();
    }
}