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

import java.util.Objects;
import java.util.regex.Pattern;

public class register_page extends AppCompatActivity {
    private TextView textView;
    private ImageButton back;
    private EditText edUSN, edname, edemail, edyear, eddept, edsection, edpassword, edconfirm_password;
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
        edUSN = (EditText)findViewById(R.id.edUSN);
        edname = (EditText)findViewById(R.id.edname);
        edemail = (EditText)findViewById(R.id.edemail);
        eddept = (EditText)findViewById(R.id.eddept);
        edyear = (EditText)findViewById(R.id.edyear);
        edsection = (EditText)findViewById(R.id.edSection);
        edpassword = (EditText)findViewById(R.id.edpassword);
        edconfirm_password = (EditText)findViewById(R.id.edcon_password);
        sign_up = (Button) findViewById(R.id.btn_signup);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("StudentsInfo");


        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {
        String USN, name, email, year, dept, section, password, confirmpassword;
        //String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        USN = edUSN.getText().toString();
        name = edname.getText().toString();
        email = edemail.getText().toString();
        dept = eddept.getText().toString();
        year = edyear.getText().toString();
        section = edsection.getText().toString();
        password = edpassword.getText().toString();
        confirmpassword = edconfirm_password.getText().toString();

        if (USN.isEmpty()) {
            edUSN.setError("USN is required!");
            edUSN.requestFocus();
            return;
        }

        if (name.isEmpty()) {
            edname.setError("Name is required!");
            edname.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            edemail.setError("Email is required!!");
            edemail.requestFocus();
            return;
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
            return;
        }

        if (year.isEmpty()) {
            edyear.setError("Year is required!!");
            edyear.requestFocus();
            return;
        }

        if (section.isEmpty()) {
            edsection.setError("Section is required!!");
            edsection.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edpassword.setError("Password is required!!");
            edpassword.requestFocus();
            return;
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
                    Students student = new Students(USN,name,email,dept,year,section);
                    student.setStudent_USN(USN);
                    student.setStudent_name(name);
                    student.setStudent_email(email);
                    student.setStudent_dept(dept);
                    student.setStudent_year(year);
                    student.setStudent_section(section);


                    FirebaseDatabase.getInstance().getReference("StudentsInfo").child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid()).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(register_page.this, "Student has registered successfully", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(register_page.this, Marks_activity.class);
                                intent1.putExtra("studentId", FirebaseAuth.getInstance().getCurrentUser().getUid());
                                startActivity(intent1);

                                Intent intent = new Intent(register_page.this, login_page.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(register_page.this, "Failed to register! try again.", Toast.LENGTH_SHORT).show();
                                Toast.makeText(register_page.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(register_page.this, "Failed to register! try again.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(register_page.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}