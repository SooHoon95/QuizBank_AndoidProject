package com.aosproject.project_team6.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aosproject.project_team6.Bean.TeacherListBean;
import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.Subscribe_NetworkTask;
import com.aosproject.project_team6.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PaymentCompleteActivity extends AppCompatActivity {

    Button btn_paymentcomplete;

    // 날짜 구하기
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_complete);

        // ActionBar 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btn_paymentcomplete = findViewById(R.id.pay_complete);
        btn_paymentcomplete.setOnClickListener(onClick);

    } // onCreate

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String now = getTime();
            String subscribeURL = "http://"+ ShareVar.IPAddress + ":8080/login/subscribe.jsp?paydate="+now+"&teacher_id="+ShareVar.LoginID;
            Subscribe_NetworkTask networkTask = new Subscribe_NetworkTask(PaymentCompleteActivity.this, subscribeURL, "insert");

            try {
                Object obj = networkTask.execute().get();
                Log.v("Message", "obj : "+ obj);
                String check = (String) obj;
                Log.v("Message", "check : "+ check);

            }catch (Exception e){
                e.printStackTrace(); Toast.makeText(PaymentCompleteActivity.this, "정보 등록 중 오류가 발생했습니다!", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(PaymentCompleteActivity.this, TeacherListActivity.class);
            startActivity(intent);

        }
    };

}