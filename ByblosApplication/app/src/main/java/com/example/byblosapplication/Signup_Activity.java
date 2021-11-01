package com.example.byblosapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class Signup_Activity extends AppCompatActivity {

    DatabaseReference databaseAccounts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        databaseAccounts = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("accounts");
    }
    String firstName;
    String lastName;
    String email;
    String homeAddress;
    int age;
    String dateOfBirth;
    String username;
    String password;
    int employeeNum;
    String id;

    public void onClick(View view){
        firstName = ((EditText)findViewById(R.id.editTextFirstName)).getText().toString().trim();
        lastName = ((EditText)findViewById(R.id.editTextLastName)).getText().toString().trim();
        email = ((EditText)findViewById(R.id.editTextEmail)).getText().toString().trim();
        homeAddress = ((EditText)findViewById(R.id.editTextHomeAddress)).getText().toString().trim();

        age = Integer.parseInt(((EditText)findViewById(R.id.editTextAge)).getText().toString());

        dateOfBirth = ((EditText)findViewById(R.id.editTextDOB)).getText().toString().trim();
        username = ((EditText)findViewById(R.id.editTextUsername)).getText().toString().trim();
        password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString().trim();

        RadioButton customerBtn = (RadioButton) findViewById(R.id.customer);
        RadioButton employeeBtn = (RadioButton) findViewById(R.id.employee);

        id = databaseAccounts.push().getKey();

        if (customerBtn.isChecked()){
            Customer customer = new Customer(firstName,lastName,dateOfBirth,homeAddress,email,age,username,password,id, "customer");
            databaseAccounts.child(id).setValue(customer);
            startActivity(new Intent(Signup_Activity.this,Login_Activity.class));
        }
        else if (employeeBtn.isChecked()){
            EditText eN = (EditText)findViewById(R.id.editTextEmployeeNum);
            String eNString = eN.getText().toString();
            employeeNum = Integer.parseInt(eNString);

            Employee employee = new Employee(firstName,lastName,dateOfBirth,homeAddress,email,age,username,password,id,"employee" ,employeeNum);

            databaseAccounts.child(id).setValue(employee);
            startActivity(new Intent(Signup_Activity.this,Login_Activity.class));
        }
    }

}