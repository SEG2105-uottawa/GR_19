package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

public class Signup_Activity extends AppCompatActivity {

    DatabaseReference databaseAccounts;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        databaseAccounts = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("accounts");

        RadioButton employeeRadioButton = (RadioButton) findViewById(R.id.employeeRadioButton);
        EditText employeeNumberET = (EditText) findViewById(R.id.editTextEmployeeNum);
        employeeNumberET.setVisibility(View.GONE);

        RadioGroup groupRadio=(RadioGroup)findViewById(R.id.customerOrEmployee);

        //Show Employee Number only when employee radio button is checked
        groupRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.employeeRadioButton){
                    employeeNumberET.setVisibility(View.VISIBLE);
                }else{
                    employeeNumberET.setVisibility(View.GONE);
                }
            }
        });

    }

    public void onClick(View view){
        firstName = ((EditText)findViewById(R.id.editTextFirstName)).getText().toString().trim();
        lastName = ((EditText)findViewById(R.id.editTextLastName)).getText().toString().trim();
        email = ((EditText)findViewById(R.id.editTextEmail)).getText().toString().trim();
        homeAddress = ((EditText)findViewById(R.id.editTextHomeAddress)).getText().toString().trim();
        dateOfBirth = ((EditText)findViewById(R.id.editTextDOB)).getText().toString().trim();
        username = ((EditText)findViewById(R.id.editTextUsername)).getText().toString().trim();
        password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString().trim();

        RadioButton customerBtn = (RadioButton) findViewById(R.id.customer);
        RadioButton employeeBtn = (RadioButton) findViewById(R.id.employeeRadioButton);

        //Make sure all parameters are filled and correct
        if (firstName.equals("")|| lastName.equals("") || email.equals("") || homeAddress.equals("")  || dateOfBirth.equals("") || username.equals("")  || password.equals("")  || ((EditText)findViewById(R.id.editTextAge)).getText().toString().equals("")){
            Toast.makeText(Signup_Activity.this,"Missing Parameters", Toast.LENGTH_SHORT).show();
        }else{
            age = Integer.parseInt(((EditText)findViewById(R.id.editTextAge)).getText().toString());
            id = databaseAccounts.push().getKey();

            //Only allows for unique username, password and employee number
            databaseAccounts.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Boolean ok = true;
                    for (DataSnapshot userSnapshot: snapshot.getChildren()){
                        if (userSnapshot.child("username").getValue(String.class).equals(username)){
                            Toast.makeText(Signup_Activity.this,"Username is taken", Toast.LENGTH_SHORT).show();
                            ok = false;
                        }
                        if (employeeBtn.isChecked() && userSnapshot.child("role").getValue(String.class).equals("employee")){
                            EditText eN = (EditText)findViewById(R.id.editTextEmployeeNum);
                            String eNString = eN.getText().toString();
                            if (eNString.equals("")){
                                Toast.makeText(Signup_Activity.this,"Missing Parameters", Toast.LENGTH_SHORT).show();
                                ok = false;
                            }else{
                                employeeNum = Integer.parseInt(eNString);
                                if(userSnapshot.child("employeeID").getValue(Integer.class).equals(employeeNum)){
                                    Toast.makeText(Signup_Activity.this,"Employee ID is taken", Toast.LENGTH_SHORT).show();
                                    ok = false;
                                }
                            }
                        }
                    }
                    if (ok){
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

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}