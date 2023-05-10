package com.example.login_activity_student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class marks_adapter extends RecyclerView.Adapter<marks_adapter.ViewHolder> {

    public ArrayList<Marks> marksList;

    public marks_adapter(ArrayList<Marks> marksList) {
        this.marksList = marksList;
    }

    @NonNull
    @Override
    public marks_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_marks_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull marks_adapter.ViewHolder holder, int position) {
        Marks marks = marksList.get(position);
        holder.subject1.setText(marks.getSubject1());
        holder.subject2.setText(marks.getSubject2());
        holder.subject3.setText(marks.getSubject3());
        holder.subject4.setText(marks.getSubject4());
        holder.subject5.setText(marks.getSubject5());
        holder.subject6.setText(marks.getSubject6());
        holder.subject7.setText(marks.getSubject7());
        holder.subject8.setText(marks.getSubject8());
        holder.subject9.setText(marks.getSubject9());
    }

    @Override
    public int getItemCount() {
        return marksList.size();
    }

    public void setMarks(ArrayList<Marks> markslist) {
        marksList = markslist;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subject1;
        public TextView subject2;
        public TextView subject3;
        public TextView subject4;
        public TextView subject5;
        public TextView subject6;
        public TextView subject7;
        public TextView subject8;
        public TextView subject9;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            subject1 = itemView.findViewById(R.id.subject1);
            subject2 = itemView.findViewById(R.id.subject2);
            subject3 = itemView.findViewById(R.id.subject3);
            subject4 = itemView.findViewById(R.id.subject4);
            subject5 = itemView.findViewById(R.id.subject5);
            subject6 = itemView.findViewById(R.id.subject6);
            subject7 = itemView.findViewById(R.id.subject7);
            subject8 = itemView.findViewById(R.id.subject8);
            subject9 = itemView.findViewById(R.id.subject9);
        }
    }
}
