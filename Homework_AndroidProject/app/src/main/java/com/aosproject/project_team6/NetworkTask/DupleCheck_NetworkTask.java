package com.aosproject.project_team6.NetworkTask;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.aosproject.project_team6.Bean.DupleCheckBean;
import com.aosproject.project_team6.Common.ShareVar;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DupleCheck_NetworkTask extends AsyncTask<Integer, String, Object> {
    Context context = null;
    String mAddr = null;
    ArrayList<DupleCheckBean> duplechecks;
    String where = null;
    // Constructor
    public DupleCheck_NetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.duplechecks = new ArrayList<DupleCheckBean>();
        this.where = where;
    } // constructor
    protected Object doInBackground(Integer... integers) {
        Log.v("Message", "## doInBackgroud(dupleCheck) ... start");
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        String tid = null; // networktask 잘 했는지 안했는지 받을 거임
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
                if (where.equals("select")) {
                    Log.v("Message", "  - doIn select start");
                    //return 값이 없고
                    tid = parserSelect(stringBuffer.toString());
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
        if (where.equals("select")) {
            Log.v("Message", "  - doIn return duplecheck : " + duplechecks);
            return tid; // select는 엄청 많은 값이 들어올거임
        }else{
            return null;
        }
    }; //  doInback



    private String parserSelect(String str) {
        String tid = null;
        Log.v("Message", "  - Method parserSelect Start");
        try {
            JSONObject jsonObject = new JSONObject(str);
            Log.v("Message", "jsonObject 진입");
            JSONArray jsonArray = new JSONArray(jsonObject.getString("login_info"));
            Log.v("Message", "jsonArray 진입");
            Log.v("Message", "  - parserSelect : teachers clear OK");

            if (jsonArray.length() == 0) {
                Log.v("Message", "  - parserSelect : teachers clear OK12345678");

                tid = "F";
            } else {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(0);
                Log.v("Message", "  - parserSelect : teachers clear OK123");
                switch (ShareVar.UserStatus) {
                    case "teacher":
                        tid = jsonObject1.getString("tid");
                        break;
                    case "student":
                        tid = jsonObject1.getString("sid");
                        break;
                }
            }
            return tid;
        } catch (Exception e) {
            Log.v("Message", "Fail to get DB");
            e.printStackTrace();
            return null;
        }
    }// parserSelect
}