package com.example.timetablemanagement;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GenerateTimetableActivity extends AppCompatActivity {

    private TableLayout timetableTable;
    private Button generateTimetableButton;
    private FirebaseFirestore db;
    private static final String COLLECTION_NAME = "allocationsubjects";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_timetable);

        timetableTable = findViewById(R.id.timetableTable);
        generateTimetableButton = findViewById(R.id.generateTimetableButton);
        db = FirebaseFirestore.getInstance();

        generateTimetableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateTimetable();
            }
        });
    }

    private void generateTimetable() {
        clearTimetable();

        // Array to hold day names
        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri"};

        // Create header row for hour indices (1 to 6)
        TableRow headerRow = new TableRow(this);
        headerRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        ));

        // Add empty cell for the top-left corner
        TextView emptyCell = new TextView(this);
        emptyCell.setLayoutParams(new TableRow.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
        ));
        headerRow.addView(emptyCell);

        // Add hour headers (1 to 6)
        for (int hour = 1; hour <= 6; hour++) {
            TextView hourCell = new TextView(this);
            hourCell.setLayoutParams(new TableRow.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f
            ));
            hourCell.setPadding(8, 8, 8, 8);
            hourCell.setTextSize(16);
            hourCell.setText(String.valueOf(hour));
            headerRow.addView(hourCell);
        }

        // Add header row to the timetable table
        timetableTable.addView(headerRow);

        // Loop through each day (Mon to Fri)
        for (int dayIndex = 0; dayIndex < 5; dayIndex++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            ));

            // Add day name cell (Mon to Fri)
            TextView dayCell = new TextView(this);
            dayCell.setLayoutParams(new TableRow.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1.0f
            ));
            dayCell.setPadding(8, 8, 8, 8);
            dayCell.setTextSize(16);
            dayCell.setText(days[dayIndex]);
            row.addView(dayCell);

            // Loop through each hour (1 to 6)
            for (int hour = 1; hour <= 6; hour++) {
                TextView cell = new TextView(this);
                cell.setLayoutParams(new TableRow.LayoutParams(
                        0,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        1.0f
                ));
                cell.setPadding(8, 8, 8, 8);
                cell.setTextSize(16);
                cell.setText("Loading..."); // Placeholder text

                // Generate random subject allocation
                fetchRandomSubject(dayIndex, hour - 1, cell); // Adjust hour to index (0-based)

                row.addView(cell);
            }

            // Add row to the timetable table
            timetableTable.addView(row);
        }
    }

    String[] arr = { "subjects", "lab"};
    private void fetchRandomSubject(final int dayIndex, final int hourIndex, final TextView cell) {
        db.collection("subjects")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();
                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                int randomIndex = new Random().nextInt(querySnapshot.size());

                                DocumentSnapshot document = querySnapshot.getDocuments().get(randomIndex);
                                String subjectName = document.getString("subjectName");
                                cell.setText(subjectName);
                            } else {
                                cell.setText("No subjects found");
                            }
                        } else {
                            cell.setText("Error fetching subjects");
                            Log.e(TAG, "Error fetching subjects", task.getException());
                        }
                    }
                });
    }

    private void clearTimetable() {
        // Clear existing rows in timetableTable
        timetableTable.removeAllViews();
    }


}
