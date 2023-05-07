package com.example.login_activity_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Marks_activity extends AppCompatActivity {

    private EditText mSubject1, mSubject2, mSubject3, mSubject4, mSubject5, mSubject6, mSubject7, mSubject8, mSubject9;
    private Button Add_marks;
    private FirebaseAuth mAuth;

    DatabaseReference databaseReference;
    private FirebaseUser user;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        // Retrieve the selected student ID from the intent extras
        Intent intent = getIntent();
        String studentId = intent.getStringExtra("studentId");

        Add_marks = (Button) findViewById(R.id.btn_addMarks);

        mSubject1 = (EditText) findViewById(R.id.Min1);
        mSubject2 = (EditText) findViewById(R.id.Min2);
        mSubject3 = (EditText) findViewById(R.id.Min3);
        mSubject4 = (EditText) findViewById(R.id.Min4);
        mSubject5 = (EditText) findViewById(R.id.Min5);
        mSubject6 = (EditText) findViewById(R.id.Min6);
        mSubject7 = (EditText) findViewById(R.id.Min7);
        mSubject8 = (EditText) findViewById(R.id.Min8);
        mSubject9 = (EditText) findViewById(R.id.Min9);

        Add_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAuth = FirebaseAuth.getInstance();
                user = mAuth.getCurrentUser();
                databaseReference = FirebaseDatabase.getInstance().getReference("StudentsInfo");

                // Check if studentId is null or empty
                if (TextUtils.isEmpty(studentId)) {
                    Toast.makeText(Marks_activity.this, "Error: Student ID is null or empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference selectedStudentRef = databaseReference.child(studentId);
                selectedStudentRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child(studentId).child("Marks").exists()) {
                            Toast.makeText(Marks_activity.this, "Marks have already been added for this student", Toast.LENGTH_SHORT).show();
                        } else {
                            // Get the marks entered by the user
                            String subject1Marks = mSubject1.getText().toString();
                            String subject2Marks = mSubject2.getText().toString();
                            String subject3Marks = mSubject3.getText().toString();
                            String subject4Marks = mSubject4.getText().toString();
                            String subject5Marks = mSubject5.getText().toString();
                            String subject6Marks = mSubject6.getText().toString();
                            String subject7Marks = mSubject7.getText().toString();
                            String subject8Marks = mSubject8.getText().toString();
                            String subject9Marks = mSubject9.getText().toString();

                            // Check if any of the marks fields are empty or null
                            if (TextUtils.isEmpty(subject1Marks) || TextUtils.isEmpty(subject2Marks) || TextUtils.isEmpty(subject3Marks) ||
                                    TextUtils.isEmpty(subject4Marks) || TextUtils.isEmpty(subject5Marks) || TextUtils.isEmpty(subject6Marks) ||
                                    TextUtils.isEmpty(subject7Marks) || TextUtils.isEmpty(subject8Marks) || TextUtils.isEmpty(subject9Marks)) {
                                Toast.makeText(Marks_activity.this, "Please enter marks for all subjects", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            // Create a Marks object with the entered marks
                            Marks marks = new Marks(subject1Marks, subject2Marks, subject3Marks, subject4Marks, subject5Marks, subject6Marks, subject7Marks, subject8Marks, subject9Marks);

                            DatabaseReference newChildRef = selectedStudentRef.child("Marks");
                            newChildRef.setValue(marks).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Marks_activity.this, "Marks saved successfully", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Marks_activity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Marks_activity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Marks_activity.this, Display_students.class);
        startActivity(intent);
        finish();
    }

}
