package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

        Spinner yearSpinner = ((Spinner) findViewById(R.id.yearSpinner));
        Spinner monthSpinner = ((Spinner) findViewById(R.id.monthSpinner));
        Spinner daySpinner = ((Spinner) findViewById(R.id.daySpinner));
        ArrayList<Integer> years = new ArrayList<Integer>();
        ArrayList<Integer> months= new ArrayList<Integer>();
        //Populate arrays for date of birth spinners
        for (int i = 2021; i >= 1900; i--){
            years.add(i);
        }

        for (int i = 1; i <= 12; i++){
            months.add(i);
        }


        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<Integer>(Signup_Activity.this, android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        ArrayAdapter<Integer> monthAdapter = new ArrayAdapter<Integer>(Signup_Activity.this, android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1){
                    ArrayList<Integer> days28 = new ArrayList<Integer>();
                    for (int i = 1; i <= 28; i++){
                        days28.add(i);
                    }
                    ArrayAdapter<Integer> dayAdapter = new ArrayAdapter<Integer>(Signup_Activity.this, android.R.layout.simple_spinner_item,days28);
                    daySpinner.setAdapter(dayAdapter);
                }

                else if (position == 3 || position == 5 || position == 8 || position == 10){
                    ArrayList<Integer> days30 = new ArrayList<Integer>();
                    for (int i = 1; i <= 30; i++){
                        days30.add(i);
                    }
                    ArrayAdapter<Integer> dayAdapter = new  ArrayAdapter<Integer>(Signup_Activity.this, android.R.layout.simple_spinner_item,days30);
                    daySpinner.setAdapter(dayAdapter);
                }

                if (position == 0 || position == 2 || position == 4 || position == 6 || position == 7 || position == 9 || position == 11 ){
                    ArrayList<Integer> days31 = new ArrayList<Integer>();
                    for (int i = 1; i <= 31; i++){
                        days31.add(i);
                    }
                    ArrayAdapter<Integer> dayAdapter = new  ArrayAdapter<Integer>(Signup_Activity.this, android.R.layout.simple_spinner_item,days31);
                    daySpinner.setAdapter(dayAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        RadioButton employeeRadioButton = (RadioButton) findViewById(R.id.employeeRadioButton);
        RadioGroup groupRadio=(RadioGroup)findViewById(R.id.customerOrEmployee);
    }

    public void onClick(View view){
        firstName = ((EditText)findViewById(R.id.editTextFirstName)).getText().toString().trim();
        lastName = ((EditText)findViewById(R.id.editTextLastName)).getText().toString().trim();
        email = ((EditText)findViewById(R.id.editTextEmail)).getText().toString().trim();
        homeAddress = ((EditText)findViewById(R.id.editTextHomeAddress)).getText().toString().trim();
        username = ((EditText)findViewById(R.id.editTextUsername)).getText().toString().trim();
        password = ((EditText)findViewById(R.id.editTextPassword)).getText().toString().trim();
        String year = ((Spinner) findViewById(R.id.yearSpinner)).getSelectedItem().toString();
        String month = ((Spinner) findViewById(R.id.monthSpinner)).getSelectedItem().toString();
        String day = ((Spinner) findViewById(R.id.daySpinner)).getSelectedItem().toString();
        int yearInt = Integer.parseInt(year);
        int monthInt = Integer.parseInt(month);
        int dayInt = Integer.parseInt(month);
        dateOfBirth = year + "/" + month + "/" + day;

        age = getAge(yearInt,monthInt,dayInt);


        RadioButton customerBtn = (RadioButton) findViewById(R.id.customer);
        RadioButton employeeBtn = (RadioButton) findViewById(R.id.employeeRadioButton);

        //Make sure all parameters are filled and correct
        if (firstName.equals("")|| lastName.equals("") || email.equals("") || homeAddress.equals("")  || dateOfBirth.equals("") || username.equals("")  || password.equals("")){
            Toast.makeText(Signup_Activity.this,"Missing Parameters", Toast.LENGTH_SHORT).show();
        }else if(!nameValidation(firstName) || !nameValidation(lastName))
            Toast.makeText(Signup_Activity.this,"Invalid First or Last Name", Toast.LENGTH_SHORT).show();
        else if(!emailValidation(email)) {
            Toast.makeText(Signup_Activity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
        }
        else{
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
                    }
                    if (ok){
                        if (customerBtn.isChecked()){
                            Customer customer = new Customer(firstName,lastName,dateOfBirth,homeAddress,email,age,username,password,id, "customer");
                            databaseAccounts.child(id).setValue(customer);
                            startActivity(new Intent(Signup_Activity.this,Login_Activity.class));
                        }
                        else if (employeeBtn.isChecked()){
                            employeeNum = getRandomNumber(10000,99999);
                            Employee employee = new Employee(firstName,lastName,dateOfBirth,homeAddress,email,age,username,password,id,"employee" ,employeeNum);
                            databaseAccounts.child(id).setValue(employee);
                            startActivity(new Intent(Signup_Activity.this,Login_Activity.class));
                        }else{
                            Toast.makeText(Signup_Activity.this,"Missing Parameters", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    public boolean nameValidation(String name){
        char[] chars = name.toCharArray();
        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    public boolean emailValidation(String email){
        boolean v = false;
        for(int i = 0; i < email.length();i++){
            if (String.valueOf(email.charAt(i)).equals("@"))
                v = true;
        }
        return v;
    }

    public int getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);

        return age;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}