package com.aosproject.project_team6.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.aosproject.project_team6.Bean.TeacherListBean;
import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.Workbook_NetworkTask;
import com.aosproject.project_team6.R;

import java.util.ArrayList;
import java.util.Calendar;

public class TeacherUpdateWorkbookActivity extends AppCompatActivity {

    EditText edt_title;
    Spinner spinner_number;
    Button btn_math, btn_eng, btn_workbookOK, btn_duedate;
    ArrayAdapter<CharSequence> adapter = null;
    ArrayList<TeacherListBean> workbooks;

    int dateCount = 0;

    // DB 등록용

    String myIP = ShareVar.IPAddress;
    String urlAddr = "http://" + myIP + ":8080/teacherlist/workbookUpdate.jsp?";
    String wtitle = null;
    String subject = null;
    String duedate = null;
    int Quantity = 0;
    String ID = ShareVar.LoginID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_update_workbook);

        // ActionBar 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Title Setting
        edt_title = findViewById(R.id.etd_update_title);
        edt_title.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});

        // Button Setting
        btn_math = findViewById(R.id.btn_subject_math_update);
        btn_eng = findViewById(R.id.btn_subject_eng_update);
        btn_workbookOK= findViewById(R.id.btn_new_workbook_update);
        btn_duedate = findViewById(R.id.btn_select_duedate_update);

        // Button Listener
        btn_math.setOnClickListener(subjectClick);
        btn_eng.setOnClickListener(subjectClick);
        btn_workbookOK.setOnClickListener(insertAction);

        // Spinner Setting
        adapter = ArrayAdapter.createFromResource(this, R.array.quiz_numbers, android.R.layout.simple_spinner_dropdown_item);
        spinner_number = findViewById(R.id.spinner_number_update);
        spinner_number.setAdapter(adapter);
        spinner_number.setOnItemSelectedListener(selectListener);

        /////////////////////////////////////////////////
        //                기존 값 불러오기                 //
        /////////////////////////////////////////////////
        updateWorkbook();
        edt_title.setText(workbooks.get(0).getWtitle());
        spinner_number.setSelection(workbooks.get(0).getQuantity()-1);
        if(workbooks.get(0).getSubject().equals("math")){
            btn_math.setTextColor(0xff000000);
            btn_eng.setTextColor(0xffffffff);
            subject = "math";
        }else{
            btn_eng.setTextColor(0xff000000);
            btn_math.setTextColor(0xffffffff);
            subject = "eng";
        }
        btn_duedate.setText(workbooks.get(0).getDuedate());
        /////////////////////////////////////////////////


        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                if(month >= 9){
                    btn_duedate.setText(year + "-" + (month+1) + "-" + dayOfMonth);
                    dateCount++;
                }else {
                    btn_duedate.setText(year + "-0" + (month + 1) + "-" + dayOfMonth);
                    dateCount++;
                }
            }

        }, mYear, mMonth, mDay);
        btn_duedate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
    }//onCreate

    // Subject Select
    View.OnClickListener subjectClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_subject_math_update:
                    btn_math.setTextColor(0xff000000);
                    btn_eng.setTextColor(0xffffffff);
                    subject = "math";
                    break;
                case R.id.btn_subject_eng_update:
                    btn_eng.setTextColor(0xff000000);
                    btn_math.setTextColor(0xffffffff);
                    subject = "eng";
                    break;

            }
        }
    };


    // Spinner Listener
    AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            Quantity = position;
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    // Insert to DB
    View.OnClickListener insertAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.v("Message", "## METHOD : insertAction Start ##");

            if (String.valueOf(edt_title.getText()).isEmpty()){
                Toast.makeText(TeacherUpdateWorkbookActivity.this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            }else if(subject == null){
                Toast.makeText(TeacherUpdateWorkbookActivity.this, "과목을 설정해주세요.", Toast.LENGTH_SHORT).show();
            }else if(dateCount == 0){
                Toast.makeText(TeacherUpdateWorkbookActivity.this, "마감기한을 설정해주세요.", Toast.LENGTH_SHORT).show();
            }else{

                wtitle = String.valueOf(edt_title.getText());
                duedate = (String) btn_duedate.getText();

                urlAddr = urlAddr + "wtitle="      + wtitle +
                                    "&duedate="    + duedate +
                                    "&subject="    + subject +
                                    "&quantity="   + (Quantity+1) +
                                    "&teacher_id=" + ID +
                                    "&wid="        + ShareVar.newWorkbook_id;
                Log.v("Message", "  - UPDATE TO DB : " + urlAddr);

                new AlertDialog.Builder(TeacherUpdateWorkbookActivity.this)
                        .setTitle("문제집 수정")
                        .setIcon(R.mipmap.ic_launcher_round)
                        .setMessage(wtitle + " 문제집을 수정하시겠습니까?")
                        .setPositiveButton("수정", mDialogClick)
                        .setNegativeButton("취소", null)
                        .show();

            }



        }
    };

    private String connectInsertData(){
        String result = null;
        try {
            // NetworkTask 가져와서 일을 시킬 거다 (어디에?, 어느 주소받아서?, 어느역할이야?)
            Workbook_NetworkTask networkTask = new Workbook_NetworkTask(TeacherUpdateWorkbookActivity.this, urlAddr, "update");
            Object obj = networkTask.execute().get();
            result = (String)obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    DialogInterface.OnClickListener mDialogClick = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            connectInsertData();
            new AlertDialog.Builder(TeacherUpdateWorkbookActivity.this)
                    .setMessage("수되었습니다!")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(TeacherUpdateWorkbookActivity.this, TeacherListActivity.class);
                            startActivity(intent);
                        }
                    })
                    .show();

        }
    };

    private void updateWorkbook(){
        String urlAddr2 = "http://" + ShareVar.IPAddress + ":8080/teacherlist/workbookSelect.jsp?wid="+ShareVar.newWorkbook_id;

        try {
            Workbook_NetworkTask networkTask = new Workbook_NetworkTask(TeacherUpdateWorkbookActivity.this, urlAddr2, "select");
            Object obj = networkTask.execute().get();
            workbooks = (ArrayList<TeacherListBean>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }



    }




}