package com.example.login_activity_student;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Objects;

public class Display_marks extends AppCompatActivity {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;
    private TextView subject1, subject2, subject3, subject4, subject5, subject6, subject7, subject8, subject9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_marks);

        subject1 = findViewById(R.id.Mark1);
        subject2 = findViewById(R.id.Mark2);
        subject3 = findViewById(R.id.Mark3);
        subject4 = findViewById(R.id.Mark4);
        subject5 = findViewById(R.id.Mark5);
        subject6 = findViewById(R.id.Mark6);
        subject7 = findViewById(R.id.Mark7);
        subject8 = findViewById(R.id.Mark8);
        subject9 = findViewById(R.id.Mark9);

        // Get the current user's ID
        mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        // Set the database reference to the "Marks" node under the current user's ID
        databaseReference = FirebaseDatabase.getInstance().getReference("StudentsInfo").child(userId).child("Marks");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Retrieve subject marks from snapshot
                String subject1Mark = snapshot.child("subject1").getValue(String.class);
                String subject2Mark = snapshot.child("subject2").getValue(String.class);
                String subject3Mark = snapshot.child("subject3").getValue(String.class);
                String subject4Mark = snapshot.child("subject4").getValue(String.class);
                String subject5Mark = snapshot.child("subject5").getValue(String.class);
                String subject6Mark = snapshot.child("subject6").getValue(String.class);
                String subject7Mark = snapshot.child("subject7").getValue(String.class);
                String subject8Mark = snapshot.child("subject8").getValue(String.class);
                String subject9Mark = snapshot.child("subject9").getValue(String.class);

                // Set retrieved marks to corresponding TextViews
                subject1.setText(subject1Mark);
                subject2.setText(subject2Mark);
                subject3.setText(subject3Mark);
                subject4.setText(subject4Mark);
                subject5.setText(subject5Mark);
                subject6.setText(subject6Mark);
                subject7.setText(subject7Mark);
                subject8.setText(subject8Mark);
                subject9.setText(subject9Mark);
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