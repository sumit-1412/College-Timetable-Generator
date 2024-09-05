package com.example.timetablemanagement;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewSubjectsActivity extends AppCompatActivity {

    private ListView subjectsListView;
    private FirebaseFirestore db;
    private List<String> subjectsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_subjects);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        subjectsListView = findViewById(R.id.subjectsListView);
        subjectsList = new ArrayList<>();

        // Load subjects from Firestore
        loadSubjects();
    }

    private void loadSubjects() {
        db.collection("subjects")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            subjectsList.clear();
                            // Add headers
                            subjectsList.add("Code\t\tSubject Name");
                            for (DocumentSnapshot document : task.getResult()) {
                                String subjectCode = document.getString("subjectCode");
                                String subjectName = document.getString("subjectName");
                                subjectsList.add(subjectCode + "\t\t\t->\t\t\t\t" + subjectName);
                            }
                            // Update ListView with subjects
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewSubjectsActivity.this,
                                    android.R.layout.simple_list_item_1, subjectsList);
                            subjectsListView.setAdapter(adapter);
                        } else {
                            Toast.makeText(ViewSubjectsActivity.this, "Error getting subjects: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
