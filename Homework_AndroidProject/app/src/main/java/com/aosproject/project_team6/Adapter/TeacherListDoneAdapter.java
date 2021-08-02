package com.aosproject.project_team6.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aosproject.project_team6.Bean.TeacherListBean;
import com.aosproject.project_team6.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TeacherListDoneAdapter extends RecyclerView.Adapter<TeacherListDoneAdapter.ViewHolder> {

    private Context mContext = null;
    private int layout = 0;

    //data Setting
    private ArrayList<TeacherListBean> data = null;
    private String duedate = "";
    private int dDay = 0 ;
    private Date makeDday = null;
    private Date today = new Date();


    //Con
    public TeacherListDoneAdapter(Context mContext, int layout, ArrayList<TeacherListBean> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
    }

    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder{
        
        public ImageView iv_teacher_subject_icon_done;
        public TextView tv_teacher_workbooktitle_done;
        public TextView tv_teacherDone_wid;
        public TextView tv_teacherDone_WDday;
        
        public ViewHolder(View itemView) {
            super(itemView);
            
            iv_teacher_subject_icon_done = itemView.findViewById(R.id.iv_teacher_subject_icon_done);
            tv_teacher_workbooktitle_done = itemView.findViewById(R.id.tv_teacher_workbooktitle_done);
            tv_teacherDone_wid = itemView.findViewById(R.id.tv_teacherDone_wid);
            tv_teacherDone_WDday = itemView.findViewById(R.id.tv_teacherDone_WDday);
//            iv_teacher_workbookmenu = itemView.findViewById(R.id.iv_teacher_workbookmenu_done);


        }
        
    }
    
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cardview_teacher_done_quiz, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TeacherListDoneAdapter.ViewHolder holder, int position) {

        //디데이 설정하기
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar getToday = Calendar.getInstance();
        getToday.setTime(new Date());
        Calendar cmpdate = Calendar.getInstance();
        String setDday_tv ="";

        duedate = data.get(position).getDuedate();
        try{
            makeDday = sdf.parse(duedate);

            cmpdate.setTime(makeDday);
        }catch (Exception e){
            e.printStackTrace();
        }
        long diffSec = (getToday.getTimeInMillis() - cmpdate.getTimeInMillis()) / 1000;
        long diffDays = diffSec / (24*60*60);//일자수차이
        dDay =(int) diffDays;
        if(dDay < 0){
            setDday_tv = "" + Integer.toString(dDay);
        } else {
            setDday_tv = "+ " + Integer.toString(dDay);
        }

        holder.tv_teacher_workbooktitle_done.setText(data.get(position).getWtitle());
        holder.tv_teacherDone_WDday.setText("D " + setDday_tv);

        if (data.get(position).getSubject().equals("math")){
            holder.iv_teacher_subject_icon_done.setImageResource(R.drawable.icon_math);
        }else{
            holder.iv_teacher_subject_icon_done.setImageResource(R.drawable.icon_eng);

        }
        
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
