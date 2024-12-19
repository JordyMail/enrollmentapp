package com.example.enrollmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private TextView tvWelcomeMessage;
    private Button btnEnrollmentMenu;
    private Button btnViewSummary;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        tvWelcomeMessage = findViewById(R.id.tvWelcomeMessage);
        btnEnrollmentMenu = findViewById(R.id.btnEnrollmentMenu);
        btnViewSummary = findViewById(R.id.btnViewSummary);
        btnLogout = findViewById(R.id.btnLogout);

        // Retrieve student name from intent or shared preferences
        String studentName = getIntent().getStringExtra("STUDENT_NAME");
        if (studentName == null || studentName.isEmpty()) {
            studentName = "Student"; // Default name if not provided
        }
        tvWelcomeMessage.setText("Welcome, " + studentName + "!");

        // Set click listener for enrollment menu button
        btnEnrollmentMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, EnrollmentActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for view summary button
        btnViewSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, EnrollmentSummaryActivity.class);
                startActivity(intent);
            }
        });

        // Set click listener for logout button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to login activity and clear the activity stack
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}
