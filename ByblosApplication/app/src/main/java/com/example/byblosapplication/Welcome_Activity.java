package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.*;

import com.google.firebase.database.*;

public class Welcome_Activity extends AppCompatActivity {
    DatabaseReference databaseAccounts;
    Person loggedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        databaseAccounts = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("accounts");

        //Set the loggedUser
        Bundle extras = getIntent().getExtras();

        if (extras.getString("id").equals("admin")){
            loggedUser = new Admin("admin","admin","admin","admin");
            Toast.makeText(Welcome_Activity.this,"Welcome " + loggedUser.username + "! " + "You are logged in as a \"" + loggedUser.role +"\"", Toast.LENGTH_SHORT).show();
        }

        databaseAccounts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    String id = userSnapshot.child("id").getValue(String.class);
                    if (id.equals(extras.getString("id"))){
                        String firstName = userSnapshot.child("firstName").getValue(String.class);
                        String lastName = userSnapshot.child("lastName").getValue(String.class);
                        String dateOfBirth = userSnapshot.child("dateOfBirth").getValue(String.class);
                        String homeAddress = userSnapshot.child("homeAddress").getValue(String.class);
                        String emailAddress = userSnapshot.child("emailAddress").getValue(String.class);
                        int age = userSnapshot.child("age").getValue(Integer.class);
                        String username = userSnapshot.child("username").getValue(String.class);
                        String password = userSnapshot.child("password").getValue(String.class);
                        String role = userSnapshot.child("role").getValue(String.class);
                        if (role.equals("customer")){
                            loggedUser = new Customer(firstName,lastName,dateOfBirth,homeAddress,emailAddress,age,username,password,id,role);
                            Toast.makeText(Welcome_Activity.this,"Welcome " + loggedUser.username + "! " + "You are logged in as a \"" + loggedUser.role +"\"", Toast.LENGTH_SHORT).show();
                        }else if(role.equals("employee")){
                            int employeeNumber = userSnapshot.child("employeeID").getValue(Integer.class);
                            loggedUser = new Employee(firstName,lastName,dateOfBirth,homeAddress,emailAddress,age,username,password,id,role,employeeNumber);
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