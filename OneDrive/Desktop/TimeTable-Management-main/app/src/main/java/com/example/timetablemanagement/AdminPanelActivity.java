package com.example.timetablemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminPanelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        Button addFacultyButton = findViewById(R.id.btnAddFaculty);
        Button addSubjectButton = findViewById(R.id.btnAddSubjectDetails);
        Button addLabButton = findViewById(R.id.btnAddLabDetails);
        addFacultyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanelActivity.this, AddFacultyActivity.class);
                startActivity(intent);
            }
        });

        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanelActivity.this, AddSubjectActivity.class);
                startActivity(intent);
            }
        });

        addLabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanelActivity.this, AddLabActivity.class);
                startActivity(intent);
            }
        });

        Button allocateSubjectButton = findViewById(R.id.btnAllocateSubject);
        allocateSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanelActivity.this, AllocateSubjectActivity.class);
                startActivity(intent);
            }
        });

        Button allocateLabButton = findViewById(R.id.btnAllocateLabs);
        allocateLabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanelActivity.this, AllocateLabActivity.class);
                startActivity(intent);
            }
        });

        Button generateTimetable = findViewById(R.id.btnGenerateTimetable);
        generateTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanelActivity.this, GenerateTimetableActivity.class);
                startActivity(intent);
            }
        });

        Button viewSubjectsButton = findViewById(R.id.btnViewSubjects);
        viewSubjectsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanelActivity.this, ViewSubjectsActivity.class);
                startActivity(intent);
            }
        });

        Button viewFacultiesButton = findViewById(R.id.btnViewFaculties);
        viewFacultiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminPanelActivity.this, ViewFacultyActivity.class);
                startActivity(intent);
            }
        });



        // Add other button click listeners here if needed
    }
}
