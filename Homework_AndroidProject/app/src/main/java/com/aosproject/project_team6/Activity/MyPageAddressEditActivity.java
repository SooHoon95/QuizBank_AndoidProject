package com.aosproject.project_team6.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.Workbook_NetworkTask;
import com.aosproject.project_team6.R;

public class MyPageAddressEditActivity extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    //변수
    String urlAddr = null;
    String urlAddrUpdate = null;
    String tid = ShareVar.LoginID; // 선생님 tid값 (ShareVar)
    String taddress1 = ShareVar.taddress1; // 선생님 taddress1값 (ShareVar)
    String taddress2 = ShareVar.taddress2; // 선생님 taddress2값 (ShareVar)
    String macIP;

    WebView webView;
    TextView tv_mypage_addressEdit1;
    EditText et_mypage_addressEdit2;
    Button btn_Mypage_AddressEdit_OK, btn_Mypage_AddressEdit_Cancel;

    Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_address_edit);

        macIP = ShareVar.IPAddress;
        //업데이트용 jsp
        urlAddrUpdate = "http://" + macIP + ":8080/MyPage/questionbank_MyPageTeacher_AddressUpdate.jsp?";

        tv_mypage_addressEdit1 = findViewById(R.id.tv_mypage_addressEdit1);
        et_mypage_addressEdit2 = findViewById(R.id.et_mypage_addressEdit2);
        btn_Mypage_AddressEdit_OK = findViewById(R.id.btn_Mypage_AddressEdit_OK);
        btn_Mypage_AddressEdit_Cancel = findViewById(R.id.btn_Mypage_AddressEdit_Cancel);


        tv_mypage_addressEdit1.setOnClickListener(onClickListener);

        //입력된 Address DB Update
        btn_Mypage_AddressEdit_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taddress1 = tv_mypage_addressEdit1.getText().toString();
                taddress2 = et_mypage_addressEdit2.getText().toString();
                //********여기에 DB 내용 넣어야 함 ********
                urlAddrUpdate = urlAddrUpdate + "taddress1=" + taddress1 +  "&taddress2=" + taddress2 + "&tid=" + tid;
                String result = connectInsertData();
                Log.v("MESSAGE",taddress1 +"," + taddress2);

                //ShareVar 변경
                ShareVar.taddress1 = taddress1;
                ShareVar.taddress2 = taddress2;
//                if (result.equals("1")) {
//                    // 정상인 경우 ( 1만 정상이라는 것은 jsp 에서 판단 할 수 있도록 만들 예정임. )
//                    Toast.makeText(MyPageAddressEditActivity.this, "주소가 입력되었습니다", Toast.LENGTH_SHORT).show();
//                } else {/*에러걸렸으면*/
//                    Toast.makeText(MyPageAddressEditActivity.this, "주소 입력이 실패되었습니다.", Toast.LENGTH_SHORT).show();
//                }
                intent = new Intent(MyPageAddressEditActivity.this, MyPageTeacherActivity.class);
                startActivity(intent);
            }
        });

    }//onCreate

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.tv_mypage_addressEdit1:
                    intent = new Intent(MyPageAddressEditActivity.this, MyPageAddressWebViewActivity.class);
                    startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY); // webview에서 입력값을 가져옴
//                    startActivity(intent);
                    break;
//                case R.id.btn_Mypage_AddressEdit_OK:



            }

        }
    };

    //WebView에서 받은 값을 tv_mypage_addressEdit1에 넣어주기
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        tv_mypage_addressEdit1.setText(data);
                    }
                }
                break;
        }
    }

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
            Workbook_NetworkTask networkTask = new Workbook_NetworkTask(MyPageAddressEditActivity.this, urlAddrUpdate, "update");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}