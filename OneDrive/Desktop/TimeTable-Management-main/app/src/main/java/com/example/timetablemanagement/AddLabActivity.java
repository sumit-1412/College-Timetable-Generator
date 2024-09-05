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

public class AddLabActivity extends AppCompatActivity {

    private EditText labCodeEditText, labNameEditText;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lab);

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize UI components
        labCodeEditText = findViewById(R.id.labCodeEditText);
        labNameEditText = findViewById(R.id.labNameEditText);
        Button submitLabButton = findViewById(R.id.submitLabButton);

        // Set onClickListener for submitLabButton
        submitLabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve input data
                String labCode = labCodeEditText.getText().toString().trim();
                String labName = labNameEditText.getText().toString().trim();

                // Add lab to Firestore
                addLab(labCode, labName);
            }
        });
    }

    private void addLab(String labCode, String labName) {
        Map<String, Object> lab = new HashMap<>();
        lab.put("labCode", labCode);
        lab.put("labName", labName);

        db.collection("labs").document(labCode)
                .set(lab)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddLabActivity.this, "Lab added successfully", Toast.LENGTH_SHORT).show();
                        // Clear input fields
                        labCodeEditText.setText("");
                        labNameEditText.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddLabActivity.this, "Error adding lab: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
