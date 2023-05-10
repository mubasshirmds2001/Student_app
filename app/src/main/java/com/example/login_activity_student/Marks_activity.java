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

import java.util.HashMap;
import java.util.Objects;

public class Marks_activity extends AppCompatActivity {

    private EditText mSubject1, mSubject2, mSubject3, mSubject4, mSubject5, mSubject6, mSubject7, mSubject8, mSubject9,mstudentUSN;
    private Button mAddMarks;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;

    private String mCurrentUserId;
    private String mStudentUSN;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        mAddMarks = findViewById(R.id.btn_addMarks);

        mstudentUSN = findViewById(R.id.ed_Stud_USN);
        mSubject1 = findViewById(R.id.Min1);
        mSubject2 = findViewById(R.id.Min2);
        mSubject3 = findViewById(R.id.Min3);
        mSubject4 = findViewById(R.id.Min4);
        mSubject5 = findViewById(R.id.Min5);
        mSubject6 = findViewById(R.id.Min6);
        mSubject7 = findViewById(R.id.Min7);
        mSubject8 = findViewById(R.id.Min8);
        mSubject9 = findViewById(R.id.Min9);

        mAddMarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveMarks();
            }
        });
    }

    private void saveMarks() {
        String studentUSN = mstudentUSN.getText().toString();
        String subject1Marks = mSubject1.getText().toString();
        String subject2Marks = mSubject2.getText().toString();
        String subject3Marks = mSubject3.getText().toString();
        String subject4Marks = mSubject4.getText().toString();
        String subject5Marks = mSubject5.getText().toString();
        String subject6Marks = mSubject6.getText().toString();
        String subject7Marks = mSubject7.getText().toString();
        String subject8Marks = mSubject8.getText().toString();
        String subject9Marks = mSubject9.getText().toString();

        if (TextUtils.isEmpty(studentUSN) || TextUtils.isEmpty(subject1Marks) || TextUtils.isEmpty(subject2Marks) || TextUtils.isEmpty(subject3Marks) ||
                TextUtils.isEmpty(subject4Marks) || TextUtils.isEmpty(subject5Marks) || TextUtils.isEmpty(subject6Marks) ||
                TextUtils.isEmpty(subject7Marks) || TextUtils.isEmpty(subject8Marks) || TextUtils.isEmpty(subject9Marks)) {
            Toast.makeText(Marks_activity.this, "Please enter marks for all subjects", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference marksRef = database.getReference("Marks");
        String marksId = marksRef.push().getKey();

        // Create a Marks object with the entered marks
        Marks marks = new Marks(studentUSN, subject1Marks, subject2Marks, subject3Marks, subject4Marks, subject5Marks, subject6Marks, subject7Marks, subject8Marks, subject9Marks);


        // Set the marks data in the Firebase Realtime Database
        marksRef.child(marksId).setValue(marks)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Handle the success case
                        Toast.makeText(Marks_activity.this, "Marks added successfully", Toast.LENGTH_SHORT).show();
                        // Clear the marks fields after successful addition
                        mSubject1.setText("");
                        mSubject2.setText("");
                        mSubject3.setText("");
                        mSubject4.setText("");
                        mSubject5.setText("");
                        mSubject6.setText("");
                        mSubject7.setText("");
                        mSubject8.setText("");
                        mSubject9.setText("");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the failure case
                        Toast.makeText(Marks_activity.this, "Failed to add marks", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Marks_activity.this, Lecturer_Home.class);
        startActivity(intent);
        finish();
    }

}
