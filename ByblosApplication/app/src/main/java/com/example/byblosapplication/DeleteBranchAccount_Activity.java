package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class DeleteBranchAccount_Activity extends AppCompatActivity {
    DatabaseReference databaseAccounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_branch_account);
        databaseAccounts = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("accounts");

        Spinner branchSpinner = (Spinner) findViewById(R.id.branchSpinner);
        ArrayList<String> branchList = new ArrayList<String>();
        databaseAccounts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    if (userSnapshot.child("role").getValue(String.class).equals("employee"))
                        branchList.add(userSnapshot.child("username").getValue(String.class));
                }
                ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(DeleteBranchAccount_Activity.this, android.R.layout.simple_spinner_item, branchList);
                serviceAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                branchSpinner.setAdapter(serviceAdapter);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void DeleteAccount(View view){

        databaseAccounts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = ((Spinner)findViewById(R.id.branchSpinner)).getSelectedItem().toString();
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    if (userSnapshot.child("username").getValue(String.class).equals(name)){
                        userSnapshot.getRef().removeValue();
                        Toast.makeText(DeleteBranchAccount_Activity.this,"Deleted Branch Account", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}