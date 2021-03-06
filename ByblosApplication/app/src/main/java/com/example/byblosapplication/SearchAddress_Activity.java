package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

import java.util.ArrayList;

public class SearchAddress_Activity extends AppCompatActivity {
    DatabaseReference databaseBranches;
    String id;
    String customerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_address);
        databaseBranches = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("branches");
        Bundle extras = getIntent().getExtras();
        String searchInput =extras.getString("search");
        customerName = extras.getString("customerName");
        LinearLayout servicesLayout = (LinearLayout) findViewById(R.id.servicesLayout);
        LinearLayout applyLayout = (LinearLayout) findViewById(R.id.applyServiceLayout);
        TextView addressTV = (TextView) findViewById(R.id.branchAddressEditText);
        TextView workingHoursTextView = (TextView) findViewById(R.id.workingHoursTextView);
        ArrayList<String> services = new ArrayList<String>();
        Spinner applySpinner = (Spinner) findViewById(R.id.applyServiceSpinner);
        databaseBranches.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id = "";
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    if (userSnapshot.child("address").getValue(String.class).equals(searchInput)){
                        id = userSnapshot.child("id").getValue(String.class);
                        addressTV.setText(userSnapshot.child("address").getValue(String.class));
                        String startTime = userSnapshot.child("workingHours").child("0").getValue(String.class);
                        String endTime = userSnapshot.child("workingHours").child("1").getValue(String.class);
                        workingHoursTextView.setText(startTime + " - " + endTime);
                    }
                }
                for (DataSnapshot userSnapshot: snapshot.child(id).child("services").getChildren()){
                    TextView service = new TextView(SearchAddress_Activity.this);
                    service.setText("-" + userSnapshot.child("name").getValue(String.class));
                    services.add(userSnapshot.child("name").getValue(String.class));
                    servicesLayout.addView(service);
                }
                if (!id.equals("")){
                    servicesLayout.setVisibility(View.VISIBLE);
                    applyLayout.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(SearchAddress_Activity.this, android.R.layout.simple_spinner_item, services);
                    serviceAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
                    applySpinner.setAdapter(serviceAdapter);
                }else{
                    LinearLayout branchNotFoundLayout = (LinearLayout) findViewById(R.id.branchNotFoundLayout);
                    branchNotFoundLayout.setVisibility(View.VISIBLE);
                    TextView branchNotFoundTextView = (TextView) findViewById(R.id.branchNotFoundTextView);
                    branchNotFoundTextView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void applyService(View view){
        String service = ((Spinner)findViewById(R.id.applyServiceSpinner)).getSelectedItem().toString();
        Intent applyService = new Intent(SearchAddress_Activity.this,ServiceApplicationForm_Activity.class);
        applyService.putExtra("serviceName", service);
        applyService.putExtra("branchId", id);
        applyService.putExtra("customerName", customerName);
        startActivity(applyService);
    }

    public void review(View view){
        TextView reviewTextView = (TextView) findViewById(R.id.reviewTextView);
        int ratingBar = ((RatingBar) findViewById(R.id.ratingBar)).getNumStars();
        String reviewId = databaseBranches.push().getKey();
        if (reviewTextView.getText().toString().trim().equals(""))
            Toast.makeText(SearchAddress_Activity.this,"Please write a review before submitting", Toast.LENGTH_SHORT).show();
        else{
            databaseBranches.child(id).child("reviews").child(reviewId).child("reviewText").setValue(reviewTextView.getText().toString());
            databaseBranches.child(id).child("reviews").child(reviewId).child("reviewRating").setValue(ratingBar);
            Toast.makeText(SearchAddress_Activity.this,"Thank you for the review", Toast.LENGTH_SHORT).show();
        }
    }
}