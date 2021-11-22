package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.*;

public class Login_Activity extends AppCompatActivity {

    DatabaseReference databaseAccounts;
    EditText editTextUsernameSN;
    EditText editTextPasswordSN;
    Button signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseAccounts = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("accounts");

        editTextUsernameSN = findViewById(R.id.editTextUsernameSN);
        editTextPasswordSN = findViewById(R.id.editTextPasswordSN);

        signIn = (findViewById(R.id.signin));
    }

    public void Login(View view){
        if (editTextUsernameSN.getText().toString().trim().equals("admin") && editTextPasswordSN.getText().toString().trim().equals("admin")){
            Intent user = new Intent(Login_Activity.this, Welcome_Activity.class);
            user.putExtra("id", "admin");
            startActivity(user);
        }else{
            databaseAccounts.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot userSnapshot: snapshot.getChildren()){
                        String username = userSnapshot.child("username").getValue(String.class);
                        String password = userSnapshot.child("password").getValue(String.class);
                        if (username.equals(editTextUsernameSN.getText().toString().trim()) && password.equals(editTextPasswordSN.getText().toString().trim())){
                            Intent user = new Intent(Login_Activity.this, Welcome_Activity.class);
                            user.putExtra("id", userSnapshot.child("id").getValue(String.class));
                            user.putExtra("firstName",userSnapshot.child("firstName").getValue(String.class));
                            user.putExtra("lastName",userSnapshot.child("lastName").getValue(String.class));
                            user.putExtra("dateOfBirth",userSnapshot.child("dateOfBirth").getValue(String.class));
                            user.putExtra("homeAddress",userSnapshot.child("homeAddress").getValue(String.class));
                            user.putExtra("emailAddress",userSnapshot.child("emailAddress").getValue(String.class));
                            user.putExtra("age",userSnapshot.child("age").getValue(Integer.class));
                            user.putExtra("username",userSnapshot.child("username").getValue(String.class));
                            user.putExtra("password",userSnapshot.child("password").getValue(String.class));
                            user.putExtra("role",userSnapshot.child("role").getValue(String.class));
                            if (userSnapshot.child("role").getValue(String.class).equals("employee")){
                                user.putExtra("employeeID",userSnapshot.child("employeeID").getValue(Integer.class));
                                user.putExtra("branchNumber",userSnapshot.child("branchNumber").getValue(Integer.class));
                            }
                            startActivity(user);

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}