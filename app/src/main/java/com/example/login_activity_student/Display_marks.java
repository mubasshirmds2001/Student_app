package com.example.login_activity_student;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView Stud_usn;
    private marks_adapter adapter;
    private ArrayList<Marks> marksList;
    private DatabaseReference marksRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_marks);

        Stud_usn = (TextView) findViewById(R.id.student_USN);
        recyclerView =(RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(Display_marks.this));
        marksList = new ArrayList<>();
        adapter = new marks_adapter(marksList);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference marksRef = database.getInstance().getReference("Marks");

        marksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Marks> markslst = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Marks marks = snapshot.getValue(Marks.class);
                    markslst.add(marks);
                }
                adapter.setMarks(markslst);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log.e(TAG, "Failed to read value.", databaseError.toException());
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