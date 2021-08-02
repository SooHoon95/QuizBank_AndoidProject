package com.aosproject.project_team6.Activity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.DupleCheck_NetworkTask;
import com.aosproject.project_team6.NetworkTask.StudentLogin_NetworkTask;
import com.aosproject.project_team6.NetworkTask.TeacherLogin_NetworkTask;
import com.aosproject.project_team6.R;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SignupActivity extends AppCompatActivity {
    TextView title;
    EditText email, password, passwordcheck, name;
    Button signup, dupleCheck, signup_before;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // ActionBar 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        title = findViewById(R.id.tv_signup);
        email = findViewById(R.id.edt_normal_signup_email);
        password = findViewById(R.id.edt_normal_signup_password);
        passwordcheck = findViewById(R.id.edt_normal_signup_passwordcheck);
        name = findViewById(R.id.edt_normal_signup_name);
        signup = findViewById(R.id.btn_normal_signup);
        signup.setOnClickListener(onClickListener);
        dupleCheck = findViewById(R.id.btn_dupleCheck);
        dupleCheck.setOnClickListener(emailCheck);
        signup_before = findViewById(R.id.btn_normal_signup_before);
    }//onCreate
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (emptyCheck("Email") == true) {
                if (emptyCheck("Password") == true) {
                    if (emptyCheck("Name") == true) {
                        if (ruleCheck("Email") == true) {
                            if (ruleCheck("Password") == true) {
                                signup.setText("가입중");
                                signup.setEnabled(false);
                                String DBEmail = email.getText().toString().trim();
                                String DBPw = password.getText().toString().trim();
                                String DBName = name.getText().toString().trim();
                                if (ShareVar.UserStatus == "teacher"){
                                    try {
                                        Log.v("Message", "Signup DB(teacher) : " + DBEmail + "/" + DBPw + "/" + DBName);
                                        String urlAddr_teacher = "http://" + ShareVar.IPAddress + ":8080/login/normalSignupTeacher.jsp?tid=" + DBEmail + "&tpw=" + DBPw + "&tname=" + DBName;
                                        TeacherLogin_NetworkTask teacherNetworkTask = new TeacherLogin_NetworkTask(SignupActivity.this, urlAddr_teacher, "insert");
                                        Object obj = teacherNetworkTask.execute().get();
                                        new AlertDialog.Builder(SignupActivity.this)
                                                .setTitle("강사회원")
                                                .setIcon(R.drawable.mainicon)
                                                .setMessage("가입을 축하드립니다!")
                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        finish();
                                                    }
                                                })
                                                .show();

                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }else if(ShareVar.UserStatus == "student"){
                                    try {
                                        // Login 창에서 status 학생으로 선택하고 들어왔을 경우!
                                        Log.v("Message", "Signup DB(student) : " + DBEmail + "/" + DBPw + "/" + DBName);
                                        String urlAddr_student = "http://" + ShareVar.IPAddress + ":8080/login/normalSignupStudent.jsp?sid=" + DBEmail + "&spw=" + DBPw + "&sname=" + DBName;
                                        StudentLogin_NetworkTask studentNetworkTask = new StudentLogin_NetworkTask(SignupActivity.this, urlAddr_student, "insert");
                                        Object obj = studentNetworkTask.execute().get();
                                        new AlertDialog.Builder(SignupActivity.this)
                                                .setTitle("학생회원")
                                                .setIcon(R.drawable.mainicon)
                                                .setMessage("가입을 축하드립니다!")
                                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        finish();
                                                    }
                                                })
                                                .show();
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }else {
                                    // 회원가입 진입시, Status를 선택하지 않았을 경우
                                    // Dialog 띄워서 학생인지 강사인지 고르게 하기
                                    new AlertDialog.Builder(SignupActivity.this)
                                            .setIcon(R.drawable.mainicon)
                                            .setTitle("회원가입")
                                            .setMessage("회원가입 구분을 선택해주세요!")
                                            .setPositiveButton("학생", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Log.v("Message", "Signup DB(student) : " + DBEmail +"/" + DBPw + "/" + DBName);
                                                    String urlAddr_student = "http://"+ ShareVar.IPAddress +":8080/login/normalSignupStudent.jsp?tid="+DBEmail+"&tpw="+DBPw+"&tname="+DBName;
                                                    StudentLogin_NetworkTask studentNetworkTask = new StudentLogin_NetworkTask(SignupActivity.this, urlAddr_student, "insert");
                                                    new AlertDialog.Builder(SignupActivity.this)
                                                            .setTitle("학생회원")
                                                            .setIcon(R.drawable.mainicon)
                                                            .setMessage("가입을 축하드립니다!")
                                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    finish();
                                                                }
                                                            })
                                                            .show();
                                                }
                                            })
                                            .setNegativeButton("강사", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Log.v("Message", "Signup DB(teacher) : " + DBEmail +"/" + DBPw + "/" + DBName);
                                                    String urlAddr_teacher = "http://"+ ShareVar.IPAddress +":8080/login/normalSignupTeacher.jsp?tid="+DBEmail+"&tpw="+DBPw+"&tname="+DBName;
                                                    TeacherLogin_NetworkTask studentNetworkTask = new TeacherLogin_NetworkTask(SignupActivity.this, urlAddr_teacher, "insert");
                                                    new AlertDialog.Builder(SignupActivity.this)
                                                            .setTitle("강사회원")
                                                            .setIcon(R.drawable.mainicon)
                                                            .setMessage("가입을 축하드립니다!")
                                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    finish();
                                                                }
                                                            })
                                                            .show();
                                                }
                                            })
                                            .show();
                                }
                            }
                        }
                    }
                }
            }
        }
    };


    private boolean emptyCheck(String check){
        switch (check){
            case "Email":
                if (email.length() == 0){
                    email.requestFocus();
                    Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    return true;
                }
            case "Password":
                if (7 >= password.length()){
                    password.requestFocus();
                    Toast.makeText(this, "최소 8자 이상의 비밀번호를 설정해주세요", Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    if (7 >= passwordcheck.length()){
                        passwordcheck.requestFocus();
                        Toast.makeText(this, "최소 8자 이상의 비밀번호를 설정해주세요", Toast.LENGTH_SHORT).show();
                        return false;
                    }else {
                        if (!password.getText().toString().equals(passwordcheck.getText().toString())) {
                            passwordcheck.requestFocus();
                            Toast.makeText(this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                            return false;
                        } else{
                            return true;
                        }
                    }
                }
            case "Name" :
                if (1 >= name.length()) {
                    name.requestFocus();
                    Toast.makeText(this, "이름을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    return true;
                }
        }// switch
        return false;
    }
    private boolean ruleCheck(String check) {
        Pattern pattern;
        Matcher matcher;
        switch (check) {
            case "Email" :
                pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$");
                matcher = pattern.matcher(email.getText().toString());
                if(matcher.find()){
                    //이메일 형식에 맞을 때
                    return true;
                }else{
                    //이메일 형식에 맞지 않을 때
                    Toast.makeText(SignupActivity.this, "이메일을 형식에 맞춰 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            case "Password" :
                //숫자, 문자, 특수문자 중 2가지 포함(8~15자)
                pattern = Pattern.compile("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{8,15}$");
                matcher = pattern.matcher(password.getText().toString());
                if(matcher.find()){
                    //이메일 형식에 맞을 때
                    return true;
                }else{
                    //이메일 형식에 맞지 않을 때
                    Toast.makeText(SignupActivity.this, "비밀번호는 숫자, 문자, 특수문자 중 2가지 포함(8~15자)으로 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            default:
                break;
        }
        return false;
    };
    View.OnClickListener emailCheck = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (email.length() == 0) {
                email.requestFocus();
                Toast.makeText(SignupActivity.this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
            }else{
                if (dupleCheck(ShareVar.UserStatus, email.getText().toString()) == true){
                    Toast.makeText(SignupActivity.this, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show();
                    count ++;
                    signup.setVisibility(View.VISIBLE);
                    signup_before.setVisibility(View.GONE);
                }else {
                    Toast.makeText(SignupActivity.this, "이미 사용중인 아이디입니다.", Toast.LENGTH_SHORT).show();
                    count = 0;
                }
            }
        }//onClick
    };
    private boolean dupleCheck(String str, String checkID){
        Log.v("Message", "METHOD : dupleCheck Start");
        switch (str){
            case "teacher":
                try{
                    String checkUrl = "http://"+ ShareVar.IPAddress +":8080/login/teacherDupleCheck.jsp?tid="+checkID;
                    DupleCheck_NetworkTask teacherTask = new DupleCheck_NetworkTask(SignupActivity.this, checkUrl, "select");
                    Object obj = teacherTask.execute().get();
                    String check = (String) obj;
                    Log.v("Message", "  - obj : " + obj);
                    if (check != "F"){
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
                    DupleCheck_NetworkTask studentTask = new DupleCheck_NetworkTask(SignupActivity.this, checkUrl, "select");
                    Object obj = studentTask.execute().get();
                    String check = (String) obj;
                    Log.v("Message", "  - obj : " + obj);
                    if(check == "F")
                    return true;
                }catch (Exception e){
                    e.printStackTrace();
                    return false;
                }
        }
        return false;
    }
}// Main