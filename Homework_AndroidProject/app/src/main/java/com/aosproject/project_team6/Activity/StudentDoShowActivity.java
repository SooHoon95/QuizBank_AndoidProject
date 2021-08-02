package com.aosproject.project_team6.Activity;

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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aosproject.project_team6.Bean.StudentListBean;
import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.StudentWorkbook_NetworkTask;
import com.aosproject.project_team6.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StudentDoShowActivity extends AppCompatActivity {

    //ShareVar 변수 세팅하기
    private String myIP = ShareVar.IPAddress;
    private String[] myAnswerArr = ShareVar.myAnswerArr;
    private String student_id = ShareVar.LoginID;



    //문제풀기에 필요한 변수들
    private int qno = ShareVar.qno;
    private int pageNum = 1;
    private int arrNum = 0;
    String result = null;

    //data세팅
    ArrayList<StudentListBean> sDoShowInfo = null;
    String sGradeAnswer = "";
    String myanswer = "";
    String answerResult = "";
    // 점수
    int gradeResult = 0;


    //View요소
    TextView tv_Quiz_NO, tv_workbookTitle;
    WebView webView;
    RadioGroup radioGroup;
    RadioButton radio1;
    Button button;
    LinearLayout linearLayout;



    //Intent 변수
    Intent intent = null;
    String studentWorkbookTitle = "";
    String studentWid ="";
    SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
    Date today = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_do_show);

        // ActionBar 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Intent 받아오기
        intent = getIntent();
        studentWorkbookTitle = intent.getStringExtra("studentDoShowTitle");
        studentWid = intent.getStringExtra("studentDoShowWid");


        //webView 형성
        webView = findViewById(R.id.wv_student_do);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        if(Build.VERSION.SDK_INT >= 21) {
            webView.getSettings().setMixedContentMode((WebSettings.MIXED_CONTENT_ALWAYS_ALLOW));
//            WebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }


        tv_workbookTitle = findViewById(R.id.tv_student_do_show_workbookTitle);
        tv_Quiz_NO = findViewById(R.id.tv_student_Quiz_No);
        radioGroup = findViewById(R.id.rg_student_do);
        radio1 = findViewById(R.id.rb_student_do_1);

        //다음문제 버튼
        button = findViewById(R.id.btn_next_quiz);
        button.setOnClickListener(mClickListener);
        radioGroup.setOnClickListener(mClickListener);

        // qno / pageNum = 1일 때. 즉, 첫번째 뷰 채우기
        ConnectGetDate();
        webView.loadData(htmlData(sDoShowInfo.get(qno).getQimage()),"text/html","UTF-8");
        tv_workbookTitle.setText(studentWorkbookTitle);
        tv_Quiz_NO.setText((qno+1) + "번 문제");

        Log.v("Message", sDoShowInfo.get(0).getQimage());
        Log.v("Message", studentWorkbookTitle + studentWid);
        Log.v("Message", "onCreate ==============================   qno : " + qno);

    }//onCreate

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        //선언

            //intent값 받아오기
//            intent = getIntent();
//            String studentWorkbookTitle = intent.getStringExtra("studentDoShowTitle");
//            String studentWid = intent.getStringExtra("studentDoShowWid");
//            String result = null;

            //라디오 그룹에서 선택된 버튼 아이디 가져오기
            int rbSelected = 0;
            rbSelected = radioGroup.getCheckedRadioButtonId();
            //getCheckedRadioButton()의 리턴값은 라디오버튼의 id값

            RadioButton rb = findViewById(rbSelected);
           if(rb.getText().toString().equals("선생님")){

           }else if(rb.getText().toString().equals("학생")){

           }


