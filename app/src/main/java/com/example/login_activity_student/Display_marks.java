package com.example.login_activity_student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Display_marks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_marks);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Display_marks.this,Student_Home.class);
        startActivity(intent);
        finish();
    }

}