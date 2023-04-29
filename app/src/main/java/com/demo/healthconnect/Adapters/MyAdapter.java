package com.demo.healthconnect.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.demo.healthconnect.MapsActivity.MapsActivity;
import com.demo.healthconnect.R;

import java.util.List;


public class MyAdapter extends ArrayAdapter<BottomList> {
    //the tutorial list that will be displayed
    private List<BottomList> bottomListList;
    private Context mCtx;
    //here we are getting the tutoriallist and context
    //so while creating the object of this adapter class we need to give tutoriallist and context
    public MyAdapter(List<BottomList> bottomListList, Context mCtx) {
        super(mCtx, R.layout.list_item_ambulance, bottomListList);
        this.bottomListList = bottomListList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        ViewHolder holder;
        LayoutInflater inflater = LayoutInflater.from(mCtx);

//        if (TransactionHistory.TxnTyp.equalsIgnoreCase("ledger")) {
//            convertView = inflater.inflate(R.layout.list_item_ambulance, null, true);
//        }else {
//            convertView = inflater.inflate(R.layout.list_item_aadhar, null, true);
//        }

        if (MapsActivity.ambORhospital == true) {
            convertView = inflater.inflate(R.layout.list_item_ambulance, null, true);
        }else {
            convertView = inflater.inflate(R.layout.list_item_hospital, null, true);
        }


        holder = new ViewHolder();
        //getting text views
        holder.driver_name = convertView.findViewById(R.id.driver_name);
        holder.vehicle_number = convertView.findViewById(R.id.vehicle_number);
        holder.km = convertView.findViewById(R.id.km);

        convertView.setTag(holder);
        //Getting the tutorial for the specified position
        BottomList bottomList = bottomListList.get(position);
        String driver_name = bottomList.getDriver_name();
        String vehicle_number = bottomList.getVehicle_number();
        String km = bottomList.getKm();

        holder.driver_name.setText(driver_name);
        holder.vehicle_number.setText(vehicle_number);
        holder.km.setText(km);

//        if (TransactionHistory.TxnTyp.equalsIgnoreCase("ledger")) {
//            String CRDR = bottomList.getCRDR();
//            String ClosingBalance = bottomList.getClosingBalance();
//            String TypeOfTrnx = bottomList.getTypeOfTrnx();
//
//            if (CRDR.equals("Cr")) {
//                holder.CRDR.setText("CREDIT");
//                holder.CRDR.setTextColor(Color.parseColor("#008000"));
//            } else {
//                holder.CRDR.setText("DEBIT");
//                holder.CRDR.setTextColor(Color.parseColor("#FF0000"));
//            }
//
//            holder.ClosingBalance.setText(ClosingBalance);
//            holder.TypeOfTrnx.setText(TypeOfTrnx);
//
//        }
//        else {
//            String AadharNum = bottomList.getAadharNum();
//            String TxnStatus = bottomList.getTxnStatus();
//            holder.AadharNum.setText(AadharNum);
//            holder.TxnStatus.setText(TxnStatus);
//
//            if (TxnStatus.equalsIgnoreCase("FAILURE")){
//                holder.TxnStatus.setTextColor(Color.parseColor("#FF0000"));
//            }else if (TxnStatus.equalsIgnoreCase("SUCCESS")){
//                holder.TxnStatus.setTextColor(Color.parseColor("#008000"));
//            }else if (TxnStatus.equalsIgnoreCase("Pending")){
//                holder.TxnStatus.setTextColor(Color.parseColor("#FFA500"));
//            }
//
//        }

        return convertView;
    }
    static class ViewHolder {
        TextView driver_name;
        TextView vehicle_number;
        TextView km;

    }
}

