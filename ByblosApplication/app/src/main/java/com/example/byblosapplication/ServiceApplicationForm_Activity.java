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

        EditText firstNameEditText = (EditText) findViewById(R.id.firstNameEditText);
        EditText lastNameEditText = (EditText) findViewById(R.id.lastNameEditText);
        EditText ageEditText = (EditText) findViewById(R.id.ageEditText);
        EditText homeAddressEditText = (EditText) findViewById(R.id.homeAddressEditText);
        EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        EditText preferredCarTypeEditText = (EditText) findViewById(R.id.preferredCarTypeEditText);
        EditText areaOfUseEditText = (EditText) findViewById(R.id.areaOfUseEditText);
        EditText startLocationEditText = (EditText) findViewById(R.id.startLocationEditText);
        EditText destinationEditText = (EditText) findViewById(R.id.destinationEditText);
        EditText numberOfMoversEditText = (EditText) findViewById(R.id.numberOfMoversEditText);
        EditText numberOfItemsEditText = (EditText) findViewById(R.id.numberOfItemsEditText);

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
        if (firstNameEditText.isShown())
            customerFields.add(firstNameEditText.getText().toString());
        if (lastNameEditText.isShown())
            customerFields.add(lastNameEditText.getText().toString());
        if (ageEditText.isShown())
            customerFields.add(ageEditText.getText().toString());
        if (homeAddressEditText.isShown())
            customerFields.add(homeAddressEditText.getText().toString());
        if (emailEditText.isShown())
            customerFields.add(emailEditText.getText().toString());
        if (license.isShown())
            customerFields.add(license.getSelectedItem().toString());
        if (preferredCarTypeEditText.isShown())
            customerFields.add(preferredCarTypeEditText.getText().toString());
        if (pickupDate.isShown()) {
            String pickupDateS = (yearPickupSpinner.getSelectedItem().toString() + "/" + monthPickupSpinner.getSelectedItem().toString() + "/" + dayPickupSpinner.getSelectedItem().toString());
            customerFields.add(pickupDateS);
        }
        if (returnDate.isShown()){
            String returnDateS = (yearReturnSpinner.getSelectedItem().toString() + "/" + monthReturnSpinner.getSelectedItem().toString() + "/" + dayReturnSpinner.getSelectedItem().toString());
            customerFields.add(returnDateS);
        }
        if (areaOfUseEditText.isShown())
            customerFields.add(areaOfUseEditText.getText().toString());
        if (startLocationEditText.isShown())
            customerFields.add(startLocationEditText.getText().toString());
        if (destinationEditText.isShown())
            customerFields.add(destinationEditText.getText().toString());
        if (numberOfMoversEditText.isShown())
            customerFields.add(numberOfMoversEditText.getText().toString());
        if (numberOfItemsEditText.isShown())
            customerFields.add(numberOfItemsEditText.getText().toString());

        DatabaseReference serviceRequest = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("requests");
        String id = serviceRequest.push().getKey();
        serviceRequest.child(id).setValue(customerFields);
    }
}