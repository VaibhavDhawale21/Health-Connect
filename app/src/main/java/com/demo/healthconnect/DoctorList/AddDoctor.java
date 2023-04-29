package com.demo.healthconnect.DoctorList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.healthconnect.Login.Login;
import com.demo.healthconnect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddDoctor extends AppCompatActivity {

    EditText doctor_name, speciality,degree,gender,age,email,contactNo;
    Button add_doctor;
    TextView logout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doctor);

        doctor_name = findViewById(R.id.doctor_name);
        speciality = findViewById(R.id.speciality);
        degree = findViewById(R.id.degree);
        gender = findViewById(R.id.gender);
        age = findViewById(R.id.age);
        email = findViewById(R.id.email);
        contactNo = findViewById(R.id.contactNo);
        add_doctor = findViewById(R.id.add_doctor);
        logout = findViewById(R.id.logout);

        Intent intent = getIntent();
        String flag = intent.getStringExtra("flag");
        String key = intent.getStringExtra("key");

        if (flag.equalsIgnoreCase("edit")){

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_user = database.getReference("DrList");

            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        try {
                            if (key.equals(ds.getKey())) {
                                String DrNameS = ds.child("DrName").getValue().toString();
                                String SpecialityS = ds.child("Speciality").getValue().toString();
                                String DegreeS = ds.child("Degree").getValue().toString();
                                String GenderS = ds.child("Gender").getValue().toString();
                                String AgeS = ds.child("Age").getValue().toString();
                                String EmailIDS = ds.child("EmailID").getValue().toString();
                                String ContactNoS = ds.child("ContactNo").getValue().toString();

                                doctor_name.setText(DrNameS);
                                speciality.setText(SpecialityS);
                                degree.setText(DegreeS);
                                gender.setText(GenderS);
                                age.setText(AgeS);
                                email.setText(EmailIDS);
                                contactNo.setText(ContactNoS);

                            }
                        }catch (Exception e){

                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //progress.dismiss();

                }
            });
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DrList").push();

        add_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String drName = doctor_name.getText().toString().trim();
                String spe = speciality.getText().toString();
                String degreeS = degree.getText().toString();
                String genderS = gender.getText().toString();
                String ageS = age.getText().toString();
                String emailS = email.getText().toString();
                String contactNoS = contactNo.getText().toString();

                if(TextUtils.isEmpty(drName))
                {
                    Toast.makeText(AddDoctor.this,"Please enter Doctor Name",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(spe))
                {
                    Toast.makeText(AddDoctor.this,"Please enter Speciality",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(degreeS))
                {
                    Toast.makeText(AddDoctor.this,"Please enter Speciality",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(genderS))
                {
                    Toast.makeText(AddDoctor.this,"Please enter Doctor Name",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(ageS))
                {
                    Toast.makeText(AddDoctor.this,"Please enter Speciality",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(emailS))
                {
                    Toast.makeText(AddDoctor.this,"Please enter Doctor Name",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(contactNoS))
                {
                    Toast.makeText(AddDoctor.this,"Please enter Doctor Name",Toast.LENGTH_SHORT).show();
                } else {
                    if (flag.equalsIgnoreCase("edit")) {
                        DatabaseReference myRef = database.getReference("DrList").child(key);
                        myRef.child("DrName").setValue(drName);
                        myRef.child("Speciality").setValue(spe);
                        myRef.child("Degree").setValue(degreeS);
                        myRef.child("Gender").setValue(genderS);
                        myRef.child("Age").setValue(ageS);
                        myRef.child("EmailID").setValue(emailS);
                        myRef.child("ContactNo").setValue(contactNoS);
                    }else {
                        myRef.child("DrName").setValue(drName);
                        myRef.child("Speciality").setValue(spe);
                        myRef.child("Degree").setValue(degreeS);
                        myRef.child("Gender").setValue(genderS);
                        myRef.child("Age").setValue(ageS);
                        myRef.child("EmailID").setValue(emailS);
                        myRef.child("ContactNo").setValue(contactNoS);
                    }
                    Toast.makeText(AddDoctor.this,"Added Successfully",Toast.LENGTH_SHORT).show();

                    doctor_name.setText("");
                    speciality.setText("");
                    degree.setText("");
                    gender.setText("");
                    age.setText("");
                    email.setText("");
                    contactNo.setText("");
                }


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
        Intent MainIntent = new Intent(AddDoctor.this, Login.class);
        startActivity(MainIntent);
    }
}