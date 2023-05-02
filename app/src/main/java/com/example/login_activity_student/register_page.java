package com.example.login_activity_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class register_page extends AppCompatActivity {
    private TextView textView;
    private ImageButton back;
    private EditText edname, edemail, edyear, eddept, edsection, edpassword, edconfirm_password;
    private Button sign_up;
    private FirebaseAuth mAuth;

    // creating a variable for our
    // Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    // creating a variable for
    // our object class
    Students students;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        textView= (TextView) findViewById(R.id.login_here);
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
                Intent intent = new Intent(register_page.this,login_page.class);
                startActivity(intent);
            }
        });

        // taking FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();

        // below line is used to get the
        // instance of our FIrebase database.
        firebaseDatabase = FirebaseDatabase.getInstance();

        // below line is used to get reference for our database.
        databaseReference = firebaseDatabase.getReference("StudentsInfo");

        // initializing our object
        // class variable.
        students = new Students();

        edname = (EditText)findViewById(R.id.edname);
        edemail = (EditText)findViewById(R.id.edemail);
        eddept = (EditText)findViewById(R.id.eddept);
        edyear = (EditText)findViewById(R.id.edyear);
        edsection = (EditText)findViewById(R.id.edSection);
        edpassword = (EditText)findViewById(R.id.edpassword);
        edconfirm_password = (EditText)findViewById(R.id.edcon_password);
        sign_up = (Button) findViewById(R.id.btn_signup);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {
        String name, email, year, dept, section, password, confirmpassword;
        //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        name = edname.getText().toString();
        email = edemail.getText().toString();
        dept = edyear.getText().toString();
        year = edyear.getText().toString();
        section = edsection.getText().toString();
        password = edpassword.getText().toString();
        confirmpassword = edconfirm_password.getText().toString();

        if (name.isEmpty()) {
            edname.setError("Name is required!");
            edname.requestFocus();
        }

        if (email.isEmpty()) {
            edemail.setError("Email is required!!");
            edemail.requestFocus();
        }
        // email validation
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edemail.setError("Invalid email address");
            edemail.requestFocus();
            return;
        }

        if (dept.isEmpty()) {
            edyear.setError("Dept is required!!");
            edyear.requestFocus();
        }

        if (year.isEmpty()) {
            edyear.setError("Year is required!!");
            edyear.requestFocus();
        }

        if (section.isEmpty()) {
            edsection.setError("Section is required!!");
            edsection.requestFocus();
        }

        if (password.isEmpty()) {
            edpassword.setError("Password is required!!");
            edpassword.requestFocus();
        }
        if (password.length() < 8) {
            edpassword.setError("Password must be at least 8 characters");
            edpassword.requestFocus();
            return;
        }

        if (!password.equals(confirmpassword)) {
            edconfirm_password.setError("Passwords do not match");
            edconfirm_password.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    adddatatofirebase(name,email,dept,year,section);
                    Intent intent = new Intent(register_page.this, login_page.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void adddatatofirebase(String name, String email, String dept, String year, String section) {
        // below lines of code is used to set
        // data in our object class.
        students.setStudent_name(name);
        students.setStudent_email(email);
        students.setStudent_dept(dept);
        students.setStudent_year(year);
        students.setStudent_section(section);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(students);
                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(
                        getApplicationContext(), "Registration failed!!" + " Please try again later", Toast.LENGTH_LONG).show();
            }
        });

    }
}