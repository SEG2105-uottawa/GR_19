package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class EditBranch_Activity extends AppCompatActivity {
    DatabaseReference databaseBranches;
    String branchID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_branch);
        Bundle extras = getIntent().getExtras();
        String branchAddress = extras.getString("address");
        databaseBranches = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("branches");
        branchID = "";

        Spinner startTime = ((Spinner) findViewById(R.id.startSpinner));
        Spinner endTime = ((Spinner) findViewById(R.id.endSpinner));
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
        ArrayAdapter<String> hourAdapter = new ArrayAdapter<String>(EditBranch_Activity.this, android.R.layout.simple_spinner_item, hours);
        hourAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        startTime.setAdapter(hourAdapter);
        endTime.setAdapter(hourAdapter);

        //Get the branchID
        databaseBranches.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    if (userSnapshot.child("address").getValue().equals(branchAddress)){
                        branchID = userSnapshot.child("id").getValue(String.class);
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void UpdateBranch(View view){
        String startTime = ((Spinner) findViewById(R.id.startSpinner)).getSelectedItem().toString();
        String endTime = ((Spinner) findViewById(R.id.endSpinner)).getSelectedItem().toString();
        databaseBranches.child(branchID + "/workingHours/0").setValue(startTime);
        databaseBranches.child(branchID + "/workingHours/1").setValue(endTime);
        Toast.makeText(EditBranch_Activity.this,"Updated Branch", Toast.LENGTH_SHORT).show();
    }
}