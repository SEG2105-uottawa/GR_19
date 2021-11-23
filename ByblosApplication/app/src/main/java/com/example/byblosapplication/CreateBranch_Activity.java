package com.example.byblosapplication;

import androidx.appcompat.app.AppCompatActivity;

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
    }

    public void CreateBranch(View view){
        String branchAddress = ((EditText) findViewById(R.id.editTextBranchAddress)).getText().toString().trim();
        String startTime = ((EditText) findViewById(R.id.editTextStartTime)).getText().toString().trim();
        String endTime = ((EditText) findViewById(R.id.editTextEndTime)).getText().toString().trim();
        String phoneNumber = ((EditText) findViewById(R.id.editTextPhoneNumber)).getText().toString().trim();

        if (branchAddress.equals("") || startTime.equals("") || endTime.equals("") || phoneNumber.equals(""))
            Toast.makeText(CreateBranch_Activity.this,"Missing Parameters", Toast.LENGTH_SHORT).show();
        else{
            String id = databaseBranches.push().getKey();
            ArrayList<String> workingHours = new ArrayList<String>();
            workingHours.add(0,startTime);
            workingHours.add(1,endTime);
            Branch branch = new Branch(branchAddress,workingHours, phoneNumber,id);
            databaseBranches.child(id).setValue(branch);
            Toast.makeText(CreateBranch_Activity.this,"Created Branch", Toast.LENGTH_SHORT).show();
        }
    }
}