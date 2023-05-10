package com.example.login_activity_student;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Student_Home extends AppCompatActivity {

    private TextView stud_name;
    private ImageView Marks,Attendence;
    private FirebaseDatabase firebaseDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);

        Marks = (ImageView) findViewById(R.id.imgbtn_Marks);
        Attendence = (ImageView) findViewById(R.id.imgbtn_Attendence);

        Marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Student_Home.this,CIE_decider.class));
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        stud_name = findViewById(R.id.Student_name);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String studentUid = getIntent().getStringExtra("studentUid");
            if (studentUid != null) {
                DatabaseReference databaseRef = firebaseDatabase.getReference("StudentsInfo").child(studentUid);
                databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.d(TAG, "onDataChange: " + snapshot.toString());
                        if (snapshot.exists()) {
                            String name = snapshot.child("student_name").getValue(String.class);
                            Log.d(TAG, "student_name: " + name);
                            if (name != null) {
                                stud_name.setText("Welcome, " + name);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        }
    }
}

