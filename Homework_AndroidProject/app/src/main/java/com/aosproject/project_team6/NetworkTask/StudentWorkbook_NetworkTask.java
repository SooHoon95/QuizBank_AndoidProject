package com.aosproject.project_team6.NetworkTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.aosproject.project_team6.Bean.AuthBean;
import com.aosproject.project_team6.Bean.StudentListBean;
import com.aosproject.project_team6.Bean.TeacherListBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class StudentWorkbook_NetworkTask extends AsyncTask<Integer, String, Object> {

    Context context = null;
    String mAddr = null;
    ArrayList<TeacherListBean> workbooks;
    ArrayList<StudentListBean> studentworkbooks;
    ArrayList<StudentListBean> sDoShowQuiz;
    ArrayList<StudentListBean> sDoneShowQuiz;
    ArrayList<StudentListBean> getDuedate;
    ArrayList<StudentListBean> getdDay;
    ArrayList<AuthBean> authListImport ;

    String where = null;

    // Constructor


    public StudentWorkbook_NetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.workbooks = new ArrayList<TeacherListBean>();
        this.studentworkbooks = new ArrayList<StudentListBean>();
        this.sDoShowQuiz = new ArrayList<StudentListBean>();
        this.getDuedate = new ArrayList<StudentListBean>();
        this.getdDay = new ArrayList<StudentListBean>();
        this.sDoneShowQuiz = new ArrayList<StudentListBean>();
        this.authListImport= new ArrayList<AuthBean>();
        this.where = where;
    } // constructor


    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v("Message", "  - doInBackgroud ... start");

        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        String result = null; // networktask 잘 했는지 안했는지 받을 거임
        String compareValue = null;

        try {
            URL url = new URL(mAddr);//ip주소 -- 생성자 할때 받음
            Log.v("Message", "  - doIn mAddr : " + mAddr);


            Log.v("Message", "  - doIn Http start");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            //서버 받으려면 무조건 httpurl 필요하구나.
            Log.v("Message", "  - doIn Http check 1");
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                Log.v("Message", "  - doIn Http check 2");
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                Log.v("Message", "  - doIn bufferedReader : " + bufferedReader);
                //--> string 인식 하려구!
                while (true) {
                    String strline = bufferedReader.readLine();
                    if (strline == null) break;
                    stringBuffer.append(strline + "\n");
                }
                //이제 JSON 을 만들어 줘야 하므로 구분하자
                //넌 무슨 기능이니? select, insert, delete
//                if (where.equals("select")) {
//
//                    Log.v("Message", "  - doIn select start");
//                    //return 값이 없고
//                    parserSelect(stringBuffer.toString());
//                }
                if (where.equals("Student_Select")) {
                    parserStudentSelect(stringBuffer.toString());
                    //return 값이 있다.
//                    result = parserAction(stringBuffer.toString());
                } else if (where.equals("StudentDoneWorkbook_Select")) {
                    parserStudentDoneSelect(stringBuffer.toString());

                } else if (where.equals("studentWorkbookTitle")) {
                    parserStudentDoShowWorkbook(stringBuffer.toString());

                } else if (where.equals("StudentDoShowQuiz")) {
                    parserStudentDoShowQuiz(stringBuffer.toString());

                } else if (where.equals("Insert_my_answer")) {
                    result = parserAction(stringBuffer.toString());

                } else if (where.equals("Make_Tb_solve")) {
                    result = parserAction(stringBuffer.toString());

                } else if (where.equals("GradeMyAnswer")) {
                    parserGradeAnswer(stringBuffer.toString());

                } else if (where.equals("setScore")) {
                    result = parserAction(stringBuffer.toString());

                } else if (where.equals("getDuedate")) {
                    getDuedate(stringBuffer.toString());
                } else if (where.equals("D_day")) {
                    getDday(stringBuffer.toString());
                } else if (where.equals("getScore")) {
                    getScore(stringBuffer.toString());
                } else if (where.equals("studentCheckAnswer")) {
                    parserCheckAnswer(stringBuffer.toString());
                } else if (where.equals("SelectDoneWorkbook_myAnswer")){
                    getMyAnswer(stringBuffer.toString());
                } else if (where.equals("AuthInsert")){
                    result = parserAction(stringBuffer.toString());
                }else if(where.equals("AuthList")){
                    parserAuthList(stringBuffer.toString());
                }
                else {
                    Log.v("InsertTest", "  - Insert_my_answer start");

                    result = parserAction(stringBuffer.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //
//        if (where.equals("select")) {
//
//            Log.v("Message", "  - doIn return workbooks : " + workbooks);
//            return workbooks; // select는 엄청 많은 값이 들어올거임
//        }
        if (where.equals("Student_Select")) {
            Log.v("Message", "  - doIn return Studentworkbooks : " + studentworkbooks);
            return studentworkbooks;
        } else if (where.equals("StudentDoneWorkbook_Select")) {
            return studentworkbooks;
        } else if (where.equals("StudentDoShowQuiz")) {
            return sDoShowQuiz;
        } else if (where.equals("Insert_my_answer")) {
            return result;
        } else if (where.equals("Make_Tb_solve")) {
            return result;
        } else if (where.equals("GradeMyAnswer")) {
            return result = parserGradeAnswer(stringBuffer.toString());
        } else if (where.equals("getDuedate")) {
            return getDuedate;
        } else if (where.equals("D_day")) {
            return getdDay;
        } else if (where.equals("getScore")) {
            return result = getScore(stringBuffer.toString());
        } else if (where.equals("studentCheckAnswer")) {
            return sDoneShowQuiz;
        } else if (where.equals("SelectDoneWorkbook_myAnswer")){
           return result = getMyAnswer(stringBuffer.toString());
        } else if (where.equals("AuthInsert")){
            return result = parserAction(stringBuffer.toString());
        }else if( where.equals("AuthList")) {
            return authListImport;
        }
        else {
            return result; //입력 수정 삭제는 잘했다 못했다만 넘어 올거고
        }


    }//  doInback


    //업데이트 또는 삭제
    private String parserAction(String str){
        String returnValue = null;
        try{
            Log.v("Message", "Method ParserAction Start");
            JSONObject jsonObject = new JSONObject(str);
            returnValue = jsonObject.getString("result");
        }catch (Exception e){
            e.printStackTrace();
            Log.v("InsertTest", "Error111111111");
        }
        return returnValue;
    }


//    private void parserSelect(String str){
//
//        Log.v("Message", "  - Method parserSelect Start");
//        try{
//            JSONObject jsonObject = new JSONObject(str);
//            JSONArray jsonArray = new JSONArray(jsonObject.getString("workbook_info"));
//            workbooks.clear();
//
//            for(int i=0; i<jsonArray.length(); i++){
//                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
//                int wid = jsonObject1.getInt("wid");
//                String wtitle = jsonObject1.getString("wtitle");
//                String subject = jsonObject1.getString("subject");
//                String duedate = jsonObject1.getString("duedate");
//                //어레이에 있는거 뽑아와서 빈
//                TeacherListBean bean = new TeacherListBean(wid, wtitle, subject, duedate);
//                workbooks.add(bean);
//                //members는 어레이리스트, member는 빈
//                //--->for문 돌면서 차곡차곡 쌓기
//            }
//
//
//        }catch (Exception e){
//            Log.v("Message", "Fail to get DB");
//            e.printStackTrace();
//        }
//
//    }// parserSelect

    private void parserStudentSelect(String str){
        Log.v("Message", "  - Method parserStudentSelect Start");
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("studentworkbook"));
            Log.v("Message","make Json");
            studentworkbooks.clear();

            Log.v("Message","make Json2");

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String wtitle = jsonObject1.getString("wtitle");
                String duedate = jsonObject1.getString("duedate");
                String subject = jsonObject1.getString("subject");
                int wid = jsonObject1.getInt("wid");
                //어레이에 있는거 뽑아와서 빈
                StudentListBean studentlistbean = new StudentListBean(wtitle, subject, duedate, wid);
                studentworkbooks.add(studentlistbean);
                //members는 어레이리스트, member는 빈
                //--->for문 돌면서 차곡차곡 쌓기
            }


        }catch (Exception e){
            Log.v("Message", "Fail to get DB");
            e.printStackTrace();
        }
    }//parserStudentSelect

    private void parserStudentDoneSelect(String str){
        Log.v("Message", "  - Method parserStudentDoneSelect Start");
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("Student_Done_Workbook"));
            Log.v("Message","make Json");
            studentworkbooks.clear();

            Log.v("Message","make Json");

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String wtitle = jsonObject1.getString("wtitle");
                String duedate = jsonObject1.getString("duedate");
                String subject = jsonObject1.getString("subject");
                int wid = jsonObject1.getInt("wid");
                //어레이에 있는거 뽑아와서 빈
                StudentListBean studentlistbean = new StudentListBean(wtitle, subject, duedate, wid);
                studentworkbooks.add(studentlistbean);
                //members는 어레이리스트, member는 빈
                //--->for문 돌면서 차곡차곡 쌓기
            }


        }catch (Exception e){
            Log.v("Message", "Fail to get DB");
            e.printStackTrace();
        }
    }//

    //학생 숙제 출력
    private void parserStudentDoShowWorkbook(String str){
        Log.v("Message", "  - Method parserStudentDoShow Start");

        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("student_do_show"));
            Log.v("Message","make Json");
            studentworkbooks.clear();

            Log.v("Message","make Json1");

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String wtitle = jsonObject1.getString("wtitle");
                String duedate = jsonObject1.getString("duedate");
                String subject = jsonObject1.getString("subject");
                //어레이에 있는거 뽑아와서 빈
                StudentListBean studentlistbean = new StudentListBean(wtitle, subject, duedate);
                studentworkbooks.add(studentlistbean);
                //members는 어레이리스트, member는 빈
                //--->for문 돌면서 차곡차곡 쌓기
            }


        }catch (Exception e){
            Log.v("Message", "Fail to get DB");
            e.printStackTrace();
        }
    }//

    private void parserStudentDoShowQuiz(String str){
        Log.v("Message", "  - Method parserStudentDoShowQuiz Start");
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("student_do_show_quiz"));
            Log.v("Message","make Json");
            studentworkbooks.clear();

            Log.v("Message","make Json1");

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int seq_no = jsonObject1.getInt("seq_no");
                String qno = jsonObject1.getString("qno");
                String qimage = jsonObject1.getString("qimage");
