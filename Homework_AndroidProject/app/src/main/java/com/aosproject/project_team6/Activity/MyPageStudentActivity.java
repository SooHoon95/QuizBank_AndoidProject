package com.aosproject.project_team6.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.Workbook_NetworkTask;
import com.aosproject.project_team6.R;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class MyPageStudentActivity extends AppCompatActivity {

    //변수
    String urlAddr = null;
    String urlAddrUpdate = null;
    String sid = ShareVar.LoginID;
    String macIP;
    String msg = "Message";


    TextView tv_MyPage_student_name, tv_MyPage_student_DivisionEdit, tv_Mypage_student_passwordEdit
            , tv_MyPage_student_DeleteMyAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_student);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        macIP = ShareVar.IPAddress;
        //업데이트용 jsp
        urlAddrUpdate = "http://" + macIP + ":8080/MyPage/questionbank_MyPageStudent_DeleteUpdate.jsp?";

        tv_MyPage_student_name = findViewById(R.id.tv_MyPage_student_name);
        tv_MyPage_student_DivisionEdit = findViewById(R.id.tv_MyPage_student_DivisionEdit);
        tv_Mypage_student_passwordEdit = findViewById(R.id.tv_MyPage_student_PasswordEdit);
//        tv_MyPage_student_DeleteMyAccount = findViewById(R.id.tv_MyPage_student_DeleteMyAccount);

        //TextView Setting
        tv_MyPage_student_name.setText(ShareVar.sname + "님의 마이페이지");

        tv_MyPage_student_DivisionEdit.setOnClickListener(mOnClickListener);
        tv_Mypage_student_passwordEdit.setOnClickListener(mOnClickListener);
//        tv_MyPage_student_DeleteMyAccount.setOnClickListener(mOnClickListener);


    }//onCreate

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.tv_MyPage_student_DivisionEdit:
                    intent = new Intent(MyPageStudentActivity.this, MyPageDivisionActivity.class);
                    intent.putExtra("sid", sid);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.tv_MyPage_student_PasswordEdit:
                    intent = new Intent(MyPageStudentActivity.this, MyPageStudentPasswordEdit.class);
                    startActivity(intent);
                    finish();
            }
        }
    };


    //Dialog DeleteAccount
    DialogInterface.OnClickListener mDeleteAccount = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Intent intent = null;
            if(which == DialogInterface.BUTTON_POSITIVE){
                //********여기에 DB 내용 넣어야 함 ********
                urlAddrUpdate = urlAddrUpdate + "sid=" + sid;
                String result = connectInsertData();
//                if(result.equals("1")){
//                    // 정상인 경우 ( 1만 정상이라는 것은 jsp 에서 판단 할 수 있도록 만들 예정임. )
//                    Toast.makeText(MyPageTeacherActivity.this, "탈퇴되었습니다", Toast.LENGTH_SHORT).show();
////                    intent = new Intent(MyPageTeacherActivity.this, MainActivity.class);
////                    startActivity(intent);
//                }else  {/*에러걸렸으면*/
//                    Toast.makeText(MyPageTeacherActivity.this, "탈퇴가 실패되었습니다.",  Toast.LENGTH_SHORT).show();
//                }
                intent = new Intent(MyPageStudentActivity.this, MainActivity.class);
                startActivity(intent);

            }else{

            };

        }
    };


    //    private void connectGetdata() {
//        try {
//
//            Workbook_NetworkTask networkTask = new Workbook_NetworkTask(MyPageDivisionActivity.this, urlAddr, "select");
//            Object obj = networkTask.execute().get();
//            members = (ArrayList<StudentMyPage>) obj;
//
//            adapter = new StudentAdapter(MyPageDivisionActivity.this, R.layout.activity_my_page_division, members);
//            listView.setAdapter(adapter);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    private String connectInsertData(){
        String result = null;
        try{
            Workbook_NetworkTask networkTask = new Workbook_NetworkTask(MyPageStudentActivity.this, urlAddrUpdate, "update");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }




}