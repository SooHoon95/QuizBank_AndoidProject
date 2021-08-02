package com.aosproject.project_team6.StudentFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aosproject.project_team6.Adapter.StudentListAdapter;
import com.aosproject.project_team6.Bean.StudentListBean;
import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.StudentWorkbook_NetworkTask;
import com.aosproject.project_team6.NetworkTask.Workbook_NetworkTask;
import com.aosproject.project_team6.R;

import java.util.ArrayList;

public class FragmentStudentDoQuiz extends Fragment {

    private String student_id = ShareVar.LoginID;

    private View view;
    private CardView cardView;

    String myIP = ShareVar.IPAddress;
    String urlAddr = "http://"+myIP+":8080/studentlist/studentWorkbookSelectList.jsp?student_id=" + student_id;

//    http://192.168.0.3:8080/studentlist/studentWorkbookSelectList.jsp?student_id=ㅁㅁㅁ" + student_id
//    http://192.168.0.3:8080/studentlist/studentWorkbookSelectList.jsp?student_id=aaa@naver.com

    ArrayList<StudentListBean> studentworkbooks;
    StudentListAdapter adapter;


    RecyclerView recyclerView = null;
    RecyclerView.LayoutManager layoutManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("addr", myIP);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_student_do_quiz, container, false);

        recyclerView = view.findViewById(R.id.rv_teacher_lists_before);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        connectGetData();
        return view;
    }

    private void connectGetData(){
        Log.v("studentlist", "connectGetData Start");

        try{
            Log.v("Message", "  - Before start NetworkTask");

            StudentWorkbook_NetworkTask networkTask = new StudentWorkbook_NetworkTask(getActivity(), urlAddr, "Student_Select");
            Object obj = networkTask.execute().get();
            studentworkbooks = (ArrayList<StudentListBean>)obj;

            Log.v("studentlist","studentworkbooks =  " + studentworkbooks);
            Log.v("studentlist","duedate =  " + studentworkbooks.get(0).getDuedate());

            adapter = new StudentListAdapter(getActivity(), R.layout.custom_cardview_student_do_quiz, studentworkbooks);
            recyclerView.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }




}//===========
