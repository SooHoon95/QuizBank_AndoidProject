package com.aosproject.project_team6.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aosproject.project_team6.Adapter.TeacherListAdapter;
import com.aosproject.project_team6.Bean.TeacherListBean;
import com.aosproject.project_team6.Bean.WorkbookBean;
import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.ImageUpload_NetworkTask;
import com.aosproject.project_team6.NetworkTask.Question_NetworkTask;
import com.aosproject.project_team6.NetworkTask.Workbook_NetworkTask;
import com.aosproject.project_team6.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NewQuestionActivity extends AppCompatActivity {

    TextView tv_title, tv_currentPage, tv_totalPage, tv_upload_result;
    EditText edt_answer;
    Button btn_imageupload, btn_nextbutton;
    ImageView iv_questionImage;
    ArrayList<WorkbookBean> questions;

    // ImageUpload
    String imageName;
    private final int REQ_CODE_SELECT_IMAGE = 300; // Gallery Return Code
    private String img_path = null; // 최종 file name
    private String f_ext = null;    // 최종 file extension
    File tempSelectFile;
    String devicePath = Environment.getDataDirectory().getAbsolutePath() + "/data/com.aosproject.project_team6.Activity/";
    String urlAddr = null;

    // url
    String myIP = ShareVar.IPAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_question);

        // ActionBar 숨기기
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 이미지를 불러오기 위한 권한 요청하기
        ActivityCompat.requestPermissions(NewQuestionActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);
        ActivityCompat.requestPermissions(NewQuestionActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MODE_PRIVATE);


        // 버튼들 구성하기
        edt_answer = findViewById(R.id.edt_new_question_set_answer);
        btn_imageupload = findViewById(R.id.btn_new_question_imageupload);
        btn_imageupload.setOnClickListener(mClickListener);
        btn_nextbutton = findViewById(R.id.btn_new_question_next);
        btn_nextbutton.setOnClickListener(nextClick);

        // 결과 문구 세팅
        tv_upload_result = findViewById(R.id.tv_update_result);

        // 문제집 이름 세팅
        tv_title = findViewById(R.id.tv_new_question_title);
        tv_title.setText(ShareVar.NewWorkbook_Name);

        // 이미지 세팅
        iv_questionImage = findViewById(R.id.iv_new_question_image);
        iv_questionImage.setImageResource(R.drawable.basicimage);
        iv_questionImage.setOnClickListener(mClickListener);

        // 하단 페이지 숫자 세팅하기
        tv_currentPage = findViewById(R.id.tv_new_question_currentpage);
        tv_currentPage.setText(ShareVar.pageCount);
        tv_totalPage = findViewById(R.id.tv_new_question_totalpage);
        tv_totalPage.setText(ShareVar.totalCount);


    }//onCreate


    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_new_question_image:
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
                    break;
                case R.id.btn_new_question_imageupload:
                    urlAddr = "http://"+ ShareVar.IPAddress + ":8080/teacherlist/multipartRequest.jsp";
                    ImageUpload_NetworkTask networkTask = new ImageUpload_NetworkTask(NewQuestionActivity.this, urlAddr, img_path, iv_questionImage);
            try {
                Integer result = networkTask.execute(100).get();
                switch (result) {
                    case 1:

                        if (edt_answer.getText().toString().isEmpty() != true) {

                            String qanswer = edt_answer.getText().toString();

                            connectInsertData(imageName, qanswer, ShareVar.pageCount, ShareVar.newWorkbook_id);
                            Log.v("Message", "imagename : " + imageName);
                            Toast.makeText(NewQuestionActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                            File file = new File(img_path);
                            file.delete();

                            btn_imageupload.setVisibility(View.INVISIBLE);
                            tv_upload_result.setText(ShareVar.pageCount + "번 문제가 등록되었습니다!");
                            tv_upload_result.setVisibility(View.VISIBLE);
                            break;
                        }else{
                            Toast.makeText(NewQuestionActivity.this, "정답을 입력해주세요!", Toast.LENGTH_SHORT).show();
                            break;
                        }

                    case 0:
                        Toast.makeText(NewQuestionActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
//            case R.id.upload_back:
//            finish();
//            break;
        }
    }
};

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        Log.v("Message", "Data :" + String.valueOf(data));

        if (requestCode == REQ_CODE_SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                //이미지의 URI를 얻어 경로값으로 반환.
                img_path = getImagePathToUri(data.getData());
                Log.v("Message", "image path :" + img_path);
                Log.v("Message", "Data :" +String.valueOf(data.getData()));

                //이미지를 비트맵형식으로 반환
                Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                int height = image_bitmap.getHeight();
                int width = image_bitmap.getWidth();

                Bitmap image_bitmap_copy = null;


                //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
//                Bitmap image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);
                while (width > 400){
                    image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, (height*400)/width, true);
                    height = image_bitmap_copy.getHeight();
                    width = image_bitmap_copy.getWidth();
                }
                iv_questionImage.setImageBitmap(image_bitmap_copy);

                // 파일 이름 및 경로 바꾸기(임시 저장, 경로는 임의로 지정 가능)
                String date = new SimpleDateFormat("yyyyMMddhhmm").format(new Date());

                imageName = date + "." + f_ext;
                tempSelectFile = new File(devicePath , imageName);
                OutputStream out = new FileOutputStream(tempSelectFile);
                image_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                // 임시 파일 경로로 위의 img_path 재정의
                img_path = devicePath + imageName;
                Log.v("Message","fileName :" + img_path);
                Log.v("Message","imageName_activityResult :" + imageName);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }



        super.onActivityResult(requestCode, resultCode, data);
    }//onActivityResult

    private String getImagePathToUri(Uri data) {

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);
        Log.v("Message", "Image Path :" + imgPath);

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        // 확장자 명 저장
        f_ext = imgPath.substring(imgPath.length()-3, imgPath.length());

        return imgPath;
    }

    private String connectInsertData(String image, String answer, String qno, String wid){
        String result = null;
        try{
            urlAddr = "http://"+ ShareVar.IPAddress + ":8080/teacherlist/questionInsert.jsp?qimage="+image+"&qanswer="+answer + "&qno="+qno + "&wid="+wid;
            Question_NetworkTask networkTask1 = new Question_NetworkTask(NewQuestionActivity.this, urlAddr, "insert");
            Object obj = networkTask1.execute().get();
            result = (String)obj;

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    View.OnClickListener nextClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            initData();
        }
    };

    private void initData(){
        Log.v("Message", "## METHOD : initData start... ##");

        // 다음 버튼 누르면 항상 초기화 시켜주기
        if(ShareVar.pageCount != ShareVar.totalCount){
            ShareVar.pageCount = Integer.toString(Integer.parseInt(ShareVar.pageCount) + 1);
            tv_currentPage.setText(ShareVar.pageCount);
            Log.v("Message", "  - currentNum : "+ tv_currentPage.getText().toString());
            iv_questionImage.setImageResource(R.drawable.basicimage);
            edt_answer.setText("");
            tv_upload_result.setText("");
            tv_upload_result.setVisibility(View.INVISIBLE);
            btn_imageupload.setVisibility(View.VISIBLE);

        }

        // 문제등록 페이지가 마지막에 왔을 때, 버튼 변환시켜주기
        if(ShareVar.pageCount==ShareVar.totalCount){

            btn_nextbutton.setText("마침");
            btn_nextbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShareVar.pageCount = "1";
                    ShareVar.totalCount = null;
                    ShareVar.NewWorkbook_Name = null;
                    ShareVar.newWorkbook_id = null;
                    finish();
                }
            });
        }


    }




}//Main