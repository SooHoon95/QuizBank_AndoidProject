package com.aosproject.project_team6.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.aosproject.project_team6.Bean.StudentLoginbean;
import com.aosproject.project_team6.Bean.TeacherListBean;
import com.aosproject.project_team6.Bean.TeacherLoginBean;
import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.DupleCheck_NetworkTask;
import com.aosproject.project_team6.NetworkTask.StudentLogin_NetworkTask;
import com.aosproject.project_team6.NetworkTask.TeacherLogin_NetworkTask;
import com.aosproject.project_team6.R;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.util.ArrayList;

public class SignInActivity extends AppCompatActivity {
    private ISessionCallback mSessionCallback;


    Button btn_login, btn_signup;
    EditText edt_email, edt_password;
    RadioGroup rg_login;
    RadioButton rb_student, rb_teacher;
    ArrayList<TeacherLoginBean> teachers;
    ArrayList<StudentLoginbean> students;
    String getkakaoId = "";
    String kakaoNick= "";


    ////////////////////////////////////
    //   자동로그인 Setting              //
    ////////////////////////////////////
    CheckBox cb_autoLogin;
    SharedPreferences autoSignin = null;
    String SigninEmail = null;
    String userStatus = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // ActionBar 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 자동로그인 체크박스
        cb_autoLogin = findViewById(R.id.cb_autoLogin);

        // View Setting
        btn_login = findViewById(R.id.btn_signin);
        btn_signup = findViewById(R.id.btn_signin_signup);

        edt_email = findViewById(R.id.edt_signin_id);
        edt_password = findViewById(R.id.edt_signin_pw);

        rg_login = findViewById(R.id.rg_login);
        rb_student = findViewById(R.id.rb_student_login);
        rb_teacher = findViewById(R.id.rb_teacher_login);

        // 학생과 강사 구별해주는 라디오 그룹 리스너.
        rg_login.setOnCheckedChangeListener(userStatusListener);

        // 로그인 클릭 시 리스너
        btn_login.setOnClickListener(onClickListener);
        btn_signup.setOnClickListener(onClickListener2);

        mSessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                // 로그인 요청
                UserManagement.getInstance().me(new MeV2ResponseCallback() {

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        // 로그인 실패
                        Toast.makeText(SignInActivity.this, "로그인 도중에 오류가 발생 했습니다. ", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        // 세선이 닫혔을 때
                        Toast.makeText(SignInActivity.this, "세션이 닫혔습니다. 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) { // 로그인 성공 시, 정보를 result에 담아서 줌
                        getkakaoId = result.getKakaoAccount().getEmail();
                        kakaoNick = result.getKakaoAccount().getProfile().getNickname();
//                        dupleCheck(ShareVar.UserStatus, getkakaoId);

                        if(ShareVar.UserStatus == "none") {
                            Toast.makeText(SignInActivity.this, "회원 타입을 선택해주세요", Toast.LENGTH_SHORT).show();
                            ShareVar.LoginID = null;
                        }else {
                            if (ShareVar.UserStatus == "student") {
                                if (dupleCheck(ShareVar.UserStatus, getkakaoId) == false) { // 이미 DB에 있는 아이디
                                    Toast.makeText(SignInActivity.this, "숙제뭐양에 오신 것을 환영합니다!", Toast.LENGTH_SHORT).show();
                                    ShareVar.LoginID = getkakaoId;
                                } else {
                                    String urlAddr = "http://" + ShareVar.IPAddress + ":8080/login/signupStudent.jsp?sid=" + getkakaoId + "&sname=" + kakaoNick;
                                    StudentLogin_NetworkTask studentNetwork = new StudentLogin_NetworkTask(SignInActivity.this, urlAddr, "insert");
                                    ShareVar.LoginID = getkakaoId;

                                    Object obj2 = studentNetwork.execute();
                                    Toast.makeText(SignInActivity.this, "숙제뭐양에 오신 것을 환영합니다!", Toast.LENGTH_SHORT).show();
                                }

                                Intent intent = new Intent(SignInActivity.this, StudentListActivity.class);
                                intent.putExtra("name", result.getKakaoAccount().getProfile().getNickname());
                                intent.putExtra("profileImg", result.getKakaoAccount().getProfile().getProfileImageUrl());
                                intent.putExtra("email", result.getKakaoAccount().getEmail());
                                startActivity(intent);
                                finish();

                            } else if (ShareVar.UserStatus == "teacher") {
                                if (dupleCheck(ShareVar.UserStatus, getkakaoId) == false) {
                                    Toast.makeText(SignInActivity.this, "숙제뭐양에 오신 것을 환영합니다!", Toast.LENGTH_SHORT).show();
                                    ShareVar.LoginID = getkakaoId;
                                } else {
                                    String urlAddr = "http://" + ShareVar.IPAddress + ":8080/login/signupTeacher.jsp?tid=" + getkakaoId + "&tname=" + kakaoNick;
                                    TeacherLogin_NetworkTask teacherNetwork = new TeacherLogin_NetworkTask(SignInActivity.this, urlAddr, "insert");
                                    ShareVar.LoginID = getkakaoId;
                                    Object obj = teacherNetwork.execute();
                                }
                                Intent intent = new Intent(SignInActivity.this, TeacherListActivity.class);
                                intent.putExtra("name", result.getKakaoAccount().getProfile().getNickname());
                                intent.putExtra("profileImg", result.getKakaoAccount().getProfile().getProfileImageUrl());
                                intent.putExtra("email", result.getKakaoAccount().getEmail());
                                startActivity(intent);
                                finish();
                            }
                        }
                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                Toast.makeText(SignInActivity.this, "onSessionOpenFailed", Toast.LENGTH_SHORT).show();
            }
        };

        Session.getCurrentSession().addCallback(mSessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();
        // getAppKeyHash();

        /////////////////////////////////////////
        //   자동로그인 데이터 값 있으면 메인으로 이동   //
        /////////////////////////////////////////
        autoSignin = getSharedPreferences("signInAuto", Activity.MODE_PRIVATE);

        SigninEmail = autoSignin.getString("autoEmail", null);


        if (SigninEmail != null){
            // 자동로그인한 이메일 주소 ShareVar에 저장.
            ShareVar.LoginID = SigninEmail;
            userStatus = autoSignin.getString("userStatus", null);
            Log.v("Message", "AutoLogin Info : " + SigninEmail + " / Teacher or Student ? : " + userStatus);

            /////////////////////////////////////////
            //   userStatus로 강사, 학생 구분          //
            /////////////////////////////////////////
            switch (userStatus){
                case "teacher":
                    ShareVar.taddress1 = autoSignin.getString("autoAddress1", null);
                    ShareVar.taddress2 = autoSignin.getString("autoAddress2", null);
                    Intent intent = new Intent(SignInActivity.this, TeacherListActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case "student":
                    ShareVar.sdivision = autoSignin.getString("autoDivision", null);
                    Intent intent2 = new Intent(SignInActivity.this, StudentListActivity.class);
                    startActivity(intent2);
                    finish();
                    break;
                default:
                    break;
            }
        }else{

        }

    }//onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(mSessionCallback);
    }


    // 학생과 강사 구별해주는 라디오 그룹 리스너. (로그인 및 회원가입 시 사용)
    RadioGroup.OnCheckedChangeListener userStatusListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            switch (checkedId){
                case R.id.rb_student_login:

                    // 학생로그인 라디오 버튼을 누르면 ShareVar에 student 등록
                    ShareVar.UserStatus = "student";
                    break;
                case R.id.rb_teacher_login:

                    // 강사로그인 라디오 버튼을 누르면 ShareVar에 teacher 등록
                    ShareVar.UserStatus = "teacher";
                    break;
                default:
                    ShareVar.UserStatus = "none";
                    break;
            }
        }
    };

