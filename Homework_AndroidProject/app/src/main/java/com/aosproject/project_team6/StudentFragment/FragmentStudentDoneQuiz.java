package com.aosproject.project_team6.StudentFragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aosproject.project_team6.Adapter.StudentDoneListAdapter;
import com.aosproject.project_team6.Bean.StudentListBean;
import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.StudentWorkbook_NetworkTask;
import com.aosproject.project_team6.NetworkTask.Workbook_NetworkTask;
import com.aosproject.project_team6.R;

import java.util.ArrayList;

public class FragmentStudentDoneQuiz extends Fragment {

    private String student_id = ShareVar.LoginID;
    private View view;

    String myIP = ShareVar.IPAddress;
    String urlAddr = "http://" + myIP + ":8080/studentlist/studentDoneWorkbookSelectList.jsp?student_id=" + student_id;
//    http://192.168.0.3:8080/studentlist/studentDoneWorkbookSelectList.jsp?student_id=dkehskeh@gmail.com


    ArrayList<StudentListBean> studentDoneWorkbook;
    StudentDoneListAdapter adapter;

    RecyclerView recyclerView = null;
    RecyclerView.LayoutManager layoutManager = null;



    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("addr", myIP);
        if (getArguments() != null) {

        }
    }//onCreate



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_student_done_quiz, container,false);

        recyclerView = view.findViewById(R.id.rv_student_lists_after);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        connectGetData();

        return view;
    }//onCreateView

    public void connectGetData(){
        Log.v("Message", "connectGetData Start");

        try{
            StudentWorkbook_NetworkTask networkTask = new StudentWorkbook_NetworkTask(getActivity(), urlAddr, "StudentDoneWorkbook_Select");
            Object obj = networkTask.execute().get();
            studentDoneWorkbook = (ArrayList<StudentListBean>) obj;

            Log.v("DoneMessage","studentworkbooks =  " + studentDoneWorkbook);

            adapter = new StudentDoneListAdapter(getActivity(), R.layout.custom_cardview_student_done_quiz, studentDoneWorkbook);
            recyclerView.setAdapter(adapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }




}//Fragment
