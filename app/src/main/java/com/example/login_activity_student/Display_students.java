package com.example.login_activity_student;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Display_students extends AppCompatActivity implements OnItemClickListener {

    private student_Adapter adapter;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private ArrayList<Students> mStudentList;
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_students);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(Display_students.this));
        mStudentList = new ArrayList<>();
        adapter = new student_Adapter(mStudentList,Display_students.this);
        recyclerView.setAdapter(adapter);

        studentId =getIntent().getStringExtra("studentId");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("StudentsInfo");

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ArrayList<Students> studentList = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Students student = snapshot.getValue(Students.class);
                        studentList.add(student);
                    }
                    adapter.setStudents(studentList);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //Log.e(TAG, "Failed to read value.", databaseError.toException());
                }
            });

            adapter.setOnItemClickListener(new student_Adapter.OnItemClickListener() {
                @Override
                public void onItemClick(Students students, String currentUserUid) {
                    Intent intent = new Intent(Display_students.this, Marks_activity.class);
                    intent.putExtra("currentUserUid", currentUserUid);
                    startActivity(intent);
                    Intent intent1 = new Intent(Display_students.this, Marks_activity.class);
                    intent.putExtra("selectedStudentUid", students.getUser_id());
                    startActivity(intent1);
                    Toast.makeText(Display_students.this, "Clicked on " + students.getStudent_name(), Toast.LENGTH_LONG).show();
                    finish();
                }
            });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Perform your desired action here
        // For example, navigate back to the previous activity using Intent
        Intent intent = new Intent(Display_students.this, Lecturer_Home.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemClick(int position) {
//        Students clickedStudent = mStudentList.get(position);
//        adapter.setOnItemClickListener(clickedStudent, studentId);
    }
}
