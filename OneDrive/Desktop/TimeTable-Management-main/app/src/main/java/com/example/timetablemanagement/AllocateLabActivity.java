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

public class AllocateLabActivity extends AppCompatActivity {

    private Spinner facultySpinner, labSpinner;
    private Button allocateButton;
    private FirebaseFirestore db;
    private List<String> facultyList, labList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocate_lab);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        facultySpinner = findViewById(R.id.facultySpinner);
        labSpinner = findViewById(R.id.labSpinner);
        allocateButton = findViewById(R.id.allocateButton);

        // Fetch faculty and labs from Firestore
        fetchFaculty();
        fetchLabs();

        // Set onClickListener for allocateButton
        allocateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve selected faculty and lab
                String selectedFaculty = facultySpinner.getSelectedItem().toString();
                String selectedLab = labSpinner.getSelectedItem().toString();

                // Allocate lab to faculty in Firestore
                allocateLab(selectedFaculty, selectedLab);
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AllocateLabActivity.this, android.R.layout.simple_spinner_item, facultyList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    facultySpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(AllocateLabActivity.this, "Error fetching faculty: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void fetchLabs() {
        db.collection("labs").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    labList = new ArrayList<>();
                    for (DocumentSnapshot document : task.getResult()) {
                        String labName = document.getString("labName");
                        labList.add(labName);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(AllocateLabActivity.this, android.R.layout.simple_spinner_item, labList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    labSpinner.setAdapter(adapter);
                } else {
                    Toast.makeText(AllocateLabActivity.this, "Error fetching labs: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void allocateLab(String facultyName, String labName) {
        Map<String, Object> allocation = new HashMap<>();
        allocation.put("facultyName", facultyName);
        allocation.put("labName", labName);

        db.collection("labAllocations").document(facultyName + "_" + labName)
                .set(allocation)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AllocateLabActivity.this, "Lab allocated successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AllocateLabActivity.this, "Error allocating lab: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
