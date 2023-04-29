package com.demo.healthconnect.HospitalList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.demo.healthconnect.Login.Login;
import com.demo.healthconnect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddHospital extends AppCompatActivity {

    EditText hospital_name, hospital_number,hospital_no_of_staff,
            hospital_total_bed,hospital_available_bed,
             hospital_labs,hospital_Oxygen_cylinder, ventilator,
            latitude, longitude;
    Button add_hospital;
    TextView logout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hospital);

        hospital_name = findViewById(R.id.hospital_name);
        hospital_number = findViewById(R.id.hospital_number);
        hospital_no_of_staff = findViewById(R.id.hospital_no_of_staff);
        hospital_total_bed = findViewById(R.id.hospital_total_bed);
        hospital_available_bed = findViewById(R.id.hospital_available_bed);
        hospital_labs = findViewById(R.id.hospital_labs);
        hospital_Oxygen_cylinder = findViewById(R.id.hospital_Oxygen_cylinder);
        ventilator = findViewById(R.id.ventilator);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        add_hospital = findViewById(R.id.add_hospital);
        logout = findViewById(R.id.logout);

        Intent intent = getIntent();
        String flag = intent.getStringExtra("flag");
        String key = intent.getStringExtra("key");

        if (flag.equalsIgnoreCase("edit")){

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_user = database.getReference("HospitalList");

            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        try {
                            if (key.equals(ds.getKey())) {
                                String hospital_nameS = ds.child("hospital_name").getValue().toString();
                                String hospital_numberS = ds.child("hospital_number").getValue().toString();
                                String hospital_no_of_staffS = ds.child("hospital_no_of_staff").getValue().toString();
                                String hospital_total_bedS = ds.child("hospital_total_bed").getValue().toString();
                                String hospital_available_bedS = ds.child("hospital_available_bed").getValue().toString();
                                String hospital_labsS = ds.child("hospital_labs").getValue().toString();
                                String hospital_Oxygen_cylinderS = ds.child("hospital_Oxygen_cylinder").getValue().toString();
                                String ventilatorS = ds.child("ventilator").getValue().toString();
                                String latitudeS = ds.child("latitude").getValue().toString();
                                String longitudeS = ds.child("longitude").getValue().toString();

                                hospital_name.setText(hospital_nameS);
                                hospital_number.setText(hospital_numberS);
                                hospital_no_of_staff.setText(hospital_no_of_staffS);
                                hospital_total_bed.setText(hospital_total_bedS);
                                hospital_available_bed.setText(hospital_available_bedS);
                                hospital_labs.setText(hospital_labsS);
                                hospital_Oxygen_cylinder.setText(hospital_Oxygen_cylinderS);
                                ventilator.setText(ventilatorS);
                                latitude.setText(latitudeS);
                                longitude.setText(longitudeS);

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
        DatabaseReference myRef = database.getReference("HospitalList").push();

        add_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hospital_nameS = hospital_name.getText().toString().trim();
                String hospital_numberS = hospital_number.getText().toString();
                String hospital_no_of_staffS = hospital_no_of_staff.getText().toString();
                String hospital_total_bedS = hospital_total_bed.getText().toString();
                String hospital_available_bedS = hospital_available_bed.getText().toString();
                String hospital_labsS = hospital_labs.getText().toString();
                String hospital_Oxygen_cylinderS = hospital_Oxygen_cylinder.getText().toString();
                String ventilatorS = ventilator.getText().toString();
                String latitudeS = latitude.getText().toString();
                String longitudeS = longitude.getText().toString();

                if(TextUtils.isEmpty(hospital_nameS))
                {
                    Toast.makeText(AddHospital.this,"Please enter Hospital Name",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(hospital_numberS))
                {
                    Toast.makeText(AddHospital.this,"Please enter Contact Number",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(hospital_no_of_staffS))
                {
                    Toast.makeText(AddHospital.this,"Please enter No Of Staff",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(hospital_total_bedS))
                {
                    Toast.makeText(AddHospital.this,"Please enter Total Bed",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(hospital_available_bedS))
                {
                    Toast.makeText(AddHospital.this,"Please enter Available Bed",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(hospital_labsS))
                {
                    Toast.makeText(AddHospital.this,"Please enter Lab",Toast.LENGTH_SHORT).show();
                } else if(TextUtils.isEmpty(hospital_Oxygen_cylinderS))
                {
                    Toast.makeText(AddHospital.this,"Please enter Oxygen Cylinder",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(ventilatorS))
                {
                    Toast.makeText(AddHospital.this,"Please enter ventilator",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(latitudeS))
                {
                    Toast.makeText(AddHospital.this,"Please enter latitude",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(longitudeS))
                {
                    Toast.makeText(AddHospital.this,"Please enter longitude",Toast.LENGTH_SHORT).show();
                } else {
                    if (flag.equalsIgnoreCase("edit")) {
                        DatabaseReference myRef = database.getReference("HospitalList").child(key);
                        myRef.child("hospital_name").setValue(hospital_nameS);
                        myRef.child("hospital_number").setValue(hospital_numberS);
                        myRef.child("hospital_no_of_staff").setValue(hospital_no_of_staffS);
                        myRef.child("hospital_total_bed").setValue(hospital_total_bedS);
                        myRef.child("hospital_available_bed").setValue(hospital_available_bedS);
                        myRef.child("hospital_labs").setValue(hospital_labsS);
                        myRef.child("hospital_Oxygen_cylinder").setValue(hospital_Oxygen_cylinderS);
                        myRef.child("ventilator").setValue(ventilatorS);
                        myRef.child("latitude").setValue(latitudeS);
                        myRef.child("longitude").setValue(longitudeS);
                    }else {
                        myRef.child("hospital_name").setValue(hospital_nameS);
                        myRef.child("hospital_number").setValue(hospital_numberS);
                        myRef.child("hospital_no_of_staff").setValue(hospital_no_of_staffS);
                        myRef.child("hospital_total_bed").setValue(hospital_total_bedS);
                        myRef.child("hospital_available_bed").setValue(hospital_available_bedS);
                        myRef.child("hospital_labs").setValue(hospital_labsS);
                        myRef.child("hospital_Oxygen_cylinder").setValue(hospital_Oxygen_cylinderS);
                        myRef.child("ventilator").setValue(ventilatorS);
                        myRef.child("latitude").setValue(latitudeS);
                        myRef.child("longitude").setValue(longitudeS);
                    }
                    Toast.makeText(AddHospital.this,"Added Successfully",Toast.LENGTH_SHORT).show();



                    hospital_name.setText("");
                    hospital_number.setText("");
                    hospital_no_of_staff.setText("");
                    hospital_total_bed.setText("");
                    hospital_available_bed.setText("");
                    hospital_labs.setText("");
                    hospital_Oxygen_cylinder.setText("");
                    ventilator.setText("");
                    latitude.setText("");
                    longitude.setText("");

                }

            }
        });

    }

}