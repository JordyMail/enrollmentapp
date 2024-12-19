package com.example.enrollmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class EnrollmentActivity extends AppCompatActivity {

    private Button btnSelectSubjects;
    private Button btnViewSummary;
    private Button btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);

        // Initialize views
        btnSelectSubjects = findViewById(R.id.btnSelectSubjects);
        btnViewSummary = findViewById(R.id.btnViewSummary);
        btnBackHome = findViewById(R.id.btnBackHome);

        // Set click listener for Select Subjects button
        btnSelectSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnrollmentActivity.this, SubjectSelectionActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for View Enrollment Summary button
        btnViewSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnrollmentActivity.this, EnrollmentSummaryActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for Back to Home button
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnrollmentActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
