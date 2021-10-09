package com.example.byblosapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class EmployeeNumber_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_number);
    }
    String first = Signup_Activity.firstName;
    String last = Signup_Activity.lastName;
    String dOB = Signup_Activity.dateOfBirth;
    String hA = Signup_Activity.homeAddress;
    String eA = Signup_Activity.emailAddress;
    String employeeAge = Signup_Activity.age;
    String employeeUsername = Signup_Activity.username;
    String employeePassword = Signup_Activity.password;
    int employeeID = Integer.getInteger()
    Employee employee = new Employee(first,last,dOB,hA,eA,employeeAge,employeeUsername,employeePassword,employeeID);
}