package com.example.enrollmentapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EnrolledSubjectItem {

    public static class Subject {
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

    public static class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {

        private List<Subject> enrolledSubjects;

        public SubjectAdapter(List<Subject> enrolledSubjects) {
            this.enrolledSubjects = enrolledSubjects;
        }

        @NonNull
        @Override
        public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enrolled_subject, parent, false);
            return new SubjectViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
            Subject subject = enrolledSubjects.get(position);
            holder.tvSubjectName.setText(subject.getName());
            holder.tvSubjectCredits.setText(subject.getCredits() + " Credits");
        }

        @Override
        public int getItemCount() {
            return enrolledSubjects.size();
        }

        public static class SubjectViewHolder extends RecyclerView.ViewHolder {

            TextView tvSubjectName;
            TextView tvSubjectCredits;

            public SubjectViewHolder(@NonNull View itemView) {
                super(itemView);
                tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
                tvSubjectCredits = itemView.findViewById(R.id.tvSubjectCredits);
            }
        }
    }
}
