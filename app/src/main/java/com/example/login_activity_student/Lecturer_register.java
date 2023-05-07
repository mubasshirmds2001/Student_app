package com.example.login_activity_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Lecturer_register extends AppCompatActivity {

    private TextView textView;
    private ImageButton back;
    private EditText ed_lect_name, ed_lect_email, ed_lect_password, ed_lect_confirm_password;
    private Button sign_up_lect;
    private FirebaseAuth mAuth;

    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    Lecturers lecturers;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_register);

        textView= (TextView) findViewById(R.id.lect_login_here);
        back=(ImageButton)findViewById(R.id.imgbtn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Lecturer_register.this,Lecturer_login.class);
                startActivity(intent);
            }
        });

        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        ed_lect_name = (EditText)findViewById(R.id.edlect_name);
        ed_lect_email = (EditText)findViewById(R.id.edlect_email);
        ed_lect_password = (EditText)findViewById(R.id.edlect_password);
        ed_lect_confirm_password = (EditText)findViewById(R.id.edlect_con_password);
        sign_up_lect = (Button) findViewById(R.id.btn_lect_signup);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("LecturersInfo");

        sign_up_lect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {
        String name, email, password, confirmpassword;
        //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        name = ed_lect_name.getText().toString();
        email = ed_lect_email.getText().toString();
        password = ed_lect_password.getText().toString();
        confirmpassword = ed_lect_confirm_password.getText().toString();

        if (name.isEmpty()) {
            ed_lect_name.setError("Name is required!");
            ed_lect_name.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            ed_lect_email.setError("Email is required!!");
            ed_lect_email.requestFocus();
            return;
        }
        // email validation
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ed_lect_email.setError("Invalid email address");
            ed_lect_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            ed_lect_password.setError("Password is required!!");
            ed_lect_password.requestFocus();
            return;
        }
        if (password.length() < 8) {
            ed_lect_confirm_password.setError("Password must be at least 8 characters");
            ed_lect_confirm_password.requestFocus();
            return;
        }

        if (!password.equals(confirmpassword)) {
            ed_lect_confirm_password.setError("Passwords do not match");
            ed_lect_confirm_password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Lecturers lecturers = new Lecturers(name,email);

                    lecturers.setLecturer_name(name);
                    lecturers.setLecturer_email(email);

                    FirebaseDatabase.getInstance().getReference("LecturersInfo").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(lecturers).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(Lecturer_register.this, "Lecturer has registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Lecturer_register.this, Lecturer_login.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(Lecturer_register.this, "Failed to register! try again.", Toast.LENGTH_SHORT).show();
                                Toast.makeText(Lecturer_register.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(Lecturer_register.this, "Failed to register! try again.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(Lecturer_register.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}