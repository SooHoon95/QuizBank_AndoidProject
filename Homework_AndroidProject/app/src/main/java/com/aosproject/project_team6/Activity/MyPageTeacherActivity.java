package com.aosproject.project_team6.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.Workbook_NetworkTask;
import com.aosproject.project_team6.R;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class MyPageTeacherActivity extends AppCompatActivity {

    //변수
    String urlAddrUpdate = null;
    String tid = ShareVar.LoginID; // 선생님 tid값(ShareVar)
    String macIP;
    String msg = "Message";
    String tdeletedate = null;

    //자동로그인/로그아웃
    SharedPreferences autoSignin = null;
    String SigninEmail = null;
    String userStatus = null;

    TextView tv_MyPage_teacher_name, tv_Mypage_teacher_AccountInfo, tv_Mypage_teacher_Attend,
            tv_MyPage_teacher_AddressEdit, tv_MyPage_teacher_PasswordEdit
            , tv_MyPage_teacher_DeleteMyAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_teacher);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        macIP = ShareVar.IPAddress;

        //업데이트용 jsp
        urlAddrUpdate = "http://" + macIP + ":8080/MyPage/questionbank_MyPageTeacher_DeleteUpdate.jsp?";

        tv_MyPage_teacher_name = findViewById(R.id.tv_MyPage_teacher_name);
        tv_Mypage_teacher_AccountInfo = findViewById(R.id.tv_Mypage_teacher_AccountInfo);
        tv_Mypage_teacher_Attend = findViewById(R.id.tv_Mypage_teacher_Attend);
        tv_MyPage_teacher_AddressEdit = findViewById(R.id.tv_MyPage_teacher_AddressEdit);
        tv_MyPage_teacher_PasswordEdit = findViewById(R.id.tv_MyPage_teacher_PasswordEdit);
//        tv_MyPage_teacher_DeleteMyAccount = findViewById(R.id.tv_MyPage_teacher_DeleteMyAccount);

        //TextView setting
        tv_MyPage_teacher_name.setText(ShareVar.tname + "님의 마이페이지");

        tv_MyPage_teacher_AddressEdit.setOnClickListener(mOnClickListener);
        tv_MyPage_teacher_PasswordEdit.setOnClickListener(mOnClickListener);
//        tv_MyPage_teacher_DeleteMyAccount.setOnClickListener(mOnClickListener);

    }//onCreate

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;

            switch (v.getId()){
                case R.id.tv_MyPage_teacher_AddressEdit:
                    intent = new Intent(MyPageTeacherActivity.this, MyPageAddressActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.tv_MyPage_teacher_PasswordEdit:
                    intent = new Intent(MyPageTeacherActivity.this, MyPageTeacherPasswordEdit.class);
                    startActivity(intent);
                    finish();
//                case R.id.tv_MyPage_teacher_privacy:
//                    break;
//                case R.id.tv_MyPage_teacher_version:
//                    break;
//                case R.id.tv_MyPage_teacher_DeleteMyAccount:
//                    new AlertDialog.Builder(MyPageTeacherActivity.this)
//                            .setTitle("확인") // title
//                            .setMessage("탈퇴 하시겠습니까?") // content
//                            .setIcon(R.drawable.mainicon) // icon
//                            .setCancelable(false) // 버튼을 눌러야만 창이 닫아짐
//                            .setPositiveButton("탈퇴",mDeleteAccount)
//                            .setNegativeButton("취소",mDeleteAccount)
//                            .show();
//                    break;
                default:
                    break;
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
                urlAddrUpdate = urlAddrUpdate + "tid=" + tid;
                String result = connectInsertData();
//                if(result.equals("1")){
//                    // 정상인 경우 ( 1만 정상이라는 것은 jsp 에서 판단 할 수 있도록 만들 예정임. )
//                    Toast.makeText(MyPageTeacherActivity.this, "탈퇴되었습니다", Toast.LENGTH_SHORT).show();
////                    intent = new Intent(MyPageTeacherActivity.this, MainActivity.class);
////                    startActivity(intent);
//                }else  {/*에러걸렸으면*/
//                    Toast.makeText(MyPageTeacherActivity.this, "탈퇴가 실패되었습니다.",  Toast.LENGTH_SHORT).show();
//                }
                intent = new Intent(MyPageTeacherActivity.this, MainActivity.class);
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
            Workbook_NetworkTask networkTask = new Workbook_NetworkTask(MyPageTeacherActivity.this, urlAddrUpdate, "update");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}