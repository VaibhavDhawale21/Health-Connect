package com.demo.healthconnect.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.demo.healthconnect.Article.Article;
import com.demo.healthconnect.DoctorList.DoctorList;
import com.demo.healthconnect.HospitalList.HospitalList;
import com.demo.healthconnect.LabTest.LabTestList;
import com.demo.healthconnect.MapsActivity.MapsActivity;
import com.demo.healthconnect.Profile.Profile;
import com.demo.healthconnect.R;
import com.demo.healthconnect.emergency.Emergency;
import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    CardView nearby_hospitals, doctors_list, profile, emergency, article, lab_test;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);

        nearby_hospitals = findViewById(R.id.nearby_hospitals);
        doctors_list = findViewById(R.id.doctors_list);
        profile = findViewById(R.id.profile);
        emergency = findViewById(R.id.emergency);
        article = findViewById(R.id.article);
        lab_test = findViewById(R.id.lab_test);

        nearby_hospitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent MainIntent = new Intent(Dashboard.this, MapsActivity.class);
//                startActivity(MainIntent);
                Intent MainIntent = new Intent(Dashboard.this, HospitalList.class);
                startActivity(MainIntent);
//                Toast.makeText(Dashboard.this, "Work In Progress", Toast.LENGTH_SHORT).show();
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent MainIntent = new Intent(Dashboard.this, Profile.class);
                startActivity(MainIntent);
            }
        });

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent MainIntent = new Intent(Dashboard.this, Emergency.class);
                startActivity(MainIntent);
            }
        });

        doctors_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent MainIntent = new Intent(Dashboard.this, DoctorList.class);
                startActivity(MainIntent);
            }
        });

        article.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent MainIntent = new Intent(Dashboard.this, Article.class);
                startActivity(MainIntent);
            }
        });

        lab_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent MainIntent = new Intent(Dashboard.this, LabTestList.class);
                startActivity(MainIntent);
            }
        });

    }
}