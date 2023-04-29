package com.demo.healthconnect.listData;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;


public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
    private MyListData[] listdata;
    public static InternalPermissionListner internalPermissionListner;
    ArrayList<String> arrayList;

   // RecyclerView recyclerView;  
    public MyListAdapter(MyListData[] listdata) {  
        this.listdata = listdata;  
    }  
    public MyListAdapter(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
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
        final MyListData myListData = listdata[position];  
        holder.textView.setText(listdata[position].getDescription());

        String key = listdata[position].getKey();

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View view) {  
//                Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
            }  
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Edit Clicked : "+position,Toast.LENGTH_LONG).show();
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(view.getContext(),"Record Deleted"+position,Toast.LENGTH_LONG).show();
                internalPermissionListner.deleteData(key);
            }
        });
    }
  
  
    @Override  
    public int getItemCount() {
//        if(listdata == null) {
//            return 0;
//        }

        return listdata.length;  
    }  
  
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public LinearLayout relativeLayout;
        public Button edit;
        public Button delete;

        public ViewHolder(View itemView) {
            super(itemView);
            this.textView = (TextView) itemView.findViewById(R.id.doctors_list_name);
            relativeLayout = (LinearLayout)itemView.findViewById(R.id.relativeLayout);
            edit = (Button) itemView.findViewById(R.id.edit);
            delete = (Button) itemView.findViewById(R.id.delete);

        }
    }
}  