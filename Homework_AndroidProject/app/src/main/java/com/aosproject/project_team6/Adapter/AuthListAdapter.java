package com.aosproject.project_team6.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aosproject.project_team6.Bean.AuthBean;
import com.aosproject.project_team6.R;

import java.util.ArrayList;

public class AuthListAdapter extends BaseAdapter {

    private Context mContext;
    private int layout = 0;
    private ArrayList<AuthBean> data = null;
    private LayoutInflater inflater = null;

    public AuthListAdapter(Context mContext, int layout, ArrayList<AuthBean> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


//    public void AuthlistData(){
//
//        String myIp = ShareVar.IPAddress;
//        String urlAddrUpdate = "http://" + myIp + ":8080/teacherlist/AuthListSelect.jsp";
////        "http://192.168.2.11:8080/teacherlist/AuthListSelect.jsp";
//
//
//        try{
//            Workbook_NetworkTask networkTask = new Workbook_NetworkTask( mContext, urlAddrUpdate, "AuthList");
//            Object obj = networkTask.execute().get();
//            data = (ArrayList<AuthBean>) obj;
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }



    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getSid();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(this.layout, parent,false);
        }

//        AuthlistData();
        TextView textView = convertView.findViewById(R.id.authlist);
        textView.setText(data.get(position).getSid());

        return convertView;
    }//








}//=======================
