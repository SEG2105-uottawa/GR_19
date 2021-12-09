package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class Welcome_Activity extends AppCompatActivity {
    DatabaseReference databaseAccounts;
    Person loggedUser;
    String branchAddress;
    String id;
    String searchInput;
    Bundle extras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        databaseAccounts = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("accounts");

        extras = getIntent().getExtras();
        //Set the loggedUser
        if (extras.getString("id").equals("admin")){
            loggedUser = new Admin("admin","admin","admin","admin");
            TextView nameTV = (TextView) findViewById(R.id.nameEditText);
            TextView roleTV = (TextView) findViewById(R.id.roleEditText);
            roleTV.setText("Logged in as Administrator");
            nameTV.setVisibility(View.GONE);
        }else{
            String firstName = extras.getString("firstName");
            String lastName = extras.getString("lastName");
            String dateOfBirth = extras.getString("dateOfBirth");
            String homeAddress = extras.getString("homeAddress");
            String emailAddress = extras.getString("emailAddress");
            int age = extras.getInt("age");
            String username = extras.getString("username");
            String password = extras.getString("password");
            id = extras.getString("id");
            String role = extras.getString("role");
            if (role.equals("customer")){
                loggedUser = new Customer(firstName,lastName,dateOfBirth,homeAddress,emailAddress,age,username,password,id,role);
                TextView roleTV = (TextView) findViewById(R.id.roleEditText);
                roleTV.setText("Logged in as Customer");
            }else{
                int employeeId = extras.getInt("employeeId");
                branchAddress = extras.getString("branchAddress");
                loggedUser = new Employee(firstName,lastName,dateOfBirth,homeAddress,emailAddress,age,username,password,id,role,employeeId);
                TextView roleTV = (TextView) findViewById(R.id.roleEditText);
                roleTV.setText("Logged in as Employee");
            }
            TextView nameTV = (TextView) findViewById(R.id.nameEditText);
            nameTV.setText(firstName + " " + lastName);
        }
        Toast.makeText(Welcome_Activity.this,"Welcome " + loggedUser.username + "! " + "You are logged in as a \"" + loggedUser.role +"\"", Toast.LENGTH_SHORT).show();

        if (loggedUser.role.equals("admin")){
            LinearLayout adminLayout = (LinearLayout) findViewById(R.id.adminLayout);
            adminLayout.setVisibility(View.VISIBLE);
        }

        if (loggedUser.role.equals("employee")){
            LinearLayout employeeLayout = (LinearLayout) findViewById(R.id.employeeLayout);
            employeeLayout.setVisibility(View.VISIBLE);
        }

        if (loggedUser.role.equals("customer")){
            LinearLayout employeeLayout = (LinearLayout) findViewById(R.id.customerLayout);
            employeeLayout.setVisibility(View.VISIBLE);
            Spinner startTime = ((Spinner) findViewById(R.id.startTimeSpinner));
            Spinner endTime = ((Spinner) findViewById(R.id.endTimeSpinner));
            ArrayList<String> hours = new ArrayList<String>();
            hours.add("00:00");
            hours.add("01:00");
            hours.add("02:00");
            hours.add("03:00");
            hours.add("04:00");
            hours.add("05:00");
            hours.add("06:00");
            hours.add("07:00");
            hours.add("08:00");
            hours.add("09:00");
            hours.add("10:00");
            hours.add("11:00");
            hours.add("12:00");
            hours.add("13:00");
            hours.add("14:00");
            hours.add("15:00");
            hours.add("16:00");
            hours.add("17:00");
            hours.add("18:00");
            hours.add("19:00");
            hours.add("20:00");
            hours.add("21:00");
            hours.add("22:00");
            hours.add("23:00");
            ArrayAdapter<String> hourAdapter = new ArrayAdapter<String>(Welcome_Activity.this, android.R.layout.simple_spinner_item, hours);
            hourAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
            startTime.setAdapter(hourAdapter);
            endTime.setAdapter(hourAdapter);

        }
    }

    @Override
    public void onResume(){
        super.onResume();
        databaseAccounts.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (loggedUser.role.equals("employee")){
                    branchAddress = snapshot.child(id).child("branchAddress").getValue(String.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void AddService(View view){
        startActivity(new Intent(Welcome_Activity.this,AddService_Activity.class));
    }
    public void EditService(View view){
        startActivity(new Intent(Welcome_Activity.this,EditService_Activity.class));
    }
    public void DeleteService(View view){
        startActivity(new Intent(Welcome_Activity.this,DeleteService_Activity.class));
    }
    public void DeleteAccount(View view){
        startActivity(new Intent(Welcome_Activity.this,DeleteBranchAccount_Activity.class));
    }

    public void CreateBranch(View view){
        Intent information = new Intent (new Intent(Welcome_Activity.this,CreateBranch_Activity.class));
        information.putExtra("loggedUserId", id);
        startActivity(information);
    }

    public void AddServiceToBranch(View view){
        if (!(branchAddress == null)){
            Intent address = new Intent(Welcome_Activity.this, AddBranchService_Activity.class);
            address.putExtra("address", branchAddress);
            startActivity(address);
        }else{
            Toast.makeText(Welcome_Activity.this,"No branch is associated with this account. Please add branch first", Toast.LENGTH_LONG).show();
        }
    }

    public void EditBranch(View view){
        if (!(branchAddress == null)){
            Intent address = new Intent(Welcome_Activity.this, EditBranch_Activity.class);
            address.putExtra("address", branchAddress);
            startActivity(address);
        }else{
            Toast.makeText(Welcome_Activity.this,"No branch is associated with this branch. Please add branch first", Toast.LENGTH_LONG).show();
        }

    }

    public void DeleteBranchService(View view){
        if (!(branchAddress == null)){
            Intent address = new Intent(Welcome_Activity.this, DeleteBranchService_Activity.class);
            address.putExtra("address", branchAddress);
            startActivity(address);
        }else{
            Toast.makeText(Welcome_Activity.this,"No branch is associated with this branch. Please add branch first", Toast.LENGTH_LONG).show();
        }
    }

    public void ServiceRequest(View view){
        Intent address = new Intent(Welcome_Activity.this, ServiceRequest_Activity.class);
        address.putExtra("address", branchAddress);
        startActivity(address);
    }

    public void searchByAddress(View view){
        searchInput = ((TextView) findViewById(R.id.searchInputEditText)).getText().toString();
        Intent address = new Intent(Welcome_Activity.this, SearchAddress_Activity.class);
        address.putExtra("loggedUser", extras);
        address.putExtra("search", searchInput);
        startActivity(address);
    }

    public void searchByService(View view){
        searchInput = ((TextView) findViewById(R.id.searchInputEditText)).getText().toString();
        Intent address = new Intent(Welcome_Activity.this, SearchService_Activity.class);
        address.putExtra("loggedUser", extras);
        address.putExtra("search", searchInput);
        startActivity(address);
    }

    public void searchByWorkingHours(View view){
    String startTimeStr = ((Spinner) findViewById(R.id.startTimeSpinner)).getSelectedItem().toString();
    String endTimeStr = ((Spinner) findViewById(R.id.endTimeSpinner)).getSelectedItem().toString();
    int startTime = Integer.parseInt(startTimeStr.substring(0,2));
    int endTime = Integer.parseInt(endTimeStr.substring(0,2));
    Intent searchByHours = new Intent(Welcome_Activity.this, SearchHours_Activity.class);
    searchByHours.putExtra("startTime", startTime);
    searchByHours.putExtra("endTime",endTime);
    searchByHours.putExtra("startTimeStr", startTimeStr);
    searchByHours.putExtra("endTimeStr",endTimeStr);
    if (startTime > endTime){
        Toast.makeText(Welcome_Activity.this,"Invalid time input", Toast.LENGTH_LONG).show();
    }else{
        startActivity(searchByHours);
    }
    }
}