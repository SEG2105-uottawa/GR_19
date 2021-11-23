package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.*;

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
        String startTime = ((EditText) findViewById(R.id.editTextST)).getText().toString();
        String endTime = ((EditText) findViewById(R.id.editTextET)).getText().toString();
        databaseBranches.child(branchID + "/workingHours/0").setValue(startTime);
        databaseBranches.child(branchID + "/workingHours/1").setValue(endTime);
    }
}