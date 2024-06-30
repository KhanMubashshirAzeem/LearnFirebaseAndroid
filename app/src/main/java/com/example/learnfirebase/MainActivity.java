package com.example.learnfirebase;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    // creating variables for EditText and buttons.
    private EditText nameEdt, phoneEdt, addressEdt;
    private Button sendDatabtn;
    private CheckBox studentCheckBox, employeeCheckBox;

    // creating variables for Firebase Database.
    FirebaseDatabase firebaseDatabase;
    DatabaseReference studentDatabaseReference;
    DatabaseReference employeeDatabaseReference;

    // creating variables for our object classes
    EmployeeInfo studentInfo;
    EmployeeInfo employeeInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Apply window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // initializing our EditText and button
        nameEdt = findViewById(R.id.userName);
        phoneEdt = findViewById(R.id.userPhoneNumber);
        addressEdt = findViewById(R.id.userAddress);
        sendDatabtn = findViewById(R.id.idBtnSendData);
        studentCheckBox = findViewById(R.id.selectStudent);
        employeeCheckBox = findViewById(R.id.selectEmployee);

        // setting up checkbox behavior
        studentCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    employeeCheckBox.setChecked(false);
                }
            }
        });

        employeeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    studentCheckBox.setChecked(false);
                }
            }
        });

        // initializing Firebase database instance
        firebaseDatabase = FirebaseDatabase.getInstance();

        // getting reference for our database
        studentDatabaseReference = firebaseDatabase.getReference("users").child("StudentInfo");
        employeeDatabaseReference = firebaseDatabase.getReference("users").child("EmployeeInfo");

        // initializing our object class variables
        studentInfo = new EmployeeInfo();
        employeeInfo = new EmployeeInfo();

        // adding on click listener for our button
        sendDatabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text from our EditText fields
                String name = nameEdt.getText().toString();
                String phone = phoneEdt.getText().toString();
                String address = addressEdt.getText().toString();

                // checking whether the EditText fields are empty or not
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
                    Toast.makeText(MainActivity.this, "Please add some data.", Toast.LENGTH_SHORT).show();
                } else if (!studentCheckBox.isChecked() && !employeeCheckBox.isChecked()) {
                    Toast.makeText(MainActivity.this, "Please select either Student or Employee.", Toast.LENGTH_SHORT).show();
                } else {
                    if (studentCheckBox.isChecked()) {
                        addStudentDataToFirebase(name, phone, address);
                    } else if (employeeCheckBox.isChecked()) {
                        addEmployeeDataToFirebase(name, phone, address);
                    }
                }
            }
        });
    }

    private void addStudentDataToFirebase(String name, String phone, String address) {
        // setting data in our studentInfo object class
        studentInfo.setEmployeeName(name);
        studentInfo.setEmployeeContactNumber(phone);
        studentInfo.setEmployeeAddress(address);

        // generating a unique key for each student entry
        String uniqueKey = studentDatabaseReference.push().getKey();

        // adding data to Firebase database
        studentDatabaseReference.child(uniqueKey).setValue(studentInfo);
        Toast.makeText(MainActivity.this, "Student data added", Toast.LENGTH_SHORT).show();
    }

    private void addEmployeeDataToFirebase(String name, String phone, String address) {
        // setting data in our employeeInfo object class
        employeeInfo.setEmployeeName(name);
        employeeInfo.setEmployeeContactNumber(phone);
        employeeInfo.setEmployeeAddress(address);

        // generating a unique key for each employee entry
        String uniqueKey = employeeDatabaseReference.push().getKey();

        // adding data to Firebase database
        employeeDatabaseReference.child(uniqueKey).setValue(employeeInfo);
        Toast.makeText(MainActivity.this, "Employee data added", Toast.LENGTH_SHORT).show();
    }
}
