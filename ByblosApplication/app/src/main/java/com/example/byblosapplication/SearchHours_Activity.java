package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import com.google.firebase.database.*;

public class SearchHours_Activity extends AppCompatActivity {
    DatabaseReference databaseBranches;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_hours);
        Bundle extras = getIntent().getExtras();
        int startTime = extras.getInt("startTime");
        int endTime = extras.getInt("endTime");
        String startTimeStr = extras.getString("startTimeStr");
        String endTimeStr = extras.getString("endTimeStr");
        databaseBranches = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("branches");
        LinearLayout branchesLayout = (LinearLayout) findViewById(R.id.workingBranchesLayout);
        ((TextView)findViewById(R.id.hoursTextView)).setText("Search results for +\"" +startTimeStr + "-" + endTimeStr+"\"");

        databaseBranches.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean ok = false;
                for (DataSnapshot userSnapshot: snapshot.getChildren()){

                    int start = Integer.parseInt((userSnapshot.child("workingHours").child("0").getValue(String.class)).substring(0,2));
                    int end = Integer.parseInt((userSnapshot.child("workingHours").child("1").getValue(String.class)).substring(0,2));
                    if (!(endTime < start || startTime > end)){
                        ok = true;
                        Button button = new Button(SearchHours_Activity.this);
                        TextView branchTime = new TextView(SearchHours_Activity.this);
                        branchTime.setText("Open from " + userSnapshot.child("workingHours").child("0").getValue(String.class) + " to " + userSnapshot.child("workingHours").child("1").getValue(String.class));
                        branchTime.setGravity(Gravity.CENTER);
                        button.setText(userSnapshot.child("address").getValue(String.class));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent address = new Intent(SearchHours_Activity.this, SearchAddress_Activity.class);
                                String searchInput = userSnapshot.child("address").getValue(String.class);
                                address.putExtra("loggedUser", extras);
                                address.putExtra("search", searchInput);
                                startActivity(address);
                            }
                        });
                        branchesLayout.addView(branchTime);
                        branchesLayout.addView(button);
                    }
                }
                if (ok){
                    branchesLayout.setVisibility(View.VISIBLE);
                }else{
                    TextView branchNotFoundTextView = (TextView) findViewById(R.id.noBranchesFound);
                    branchNotFoundTextView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}