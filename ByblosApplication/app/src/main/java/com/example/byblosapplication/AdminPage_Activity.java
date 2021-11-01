package com.example.byblosapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminPage_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
    }

    public void AddService(View view){
        startActivity(new Intent(AdminPage_Activity.this,AddService_Activity.class));
    }
    public void EditService(View view){
        startActivity(new Intent(AdminPage_Activity.this,EditService_Activity.class));
    }
    public void DeleteService(View view){
        startActivity(new Intent(AdminPage_Activity.this,DeleteService_Activity.class));
    }
    public void DeleteAccount(View view){
        startActivity(new Intent(AdminPage_Activity.this,DeleteBranchAccount_Activity.class));
    }
}