package com.demo.healthconnect.LabTest;

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

public class AddLabTest extends AppCompatActivity {

    EditText test_name, charges, lab_name, contactNo, address, time;
    Button add_test;
    TextView logout;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_labtest);

        test_name = findViewById(R.id.test_name);
        charges = findViewById(R.id.charges);
        lab_name = findViewById(R.id.lab_name);
        contactNo = findViewById(R.id.contactNo);
        address = findViewById(R.id.address);
        charges = findViewById(R.id.charges);
        time = findViewById(R.id.time);
        logout = findViewById(R.id.logout);
        add_test = findViewById(R.id.add_test);

        Intent intent = getIntent();
        String flag = intent.getStringExtra("flag");
        String key = intent.getStringExtra("key");

        if (flag.equalsIgnoreCase("edit")){

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            final DatabaseReference table_user = database.getReference("LabTestList");

            table_user.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        try {
                            if (key.equals(ds.getKey())) {
                                String lab_nameS = ds.child("lab_name").getValue().toString();
                                String contactNoS = ds.child("contactNo").getValue().toString();
                                String addressS = ds.child("address").getValue().toString();
                                String timeS = ds.child("time").getValue().toString();
                                String test_nameS = ds.child("test_name").getValue().toString();
                                String chargesS = ds.child("charges").getValue().toString();

                                lab_name.setText(lab_nameS);
                                contactNo.setText(contactNoS);
                                address.setText(addressS);
                                time.setText(timeS);
                                test_name.setText(test_nameS);
                                charges.setText(chargesS);
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
        DatabaseReference myRef = database.getReference("LabTestList").push();

        add_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String test_nameS = test_name.getText().toString().trim();
                String chargesS = charges.getText().toString();
                String lab_nameS = lab_name.getText().toString();
                String contactNoS = contactNo.getText().toString();
                String addressS = address.getText().toString();
                String timeS = time.getText().toString();


                if(TextUtils.isEmpty(lab_nameS))
                {
                    Toast.makeText(AddLabTest.this,"Please enter Lab Name",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(contactNoS))
                {
                    Toast.makeText(AddLabTest.this,"Please enter Contact No",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(addressS))
                {
                    Toast.makeText(AddLabTest.this,"Please enter Address",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(timeS))
                {
                    Toast.makeText(AddLabTest.this,"Please enter Time",Toast.LENGTH_SHORT).show();
                }if(TextUtils.isEmpty(test_nameS))
                {
                    Toast.makeText(AddLabTest.this,"Please enter Test Name",Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(chargesS))
                {
                    Toast.makeText(AddLabTest.this,"Please enter Charges",Toast.LENGTH_SHORT).show();
                }else {
                    if (flag.equalsIgnoreCase("edit")) {
                        DatabaseReference myRef = database.getReference("LabTestList").child(key);
                        myRef.child("lab_name").setValue(lab_nameS);
                        myRef.child("contactNo").setValue(contactNoS);
                        myRef.child("address").setValue(addressS);
                        myRef.child("time").setValue(timeS);
                        myRef.child("test_name").setValue(test_nameS);
                        myRef.child("charges").setValue(chargesS);

                    }else {
                        myRef.child("lab_name").setValue(lab_nameS);
                        myRef.child("contactNo").setValue(contactNoS);
                        myRef.child("address").setValue(addressS);
                        myRef.child("time").setValue(timeS);
                        myRef.child("test_name").setValue(test_nameS);
                        myRef.child("charges").setValue(chargesS);
                    }
                    Toast.makeText(AddLabTest.this,"Added Successfully",Toast.LENGTH_SHORT).show();

                    lab_name.setText("");
                    contactNo.setText("");
                    address.setText("");
                    time.setText("");
                    test_name.setText("");
                    charges.setText("");
                }


            }
        });

    }

}