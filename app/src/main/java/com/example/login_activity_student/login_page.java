package com.example.login_activity_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseUser;

public class login_page extends AppCompatActivity {
    ImageButton back;
    private EditText edlemail, edlpassword;
    private Button sign_in;
    private FirebaseAuth mAuth;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        back = (ImageButton) findViewById(R.id.imgbtn_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();

        edlemail = (EditText) findViewById(R.id.edlemail);
        edlpassword = (EditText) findViewById(R.id.edlpassword);
        sign_in = (Button) findViewById(R.id.btn_signin);

        sign_in.setOnClickListener(new View.OnClickListener() {
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
        email = edlemail.getText().toString();
        password = edlpassword.getText().toString();

        if (email.isEmpty()) {
            edlemail.setError("Email is required!!");
            edlemail.requestFocus();
            return;
        }
        // email validation
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edlemail.setError("Invalid email address");
            edlemail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            edlpassword.setError("Password is required!!");
            edlpassword.requestFocus();
            return;

        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(login_page.this, MainActivity.class);
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