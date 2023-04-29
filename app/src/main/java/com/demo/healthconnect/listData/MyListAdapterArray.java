package com.demo.healthconnect.listData;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.healthconnect.R;
import com.demo.healthconnect.listnerHandler.InternalPermissionListner;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;


public class MyListAdapterArray extends RecyclerView.Adapter<MyListAdapterArray.ViewHolder>{
    private MyListData[] listdata;
    public static InternalPermissionListner internalPermissionListner;
    ArrayList<String> arrayList;
    ArrayList<String> arrayListKey;
    String arrayListIdentifier;
    FirebaseUser currentUser;//used to store current user of account
    FirebaseAuth mAuth;//Used for firebase authentication

   // RecyclerView recyclerView;
    public MyListAdapterArray(MyListData[] listdata) {
        this.listdata = listdata;
    }

    public MyListAdapterArray(ArrayList<String> arrayList, ArrayList<String> arrayListKey, String arrayListIdentifier) {
        this.arrayList = arrayList;
        this.arrayListKey = arrayListKey;
        this.arrayListIdentifier = arrayListIdentifier;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {  
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());  
        View listItem= layoutInflater.inflate(R.layout.mylist_doctor, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);  
        return viewHolder;  
    }  
  
    @Override  
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
//        final MyListData myListData = listdata[position];

        try {

            holder.textView.setText(arrayList.get(position));

            String key = arrayListKey.get(position);

            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(view.getContext(), "Edit Clicked : " + position, Toast.LENGTH_LONG).show();
                    internalPermissionListner.editData(key);
                }
            });
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    internalPermissionListner.deleteData(key);
                }
            });

            holder.goToLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("HealthConnect_LOGGER","size :: "+arrayListKey.size());
                    Log.e("HealthConnect_LOGGER","POSITION :: "+position);
                    internalPermissionListner.goToLocation(String.valueOf(position));
                }
            });

            mAuth = FirebaseAuth.getInstance();
            currentUser = mAuth.getCurrentUser();

            if (currentUser.getEmail().contains("admin")) {
                holder.edit.setVisibility(View.VISIBLE);
                holder.delete.setVisibility(View.VISIBLE);
            } else {
                holder.edit.setVisibility(View.GONE);
                holder.delete.setVisibility(View.GONE);
            }

            if (arrayListIdentifier.equalsIgnoreCase("hospital")){
                holder.goToLocation.setVisibility(View.VISIBLE);
            }else {
                holder.goToLocation.setVisibility(View.GONE);
            }

        }catch (Exception e){

        }
    }
  
  
    @Override  
    public int getItemCount() {
        return arrayList.size();
    }
  
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public LinearLayout relativeLayout;
        public Button edit;
        public Button delete;
        public Button goToLocation;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.doctors_list_name);
            relativeLayout = (LinearLayout)itemView.findViewById(R.id.relativeLayout);
            edit = (Button) itemView.findViewById(R.id.edit);
            delete = (Button) itemView.findViewById(R.id.delete);
            goToLocation = (Button) itemView.findViewById(R.id.goToLocation);
        }
    }
}  