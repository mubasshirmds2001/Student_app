package com.example.login_activity_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash_Activity extends AppCompatActivity {

    ImageView logo;
    TextView tagLine, footer, logoText;
    Animation scale, fade;

    private static int SPLASH_SCREEN_TIME = 2000;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        logoText = findViewById(R.id.logo_text);
        tagLine = findViewById(R.id.tagline);
        //

        scale = AnimationUtils.loadAnimation(this, R.anim.scale);
        fade = AnimationUtils.loadAnimation(this, R.anim.fade);

        logo.setAnimation(scale);
        logoText.setAnimation(fade);
        tagLine.setAnimation(fade);
        //footer.setAnimation(fade);

//        new Handler().postDelayed(new Runnable() {
//                                      @Override
//                                      public void run() {
//                                          Intent intent = new Intent(Splash_Activity.this, first_page.class);
//                                          startActivity(intent);
//                                          finish();
//                                      }
//                                  }, SPLASH_SCREEN_TIME
//        );

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                if(user!=null){
                    loginDeciderclass();
                }else
                {
                    startActivity(new Intent(getApplicationContext(),first_page.class));
                    finish();
                }
            }
        }, 2000);
        }

    private void loginDeciderclass() {
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("LecturersInfo").child(uid);
            databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        // User is a lecturer, redirect to lecturer home page
                        startActivity(new Intent(getApplicationContext(),Lecturer_Home.class));
                        finish();
                    }
                    else{
                        // User is not a lecturer, redirect to student home page
                        startActivity(new Intent(getApplicationContext(),Student_Home.class));
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database errors here
                    Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        startActivity(intent);
        moveTaskToBack(true);
        finish();
    }
}