package com.demo.healthconnect.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.demo.healthconnect.DoctorList.DoctorList;
import com.demo.healthconnect.HospitalList.HospitalList;
import com.demo.healthconnect.LabTest.LabTestList;
import com.demo.healthconnect.Login.Login;
import com.demo.healthconnect.R;
import com.google.firebase.auth.FirebaseAuth;

public class AdminPanel extends AppCompatActivity {

    Button manage_hospital, manage_doctor, manage_lab_test, logout;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        manage_hospital = findViewById(R.id.manage_hospital);
        manage_doctor = findViewById(R.id.manage_doctor);
        manage_lab_test = findViewById(R.id.manage_lab_test);
        logout = findViewById(R.id.logout);

        manage_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MainIntent = new Intent(AdminPanel.this, HospitalList.class);
                startActivity(MainIntent);
            }
        });

        manage_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MainIntent = new Intent(AdminPanel.this, DoctorList.class);
                startActivity(MainIntent);
            }
        });

        manage_lab_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MainIntent = new Intent(AdminPanel.this, LabTestList.class);
                startActivity(MainIntent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                sendToLoginActivity();
            }
        });
    }

    private void sendToLoginActivity() {
        finish();
        Intent MainIntent = new Intent(AdminPanel.this, Login.class);
        startActivity(MainIntent);
    }
}