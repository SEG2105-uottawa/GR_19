package com.example.byblosapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.*;

public class SearchService_Activity extends AppCompatActivity {
    DatabaseReference databaseBranches;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_service);
        Bundle extras = getIntent().getExtras();
        String serviceInput = extras.getString("search");
        databaseBranches = FirebaseDatabase.getInstance("https://seg-2105-group-19-default-rtdb.firebaseio.com/").getReference("branches");
        LinearLayout branchesLayout = (LinearLayout) findViewById(R.id.branchesLayout);
        databaseBranches.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean ok = false;
                for (DataSnapshot userSnapshot: snapshot.getChildren()){
                    for (DataSnapshot name: userSnapshot.child("services").getChildren()) {
                        if (name.child("name").getValue(String.class).equals(serviceInput)) {
                            ok = true;
                            Button button = new Button(SearchService_Activity.this);
                            button.setText(userSnapshot.child("address").getValue(String.class));
                            button.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              Intent address = new Intent(SearchService_Activity.this, SearchAddress_Activity.class);
                                                              String searchInput = userSnapshot.child("address").getValue(String.class);
                                                              address.putExtra("loggedUser", extras);
                                                              address.putExtra("search", searchInput);
                                                              startActivity(address);
                                                          }
                                                      });

                                    branchesLayout.addView(button);
                        }
                    }
                }
                if (ok){
                    branchesLayout.setVisibility(View.VISIBLE);
                }else{
                    TextView branchNotFoundTextView = (TextView) findViewById(R.id.serviceNotFoundTextView);
                    branchNotFoundTextView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}