package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

public class Welcome_Activity extends AppCompatActivity {
    DatabaseReference databaseAccounts;
    Person loggedUser;
    String branchAddress;
    String id;
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
            id = extras.getString("id");
            String role = extras.getString("role");
            if (role.equals("customer")){
                loggedUser = new Customer(firstName,lastName,dateOfBirth,homeAddress,emailAddress,age,username,password,id,role);
            }else{
                int employeeId = extras.getInt("employeeId");
                branchAddress = extras.getString("branchAddress");
                loggedUser = new Employee(firstName,lastName,dateOfBirth,homeAddress,emailAddress,age,username,password,id,role,employeeId);
            }
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
}