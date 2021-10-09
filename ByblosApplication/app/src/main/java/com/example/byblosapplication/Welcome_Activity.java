package com.example.byblosapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Welcome_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    TextView welcomeUser = (TextView) findViewById(R.id.welcomeUser);
   // welcomeUser.setText("Welcome " + firstName + "! " + "You are logged in as \"" + role +"\"");
}