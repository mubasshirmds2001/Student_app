package com.example.login_activity_student;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CIE_Display_Decider extends AppCompatActivity {
    private Button cie1,cie2,cie3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cie_display_decider);
        cie1 = (Button) findViewById(R.id.btn_CIE1);
        cie2 = (Button) findViewById(R.id.btn_CIE2);
        cie3 = (Button) findViewById(R.id.btn_CIE3);

        cie1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CIE_Display_Decider.this,DisplayMarks_CIE1.class);
                startActivity(intent);
                finish();
            }
        });

        cie2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CIE_Display_Decider.this,DisplayMarks_CIE2.class);
                startActivity(intent);
                finish();
            }
        });

        cie3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CIE_Display_Decider.this,DisplayMarks_CIE3.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CIE_Display_Decider.this, Student_Home.class);
        startActivity(intent);
        finish();
    }
}