    // 로그인 버튼 눌렀을 때
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // 공백 체크 해주기
            if(ShareVar.UserStatus == "none"){
                Toast.makeText(SignInActivity.this, "회원 타입을 선택하세요.", Toast.LENGTH_SHORT).show();
            }else {


                String userEmail = edt_email.getText().toString().trim();
                String userPassword = edt_password.getText().toString().trim();
                if (userEmail.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else if (userPassword.isEmpty()) {
                    Toast.makeText(SignInActivity.this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                } else {

                    // 로그인하는 Method 로 연결하기.
                    loginAction(userEmail, userPassword);
                }
            }
        }//onClick
    }; // onClickListener


    // 로그인 Method
    public void loginAction(String email, String password){
        Log.v("Message", "## METHOD : loginAction Start... ##");

        // 공백 체크 후, 빈값이 아니면 로그인 시도 시작.

        // Email 공백 체크.
        if (edt_email.getText().toString().trim().equals("") || edt_email.getText().toString().trim() == null) {
            Toast.makeText(getApplicationContext(), "이메일 입력해 주세요", Toast.LENGTH_SHORT).show();
            edt_email.requestFocus();

            // 비밀번호 공백 체크.
        } else if (edt_password.getText().toString().trim().equals("") || edt_password.getText().toString().trim() == null) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
            edt_password.requestFocus();
        } else{

            // 로그인 시도 시작
            try{
                switch (ShareVar.UserStatus){
                    //////////////////////////////
                    // 라디오버튼을 강사으로 두었을 경우
                    //////////////////////////////
                    case "teacher":
                        Log.v("Message", "  - teacher LoginCheck Start");
                        // 입력한 Email로 정보 가져온 후, 비밀번호 대조
                        String checkEmail = String.valueOf(edt_email.getText());
                        String urlAddr = "http://"+ ShareVar.IPAddress + ":8080/login/teacherLoginCheck.jsp?tid="+checkEmail;
                        Log.v("Message", "login url : " + urlAddr);
                        TeacherLogin_NetworkTask networkTask = new TeacherLogin_NetworkTask(SignInActivity.this, urlAddr, "select");
                        Object obj = networkTask.execute().get();
                        teachers = (ArrayList<TeacherLoginBean>) obj;
                        String checkPassword = String.valueOf(edt_password.getText());
                        if (checkPassword.equals(teachers.get(0).getTpw())){
                            ShareVar.LoginID=teachers.get(0).getTid();
                            ShareVar.tname=teachers.get(0).getTname();
                            ShareVar.tpw=teachers.get(0).getTpw();
                            ShareVar.taddress1=teachers.get(0).getTaddress1();
                            ShareVar.taddress2=teachers.get(0).getTaadress2();
//                                ShareVar.tdeletedate=teachers.get(0).getTdeletedate();

                            //////////////////////////////////////////////////
                            // teacher DB table 에서 필요한거 추가로 가져오면 됩니다.//
                            //////////////////////////////////////////////////
                            if (cb_autoLogin.isChecked()) {
                                saveSigninData(teachers.get(0).getTid(), teachers.get(0).getTname(),
                                        teachers.get(0).getTaddress1(), teachers.get(0).getTaadress2(), null);
                            }

                            Intent intent = new Intent(SignInActivity.this, TeacherListActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(this, "아이디와 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                        break;

                    //////////////////////////////
                    // 라디오버튼을 학생으로 두었을 경우
                    //////////////////////////////
                    case "student":
                        Log.v("Message", "  - teacher LoginCheck Start");
                        // 입력한 Email로 정보 가져온 후, 비밀번호 대조
                        String checkEmail2 = String.valueOf(edt_email.getText());
                        String urlAddr2 = "http://"+ ShareVar.IPAddress + ":8080/login/studentLoginCheck.jsp?sid="+checkEmail2;
                        Log.v("Message", "login url : " + urlAddr2);
                        StudentLogin_NetworkTask networkTask2 = new StudentLogin_NetworkTask(SignInActivity.this, urlAddr2, "select");
                        Object obj2 = networkTask2.execute().get();
                        students = (ArrayList<StudentLoginbean>) obj2;
                        String checkPassword2 = String.valueOf(edt_password.getText());
                        Log.v("Message", checkPassword2);
                        Log.v("Message", students.get(0).getSpw());
                        if (checkPassword2.equals(students.get(0).getSpw())){
                            ShareVar.LoginID=students.get(0).getSid();
                            ShareVar.sname=students.get(0).getSname();
                            ShareVar.spw=students.get(0).getSpw();
                            ShareVar.sdivision=students.get(0).getSdivision();
//                                ShareVar.sdeletedate=students.get(0).getSdeletedate();

                            //////////////////////////////////////////////////
                            // student DB table 에서 필요한거 추가로 가져오면 됩니다.//
                            //////////////////////////////////////////////////
                            if (cb_autoLogin.isChecked()) {
                                saveSigninData(students.get(0).getSid(), students.get(0).getSname(),
                                        null, null, students.get(0).getSdivision());
                            }



                            Intent intent = new Intent(SignInActivity.this, StudentListActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(this, "아이디와 비밀번호를 확인해 주세요.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                } // switch
            }catch (Exception e){
                e.printStackTrace();
            }
        }// else (아이디, 비밀번호 입력값이 공백이 아닐때)
    } // loginAction


    //일반회원 회원가입
    View.OnClickListener onClickListener2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(ShareVar.UserStatus == "none"){
                Toast.makeText(SignInActivity.this, "회원 타입을 선택하세요.", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(SignInActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        }
    };


    private boolean dupleCheck(String str, String checkID){
        Log.v("Message", "METHOD : dupleCheck Start");
        switch (str){
            case "teacher":
                try{
                    String checkUrl = "http://"+ ShareVar.IPAddress +":8080/login/teacherDupleCheck.jsp?tid="+checkID;
                    DupleCheck_NetworkTask teacherTask = new DupleCheck_NetworkTask(SignInActivity.this, checkUrl, "select");
                    Object obj = teacherTask.execute().get();
                    String checkId = (String) obj;
                    Log.v("Message", "  - obj : " + obj);
                    if (checkId.equals(getkakaoId)){
                        return false; // 이미 있는 아이디 이다.
                    }
                    return true; // 아이디 없으니까 DB에 넣는 메소드 실행하기
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
            case "student":
                try{
                    String checkUrl = "http://"+ ShareVar.IPAddress +":8080/login/studentDupleCheck.jsp?sid="+checkID;
                    DupleCheck_NetworkTask studentTask = new DupleCheck_NetworkTask(SignInActivity.this, checkUrl, "select");
                    Object obj = studentTask.execute().get();
                    String check = (String) obj;
                    Log.v("Message", "  - obj : " + obj);
                    if (check.equals(getkakaoId)){
                        return false; // 이미 있는 아이디 이다.
                    }
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
        }
        return false;
    }

    public void saveSigninData(String email, String name, String address1, String address2, String sdivision){
        Log.v("Message", "Save Id : " + email);
        SharedPreferences.Editor editor = autoSignin.edit();
        editor.putString("autoEmail", email);
        editor.putString("autoName", name);
        switch(ShareVar.UserStatus){
            case "teacher":
                editor.putString("autoAddress1", address1);
                editor.putString("autoAddress2", address2);
                editor.putString("userStatus", "teacher");
                break;
            case "student":
                editor.putString("autoDivision", sdivision);
                editor.putString("userStatus", "student");
                break;
        }
        editor.commit();
    } // saveSigninData



}//Main