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

public class SubjectSelectionActivity extends AppCompatActivity {

    private RecyclerView rvSubjects;
    private TextView tvTotalCredits;
    private Button btnSubmit;
    private List<Subject> subjectList;
    private SubjectAdapter subjectAdapter;
    private int totalCredits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_selection);

        // Initialize views
        rvSubjects = findViewById(R.id.rvSubjects);
        tvTotalCredits = findViewById(R.id.tvTotalCredits);
        btnSubmit = findViewById(R.id.btnSubmit);

        // Sample subjects (dummy data)
        subjectList = new ArrayList<>();
        subjectList.add(new Subject("Mathematics", 3));
        subjectList.add(new Subject("Physics", 4));
        subjectList.add(new Subject("Chemistry", 3));
        subjectList.add(new Subject("Biology", 2));

        // Set up RecyclerView
        subjectAdapter = new SubjectAdapter(subjectList, new SubjectAdapter.OnSubjectSelectedListener() {
            @Override
            public void onSubjectSelected(int credits, boolean isSelected) {
                if (isSelected) {
                    totalCredits += credits;
                } else {
                    totalCredits -= credits;
                }
                tvTotalCredits.setText("Total Credits: " + totalCredits);
            }
        });

        rvSubjects.setLayoutManager(new LinearLayoutManager(this));
        rvSubjects.setAdapter(subjectAdapter);

        // Set click listener for submit button
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalCredits == 0) {
                    Toast.makeText(SubjectSelectionActivity.this, "Please select at least one subject.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SubjectSelectionActivity.this, "Enrollment submitted successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SubjectSelectionActivity.this, EnrollmentSummaryActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    // Subject model class
    private static class Subject {
        private String name;
        private int credits;

        public Subject(String name, int credits) {
            this.name = name;
            this.credits = credits;
        }

        public String getName() {
            return name;
        }

        public int getCredits() {
            return credits;
        }
    }

    // RecyclerView Adapter for subjects
    private static class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

        private List<Subject> subjects;
        private OnSubjectSelectedListener listener;
        private List<Boolean> selectedStates;

        public interface OnSubjectSelectedListener {
            void onSubjectSelected(int credits, boolean isSelected);
        }

        public SubjectAdapter(List<Subject> subjects, OnSubjectSelectedListener listener) {
            this.subjects = subjects;
            this.listener = listener;
            this.selectedStates = new ArrayList<>(subjects.size());
            for (int i = 0; i < subjects.size(); i++) {
                selectedStates.add(false);
            }
        }

        @Override
        public SubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(parent.getContext(), android.R.layout.simple_list_item_multiple_choice, null);
            return new SubjectViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SubjectViewHolder holder, int position) {
            Subject subject = subjects.get(position);
            holder.bind(subject, selectedStates.get(position), position);
        }

        @Override
        public int getItemCount() {
            return subjects.size();
        }

        class SubjectViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;
            private View itemView;

            public SubjectViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                textView = itemView.findViewById(android.R.id.text1);
            }

            public void bind(Subject subject, boolean isSelected, int position) {
                textView.setText(subject.getName() + " - " + subject.getCredits() + " Credits");
                itemView.setSelected(isSelected);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean newState = !selectedStates.get(position);
                        selectedStates.set(position, newState);
                        listener.onSubjectSelected(subject.getCredits(), newState);
                        notifyItemChanged(position);
                    }
                });
            }
        }
    }
}
