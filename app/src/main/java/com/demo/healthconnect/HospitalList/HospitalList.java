package com.demo.healthconnect.HospitalList;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.healthconnect.DoctorList.AddDoctor;
import com.demo.healthconnect.DoctorList.DoctorList;
import com.demo.healthconnect.R;
import com.demo.healthconnect.listData.MyListAdapter;
import com.demo.healthconnect.listData.MyListAdapterArray;
import com.demo.healthconnect.listData.MyListData;
import com.demo.healthconnect.listnerHandler.InternalPermissionListner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HospitalList extends AppCompatActivity {
    ListView listView;
    String[] listItem;
    ArrayList<String> DrInfo = new ArrayList<>();
    ArrayList<String> DrInfoKey = new ArrayList<>();
    ArrayList<String> latitude = new ArrayList<>();
    ArrayList<String> longitude = new ArrayList<>();
    ArrayList<String> label = new ArrayList<>();
    Button add_hospital, edit, delete;
    FirebaseUser currentUser;//used to store current user of account
    FirebaseAuth mAuth;//Used for firebase authentication
    ArrayAdapter<String> madapter;
    MyListData[] myListData;
    String[] myListDataString;
    RecyclerView recyclerView;
    MyListAdapterArray adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_list);

        ProgressDialog progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
//        progress.show();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        listView=(ListView)findViewById(R.id.listView_dr);
        add_hospital =(Button) findViewById(R.id.add_hospital);
        edit =(Button) findViewById(R.id.edit);
        delete =(Button) findViewById(R.id.delete);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        if (currentUser.getEmail().contains("admin")){
            add_hospital.setVisibility(View.VISIBLE);
        }else {
            add_hospital.setVisibility(View.GONE);
        }

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("HospitalList");

        table_user.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 for (DataSnapshot ds : dataSnapshot.getChildren()) {
                     try {
                         //progress.dismiss();
                         String hospital_name = ds.child("hospital_name").getValue().toString();
                         String hospital_number = ds.child("hospital_number").getValue().toString();
                         String hospital_no_of_staff = ds.child("hospital_no_of_staff").getValue().toString();
                         String hospital_total_bed = ds.child("hospital_total_bed").getValue().toString();
                         String hospital_available_bed = ds.child("hospital_available_bed").getValue().toString();
                         String hospital_labs = ds.child("hospital_labs").getValue().toString();
                         String hospital_Oxygen_cylinder = ds.child("hospital_Oxygen_cylinder").getValue().toString();
                         String ventilator = ds.child("ventilator").getValue().toString();


                         String info = "Hospital Name : " + hospital_name + "\nContact No : " + hospital_number +
                                 "\nNo Of Staff : " + hospital_no_of_staff + "\nTotal Beds : " + hospital_total_bed +
                                 "\nAvailable Beds : " + hospital_available_bed + "\nLabs : " + hospital_labs +
                                 "\nOxygen Cylinders : " + hospital_Oxygen_cylinder + "\nVentilators : " + ventilator;
                         DrInfo.add(info);
                         DrInfoKey.add(ds.getKey());
                         latitude.add(ds.child("latitude").getValue().toString());
                         longitude.add(ds.child("longitude").getValue().toString());
                         label.add(hospital_name);


                         //progress.dismiss();

                     }catch (Exception e){
                         //progress.dismiss();
                     }
                 }

                 setRecyclerData();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 //progress.dismiss();

             }
        });

        add_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MainIntent = new Intent(HospitalList.this, AddHospital.class);
                MainIntent.putExtra("flag","add");
                MainIntent.putExtra("key","key");
                startActivity(MainIntent);
            }
        });

        MyListAdapterArray.internalPermissionListner = new InternalPermissionListner() {

            @Override
            public void goToLocation(String key) {
                openMap(key);
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
            add_hospital.setVisibility(View.VISIBLE);
        }else {
            add_hospital.setVisibility(View.GONE);
        }
    }

    public void setRecyclerData(){
//        adapter = new MyListAdapter(myListData);
        adapter = new MyListAdapterArray(DrInfo,DrInfoKey,"hospital");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void openUpdateDataActivity(String key) {
        Intent MainIntent = new Intent(HospitalList.this, AddHospital.class);
        MainIntent.putExtra("flag","edit");
        MainIntent.putExtra("key",key);
        startActivity(MainIntent);
    }

    public void openMap(String key){

        Log.e("HEALTH-CONNECT-LOGGER", "ARRAY-LIST-SIZE :: "+latitude.size());
        for (int i = 0; i < latitude.size(); i++) {
            Log.e("HEALTH-CONNECT-LOGGER", "ARRAY-LIST :: "+i + " "+latitude.get(i));
        }

        String lat = latitude.get(Integer.parseInt(key));
        String lng = longitude.get(Integer.parseInt(key));
        String labelS = label.get(Integer.parseInt(key));

        Uri mapUri = Uri.parse("geo:0,0?q="+lat + ","+lng + "("+labelS+")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void deleteFirebaseObject(String key){
        Log.e("Token 3", key);
        Toast.makeText(HospitalList.this, "Record Deleted", Toast.LENGTH_SHORT).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("HospitalList")
                .child(key)
                .removeValue();

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}