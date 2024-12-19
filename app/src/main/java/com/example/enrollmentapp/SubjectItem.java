package com.example.enrollmentapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SubjectItem extends RecyclerView.Adapter<SubjectItem.SubjectViewHolder> {

    private final List<Subject> subjectList;
    private final OnSubjectSelectListener listener;

    public SubjectItem(List<Subject> subjectList, OnSubjectSelectListener listener) {
        this.subjectList = subjectList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = subjectList.get(position);
        holder.tvSubjectName.setText(subject.getName());
        holder.tvSubjectCredits.setText(subject.getCredits() + " Credits");
        holder.cbSelectSubject.setChecked(subject.isSelected());

        holder.cbSelectSubject.setOnCheckedChangeListener((buttonView, isChecked) -> {
            subject.setSelected(isChecked);
            listener.onSubjectSelected(subject);
        });
    }

    @Override
    public int getItemCount() {
        return subjectList.size();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder {
        TextView tvSubjectName, tvSubjectCredits;
        CheckBox cbSelectSubject;

        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubjectName = itemView.findViewById(R.id.tvSubjectName);
            tvSubjectCredits = itemView.findViewById(R.id.tvSubjectCredits);
            cbSelectSubject = itemView.findViewById(R.id.cbSelectSubject);
        }
    }

    public interface OnSubjectSelectListener {
        void onSubjectSelected(Subject subject);
    }
}

