package com.aosproject.project_team6.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.Workbook_NetworkTask;
import com.aosproject.project_team6.R;

public class MyPageStudentPasswordEdit extends AppCompatActivity {

    //변수
    String urlAddrUpdate = null;
    String sid = ShareVar.LoginID; // 학생 sid값 (ShareVar)
    String spw_present = ShareVar.spw; // 학생 spw값(현재 값) (ShareVar)
    String spw = ""; // spw 바뀌는 값
    String macIP;

    EditText et_student_PresentPassword, et_student_NewPassword, et_student_NewPasswordConfirm;
    Button btn_student_PasswordEditCancel, btn_student_PasswordEditOK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_student_password_edit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        macIP = ShareVar.IPAddress;
        //등록되어있는 정보 보여주기
        urlAddrUpdate = "http://" + macIP + ":8080/MyPage/questionbank_MyPageStudent_PasswordUpdate.jsp?" ;

        //Connect EditText
        et_student_PresentPassword = findViewById(R.id.et_student_PresentPassword);
        et_student_NewPassword = findViewById(R.id.et_student_NewPassword);
        et_student_NewPasswordConfirm = findViewById(R.id.et_student_NewPasswordConfirm);
        //Connect Button
        btn_student_PasswordEditCancel = findViewById(R.id.btn_student_PasswordEditCancel);
        btn_student_PasswordEditOK = findViewById(R.id.btn_student_PasswordEditOK);

        //onClickListener
        btn_student_PasswordEditCancel.setOnClickListener(onClickListener);
        btn_student_PasswordEditOK.setOnClickListener(onClickListener);


    }//onCreate

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.btn_student_PasswordEditOK:
                    //EditText 입력값 변수
                    String PresentPassword = et_student_PresentPassword.getText().toString();
                    String NewPassword = et_student_NewPassword.getText().toString();
                    String NewPasswordConfirm = et_student_NewPasswordConfirm.getText().toString();

                    //위 입력값 변수가 null인지 유효성 검사
                    if(PresentPassword.length() == 0){ // 현재 비밀번호
                        Toast.makeText(MyPageStudentPasswordEdit.this,"현재 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                        et_student_PresentPassword.requestFocus();
                    }
                    if(NewPassword.length() == 0){ // 새 비밀번호
                        Toast.makeText(MyPageStudentPasswordEdit.this,"새 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                        et_student_NewPassword.requestFocus();
                    }
                    if(NewPasswordConfirm.length() == 0){ // 새 비밀번호 확인
                        Toast.makeText(MyPageStudentPasswordEdit.this,"새 비밀번호 확인을 입력해주세요", Toast.LENGTH_SHORT).show();
                        et_student_NewPasswordConfirm.requestFocus();
                    }

                    //새 비밀번호 length 확인(6글자 이상)
                    if(NewPassword.length() < 6){ // 새 비밀번호
                        Toast.makeText(MyPageStudentPasswordEdit.this,"새 비밀번호를 6글자 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                        et_student_NewPassword.requestFocus();
                        //ShareVar 비밀번호 & EditText에 입력한 비밀번호 일치 확인
                    }else if(PresentPassword.equals(spw_present)){
                        //입력한 (EditText) 새 비밀번호 & 새 비밀번호가 일치 하는 지 확인
                        if(NewPassword.equals(NewPasswordConfirm)){ // 일치하다면 *****비밀번호 변경*****
                            urlAddrUpdate = urlAddrUpdate + "spw=" + NewPassword + "&sid=" + sid;
                            String result = connectInsertData();
                            //*****ShareVar의 비밀번호도 변경해야함******
                            ShareVar.spw = NewPassword;
                            intent = new Intent(MyPageStudentPasswordEdit.this, MyPageStudentActivity.class);
                            startActivity(intent);
                            finish();

                        }else{ // 일치하지 않는다면 (새 비밀번호 & 새 비밀번호)
                            Toast.makeText(MyPageStudentPasswordEdit.this,
                                    "입력하신 새 비밀번호와 새 비밀번호 확인이 일치하지않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }else { //일치하지 않는다면 (ShareVar 비밀번호 & EditText에 입력한 비밀번호)
                        Toast.makeText(MyPageStudentPasswordEdit.this,
                                "입력하신 현재 비밀번호가 일치하지않습니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_student_PasswordEditCancel:
                    intent = new Intent(MyPageStudentPasswordEdit.this, MyPageStudentActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }

        }
    };


    private String connectInsertData(){
        String result = null;
        try{
            Workbook_NetworkTask networkTask = new Workbook_NetworkTask(MyPageStudentPasswordEdit.this, urlAddrUpdate, "update");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


}