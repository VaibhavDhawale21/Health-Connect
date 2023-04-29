package com.demo.healthconnect.LabTest;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LabTestList extends AppCompatActivity {
    public static ListView listView;
    String[] listItem;
    ArrayList<String> DrInfo = new ArrayList<>();
    ArrayList<String> DrInfoKey = new ArrayList<>();
    Button add_lab_test;
    public static FirebaseUser currentUser;//used to store current user of account
    public static FirebaseAuth mAuth;//Used for firebase authentication
    public static ArrayAdapter<String> adapterCopy;
    public static Button call;
    public static ProgressDialog progress;
    public static FirebaseDatabase database;
    public static DatabaseReference table_user;
    MyListData[] myListData;
    RecyclerView recyclerView;
    MyListAdapterArray adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labtest_list);

        progress = new ProgressDialog(this);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        listView=(ListView)findViewById(R.id.listView_dr);
        add_lab_test=(Button) findViewById(R.id.add_lab_test);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        if (currentUser.getEmail().contains("admin")){
            add_lab_test.setVisibility(View.VISIBLE);
        }else {
            add_lab_test.setVisibility(View.GONE);
        }

        database = FirebaseDatabase.getInstance();
        table_user = database.getReference("LabTestList");

        table_user.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 Log.e("Token 1", dataSnapshot.getKey());
                 for (DataSnapshot ds : dataSnapshot.getChildren()) {
                     try {
                         progress.dismiss();
                         String lab_name = ds.child("lab_name").getValue().toString();
                         String contactNo = ds.child("contactNo").getValue().toString();
                         String address = ds.child("address").getValue().toString();
                         String time = ds.child("time").getValue().toString();
                         String test_name = ds.child("test_name").getValue().toString();
                         String charges = ds.child("charges").getValue().toString();

                         String info = "Lab Name : " + lab_name + "\nContac No : " + contactNo +
                                 "\nAddress : " + address + "\nTime : " + time +
                                 "\nTest Name : " + test_name + "\nCharges : " + charges;
                         DrInfo.add(info);
                         DrInfoKey.add(ds.getKey());

                     }catch (Exception e){
                         progress.dismiss();
                     }
                 }
//                 setList();
                 progress.dismiss();
                 setRecyclerData();
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 progress.dismiss();
             }
        });

        add_lab_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MainIntent = new Intent(LabTestList.this, AddLabTest.class);
                MainIntent.putExtra("flag","add");
                MainIntent.putExtra("key","key");
                startActivity(MainIntent);
            }
        });

        MyListAdapterArray.internalPermissionListner = new InternalPermissionListner() {

            @Override
            public void goToLocation(String key) {

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
            add_lab_test.setVisibility(View.VISIBLE);
        }else {
            add_lab_test.setVisibility(View.GONE);
        }
    }

    public void setRecyclerData(){
        adapter = new MyListAdapterArray(DrInfo,DrInfoKey,"lab");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void openUpdateDataActivity(String key) {
        Intent MainIntent = new Intent(LabTestList.this, AddLabTest.class);
        MainIntent.putExtra("flag","edit");
        MainIntent.putExtra("key",key);
        startActivity(MainIntent);
    }

    public void deleteFirebaseObject(String key){
        Log.e("Token 3", key);
        Toast.makeText(LabTestList.this, "Record Deleted", Toast.LENGTH_SHORT).show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("LabTestList")
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