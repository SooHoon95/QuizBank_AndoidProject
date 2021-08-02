package com.aosproject.project_team6.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aosproject.project_team6.Activity.StudentDoShowActivity;
import com.aosproject.project_team6.Bean.StudentListBean;
import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.StudentWorkbook_NetworkTask;
import com.aosproject.project_team6.NetworkTask.Workbook_NetworkTask;
import com.aosproject.project_team6.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.ViewHolder> {

    //클릭이벤트
    private String myIp = ShareVar.IPAddress;
    private String urlAddr = null;

    //View
    private Context mContext = null;
    private int layout = 0;

    //data변수
    private ArrayList<StudentListBean> data = null;
    private ArrayList<StudentListBean> getDuedate = null;
    private ArrayList<StudentListBean> getdDaay = null;
    private String duedate = "";
    private int dDay = 0;
    private String wId ="";
    private String studentId= "";
    private String test = "";
    private Date makeDday = null;
    private Date today = new Date();



    // Constructor

    public StudentListAdapter(Context mContext, int layout, ArrayList<StudentListBean> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cardview_student_do_quiz, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


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

        Log.v("DMessage", "makeDday : " + makeDday);
        Log.v("DMessage", "today : " + today);

        holder.tv_student_workbooktitle.setText(data.get(position).getWtitle());
        holder.tv_student_wid.setText(Integer.toString(data.get(position).getWid()));
        holder.tv_student_WDday.setText("D " + setDday_tv);

        if (data.get(position).getSubject().equals("math")){
            holder.iv_student_subject_icon.setImageResource(R.drawable.icon_math);
        }else{
            holder.iv_student_subject_icon.setImageResource(R.drawable.icon_eng);

        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_student_subject_icon;
        public TextView tv_student_workbooktitle;
        public TextView tv_student_wid;
        public TextView tv_student_WDday;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_student_subject_icon = itemView.findViewById(R.id.iv_student_subject_icon);
            tv_student_workbooktitle = itemView.findViewById(R.id.tv_student_workbooktitle);
            tv_student_wid = itemView.findViewById(R.id.tv_student_wid);
            tv_student_WDday = itemView.findViewById(R.id.tv_student_WDday);

//            getDuedate(); // workbook 컬럼 정보들 가져오기
//            setDday();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //아이템 뷰 클릭 시 미리 정의한 다른 리스너의 메서드 호출하기
                    if(position != RecyclerView.NO_POSITION){
                        //solve테이블에 학생아이디와 문제집 번호 insert하기
                        InsertSolveTable();

                        Intent intent;
                        intent = new Intent(mContext, StudentDoShowActivity.class);
                        intent.putExtra("studentDoShowTitle",tv_student_workbooktitle.getText().toString());
                        intent.putExtra("studentDoShowWid",tv_student_wid.getText().toString());

                        Log.v("Message", tv_student_workbooktitle.getText().toString() + tv_student_wid.getText().toString());
                        mContext.startActivity(intent);
                    }
                }
            });





        }
        //버튼 액션 >> solve 테이블에 문제집 아이디랑 테이블 집어넣기
        public void InsertSolveTable() {
            String result = null;
            //url 설정
            wId = tv_student_wid.getText().toString();
            studentId = ShareVar.LoginID;
            urlAddr = "http://"+myIp+":8080/studentlist/InsertTb_selove.jsp?student_id=" + studentId + "&wid=" + wId ;
//          http://192.168.0.3:8080/studentlist/InsertTb_selove.jsp?student_id=aaa@naver.com&wid=1 + studentId + "&wid=" + wId ;

            //DB접속
            try{
                StudentWorkbook_NetworkTask networkTask = new StudentWorkbook_NetworkTask(mContext, urlAddr, "Make_Tb_solve");
                Object obj = networkTask.execute().get();
                result = (String) obj;
//                sDoShowInfo = (ArrayList<StudentListBean>) obj;

            }catch (Exception e){
                e.printStackTrace();
                Log.v("AA","Error 1111");
            }

        }//ConnectGetData


//        public void getDuedate(){
//            String result = null;
//            //url 설정
//
//            wId = tv_student_wid.getText().toString();
//            studentId = ShareVar.studentId;
//            urlAddr = "http://"+myIp+":8080/studentlist/SelectDuedate.jsp?student_id=" + studentId ;
//
//            //DB접속
//            try{
//                Workbook_NetworkTask networkTask = new Workbook_NetworkTask(mContext, urlAddr, "getDuedate");
//                Object obj = networkTask.execute().get();
//                getDuedate = (ArrayList<StudentListBean>) obj;
//
////                sDoShowInfo = (ArrayList<StudentListBean>) obj;
//            }catch (Exception e){
//                e.printStackTrace();
//                Log.v("AA","Error 1111");
//            }
//
//        }
//
//        //디데이 가져오기
//        public ArrayList<StudentListBean> setDday(){
//
//            String setDdayUrl = "http://" + myIp + ":8080/studentlist/SelectDday.jsp?duedate=" + duedate;
//
////            http://192.168.2.11:8080/studentlist/SelectDday.jsp?duedate=2021-06-30;
//            String result = null;
//            //DB접속
//            try{
//                Workbook_NetworkTask networkTask = new Workbook_NetworkTask(mContext, setDdayUrl, "D_day");
//                Object obj = networkTask.execute().get();
//                getdDaay = (ArrayList<StudentListBean>) obj;
////                sDoShowInfo = (ArrayList<StudentListBean>) obj;
//            }catch (Exception e){
//                e.printStackTrace();
//                Log.v("AA","Error 1111");
//            }
//                return getdDaay;
//        }//=======
//
//
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}

