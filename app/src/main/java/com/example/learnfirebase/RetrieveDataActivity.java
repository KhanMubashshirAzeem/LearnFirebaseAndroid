package com.example.learnfirebase;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RetrieveDataActivity extends AppCompatActivity {

    // Variables for TextViews to display the retrieved data
    private TextView nameTextView, phoneTextView, addressTextView;

    // Firebase database reference
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_data);

        // Initializing the TextViews
        nameTextView = findViewById(R.id.userNameR);
        phoneTextView = findViewById(R.id.userContactR);
        addressTextView = findViewById(R.id.userAddressR);

        // Getting the Firebase database instance and reference
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("EmployeeInfo");

        // Calling method to retrieve data from Firebase
        retrieveDataFromFirebase();
    }

    private void retrieveDataFromFirebase() {
        // Adding a listener to read the data at our database reference

        String userId = databaseReference.getKey();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                EmployeeInfo employeeInfo = dataSnapshot.getValue(EmployeeInfo.class);

                if (employeeInfo != null) {
                    // Setting the retrieved data to the TextViews
                    nameTextView.setText(employeeInfo.getUserName());
                    phoneTextView.setText(employeeInfo.getUserContactNumber());
                    addressTextView.setText(employeeInfo.getUserAddress());
                } else {
                    // If no data exists, show a toast message
                    Toast.makeText(RetrieveDataActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value, show a toast message
                Toast.makeText(RetrieveDataActivity.this, "Failed to retrieve data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
