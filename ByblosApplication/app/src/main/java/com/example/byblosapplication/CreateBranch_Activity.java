package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class CreateBranch_Activity extends AppCompatActivity {
    DatabaseReference databaseBranches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_branch);
        databaseBranches = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("branches");

        Spinner startTime = ((Spinner) findViewById(R.id.spinnerStartTime));
        Spinner endTime = ((Spinner) findViewById(R.id.spinnerEndTime));
        ArrayList<String> hours = new ArrayList<String>();
        hours.add("00:00");
        hours.add("01:00");
        hours.add("02:00");
        hours.add("03:00");
        hours.add("04:00");
        hours.add("05:00");
        hours.add("06:00");
        hours.add("07:00");
        hours.add("08:00");
        hours.add("09:00");
        hours.add("10:00");
        hours.add("11:00");
        hours.add("12:00");
        hours.add("13:00");
        hours.add("14:00");
        hours.add("15:00");
        hours.add("16:00");
        hours.add("17:00");
        hours.add("18:00");
        hours.add("19:00");
        hours.add("20:00");
        hours.add("21:00");
        hours.add("22:00");
        hours.add("23:00");

        ArrayAdapter<String> hourAdapter = new ArrayAdapter<String>(CreateBranch_Activity.this, android.R.layout.simple_spinner_item, hours);
        hourAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        startTime.setAdapter(hourAdapter);
        endTime.setAdapter(hourAdapter);
    }

    public void CreateBranch(View view){
        String branchAddress = ((EditText) findViewById(R.id.editTextBranchAddress)).getText().toString().trim();
        String startTime = ((Spinner) findViewById(R.id.spinnerStartTime)).getSelectedItem().toString();
        String endTime = ((Spinner) findViewById(R.id.spinnerEndTime)).getSelectedItem().toString();
        String phoneNumber = ((EditText) findViewById(R.id.editTextPhoneNumber)).getText().toString().trim();

        if (branchAddress.equals("") || startTime.equals("") || endTime.equals("") || phoneNumber.equals(""))
            Toast.makeText(CreateBranch_Activity.this,"Missing Parameters", Toast.LENGTH_SHORT).show();
        else if (!isNumeric(phoneNumber)){
            Toast.makeText(CreateBranch_Activity.this,"Not a valid phone number", Toast.LENGTH_SHORT).show();
        }
        else{
            String id = databaseBranches.push().getKey();
            ArrayList<String> workingHours = new ArrayList<String>();
            workingHours.add(0,startTime);
            workingHours.add(1,endTime);
            Branch branch = new Branch(branchAddress,workingHours, phoneNumber,id);
            databaseBranches.child(id).setValue(branch);

            //Update loggedUser's branch address
            Bundle extras = getIntent().getExtras();
            String userId = extras.getString("loggedUserId");
            DatabaseReference databaseAccount = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("accounts/" + userId);
            databaseAccount.child("branchAddress").setValue(branchAddress);

            Toast.makeText(CreateBranch_Activity.this,"Created Branch", Toast.LENGTH_SHORT).show();
        }
    }

    public static Boolean isNumeric(String string){
        try {
            long number = Long.parseLong(string);
            if (string.length() < 9 || string.length() > 12)
                return false;
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}