package com.example.byblosapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import com.google.firebase.database.*;

public class Signup_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }
    String firstName;
    String lastName;
    String email;
    String homeAddress;
    String age;
    String dateOfBirth;
    String username;
    String password;

    public void onClick(View view){
        firstName = findViewById(R.id.firstName).toString().trim();
        lastName = findViewById(R.id.lastName).toString().trim();
        email = findViewById(R.id.email).toString().trim();
        homeAddress = findViewById(R.id.homeAddress).toString().trim();
        age = findViewById(R.id.age).toString().trim();
        dateOfBirth = findViewById(R.id.dateOfBirth).toString().trim();
        username = findViewById(R.id.username).toString().trim();
        password = findViewById(R.id.password).toString().trim();

        RadioButton customerBtn = (RadioButton) findViewById(R.id.customer);
        RadioButton employeeBtn = (RadioButton) findViewById(R.id.employee);

        if (customerBtn.isChecked()){
            Customer customer = new Customer(firstName,lastName,dateOfBirth,homeAddress,email,age,username,password);
            startActivity(new Intent(Signup_Activity.this,Login_Activity.class));
        }
        else if (employeeBtn.isChecked()){
            startActivity(new Intent(Signup_Activity.this, EmployeeNumber_Activity.class));
        }
    }
}