package com.aosproject.project_team6.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.StudentWorkbook_NetworkTask;
import com.aosproject.project_team6.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentResultActivity extends AppCompatActivity {

    TextView tv_title, tv_score, tv_time;
    Button button;

    private String sid = ShareVar.LoginID;
    private String myIP = ShareVar.IPAddress;
    private String resultSetTitile, reusltsetScore, resultsetTime, resultsetWid ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_result);

        // ActionBar 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //날짜 만들어주기
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date time  = new Date();

        String nowTime = format.format(time);


        button = findViewById(R.id.btn_close);

        tv_title = findViewById(R.id.tv_result_wtitle);
        tv_score = findViewById(R.id.tv_result_score);
        tv_time = findViewById(R.id.tv_result_submit_time);

        Intent intent =  null;
        intent = getIntent();
        resultSetTitile = intent.getStringExtra("studentWorkbookTitle");
        resultsetTime = intent.getStringExtra("StudentDoDay");
        resultsetWid = intent.getStringExtra("studentDoShowWid");

        Log.v("Message",resultsetWid);


//        Log.v("time", resultsetTime);
        reusltsetScore = getScore();

        //view에 값 넣기
        tv_title.setText(resultSetTitile);
        tv_score.setText(reusltsetScore + "개 정답!!");
        tv_time.setText("제출 시간 : " + nowTime);
        SpannableString content = new SpannableString(tv_title.getText().toString());
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv_title.setText(content);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentResultActivity.this, StudentListActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }// onCreate

    public String getScore(){

        try {
            String myanswerUrl = "http://" + myIP + ":8080/studentlist/SelectScore.jsp?sid=" + sid + "&wid=" + resultsetWid;

            StudentWorkbook_NetworkTask networkTask = new StudentWorkbook_NetworkTask(StudentResultActivity.this, myanswerUrl, "getScore");
            Object obj = networkTask.execute().get();
            reusltsetScore = (String) obj;

        } catch (Exception e) {
            e.printStackTrace();
            Log.v("Message", "Error : getScore");
        }
        return reusltsetScore;
    }



}