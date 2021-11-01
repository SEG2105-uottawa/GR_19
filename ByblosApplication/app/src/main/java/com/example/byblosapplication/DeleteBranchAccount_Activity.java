package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

public class DeleteBranchAccount_Activity extends AppCompatActivity {
    DatabaseReference databaseAccounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_branch_account);
        databaseAccounts = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("accounts");
    }

    public void DeleteAccount(View view){
        int employeeNum = Integer.parseInt(((EditText)findViewById(R.id.employeeID)).getText().toString());

        databaseAccounts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    System.out.println(userSnapshot.child("employeeID").getValue(int.class));
                    if (userSnapshot.child("role").getValue(String.class).equals("employee")){
                        if (userSnapshot.child("employeeID").getValue(int.class).equals(employeeNum)){
                            userSnapshot.getRef().removeValue();
                            Toast.makeText(DeleteBranchAccount_Activity.this,"Deleted Branch Account", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}