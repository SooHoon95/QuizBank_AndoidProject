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
public class OthersPageTeacherActivity extends AppCompatActivity {
    //변수
    String urlAddrUpdate = null;
    String tid = ShareVar.LoginID; // 선생님 tid값(ShareVar)
    String macIP;
    String msg = "Message";
    String tdeletedate = null;
    TextView tv_version, tv_logout, tv_delete;

    //자동로그인/로그아웃
    SharedPreferences autoSignin = null;
    String SigninEmail = null;
    String userStatus = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others_page_teacher);
        // ActionBar 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        macIP = ShareVar.IPAddress;
        //업데이트용 jsp
        urlAddrUpdate = "http://" + macIP + ":8080/MyPage/questionbank_MyPageTeacher_DeleteUpdate.jsp?";
        tv_version = findViewById(R.id.tv_OthersPage_teacher_version);
        tv_logout = findViewById(R.id.tv_OthersPage_teacher_Logout);
        tv_delete = findViewById(R.id.tv_OthersPage_teacher_DeleteMyAccount);
        tv_version.setOnClickListener(onClickListener);
        tv_logout.setOnClickListener(onClickListener);
        tv_delete.setOnClickListener(onClickListener);
    }//onCreate
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tv_OthersPage_teacher_version:
                    new androidx.appcompat.app.AlertDialog.Builder(OthersPageTeacherActivity.this)
                            .setTitle("문제맛집 버전")
                            .setMessage("현재 버전은 1.0 입니다.")
                            .setIcon(R.drawable.mainicon)
                            .setPositiveButton("닫기",null)
                            .show();
                    break;
                case R.id.tv_OthersPage_teacher_Logout:
                    new AlertDialog.Builder(OthersPageTeacherActivity.this)
                            .setTitle("확인") // title
                            .setMessage("로그아웃 하시겠습니까?") // content
                            .setIcon(R.drawable.mainicon) // icon
                            .setCancelable(false) // 버튼을 눌러야만 창이 닫아짐
                            .setPositiveButton("로그아웃",mLogout)
                            .setNegativeButton("취소",mLogout)
                            .show();
                    break;
                case R.id.tv_OthersPage_teacher_DeleteMyAccount:
                    new AlertDialog.Builder(OthersPageTeacherActivity.this)
                            .setTitle("확인") // title
                            .setMessage("탈퇴 하시겠습니까?") // content
                            .setIcon(R.drawable.mainicon) // icon
                            .setCancelable(false) // 버튼을 눌러야만 창이 닫아짐
                            .setPositiveButton("탈퇴",mDeleteAccount)
                            .setNegativeButton("취소",mDeleteAccount)
                            .show();
                    break;
            }
        }
    };
    //Dialog Logout
    DialogInterface.OnClickListener mLogout = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            if(which == DialogInterface.BUTTON_POSITIVE){
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        //로그아웃 성공 시
                        saveSignOutData();
                        Intent intent = new Intent(OthersPageTeacherActivity.this, SignInActivity.class );
                        startActivity(intent);
                        //ShareVar 내역 비우기
                        ShareVar.tname = null;
                        ShareVar.tpw = null;
                        ShareVar.taddress1 = null;
                        ShareVar.taddress2 = null;
                        finish(); // 현재 액티비티 종료
                    }
                });
            }else{
            };
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
                intent = new Intent(OthersPageTeacherActivity.this, MainActivity.class);
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
            Workbook_NetworkTask networkTask = new Workbook_NetworkTask(OthersPageTeacherActivity.this, urlAddrUpdate, "update");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public void saveSignOutData(){
        autoSignin = getSharedPreferences("signInAuto", Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = autoSignin.edit();
        editor.putString("autoEmail", null);
        editor.putString("autoName", null);
        switch(ShareVar.UserStatus){
            case "teacher":
                editor.putString("autoAddress1", null);
                editor.putString("autoAddress2", null);
                editor.putString("userStatus", null);
                break;
            case "student":
                editor.putString("autoDivision", null);
                editor.putString("userStatus", null);
                break;
        }
        editor.commit();
    } // saveSigninData

}