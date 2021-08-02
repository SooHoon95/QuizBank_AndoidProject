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

import com.aosproject.project_team6.Activity.StudentCheckAnswer;
import com.aosproject.project_team6.Bean.StudentListBean;
import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StudentDoneListAdapter extends RecyclerView.Adapter<StudentDoneListAdapter.ViewHolder> {

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

    public StudentDoneListAdapter(Context mContext, int layout, ArrayList<StudentListBean> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cardview_student_done_quiz, parent, false);
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

        Log.v("DoneMessage", "makeDday : " + makeDday);
        Log.v("DoneMessage", "today : " + today);
        Log.v("DoneMessage", "Title : " + data.get(position).getWtitle());
        Log.v("DoneMessage", "tv_studentDone_wid : " + Integer.toString(data.get(position).getWid()));
        Log.v("DoneMessage", "tv_studentDone_WDday : " + "D " + setDday_tv);

        holder.tv_studentDone_workbooktitle.setText(data.get(position).getWtitle());
        holder.tv_studentDone_wid.setText(Integer.toString(data.get(position).getWid()));
        holder.tv_studentDone_WDday.setText("D " + setDday_tv);

        if (data.get(position).getSubject().equals("math")){
            holder.iv_studentDone_subject_icon.setImageResource(R.drawable.icon_math);
        }else{
            holder.iv_studentDone_subject_icon.setImageResource(R.drawable.icon_eng);

        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_studentDone_subject_icon;
        public TextView tv_studentDone_workbooktitle;
        public TextView tv_studentDone_wid;
        public TextView tv_studentDone_WDday;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_studentDone_subject_icon = itemView.findViewById(R.id.iv_studentDone_subject_icon);
            tv_studentDone_workbooktitle = itemView.findViewById(R.id.tv_studentDone_workbooktitle);
            tv_studentDone_wid = itemView.findViewById(R.id.tv_studentDone_wid);
            tv_studentDone_WDday = itemView.findViewById(R.id.tv_studentDone_WDday);

//            getDuedate(); // workbook 컬럼 정보들 가져오기
//            setDday();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    //아이템 뷰 클릭 시 미리 정의한 다른 리스너의 메서드 호출하기
                    if(position != RecyclerView.NO_POSITION){
                        //solve테이블에 학생아이디와 문제집 번호 insert하기
//                        InsertSolveTable();

                        Intent intent;
                        intent = new Intent(mContext, StudentCheckAnswer.class);
                        intent.putExtra("studentDoneAnswerTitle",tv_studentDone_workbooktitle.getText().toString());
                        intent.putExtra("studentDoneAnswerWid",tv_studentDone_wid.getText().toString());

                        mContext.startActivity(intent);
                        Log.v("DoneMessage", tv_studentDone_workbooktitle.getText().toString() + tv_studentDone_wid.getText().toString());

                    }
                }
            });// ClickListener

        }


//        //버튼 액션 >> solve 테이블에 문제집 아이디랑 테이블 집어넣기
//        public void InsertSolveTable() {
//            String result = null;
//            //url 설정
//            wId = tv_studentDone_wid.getText().toString();
//            studentId = ShareVar.studentId;
//            urlAddr = "http://"+myIp+":8080/studentlist/InsertTb_selove.jsp?student_id=" + studentId + "&wid=" + wId ;
////          http://192.168.0.3:8080/studentlist/InsertTb_selove.jsp?student_id=aaa@naver.com&wid=1 + studentId + "&wid=" + wId ;
//
//            //DB접속
//            try{
//                Workbook_NetworkTask networkTask = new Workbook_NetworkTask(mContext, urlAddr, "Make_Tb_solve");
//                Object obj = networkTask.execute().get();
//                result = (String) obj;
////                sDoShowInfo = (ArrayList<StudentListBean>) obj;
//
//            }catch (Exception e){
//                e.printStackTrace();
//                Log.v("AA","Error 1111");
//            }
//
//        }//ConnectGetData


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

