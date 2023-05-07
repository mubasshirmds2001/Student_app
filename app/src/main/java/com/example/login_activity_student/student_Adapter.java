package com.example.login_activity_student;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

    public class student_Adapter extends RecyclerView.Adapter<student_Adapter.StudentViewHolder> {

        private ArrayList<Students> mStudentList;
        private OnItemClickListener mlistener;
        private LayoutInflater mInflater;
        private Context context;

        public student_Adapter(Context context,ArrayList<Students> mStudentList) {
            this.context = context;
            this.mStudentList= mStudentList;
        }

        public interface OnItemClickListener {
            void onItemClick(Students students);
        }
        public void setStudents(ArrayList<Students> students) {
            mStudentList = students;
            notifyDataSetChanged();
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            mlistener = listener;
        }

        @NonNull
        @Override
        public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_details_single_row, parent, false);
            return new StudentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StudentViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Students students = mStudentList.get(position);
            holder.student_USN.setText(students.getStudent_USN());
            holder.student_name.setText(students.getStudent_name());
            holder.student_dept.setText(students.getStudent_dept());
            holder.student_year.setText(students.getStudent_year());
            holder.student_section.setText(students.getStudent_section());

            holder.itemView.setTag(students.getStudent_USN());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mlistener != null) {
                        mlistener.onItemClick(students);
                    }

                    // Get the student ID from the tag
                    String studentId = view.getTag().toString();

                    // Create the intent to start the new activity
                    Intent intent = new Intent(context, Marks_activity.class);

                    // Pass the selected student ID as an extra
                    intent.putExtra("studentId", studentId);

                    // Start the activity
                    context.startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            if (mStudentList == null) {
                return 0;
            }
            return mStudentList.size();
        }

        public static class StudentViewHolder extends RecyclerView.ViewHolder{

            public TextView student_USN;
            public TextView student_name;
            public TextView student_dept;
            public TextView student_year;
            public TextView student_section;

            public StudentViewHolder(@NonNull View itemView) {
                super(itemView);

                student_USN = itemView.findViewById(R.id.usn);
                student_name = itemView.findViewById(R.id.name);
                student_dept = itemView.findViewById(R.id.dept);
                student_year = itemView.findViewById(R.id.year);
                student_section = itemView.findViewById(R.id.section);
            }
        }
    }

