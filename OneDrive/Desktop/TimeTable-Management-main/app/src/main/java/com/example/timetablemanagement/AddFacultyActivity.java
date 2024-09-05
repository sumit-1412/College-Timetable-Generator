package com.example.timetablemanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AddFacultyActivity extends AppCompatActivity {

    private EditText facultyIdEditText, facultyNameEditText, facultyDeptEditText, phoneNoEditText, emailIdEditText, qualificationEditText;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        facultyIdEditText = findViewById(R.id.facultyIdEditText);
        facultyNameEditText = findViewById(R.id.facultyNameEditText);
        facultyDeptEditText = findViewById(R.id.facultyDeptEditText);
        phoneNoEditText = findViewById(R.id.phoneNoEditText);
        emailIdEditText = findViewById(R.id.emailIdEditText);
        qualificationEditText = findViewById(R.id.qualificationEditText);
        Button submitFacultyButton = findViewById(R.id.submitFacultyButton);

        // Set onClickListener for submitFacultyButton
        submitFacultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve input data
                String facultyId = facultyIdEditText.getText().toString().trim();
                String facultyName = facultyNameEditText.getText().toString().trim();
                String facultyDept = facultyDeptEditText.getText().toString().trim();
                String phoneNo = phoneNoEditText.getText().toString().trim();
                String emailId = emailIdEditText.getText().toString().trim();
                String qualification = qualificationEditText.getText().toString().trim();

                // Add faculty to Firestore
                addFaculty(facultyId, facultyName, facultyDept, phoneNo, emailId, qualification);
            }
        });
    }

    private void addFaculty(String facultyId, String facultyName, String facultyDept, String phoneNo, String emailId, String qualification) {
        Map<String, Object> faculty = new HashMap<>();
        faculty.put("facultyId", facultyId);
        faculty.put("facultyName", facultyName);
        faculty.put("facultyDept", facultyDept);
        faculty.put("phoneNo", phoneNo);
        faculty.put("emailId", emailId);
        faculty.put("qualification", qualification);

        db.collection("faculties").document(facultyId)
                .set(faculty)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddFacultyActivity.this, "Faculty added successfully", Toast.LENGTH_SHORT).show();
                        // Clear input fields
                        facultyIdEditText.setText("");
                        facultyNameEditText.setText("");
                        facultyDeptEditText.setText("");
                        phoneNoEditText.setText("");
                        emailIdEditText.setText("");
                        qualificationEditText.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddFacultyActivity.this, "Error adding faculty: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
