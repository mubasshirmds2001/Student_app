package com.example.login_activity_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Marks_activity extends AppCompatActivity {

    private EditText mSubject1, mSubject2, mSubject3, mSubject4, mSubject5, mSubject6, mSubject7, mSubject8, mSubject9;
    private Button Add_marks;
    private FirebaseAuth mAuth;

    DatabaseReference databaseReference;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        Add_marks = (Button) findViewById(R.id.btn_addMarks);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();

        String userId = mAuth.getCurrentUser().getUid();

//        subject1 = (TextView) findViewById(R.id.spinner_subjects1);
//        subject2 = (TextView) findViewById(R.id.spinner_subjects2);
//        subject3 = (TextView) findViewById(R.id.spinner_subjects3);
//        subject4 = (TextView) findViewById(R.id.spinner_subjects4);
//        subject5 = (TextView) findViewById(R.id.spinner_subjects5);
//        subject6 = (TextView) findViewById(R.id.spinner_subjects6);
//        subject7 = (TextView) findViewById(R.id.spinner_subjects7);
//        subject8 = (TextView) findViewById(R.id.spinner_subjects8);

//        Max1 = (EditText) findViewById(R.id.Max1);
//        Max2 = (EditText) findViewById(R.id.Max2);
//        Max3 = (EditText) findViewById(R.id.Max3);
//        Max4 = (EditText) findViewById(R.id.Max4);
//        Max5 = (EditText) findViewById(R.id.Max5);
//        Max6 = (EditText) findViewById(R.id.Max6);
//        Max7 = (EditText) findViewById(R.id.Max7);
//        Max8 = (EditText) findViewById(R.id.Max8);
//        Max9 = (EditText) findViewById(R.id.Max9);

        mSubject1 = (EditText) findViewById(R.id.Min1);
        mSubject2 = (EditText) findViewById(R.id.Min2);
        mSubject3 = (EditText) findViewById(R.id.Min3);
        mSubject4 = (EditText) findViewById(R.id.Min4);
        mSubject5 = (EditText) findViewById(R.id.Min5);
        mSubject6 = (EditText) findViewById(R.id.Min6);
        mSubject7 = (EditText) findViewById(R.id.Min7);
        mSubject8 = (EditText) findViewById(R.id.Min8);
        mSubject9 = (EditText) findViewById(R.id.Min9);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Add_marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DatabaseReference marksRef = FirebaseDatabase.getInstance().getReference("Marks");
                // Get the marks entered by the user
                String subject1Marks = mSubject1.getText().toString().trim();
                String subject2Marks = mSubject2.getText().toString().trim();
                String subject3Marks = mSubject3.getText().toString().trim();
                String subject4Marks = mSubject4.getText().toString().trim();
                String subject5Marks = mSubject5.getText().toString().trim();
                String subject6Marks = mSubject6.getText().toString().trim();
                String subject7Marks = mSubject7.getText().toString().trim();
                String subject8Marks = mSubject8.getText().toString().trim();
                String subject9Marks = mSubject9.getText().toString().trim();

                // Create a Marks object with the entered marks
                Marks marks = new Marks(subject1Marks, subject2Marks, subject3Marks, subject4Marks, subject5Marks, subject6Marks, subject7Marks, subject8Marks, subject9Marks);

                // Get the student's USN from intent
                String studentUsn = getIntent().getStringExtra("USN");
                // create a reference to the student's marks
                databaseReference = mDatabase.getReference("StudentsInfo").child(userId).child("Marks");
                databaseReference.setValue(marks).addOnSuccessListener(new OnSuccessListener<Void>() {
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
        });
    }
}