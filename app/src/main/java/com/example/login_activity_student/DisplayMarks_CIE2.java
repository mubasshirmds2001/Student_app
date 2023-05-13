package com.example.login_activity_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DisplayMarks_CIE2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView Stud_usn;
    private marks_adapter adapter;
    private ArrayList<Marks> marksList;
    private DatabaseReference marksRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_marks_cie2);
        Stud_usn = (TextView) findViewById(R.id.student_USN);
        recyclerView =(RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayMarks_CIE2.this));
        marksList = new ArrayList<>();
        adapter = new marks_adapter(marksList);
        recyclerView.setAdapter(adapter);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference marksRef = database.getInstance().getReference("Marks").child("CIE-2");


        marksRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Marks> markslst = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Marks marks = snapshot.getValue(Marks.class);
                    markslst.add(marks);
                }

                // Sort the markslst based on the last two digits of USN
                Collections.sort(markslst, new Comparator<Marks>() {
                    @Override
                    public int compare(Marks m1, Marks m2) {
                        String usn1 = m1.getStudent_USN();
                        String usn2 = m2.getStudent_USN();

                        // Extract the last two digits from the USN
                        int lastDigits1 = Integer.parseInt(usn1.substring(usn1.length() - 2));
                        int lastDigits2 = Integer.parseInt(usn2.substring(usn2.length() - 2));

                        return Integer.compare(lastDigits1, lastDigits2);
                    }
                });

                adapter.setMarks(markslst);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Log.e(TAG, "Failed to read value.", databaseError.toException());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DisplayMarks_CIE2.this,CIE_Display_Decider.class);
        startActivity(intent);
        finish();
    }

}