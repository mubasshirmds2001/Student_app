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

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_students);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("StudentsInfo");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(Display_students.this));
        adapter = new student_Adapter();

        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new student_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Students students) {
                String studentUsn = students.getStudent_USN();
                Intent intent = new Intent(Display_students.this, Marks_activity.class);
                intent.putExtra("USN", studentUsn);
                startActivity(intent);
                Toast.makeText(Display_students.this, "Clicked on " + students.getStudent_name(), Toast.LENGTH_LONG).show();
                startActivity(new Intent(Display_students.this,Marks_activity.class));
                finish();
            }
        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Students> students = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Students student = snapshot.getValue(Students.class);
                    students.add(student);
                }
                adapter.setStudents(students);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log.e(TAG, "Failed to read value.", databaseError.toException());
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

    }
}