//            String myanswer = "";
//            String answerSplit = "";

            if(pageNum < 5) {
                //처리과정

                //정답 선택하기
                switch (rbSelected) {
                    case R.id.rb_student_do_1:
                        myanswer = "1,";
                        answerResult += myanswer;
                        break;
                    case R.id.rb_student_do_2:
                        myanswer = "2,";
                        answerResult += myanswer;
                        break;
                    case R.id.rb_student_do_3:
                        myanswer = "3,";
                        answerResult += myanswer;
                        break;
                    case R.id.rb_student_do_4:
                        myanswer = "4,";
                        answerResult += myanswer;
                        break;
                    case R.id.rb_student_do_5:
                        myanswer = "5,";
                        answerResult += myanswer;
                        break;
                    case 0:
                        myanswer = "0,";
                        answerResult += myanswer;
                        break;
                }
//                1,2,3,4,5
                Log.v("Message", "answerResult2: " + answerResult);

                myAnswerArr = answerResult.split(",");

                for(int i = 0; i<myAnswerArr.length; i++){
                    Log.v("split", myAnswerArr[i]);
//                    myAnswerArr[i]
                }


                //다음 액션 ( 다음 문제로 뷰 체우기)

                //문제번호 +1
                qno++; // qno = 1
                pageNum++; // = 1
                Log.v("Message", "qno : " + qno);
                //View 구성요소들 다시 띄우기
                tv_Quiz_NO.setText((qno + 1) + "번 문제");
                webView.loadData(htmlData(sDoShowInfo.get(qno).getQimage()), "text/html", "UTF-8");
                radioGroup.clearCheck();
                radio1.setChecked(true);
            }else {     /*pageNum가  6이상일 때. 즉, 5번 문제 출력이후*/
                //DB 접속
                //정답 선택하기
                switch (rbSelected) {
                    case R.id.rb_student_do_1:
                        myanswer = "1,";
                        answerResult += myanswer;
                        break;
                    case R.id.rb_student_do_2:
                        myanswer = "2,";
                        answerResult += myanswer;
                        break;
                    case R.id.rb_student_do_3:
                        myanswer = "3,";
                        answerResult += myanswer;
                        break;
                    case R.id.rb_student_do_4:
                        myanswer = "4,";
                        answerResult += myanswer;
                        break;
                    case R.id.rb_student_do_5:
                        myanswer = "5,";
                        answerResult += myanswer;
                        break;
                }
//                1,2,3,4,5
                Log.v("Message", "answerResult: " + answerResult);

                myAnswerArr = answerResult.split(",");

                for(int i = 0; i<myAnswerArr.length; i++) {
                    Log.v("split", "myarr : " + myAnswerArr[i]);

//                    myAnswerArr[i]
                }

                //문제번호 / 페이지 번호 +1
                qno++; // qno = 1
                pageNum++; // = 1
                Log.v("Message", "qno : " + qno);


                InsertMyAnswer(); // 내 답안 입력하기
                GradeMyAnswer();// 채점하기
                setScore();

                intent = new Intent(StudentDoShowActivity.this, StudentResultActivity.class);
                intent.putExtra("answerResult", answerResult);
                intent.putExtra("StudentDoDay", today);
                intent.putExtra("studentWorkbookTitle", studentWorkbookTitle);
                intent.putExtra("studentDoShowWid" , studentWid);
                startActivity(intent);

                Log.v("Message", "Do day : " + today);
                startActivity(intent);
                finish();
            }// if else 끝


        }
    };



    public void ConnectGetDate() {
        //URL
        String urlAddr = "http://" + myIP + ":8080/studentlist/studentDoShowQuiz.jsp?wid=" ;
//    http://192.168.0.77:8080/studentlist/studentDoShowQuiz.jsp?wid=4
        try{
            StudentWorkbook_NetworkTask networkTask = new StudentWorkbook_NetworkTask(StudentDoShowActivity.this, urlAddr+studentWid, "StudentDoShowQuiz");
            Object obj = networkTask.execute().get();
            sDoShowInfo = (ArrayList<StudentListBean>) obj;

        }catch (Exception e){
            e.printStackTrace();
            Log.v("AA","Error 1111");
        }

    }

    public void InsertMyAnswer(){

        try {
            String myanswerUrl = "http://" + myIP + ":8080/studentlist/studentInsertMyanswer.jsp?myanswer=" + answerResult;
            String myanswerUrl2 = "&wid=" + studentWid + "&student_id=" + student_id;

            Log.v("Test param", "myanswer : " + answerResult + "\n" + "studentWid : " + studentWid +
                    "\n" + "student_id : " + student_id + "\n" + "Url : " + myanswerUrl + myanswerUrl2);
//              http://192.168.2.11:8080/studentlist/studentInsertMyanswer.jsp?myanswer=2&wid=1&student_id=aaa@naver.com

            StudentWorkbook_NetworkTask networkTask = new StudentWorkbook_NetworkTask(StudentDoShowActivity.this, myanswerUrl + myanswerUrl2, "Insert_my_answer");
            Object obj = networkTask.execute().get();
            result = (String) obj;
            Log.v("Message", "test InsertMyAnswer");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//InsertMyAnswer

    public void GradeMyAnswer(){

        try {
            for(int i =0; i<5; i++) {
                String eachAnswer = myAnswerArr[i]; // 1번 문제에서 다음 버튼 누르면 0번째 배열 값 불러옴.

                Log.v("Message", " EachAnswer : " + eachAnswer);

                String myanswerUrl = "http://" + myIP + ":8080/studentlist/GradeMyAnswer.jsp?eachAnswer=" + eachAnswer;
                String myanswerUrl2 = "&qno=" + (i + 1) + "&wid=" + studentWid;


                Log.v("Test param", "myanswer : " + answerResult + "\n" + "qno : " + qno +
                        "\n" + "wid : " + studentWid + "\n" + "Url : " + myanswerUrl + myanswerUrl2);
//              http://192.168.2.10:8080/studentlist/GradeMyAnswer.jsp?eachAnswer=2&qno=1&wid=1

                StudentWorkbook_NetworkTask networkTask = new StudentWorkbook_NetworkTask(StudentDoShowActivity.this, myanswerUrl + myanswerUrl2, "GradeMyAnswer");
//            Log.v("Message", "test");

                Object obj = networkTask.execute().get();
                sGradeAnswer = (String) obj;

                gradeResult += Integer.parseInt(sGradeAnswer);
                Log.v("Message", "sGradeAnswer : " + sGradeAnswer);
                Log.v("Message", "gradeResult : " + gradeResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("Message", "Error : GradeMyAnswer");
        }

    }//

    public void setScore(){

        Log.v("Message","Score : " + gradeResult);
        try {
                String myanswerUrl = "http://" + myIP + ":8080/studentlist/InsertScore.jsp?score=" + gradeResult;
                String myanswerUrl2 = "&student_id=" + student_id + "&wid=" + studentWid;


                Log.v("Test param", "score : " + gradeResult + "\n" + "student_id : " + student_id +
                        "\n" + "wid : " + studentWid + "\n" + "Url : " + myanswerUrl + myanswerUrl2);

                StudentWorkbook_NetworkTask networkTask = new StudentWorkbook_NetworkTask(StudentDoShowActivity.this, myanswerUrl + myanswerUrl2, "setScore");
                Object obj = networkTask.execute().get();
                result = (String) obj;
            Log.v("Message", "test seScore");

        } catch (Exception e) {
            e.printStackTrace();
            Log.v("Message", "Error : GradeMyAnswer");
        }

    }


    private String htmlData(String sstatus) {

        Log.v("Message",sstatus);

        String content =
                "<?xml version=\"1.0\" encoding=\"utf-8\" ?>" +
                        "<html><head>" +
                        "<meta http-equiv=\"content-type\" content=\"text/html; charset=utf8\"/>" +
                        "</head>" +
                        "<body><center>" +
                        "<img src=\"http://"+myIP+":8080/studentlist/";
        content += sstatus + ".PNG\" alt=\"How was you feel?\" style=\"float:left; width: auto; height: 100%;\"></center></body></html>";

        Log.v("Message", content);
        return content;
    }

}//=============