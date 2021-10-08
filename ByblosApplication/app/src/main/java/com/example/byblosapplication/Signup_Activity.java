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
    public void onClick(View view){
        String firstName = findViewById(R.id.firstName).toString();
        String lastName = findViewById(R.id.lastName).toString();
        String email = findViewById(R.id.email).toString();
        String homeAddress = findViewById(R.id.homeAddress).toString();
        String age = findViewById(R.id.age).toString();
        String dateOfBirth = findViewById(R.id.dateOfBirth).toString();
        String username = findViewById(R.id.username).toString();
        String password = findViewById(R.id.password).toString();

        RadioButton customerBtn = (RadioButton) findViewById(R.id.customer);
        RadioButton employeeBtn = (RadioButton) findViewById(R.id.employee);
        if (customerBtn.isChecked()){
            Customer customer = new Customer(firstName,lastName,dateOfBirth,homeAddress,email,Integer.parseInt(age),username,password);
            Register(customer);
          //  startActivity(new Intent(Signup_Activity.this, MainActivity.class));
        }
        else if (employeeBtn.isChecked()){
            startActivity(new Intent(Signup_Activity.this, EmployeeNumber_Activity.class));
        }
    }

    public void Register(Person p){
        FirebaseDatabase dataBase = FirebaseDatabase.getInstance();
        DatabaseReference newUser = dataBase.getReference("users/" + p);
        newUser.setValue(p);
    }
}