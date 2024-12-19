package com.example.enrollmentapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentSummaryActivity extends AppCompatActivity {

    private RecyclerView rvEnrolledSubjects;
    private TextView tvTotalCredits;
    private Button btnFinish;
    private List<String> enrolledSubjects;
    private int totalCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment_summary);

        // Initialize views
        rvEnrolledSubjects = findViewById(R.id.rvEnrolledSubjects);
        tvTotalCredits = findViewById(R.id.tvTotalCredits);
        btnFinish = findViewById(R.id.btnFinish);

        // Sample data for enrolled subjects
        enrolledSubjects = new ArrayList<>();
        enrolledSubjects.add("Mathematics - 3 Credits");
        enrolledSubjects.add("Physics - 4 Credits");
        enrolledSubjects.add("Chemistry - 3 Credits");

        // Calculate total credits (dummy logic)
        totalCredits = 10; // Example value
        tvTotalCredits.setText("Total Credits: " + totalCredits);

        // Set up RecyclerView
        rvEnrolledSubjects.setLayoutManager(new LinearLayoutManager(this));
        EnrolledSubjectsAdapter adapter = new EnrolledSubjectsAdapter(enrolledSubjects);
        rvEnrolledSubjects.setAdapter(adapter);

        // Set click listener for finish button
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnrollmentSummaryActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    // RecyclerView Adapter for displaying enrolled subjects
    private class EnrolledSubjectsAdapter extends RecyclerView.Adapter<EnrolledSubjectsAdapter.SubjectViewHolder> {

        private List<String> subjects;

        public EnrolledSubjectsAdapter(List<String> subjects) {
            this.subjects = subjects;
        }

        @Override
        public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            return new SubjectViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SubjectViewHolder holder, int position) {
            holder.bind(subjects.get(position));
        }

        @Override
        public int getItemCount() {
            return subjects.size();
        }

        class SubjectViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;

            public SubjectViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }

            public void bind(String subject) {
                textView.setText(subject);
            }
        }
    }
}
