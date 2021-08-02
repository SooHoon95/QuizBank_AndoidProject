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

public class MyPageTeacherPasswordEdit extends AppCompatActivity {

    //변수
    String urlAddrUpdate = null;
    String tid = ShareVar.LoginID; // 선생님 tid값 (ShareVar)
    String tpw_present = ShareVar.tpw; // 선생님 tpw값(현재 값)
    String tpw = ""; //선생님 tqw값(바뀌는 값)
    String macIP;

    EditText et_teacher_PresentPassword, et_teacher_NewPassword, et_teacher_NewPasswordConfirm;
    Button btn_teacher_PasswordEditCancel, btn_teacher_PasswordEditOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_teacher_password_edit);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        macIP = ShareVar.IPAddress;
        //등록되어있는 정보 보여주기
        urlAddrUpdate = "http://" + macIP + ":8080/MyPage/questionbank_MyPageTeacher_PasswordUpdate.jsp?" ;

        //Connect EditText
        et_teacher_PresentPassword = findViewById(R.id.et_teacher_PresentPassword);
        et_teacher_NewPassword = findViewById(R.id.et_teacher_NewPassword);
        et_teacher_NewPasswordConfirm = findViewById(R.id.et_teacher_NewPasswordConfirm);
        //Connect Button
        btn_teacher_PasswordEditCancel = findViewById(R.id.btn_teacher_PasswordEditCancel);
        btn_teacher_PasswordEditOK = findViewById(R.id.btn_teacher_PasswordEditOK);

        //onClickListener
        btn_teacher_PasswordEditCancel.setOnClickListener(onClickListener);
        btn_teacher_PasswordEditOK.setOnClickListener(onClickListener);


    }//onCreate

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.btn_teacher_PasswordEditOK:
                    //EditText 입력값 변수
                    String PresentPassword = et_teacher_PresentPassword.getText().toString();
                    String NewPassword = et_teacher_NewPassword.getText().toString();
                    String NewPasswordConfirm = et_teacher_NewPasswordConfirm.getText().toString();

                    //위 입력값 변수가 null인지 유효성 검사
                    if(PresentPassword.length() == 0){ // 현재 비밀번호
                        Toast.makeText(MyPageTeacherPasswordEdit.this,"현재 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                        et_teacher_PresentPassword.requestFocus();
                    }
                    if(NewPassword.length() == 0){ // 새 비밀번호
                        Toast.makeText(MyPageTeacherPasswordEdit.this,"새 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                        et_teacher_NewPassword.requestFocus();
                    }
                    if(NewPasswordConfirm.length() == 0){ // 새 비밀번호 확인
                        Toast.makeText(MyPageTeacherPasswordEdit.this,"새 비밀번호 확인을 입력해주세요", Toast.LENGTH_SHORT).show();
                        et_teacher_NewPasswordConfirm.requestFocus();
                    }

                    //새 비밀번호 length 확인(6글자 이상)
                    if(NewPassword.length() < 6){ // 새 비밀번호
                        Toast.makeText(MyPageTeacherPasswordEdit.this,"새 비밀번호를 6글자 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                        et_teacher_NewPassword.requestFocus();
                        //ShareVar 비밀번호 & EditText에 입력한 비밀번호 일치 확인
                    }else if(PresentPassword.equals(tpw_present)){
                        //입력한 (EditText) 새 비밀번호 & 새 비밀번호가 일치 하는 지 확인
                        if(NewPassword.equals(NewPasswordConfirm)){ // 일치하다면 *****비밀번호 변경*****
                            urlAddrUpdate = urlAddrUpdate + "tpw=" + NewPassword + "&tid=" + tid;
                            String result = connectInsertData();
                            //*****ShareVar의 비밀번호도 변경해야함******
                            ShareVar.tpw = NewPassword;
                            intent = new Intent(MyPageTeacherPasswordEdit.this, MyPageTeacherActivity.class);
                            startActivity(intent);
                            finish();

                        }else{ // 일치하지 않는다면 (새 비밀번호 & 새 비밀번호)
                            Toast.makeText(MyPageTeacherPasswordEdit.this,
                                    "입력하신 새 비밀번호와 새 비밀번호 확인이 일치하지않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }else { //일치하지 않는다면 (ShareVar 비밀번호 & EditText에 입력한 비밀번호)
                        Toast.makeText(MyPageTeacherPasswordEdit.this,
                                "입력하신 현재 비밀번호가 일치하지않습니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_teacher_PasswordEditCancel:
                    intent = new Intent(MyPageTeacherPasswordEdit.this, MyPageTeacherActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }

        }
    };

    private String connectInsertData(){
        String result = null;
        try{
            Workbook_NetworkTask networkTask = new Workbook_NetworkTask(MyPageTeacherPasswordEdit.this, urlAddrUpdate, "update");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


}