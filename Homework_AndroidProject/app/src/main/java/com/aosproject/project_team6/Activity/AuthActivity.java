package com.aosproject.project_team6.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.aosproject.project_team6.Adapter.AuthListAdapter;
import com.aosproject.project_team6.Bean.AuthBean;
import com.aosproject.project_team6.Bean.TeacherListBean;
import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.StudentWorkbook_NetworkTask;
import com.aosproject.project_team6.NetworkTask.Workbook_NetworkTask;
import com.aosproject.project_team6.R;

import java.util.ArrayList;

public class AuthActivity extends AppCompatActivity {

    //변수
    String urlAddrUpdate = null;
    int workbook_id = 1; // 문제집 id 값
    String macIP, student_id;

    //listView 변수
    ArrayList<AuthBean> authStudentId= null;
    ArrayList<AuthBean> data = null;
    AuthListAdapter adapter = null;
    //권한 변수
    String sdivision = "";

    EditText et_authInsert; // 학생 이메일 입력란
    Button btn_authInsert; // 버튼
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Log.v("Message","start AuthActivity");
        macIP = ShareVar.IPAddress;
        //업데이트용 jsp
//        urlAddrUpdate = "http://" + macIP + ":8080/test/quizbank_AuthInsert.jsp?";


        //연결
        et_authInsert = findViewById(R.id.et_authInsert);
        btn_authInsert = findViewById(R.id.btn_authInsert);
        //listView
        listView = findViewById(R.id.listview_auth);

        //클릭 이벤트
        btn_authInsert.setOnClickListener(mOnClickListener);

        //listView에 어뎁터 연결
//        AuthlistData();


    }//onCreate

    @Override
    protected void onResume() {
        super.onResume();
        AuthlistData();
        if (data == null) {

        }else{
            adapter = new AuthListAdapter(AuthActivity.this,R.layout.custom_auth_layout,data);
            listView.setAdapter(adapter);
        }
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_authInsert:
                    sdivision = et_authInsert.getText().toString();
                    // ******** 여기에 DB 관련 작업 내용도 넣어야 함!! ******
//                    urlAddrUpdate = urlAddrUpdate + "studentId=" + student_id +"&workbookId=" + workbook_id;
                    AuthInsert();
                    Toast.makeText(AuthActivity.this,"입력되었습니다", Toast.LENGTH_SHORT);
                    et_authInsert.setText("");
                    onResume();
                    Log.v("Message","Activity onClickListener");
                    break;
            }
        }
    };



    private void AuthInsert(){

        Log.v("Message","start AuthInsert");
        urlAddrUpdate = "http://" + macIP + ":8080/teacherlist/quizbank_AuthInsert.jsp?sdivision=" + sdivision +"&wid=" + workbook_id;
//        http://192.168.2.11:8080/teacherlist/quizbank_AuthInsert.jsp?sdivision=test&wid=1

        String result = null;
        try{
            Workbook_NetworkTask networkTask = new Workbook_NetworkTask(AuthActivity.this, urlAddrUpdate, "AuthInsert");
            Log.v("Message","nework Task END=========================");
            Object obj = networkTask.execute().get();
            Log.v("Message","nework Task END=========================");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void AuthlistData(){

        String myIp = ShareVar.IPAddress;
        String urlAddrUpdate = "http://" + myIp + ":8080/teacherlist/AuthListSelect.jsp";
//        "http://192.168.2.11:8080/teacherlist/AuthListSelect.jsp";


        try{
            StudentWorkbook_NetworkTask networkTask = new StudentWorkbook_NetworkTask( AuthActivity.this, urlAddrUpdate, "AuthList");
            Object obj = networkTask.execute().get();
            data = (ArrayList<AuthBean>) obj;

        }catch (Exception e){
            e.printStackTrace();
        }
    }




}