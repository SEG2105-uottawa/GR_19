package com.example.byblosapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

public class AddService_Activity extends AppCompatActivity {
    DatabaseReference databaseServices;
    String serviceName;
    String requiredLicense;
    String serviceDescription;
    int servicePrice;
    int requiredAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        databaseServices = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("services");

        requiredLicense = "";

        RadioGroup groupRadio=(RadioGroup)findViewById(R.id.radioGroupLicenseType);

        groupRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonG1)
                    requiredLicense = "G1";
                else if (checkedId == R.id.radioButtonG2)
                    requiredLicense = "G2";
                else if (checkedId == R.id.radioButtonG3)
                    requiredLicense = "G3";
            }
        });

    }

    public void AddService(View view){
        serviceName = ((EditText)findViewById(R.id.serviceName)).getText().toString().trim();
        serviceDescription = ((EditText)findViewById(R.id.editTextServiceDescription)).getText().toString().trim();

        if (requiredLicense.equals("") || serviceName.equals("") || ((EditText)findViewById(R.id.editTextRequiredAge)).getText().toString().equals("") || ((EditText)findViewById(R.id.editTextPrice)).getText().toString().equals("")){
            Toast.makeText(AddService_Activity.this,"Missing Parameters", Toast.LENGTH_SHORT).show();
        }else{
            servicePrice = Integer.parseInt(((EditText)findViewById(R.id.editTextPrice)).getText().toString().trim());
            requiredAge = Integer.parseInt(((EditText)findViewById(R.id.editTextRequiredAge)).getText().toString().trim());
            String id = databaseServices.push().getKey();
            Service service = new Service(serviceName,id,servicePrice,requiredAge,requiredLicense,serviceDescription);
            databaseServices.child(id).setValue(service);
            Toast.makeText(AddService_Activity.this,"Added Service", Toast.LENGTH_SHORT).show();
        }
    }
}