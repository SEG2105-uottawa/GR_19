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
        firstName = findViewById(R.id.firstName).toString();
        lastName = findViewById(R.id.lastName).toString();
        email = findViewById(R.id.email).toString();
        homeAddress = findViewById(R.id.homeAddress).toString();
        age = findViewById(R.id.age).toString();
        dateOfBirth = findViewById(R.id.dateOfBirth).toString();
        username = findViewById(R.id.username).toString();
        password = findViewById(R.id.password).toString();

        RadioButton customerBtn = (RadioButton) findViewById(R.id.customer);
        RadioButton employeeBtn = (RadioButton) findViewById(R.id.employee);

        if (customerBtn.isChecked()){
            Customer customer = new Customer(firstName,lastName,dateOfBirth,homeAddress,email,Integer.parseInt(age),username,password);
        }
        else if (employeeBtn.isChecked()){
            startActivity(new Intent(Signup_Activity.this, EmployeeNumber_Activity.class));
        }
    }
}