package com.example.enrollmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentSummaryActivity extends AppCompatActivity {

    private TextView tvTitle, tvTotalCredits;
    private RecyclerView rvEnrolledSubjects;
    private Button btnFinish;

    private List<String> enrolledSubjects;
    private int totalCredits = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_summary);

        // Initialize UI components
        tvTitle = findViewById(R.id.tvTitle);
        tvTotalCredits = findViewById(R.id.tvTotalCredits);
        rvEnrolledSubjects = findViewById(R.id.rvEnrolledSubjects);
        btnFinish = findViewById(R.id.btnFinish);

        // Initialize enrolled subjects list (replace with actual data)
        enrolledSubjects = new ArrayList<>();
        enrolledSubjects.add("Mathematics (3 credits)");
        enrolledSubjects.add("Physics (4 credits)");
        enrolledSubjects.add("Chemistry (3 credits)");
        enrolledSubjects.add("Computer Science (5 credits)");

        // Calculate total credits (replace with actual logic)
        totalCredits = 3 + 4 + 3 + 5;

        // Set total credits display
        tvTotalCredits.setText("Total Credits: " + totalCredits);

        // Setup RecyclerView
        rvEnrolledSubjects.setLayoutManager(new LinearLayoutManager(this));
        rvEnrolledSubjects.setAdapter(new SubjectsAdapter(enrolledSubjects));

        // Finish button click listener
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to HomeActivity
                Toast.makeText(EnrollmentSummaryActivity.this, "Returning to Home", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EnrollmentSummaryActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Close EnrollmentSummaryActivity
            }
        });
    }

    // Adapter class for RecyclerView
    private class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.SubjectsViewHolder> {

        private List<String> subjects;

        public SubjectsAdapter(List<String> subjects) {
            this.subjects = subjects;
        }

        @Override
        public SubjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            return new SubjectsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SubjectsViewHolder holder, int position) {
            String subject = subjects.get(position);
            holder.bind(subject);
        }

        @Override
        public int getItemCount() {
            return subjects.size();
        }

        // ViewHolder class for RecyclerView items
        public class SubjectsViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            public SubjectsViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }

            public void bind(String subject) {
                textView.setText(subject);
            }
        }
    }
}
