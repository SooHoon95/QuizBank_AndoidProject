package com.aosproject.project_team6.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.DupleCheck_NetworkTask;
import com.aosproject.project_team6.NetworkTask.StudentLogin_NetworkTask;
import com.aosproject.project_team6.NetworkTask.TeacherLogin_NetworkTask;
import com.aosproject.project_team6.R;
import com.bumptech.glide.Glide;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class KakaoSignupActivity extends AppCompatActivity {


    private String kakaoNick, kakaoProfileImg, kakaoEmail;
    Button btn_OK;
    RadioGroup rg_status;
    RadioButton rb_student, rb_teacher;
    EditText edt_address, edt_division;
    String urlAddr = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_signup);
        //액션 바 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Intent intent = getIntent();
        kakaoProfileImg = intent.getStringExtra("profileImg");
        kakaoEmail = intent.getStringExtra("email");
        kakaoNick = intent.getStringExtra("name");

        TextView tv_nick = findViewById(R.id.tv_nickName);
        TextView tv_email = findViewById(R.id.tv_email);
        ImageView iv_profile = findViewById(R.id.iv_profile);
        edt_address = findViewById(R.id.edt_address);
        edt_division = findViewById(R.id.edt_division);

        rg_status = findViewById(R.id.rg_status_check);
        rb_student = findViewById(R.id.rb_student_signup);
        rb_teacher = findViewById(R.id.rb_teacher_signup);

        btn_OK = findViewById(R.id.signup_btnOk);
        btn_OK.setOnClickListener(onClickListener);

        rg_status.setOnCheckedChangeListener(setOncheck);

        // 닉네임, 이메일 set
        tv_nick.setText(kakaoNick);
        tv_email.setText(kakaoEmail);


        // Glide를 이용해 이미지 파일 불러와 set
        Glide.with(this).load(kakaoProfileImg).into(iv_profile);

        if (dupleCheck(ShareVar.UserStatus, kakaoEmail) == false){
            switch (ShareVar.UserStatus){
                case "teacher" :
                    Intent intent1 = new Intent(KakaoSignupActivity.this, TeacherListActivity.class);
                    startActivity(intent1);
                    break;
                case "student" :
                    Intent intent2  =  new Intent(KakaoSignupActivity.this, StudentListActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    }//onCreate
//
//    View.OnClickListener signoutListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
//                @Override
//                public void onCompleteLogout() {
//                    //로그아웃 성공 시
//                    finish(); // 현재 액티비티 종료
//                }
//            });
//        }
//    };

    // 학생과 강사 구별해주는 라디오 그룹 리스너. (로그인 및 회원가입 시 사용)
    RadioGroup.OnCheckedChangeListener setOncheck = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_student_signup:

                    // 학생로그인 라디오 버튼을 누르면 ShareVar에 student 등록
                    ShareVar.UserStatus = "student";
                    edt_division.setVisibility(View.VISIBLE);
                    edt_address.setVisibility(View.GONE);
                    break;
                case R.id.rb_teacher_signup:

                    // 강사로그인 라디오 버튼을 누르면 ShareVar에 teacher 등록
                    ShareVar.UserStatus = "teacher";
                    edt_address.setVisibility(View.VISIBLE);
                    edt_division.setVisibility(View.GONE);
                    break;
            }
        }
    };


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (ShareVar.UserStatus){
                case "teacher":
                    String taddress1 = String.valueOf(edt_address.getText());
                    urlAddr = "http://"+ ShareVar.IPAddress + ":8080/login/signupTeacher.jsp?tid="+kakaoEmail+"&tname="+kakaoNick+"&taddress1="+taddress1;
                    TeacherLogin_NetworkTask teacherNetwork = new TeacherLogin_NetworkTask(KakaoSignupActivity.this, urlAddr, "insert");
                    ShareVar.LoginID = kakaoEmail;
                    Object obj = teacherNetwork.execute();
                    break;


                case "student":
                    String sdivision = String.valueOf(edt_division.getText());
                    urlAddr = "http://"+ ShareVar.IPAddress + ":8080/login/signupStudent.jsp?sid="+kakaoEmail+"&sname="+kakaoNick+"&sdivision="+sdivision;
                    StudentLogin_NetworkTask studentNetwork = new StudentLogin_NetworkTask(KakaoSignupActivity.this, urlAddr, "insert");
                    ShareVar.LoginID = kakaoEmail;
                    Object obj2 = studentNetwork.execute();
                    break;
            }
            finish();
            Toast.makeText(KakaoSignupActivity.this, "문제맛집 가입을 환영합니다!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(KakaoSignupActivity.this, SignInActivity.class);
            startActivity(intent);

            }//onClick
        };//onClickListener

    private boolean dupleCheck(String str, String checkID){
        Log.v("Message", "METHOD : dupleCheck Start");
        switch (str){
            case "teacher":
                try{
                    String checkUrl = "http://"+ ShareVar.IPAddress +":8080/login/teacherDupleCheck.jsp?tid="+checkID;
                    DupleCheck_NetworkTask teacherTask = new DupleCheck_NetworkTask(KakaoSignupActivity.this, checkUrl, "select");
                    Object obj = teacherTask.execute().get();
                    String check = (String) obj;
                    Log.v("Message", "  - obj : " + obj);
                    if (check != null){
                        return false;
                    }
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            case "student":
                try{
                    String checkUrl = "http://"+ ShareVar.IPAddress +":8080/login/studentDupleCheck.jsp?sid="+checkID;
                    DupleCheck_NetworkTask studentTask = new DupleCheck_NetworkTask(KakaoSignupActivity.this, checkUrl, "select");
                    Object obj = studentTask.execute().get();
                    String check = (String) obj;
                    Log.v("Message", "  - obj : " + obj);
                    if (check != null){
                        return false;
                    }
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
        }
        return false;
    }



    } //Main