//                String qanswer = jsonObject1.getString("qanswer");

                Log.v("Message", "seq_no : " + seq_no);
                Log.v("Message", "qno : " + qno);
                Log.v("Message", "qimage : " + qimage);

                //어레이에 있는거 뽑아와서 빈
                StudentListBean studentlistbean = new StudentListBean(seq_no, qno, qimage);
                sDoShowQuiz.add(studentlistbean);
                //members는 어레이리스트, member는 빈
                //--->for문 돌면서 차곡차곡 쌓기
            }


        }catch (Exception e){
            Log.v("Message", "Fail to get DB");
            e.printStackTrace();
        }

    }//

    //학생이 지난 숙제 확인하기
    private void parserCheckAnswer(String str){
        Log.v("Message", "  - Method parserStudentDoShowQuiz Start");
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("student_do_show_quiz"));
            Log.v("Message","make Json");
            sDoneShowQuiz.clear();

            Log.v("Message","make Json1");

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int seq_no = jsonObject1.getInt("seq_no");
                String qno = jsonObject1.getString("qno");
                String qimage = jsonObject1.getString("qimage");
                String qanswer = jsonObject1.getString("qanswer");

                Log.v("Message", "seq_no : " + seq_no);
                Log.v("Message", "qno : " + qno);
                Log.v("Message", "qimage : " + qimage);

                //어레이에 있는거 뽑아와서 빈
                StudentListBean studentlistbean = new StudentListBean(seq_no, qno, qimage, qanswer);
                sDoneShowQuiz.add(studentlistbean);
                //members는 어레이리스트, member는 빈
                //--->for문 돌면서 차곡차곡 쌓기
            }


        }catch (Exception e){
            Log.v("Message", "Fail to get DB");
            e.printStackTrace();
        }

    }


    //문제 하나씩 가져와서 채점하기
    public String parserGradeAnswer(String str){
        Log.v("Message", "  - Method parserGradeAnswer Start");
        //반환할 결과값
        String compareValue = "";
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("GradeMyAnswer"));
            Log.v("Message","make Json");
            Log.v("Message","make Json1");

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String compareResult = jsonObject1.getString("compareResult");

                Log.v("Message", "compareResult : " + compareResult);

                compareValue = compareResult;
            }


        }catch (Exception e){
            Log.v("Message", "Fail to get DB");
            e.printStackTrace();
        }
        return compareValue;
    }//

    public void getDuedate(String str){
        Log.v("Message", "  - Method getDuedate Start");
        //반환할 결과값
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("Duedate"));
            Log.v("Message","make Json");
            getDuedate.clear();

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String dDayuseDuedate = jsonObject1.getString("duedate");

                StudentListBean studentListBean = new StudentListBean(dDayuseDuedate);
                getDuedate.add(studentListBean);

                Log.v("Message", "dDayuseDuedate : " + dDayuseDuedate);
            }

        }catch (Exception e){
            Log.v("Message", "Fail to get DB");
            e.printStackTrace();
        }

    }//getDuedate

    public void getDday(String str){
        Log.v("Message", "  - Method getDday Start");
        //반환할 결과값
        String Dday = "";
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("D_day"));
            Log.v("Message","make Json");
            getdDay.clear();

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String wbDday = jsonObject1.getString("dDay");

                StudentListBean studentListBean = new StudentListBean(wbDday);
                getDuedate.add(studentListBean);
                Log.v("Message", "Dday : " + wbDday);
            }


        }catch (Exception e){
            Log.v("Message", "Fail to get DB");
            e.printStackTrace();
        }
    }//getDday


    public String getScore(String str){
        Log.v("Message", "  - Method getScore Start");
        String getScoreResult = "";
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("GetScore"));
            Log.v("Message","make Json");
            Log.v("Message","make Json1");

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String getScore = jsonObject1.getString("score");

                Log.v("Message", "compareResult : " + getScore);

                getScoreResult = getScore;
            }

        }catch (Exception e){
            Log.v("Message", "Fail to get DB");
            e.printStackTrace();
        }
        return getScoreResult;
    }

    public String getMyAnswer(String str){
        Log.v("Message", "  - Method getMyAnswer Start");
        String getMyAnswer = "";
        try{
            JSONObject jsonObject = new JSONObject(str);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("SelectDoneWorkbook_myAnswer"));
            Log.v("Message","make Json");
            Log.v("Message","make Json1");

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String getinputAnswer = jsonObject1.getString("myanswer");

                Log.v("Message", "getinputAnswer : " + getinputAnswer);

                getMyAnswer = getinputAnswer;
            }

        }catch (Exception e){
            Log.v("Message", "Fail to get DB");
            e.printStackTrace();
        }
        return getMyAnswer;
    }

    public void parserAuthList(String str){
        Log.v("Message", "  - Method parserAuthList Start");
        try{
            JSONObject jsonObject = new JSONObject(str);
            Log.v("Message","jsonObject 진입");
            JSONArray jsonArray = new JSONArray(jsonObject.getString("Auth_studentId"));
            Log.v("Message","jsonArray 진입");
            authListImport.clear();

            Log.v("Message", "  - parserSelect : workbooks clear OK");

            for(int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String sid = jsonObject1.getString("student_id");

                AuthBean bean = new AuthBean(sid);
                authListImport.add(bean);

            }

        }catch (Exception e){
            Log.v("Message", "Fail to get DB");
            e.printStackTrace();
        }
    }


}// Main
