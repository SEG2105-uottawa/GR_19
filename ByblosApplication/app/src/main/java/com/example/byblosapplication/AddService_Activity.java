package com.example.byblosapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

public class AddService_Activity extends AppCompatActivity {
    DatabaseReference databaseServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        databaseServices = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("services");
    }

    public void AddService(View view){
        String serviceName = ((EditText)findViewById(R.id.serviceName)).getText().toString().trim();
        //databaseServices.addListenerForSingleValueEvent(new ValueEventListener() {
        //@Override
        if(!TextUtils.isEmpty(serviceName)){
            String id = databaseServices.push().getKey();
            Service service = new Service(serviceName,id);
            //Activity activity = new Activity();
            databaseServices.child(id).setValue(service);
            Toast.makeText(AddService_Activity.this,"Added Service", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddService_Activity.this,"Please Enter a Name: ", Toast.LENGTH_SHORT).show();
        }

    }
}