package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.*;

public class EditService_Activity extends AppCompatActivity {
    DatabaseReference databaseServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service);
        databaseServices = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("services");
    }

    public void EditService(View view){
        String serviceName = ((EditText)findViewById(R.id.serviceNameBtn)).getText().toString().trim();
        databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()){
                    if (userSnapshot.child("name").getValue(String.class).equals(serviceName)){
                        Intent serviceEdit = new Intent(EditService_Activity.this, EditService_Activity2.class);
                        serviceEdit.putExtra("id", userSnapshot.child("id").getValue(String.class));
                        startActivity(serviceEdit);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}