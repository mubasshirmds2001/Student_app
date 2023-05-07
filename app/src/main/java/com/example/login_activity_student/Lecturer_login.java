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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Lecturer_login extends AppCompatActivity {

    ImageButton back;
    private EditText edlect_log_email, edlect_log_password;
    private Button sign_in_lect;
    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecturer_login);

        back = (ImageButton) findViewById(R.id.imgbtn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        edlect_log_email = (EditText) findViewById(R.id.edlect_log_email);
        edlect_log_password = (EditText) findViewById(R.id.edlect_log_password);
        sign_in_lect = (Button) findViewById(R.id.btn_lect_signin);

        sign_in_lect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAccount();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    private void loginUserAccount() {

        String email, password;
        email = edlect_log_email.getText().toString();
        password = edlect_log_password.getText().toString();

        if (email.isEmpty()) {
            edlect_log_email.setError("Email is required!!");
            edlect_log_email.requestFocus();
            return;
        }
        // email validation
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edlect_log_email.setError("Invalid email address");
            edlect_log_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edlect_log_password.setError("Password is required!!");
            edlect_log_password.requestFocus();
            return;

        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(Lecturer_login.this, Lecturer_Home.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    // Registration failed
                    Toast.makeText(
                            getApplicationContext(), "Login failed!!" + " Please try again later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}