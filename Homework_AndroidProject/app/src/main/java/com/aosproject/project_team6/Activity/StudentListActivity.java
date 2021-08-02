package com.aosproject.project_team6.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.aosproject.project_team6.Adapter.FragmentStudentAdapter;
import com.aosproject.project_team6.R;
import com.google.android.material.tabs.TabLayout;

public class StudentListActivity extends AppCompatActivity {

    ViewPager2 pager2;
    TabLayout tabLayout;
    FragmentStudentAdapter adapter;
    ImageView iv_subscribe, iv_mypage, iv_home, iv_seemore;
    TextView tv_subscribe, tv_mypage, tv_home, tv_seemore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("Message", "onCreate : StudentListActivity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

//        CardView cardView = findViewById(R.id.cardview_studentlist);
//        cardView.setOnClickListener(mcardClickListener);
//메뉴바 버튼
        iv_subscribe = findViewById(R.id.iv_subscribe);
        iv_mypage = findViewById(R.id.iv_mypage);
        iv_home = findViewById(R.id.iv_home);
        iv_seemore = findViewById(R.id.iv_seemore);
        tv_subscribe = findViewById(R.id.tv_subscribe);
        tv_mypage = findViewById(R.id.tv_mypage);
        tv_home = findViewById(R.id.tv_home);
        tv_seemore = findViewById(R.id.tv_seemore);

        iv_subscribe.setOnClickListener(mClickListener);
        iv_mypage.setOnClickListener(mClickListener);
        iv_home.setOnClickListener(mClickListener);
        iv_seemore.setOnClickListener(mClickListener);

        // ActionBar 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        tabLayout = findViewById(R.id.tabLayout);
        pager2 = findViewById(R.id.vp_s_do_quiz);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentStudentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.v("Message", "TabSelect");
                pager2.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


    }//onCreate

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()){
                case R.id.iv_subscribe:
                    Toast.makeText(StudentListActivity.this, "강사회원 전용 서비스입니다.", Toast.LENGTH_SHORT).show();
                    //색깔넣어주기
                    iv_subscribe.setBackgroundColor(Color.parseColor("#95CBCDCB"));
                    tv_subscribe.setBackgroundColor(Color.parseColor("#95CBCDCB"));

                    //다른 버튼 색상 꺼주기
                    iv_mypage.setBackgroundColor(Color.parseColor("#ffffff"));
                    iv_home.setBackgroundColor(Color.parseColor("#ffffff"));
                    iv_seemore.setBackgroundColor(Color.parseColor("#ffffff"));

                    tv_mypage.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv_home.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv_seemore.setBackgroundColor(Color.parseColor("#ffffff"));
                    break;
                case R.id.iv_mypage:
                    Intent intent1 = new Intent(StudentListActivity.this, MyPageStudentActivity.class);
                    startActivity(intent1);
                    //색깔넣어주기
                    iv_mypage.setBackgroundColor(Color.parseColor("#95CBCDCB"));
                    tv_mypage.setBackgroundColor(Color.parseColor("#95CBCDCB"));

                    //다른 버튼 색상 꺼주기
                    iv_subscribe.setBackgroundColor(Color.parseColor("#ffffff"));
                    iv_home.setBackgroundColor(Color.parseColor("#ffffff"));
                    iv_seemore.setBackgroundColor(Color.parseColor("#ffffff"));

                    tv_subscribe.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv_home.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv_seemore.setBackgroundColor(Color.parseColor("#ffffff"));
                    break;
                case R.id.iv_home:
                    //색깔넣어주기
                    iv_home.setBackgroundColor(Color.parseColor("#95CBCDCB"));
                    tv_home.setBackgroundColor(Color.parseColor("#95CBCDCB"));

                    //다른 버튼 색상 꺼주기
                    iv_mypage.setBackgroundColor(Color.parseColor("#ffffff"));
                    iv_subscribe.setBackgroundColor(Color.parseColor("#ffffff"));
                    iv_seemore.setBackgroundColor(Color.parseColor("#ffffff"));

                    tv_mypage.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv_subscribe.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv_seemore.setBackgroundColor(Color.parseColor("#ffffff"));

                    break;

                case R.id.iv_seemore:
                    intent = new Intent(StudentListActivity.this, OthersPageStudentActivity.class);
                    startActivity(intent);
                    //색깔넣어주기
                    iv_seemore.setBackgroundColor(Color.parseColor("#95CBCDCB"));
                    tv_seemore.setBackgroundColor(Color.parseColor("#95CBCDCB"));

                    //다른 버튼 색상 꺼주기
                    iv_mypage.setBackgroundColor(Color.parseColor("#ffffff"));
                    iv_subscribe.setBackgroundColor(Color.parseColor("#ffffff"));
                    iv_home.setBackgroundColor(Color.parseColor("#ffffff"));

                    tv_mypage.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv_subscribe.setBackgroundColor(Color.parseColor("#ffffff"));
                    tv_home.setBackgroundColor(Color.parseColor("#ffffff"));
                    break;

            }
        }
    };

//    View.OnClickListener mcardClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent intent;
//            TextView workbookTitle = findViewById(R.id.tv_student_workbooktitle);
//            String studentWorkbookTitle = workbookTitle.getText().toString();
//
//            Log.v("StudentDoShow", studentWorkbookTitle);
//            intent = new Intent(StudentListActivity.this, StudentDoShowActivity.class);
//            intent.putExtra("workbookTitle", studentWorkbookTitle);
//        }
//    };

}//