package com.example.enrollmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private TextView tvWelcomeMessage;
    private Button btnEnrollmentMenu, btnViewSummary, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize UI components
        tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage);
        btnEnrollmentMenu = findViewById(R.id.btnEnrollmentMenu);
        btnViewSummary = findViewById(R.id.btnViewSummary);
        btnLogout = findViewById(R.id.btnLogout);

        // Get the student's name (from intent or shared preferences)
        Intent intent = getIntent();
        String studentName = intent.getStringExtra("STUDENT_NAME");
        if (studentName == null || studentName.isEmpty()) {
            studentName = "Student"; // Default fallback
        }

        // Set the welcome message
        tvWelcomeMessage.setText("Welcome, " + studentName + "!");

        // Handle Enrollment Menu button click
        btnEnrollmentMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Enrollment Menu Activity
                Intent intent = new Intent(HomeActivity.this, EnrollmentActivity.class);
                startActivity(intent);
            }
        });

        // Handle View Summary button click
        btnViewSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Enrollment Summary Activity
                Intent intent = new Intent(HomeActivity.this, EnrollmentSummaryActivity.class);
                startActivity(intent);
            }
        });

        // Handle Logout button click
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simulate logout process
                Toast.makeText(HomeActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();
                // Navigate back to LoginActivity
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close HomeActivity
            }
        });
    }
}
