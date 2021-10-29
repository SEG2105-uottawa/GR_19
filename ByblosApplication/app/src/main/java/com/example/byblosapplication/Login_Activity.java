package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.database.*;

public class Login_Activity extends AppCompatActivity {

    DatabaseReference databaseAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        databaseAccounts = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("accounts");
    }

    public void Login(View view){
        databaseAccounts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapShot : snapshot.getChildren()){
                    Person person = postSnapShot.getValue(Person.class);
                    if (person.username.equals(((EditText)findViewById(R.id.editTextUsernameSN)).getText().toString().trim()) && person.password.equals(((EditText)findViewById(R.id.editTextPasswordSN)).getText().toString().trim())){
                        startActivity(new Intent(Login_Activity.this,Welcome_Activity.class));
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}