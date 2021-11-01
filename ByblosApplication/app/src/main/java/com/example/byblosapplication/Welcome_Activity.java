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
        
        Bundle extras = getIntent().getExtras();
        //Set the loggedUser
        if (extras.getString("id").equals("admin")){
            loggedUser = new Admin("admin","admin","admin","admin");
        }else{
            String firstName = extras.getString("firstName");
            String lastName = extras.getString("lastName");
            String dateOfBirth = extras.getString("dateOfBirth");
            String homeAddress = extras.getString("homeAddress");
            String emailAddress = extras.getString("emailAddress");
            int age = extras.getInt("age");
            String username = extras.getString("username");
            String password = extras.getString("password");
            String id = extras.getString("id");
            String role = extras.getString("role");
            if (role.equals("customer")){
                loggedUser = new Customer(firstName,lastName,dateOfBirth,homeAddress,emailAddress,age,username,password,id,role);
            }else{
                int employeeID = extras.getInt("employeeId");
                loggedUser = new Employee(firstName,lastName,dateOfBirth,homeAddress,emailAddress,age,username,password,id,role,employeeID);
            }
        }
        Toast.makeText(Welcome_Activity.this,"Welcome " + loggedUser.username + "! " + "You are logged in as a \"" + loggedUser.role +"\"", Toast.LENGTH_SHORT).show();
    }
}