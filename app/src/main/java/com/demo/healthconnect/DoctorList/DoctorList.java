package com.demo.healthconnect.DoctorList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.demo.healthconnect.HospitalList.AddHospital;
import com.demo.healthconnect.HospitalList.HospitalList;
import com.demo.healthconnect.R;
import com.demo.healthconnect.listData.MyListAdapter;
import com.demo.healthconnect.listData.MyListAdapterArray;
import com.demo.healthconnect.listData.MyListData;
import com.demo.healthconnect.listnerHandler.InternalPermissionListner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoctorList extends AppCompatActivity {
    ListView listView;
    String[] listItem;
    ArrayList<String> DrInfo = new ArrayList<>();
    ArrayList<String> DrInfoKey = new ArrayList<>();
    ArrayList<String> latitude = new ArrayList<>();
    ArrayList<String> longitude = new ArrayList<>();
    Button add_doctor, edit, delete;
    FirebaseUser currentUser;//used to store current user of account
    FirebaseAuth mAuth;//Used for firebase authentication
    ArrayAdapter<String> adapterCopy;
    MyListData[] myListData;
    RecyclerView recyclerView;
    MyListAdapterArray adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
//        call = (Button) findViewById(R.id.call);

        listView=(ListView)findViewById(R.id.listView_dr);
        add_doctor=(Button) findViewById(R.id.add_doctor);
        edit =(Button) findViewById(R.id.edit);
        delete =(Button) findViewById(R.id.delete);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        if (currentUser.getEmail().contains("admin")){
            add_doctor.setVisibility(View.VISIBLE);
        }else {
            add_doctor.setVisibility(View.GONE);
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("DrList");

        table_user.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 for (DataSnapshot ds : dataSnapshot.getChildren()) {
                     try {
                         progress.dismiss();
                         String DrName = ds.child("DrName").getValue().toString();
                         String Speciality = ds.child("Speciality").getValue().toString();
                         String degree = ds.child("Degree").getValue().toString();
                         String gender = ds.child("Gender").getValue().toString();
                         String age = ds.child("Age").getValue().toString();
                         String email = ds.child("EmailID").getValue().toString();
                         String contactNo = ds.child("ContactNo").getValue().toString();


                         String info = "Doctor Name : " + DrName + "\nSpeciality : "+Speciality +
                                 "\nDegree : " + degree + "\nGender : "+gender +
                                 "\nAge : " + age + "\nEmail ID : "+email +
                                 "\nContact No : " + contactNo ;
                         DrInfo.add(info);
                         DrInfoKey.add(ds.getKey());
                         latitude.add(ds.child("latitude").getValue().toString());
                         longitude.add(ds.child("longitude").getValue().toString());
                    }catch (Exception e){
                         progress.dismiss();
                    }
                 }
                 setRecyclerData();
                 progress.dismiss();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 progress.dismiss();

             }
        });

        add_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MainIntent = new Intent(DoctorList.this, AddDoctor.class);
                MainIntent.putExtra("flag","add");
                MainIntent.putExtra("key","key");
                startActivity(MainIntent);
            }
        });

        MyListAdapterArray.internalPermissionListner = new InternalPermissionListner() {

            @Override
            public void goToLocation(String position) {
                openMap(position);
            }

            @Override
            public void editData(String key) {
                openUpdateDataActivity(key);
            }

            @Override
            public void deleteData(String key) {
                deleteFirebaseObject(key);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (currentUser.getEmail().contains("admin")){
            add_doctor.setVisibility(View.VISIBLE);
        }else {
            add_doctor.setVisibility(View.GONE);
        }
    }

    public void setRecyclerData(){
        adapter = new MyListAdapterArray(DrInfo,DrInfoKey,"doctor");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void openUpdateDataActivity(String key) {
        Intent MainIntent = new Intent(DoctorList.this, AddDoctor.class);
        MainIntent.putExtra("flag","edit");
        MainIntent.putExtra("key",key);
        startActivity(MainIntent);
    }

    public void deleteFirebaseObject(String key){
        Log.e("Token 3", key);
        Toast.makeText(DoctorList.this, "Record Deleted", Toast.LENGTH_SHORT).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("DrList")
                .child(key)
                .removeValue();

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void openMap(String key){
        String lat = latitude.get(Integer.parseInt(key));
        String lng = longitude.get(Integer.parseInt(key));
        Uri mapUri = Uri.parse("geo:0,0?q="+lat + ","+lng + "(label)");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

}