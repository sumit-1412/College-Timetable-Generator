package com.example.timetablemanagement;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllocateSubjectActivity extends AppCompatActivity {

    private Spinner facultySpinner, subjectSpinner;
    private FirebaseFirestore db;
    private List<String> facultyList, subjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocate_subject);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        facultySpinner = findViewById(R.id.facultySpinner);
        subjectSpinner = findViewById(R.id.subjectSpinner);
        Button allocateButton = findViewById(R.id.allocateButton);

        // Fetch faculty and subjects from Firestore
        fetchFaculty();
        fetchSubjects();

        // Set onClickListener for allocateButton
        allocateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve selected faculty and subject
                String selectedFaculty = facultySpinner.getSelectedItem().toString();
                String selectedSubject = subjectSpinner.getSelectedItem().toString();

                // Allocate subject to faculty in Firestore
                allocateSubject(selectedFaculty, selectedSubject);
            }
        });
    }

    private void fetchFaculty() {
        db.collection("faculties").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    facultyList = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        String facultyName = document.getString("facultyName");
                        facultyList.add(facultyName);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AllocateSubjectActivity.this, android.R.layout.simple_spinner_item, facultyList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    facultySpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(AllocateSubjectActivity.this, "Error fetching faculty: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchSubjects() {
        db.collection("subjects").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    subjectList = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        String subjectName = document.getString("subjectName");
                        subjectList.add(subjectName);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AllocateSubjectActivity.this, android.R.layout.simple_spinner_item, subjectList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    subjectSpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(AllocateSubjectActivity.this, "Error fetching subjects: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void allocateSubject(String facultyName, String subjectName) {
        Map<String, Object> allocation = new HashMap<>();
        allocation.put("facultyName", facultyName);
        allocation.put("subjectName", subjectName);

        db.collection("allocations").document(facultyName + "_" + subjectName)
                .set(allocation)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AllocateSubjectActivity.this, "Subject allocated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AllocateSubjectActivity.this, "Error allocating subject: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
