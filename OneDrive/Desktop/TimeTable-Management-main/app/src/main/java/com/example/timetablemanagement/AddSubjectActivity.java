package com.example.timetablemanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class AddSubjectActivity extends AppCompatActivity {

    private EditText subjectCodeEditText, subjectNameEditText;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        subjectCodeEditText = findViewById(R.id.subjectCodeEditText);
        subjectNameEditText = findViewById(R.id.subjectNameEditText);
        Button submitSubjectButton = findViewById(R.id.submitSubjectButton);

        // Set onClickListener for submitSubjectButton
        submitSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve input data
                String subjectCode = subjectCodeEditText.getText().toString().trim();
                String subjectName = subjectNameEditText.getText().toString().trim();

                // Add subject to Firestore
                addSubject(subjectCode, subjectName);
            }
        });
    }

    private void addSubject(String subjectCode, String subjectName) {
        Map<String, Object> subject = new HashMap<>();
        subject.put("subjectCode", subjectCode);
        subject.put("subjectName", subjectName);

        db.collection("subjects").document(subjectCode)
                .set(subject)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddSubjectActivity.this, "Subject added successfully", Toast.LENGTH_SHORT).show();
                        // Clear input fields
                        subjectCodeEditText.setText("");
                        subjectNameEditText.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddSubjectActivity.this, "Error adding subject: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
