package com.aosproject.project_team6.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.aosproject.project_team6.R;

public class Pay1Activity extends AppCompatActivity {

//    EditText editTextName;
//    EditText editTextPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay1);

        // ActionBar 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //        getHashKey();
//        editTextName = findViewById(R.id.editName);
//        editTextPrice = findViewById(R.id.editPrice);

        // 버튼 클릭 이벤트
        Button button = findViewById(R.id.buttonPay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "문제뭐양 프리미엄회원";
                String price = "4900";

                Pay2Activity pay_2_activityActivity = new Pay2Activity(name, price);

                Intent intent = new Intent(getApplicationContext(), pay_2_activityActivity.getClass());
                startActivity(intent);
            }
        });


    }
}