package com.example.login_activity_student;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Display_marks extends AppCompatActivity {
    private RecyclerView recyclerView;
    private marks_adapter adapter;
    private ArrayList<Marks> marksList;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_marks);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(Display_marks.this));
        marksList = new ArrayList<>();
        adapter = new marks_adapter(marksList);
        recyclerView.setAdapter(adapter);

        String studentId = getIntent().getStringExtra("selectedStudentUid");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Marks");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Marks> marksList = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Marks marks = snapshot1.getValue(Marks.class);  // Use snapshot1 instead of snapshot
                    marksList.add(marks);
                }
                adapter.setMarks(marksList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DisplayMarksActivity", "Failed to read marks.", error.toException());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Display_marks.this,Student_Home.class);
        startActivity(intent);
        finish();
    }

}