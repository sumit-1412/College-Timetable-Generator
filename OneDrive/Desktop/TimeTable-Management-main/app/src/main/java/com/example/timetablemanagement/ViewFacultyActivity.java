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

public class ViewFacultyActivity extends AppCompatActivity {

    private ListView facultyListView;
    private FirebaseFirestore db;
    private List<String> facultyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faculty);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        facultyListView = findViewById(R.id.facultyListView);
        facultyList = new ArrayList<>();

        // Load faculties from Firestore
        loadFaculties();
    }

    private void loadFaculties() {
        db.collection("faculties")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            facultyList.clear();
                            // Add header
                            facultyList.add("ID\t\tName\t\tDepartment\t\tPhone No\t\tEmail\t\tQualification");
                            for (DocumentSnapshot document : task.getResult()) {
                                String facultyId = document.getString("facultyId");
                                String facultyName = document.getString("facultyName");
                                String facultyDept = document.getString("facultyDept");
                                String phoneNo = document.getString("phoneNo");
                                String emailId = document.getString("emailId");
                                String qualification = document.getString("qualification");

                                // Add faculty details to list
                                facultyList.add(facultyId + "\t\t" + facultyName + "\t\t" + facultyDept + "\t\t"
                                        + phoneNo + "\t\t" + emailId + "\t\t" + qualification);
                            }
                            // Update ListView with faculties
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(ViewFacultyActivity.this,
                                    android.R.layout.simple_list_item_1, facultyList);
                            facultyListView.setAdapter(adapter);
                        } else {
                            Toast.makeText(ViewFacultyActivity.this, "Error getting faculties: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
