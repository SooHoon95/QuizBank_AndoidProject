package com.aosproject.project_team6.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.aosproject.project_team6.Bean.StudentListBean;
import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.StudentWorkbook_NetworkTask;
import com.aosproject.project_team6.R;

import java.util.ArrayList;

public class StudentCheckAnswer extends AppCompatActivity {

    //ShareVar 변수 세팅하기
    private String myIP = ShareVar.IPAddress;
    private String[] myAnswerArr = ShareVar.myAnswerArr;
    private String student_id = ShareVar.LoginID;

    //View 선언
    Button button;
    TextView wTitle, quizNo, tv_correctAnswer, tv_myAnswer;
    WebView webView;

    //선언
    private int qno = ShareVar.qno;
    private int pageNum = 1;
    private int arrNum = 0;

    //data세팅
    private ArrayList<StudentListBean> sDoneShowInfo = null;
    private Intent intent = null;
    private String studentWorkbookTitle = "";
    private String studentWid = "";
    private String myAnswers = "";
    private String[] doneAnswerArr = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_check_answer);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Log.v("DoneMessage","Start StudentCheckAnswer");

        //Intent 받아오기
        intent = getIntent();
        studentWorkbookTitle = intent.getStringExtra("studentDoneAnswerTitle");
        studentWid = intent.getStringExtra("studentDoneAnswerWid");
        Log.v("DoneMessage","Done Get Intent");
        Log.v("DoneMessage","wTitle : " + studentWorkbookTitle);
        Log.v("DoneMessage","wId : " + studentWid);

        //View연결
        wTitle = findViewById(R.id.tv_chkAnswer_wTitle);
        quizNo = findViewById(R.id.tv_chkAnswer_quizNO);
        tv_correctAnswer = findViewById(R.id.tv_correct_answer);
        tv_myAnswer = findViewById(R.id.tv_myAnswer);
        button = findViewById(R.id.btn_chkAnswer_next_quiz);

        //webView 형성
        webView = findViewById(R.id.wv_chkAnswer);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= 21) {
            webView.getSettings().setMixedContentMode((WebSettings.MIXED_CONTENT_ALWAYS_ALLOW));
//            WebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        //View에 값 입력하기
        ConnectGetData();
        myAnswers = getMyAnswers();
        doneAnswerArr = myAnswers.split(",");

//        Log.v("DoneMessage", sDoneShowInfo.get(qno).getQimage());
        webView.loadData(htmlData(sDoneShowInfo.get(qno).getQimage()),"text/html","UTF-8");
        wTitle.setText(studentWorkbookTitle);
        quizNo.setText((qno+1) + "번 문제");
        tv_correctAnswer.setText("정답 :  " + sDoneShowInfo.get(qno).getQanswer());
        tv_myAnswer.setText("내가 고른 답 :   " + doneAnswerArr[qno]);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pageNum < 5) {

                    //문제번호 +1
                    qno++; // qno = 1
                    pageNum++; // = 2
                    Log.v("DoneMessage", "qno : " + qno);
                    //View 구성요소들 다시 띄우기
                    quizNo.setText((qno + 1) + "번 문제");
                    webView.loadData(htmlData(sDoneShowInfo.get(qno).getQimage()), "text/html", "UTF-8");
                    tv_correctAnswer.setText("정답 :  " + sDoneShowInfo.get(qno).getQanswer());
                    tv_myAnswer.setText("내가 고른 답 :   " + doneAnswerArr[qno]);


                } else {
                    // 다이얼로그 띄우기
                    new AlertDialog.Builder(StudentCheckAnswer.this)
                            .setTitle("메인화면으로 돌아갑니다")
                            .setMessage("모든결과를 열람하셨습니다")
                            .setCancelable(false)
                            .setPositiveButton("확인", nClick)
                            .show();


                }
            }

        });

    }//onCreate

    DialogInterface.OnClickListener nClick =new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          finish();
        }
    };

    public void ConnectGetData() {
        //URL
        String urlAddr = "http://" + myIP + ":8080/studentlist/studentCheckAnswer.jsp?wid=" + studentWid;
//    http://172.30.1.59:8080/studentlist/studentCheckAnswer.jsp?wid=1
        try {
            StudentWorkbook_NetworkTask networkTask = new StudentWorkbook_NetworkTask(StudentCheckAnswer.this, urlAddr, "studentCheckAnswer");
            Object obj = networkTask.execute().get();
            sDoneShowInfo = (ArrayList<StudentListBean>) obj;
            Log.v("DoneMessage", "Done ConnectGetData");
            Log.v("DoneMessage", sDoneShowInfo.get(0).getQimage());

        } catch (Exception e) {
            e.printStackTrace();
            Log.v("DoneMessage", "Error 1111");
        }

    }

    private String getMyAnswers(){
        //URL
        String urlAddr = "http://" + myIP + ":8080/studentlist/SelectDoneWorkbook_myAnswer.jsp?student_id=" + student_id + "&wid=" + studentWid;
//        http://192.168.0.3:8080/studentlist/SelectDoneWorkbook_myAnswer.jsp?student_id=aaa@naver.com&wid=4
        String myAnswers1= "";
        try {
            StudentWorkbook_NetworkTask networkTask = new StudentWorkbook_NetworkTask(StudentCheckAnswer.this, urlAddr, "SelectDoneWorkbook_myAnswer");
            Object obj = networkTask.execute().get();
            myAnswers1 = (String) obj;
            Log.v("DoneMessage", "Done getMyAnswers");

        } catch (Exception e) {
            e.printStackTrace();
            Log.v("DoneMessage", "Error 1111");
        }
        return myAnswers1;

    }


    private String htmlData(String sstatus) {

        Log.v("DoneMessage", sstatus);

        String content =
                "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
                        "<html><head>" +
                        "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf8\"/>" +
                        "</head>" +
                        "<body><center>" +
                        "<img src=\"http://" + myIP + ":8080/studentlist/";
        content += sstatus + ".PNG\" alt=\"How was you feel?\" style=\"float:left; width: auto; height: 100%;\"></center></body></html>";

        Log.v("DoneMessage", content);
        return content;
    }


}//==============================