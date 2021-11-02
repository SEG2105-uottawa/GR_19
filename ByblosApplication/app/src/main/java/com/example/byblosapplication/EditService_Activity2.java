package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

public class EditService_Activity2 extends AppCompatActivity {
    DatabaseReference databaseServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service2);
        databaseServices = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("services");
    }

    public void EditService(View view){
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("id");
        String name = ((EditText)findViewById(R.id.changeServiceName)).getText().toString().trim();
        databaseServices.child(id).child("name").setValue(name);
        Toast.makeText(EditService_Activity2.this,"Edited Service", Toast.LENGTH_SHORT).show();
    }
}