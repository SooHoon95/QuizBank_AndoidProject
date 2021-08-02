package com.aosproject.project_team6.TeacherFragment;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aosproject.project_team6.Activity.AuthActivity;
import com.aosproject.project_team6.Activity.NewQuestionActivity;
import com.aosproject.project_team6.Activity.TeacherListActivity;
import com.aosproject.project_team6.Activity.TeacherNewWorkbookActivity;
import com.aosproject.project_team6.Activity.TeacherUpdateWorkbookActivity;
import com.aosproject.project_team6.Adapter.TeacherListAdapter;
import com.aosproject.project_team6.Bean.TeacherListBean;
import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.Question_NetworkTask;
import com.aosproject.project_team6.NetworkTask.Workbook_NetworkTask;
import com.aosproject.project_team6.R;
import java.util.ArrayList;
public class FragmentTeacherDoQuiz extends Fragment {
    private View view;
    String myIP = ShareVar.IPAddress;
    String urlAddr = "http://"+myIP+":8080/teacherlist/workbookSelectList.jsp?";
    ArrayList<TeacherListBean> workbooks;
    TeacherListAdapter adapter;
    Button btn_new_workbook;
    // Dialog Buttons
    Button btn_insert, btn_workbook_update, btn_delete, btn_Auth;
    RecyclerView recyclerView = null;
    RecyclerView.LayoutManager layoutManager = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("Message", "FragmentTeacherDoQuiz Start");
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_teacher_do_quiz, container, false);
        recyclerView = view.findViewById(R.id.rv_teacher_lists_before);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        connectGetData();
        // Test
        return view;
    }
    private void connectGetData(){
        Log.v("Message", "METHOD : connectGetData Start");

        try{
            Log.v("Message", "  - Before start NetworkTask");

            urlAddr = urlAddr + "tid=" + ShareVar.LoginID;

            Workbook_NetworkTask networkTask = new Workbook_NetworkTask(getActivity(), urlAddr, "select");
            Object obj = networkTask.execute().get();
            workbooks = (ArrayList<TeacherListBean>) obj;
            Log.v("Message", "  - workbooks(arraylist) : " + workbooks);
            adapter = new TeacherListAdapter(getActivity(), R.layout.custom_cardview_teacher_do_quiz, workbooks);
            adapter.setOnItemClickListener(adapterClick);
            Log.v("Message", "  - adapter is... : " + adapter);
            recyclerView.setAdapter(adapter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }//connectGetData
    // test
    TeacherListAdapter.OnItemClickListener adapterClick = new TeacherListAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            String info = workbooks.get(position).getWtitle();
            int quantity = workbooks.get(position).getQuantity();
            int wid = workbooks.get(position).getWid();
            ShareVar.NewWorkbook_Name = info;
            ShareVar.totalCount = Integer.toString(quantity);
            ShareVar.newWorkbook_id = Integer.toString(wid);
            Log.v("Message", "RecyclerView Data : " + info);
            final LinearLayout linear = (LinearLayout) View.inflate(getActivity(),R.layout.custom_dialoglayout_workbook, null);
            // 문제 등록하기 버튼 누르면 신규 등록으로 넘어가기
            btn_insert = linear.findViewById(R.id.dialog_button_insert);
            btn_insert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), NewQuestionActivity.class);
                    startActivity(intent);
                }
            });
            //권한주기
            btn_Auth = linear.findViewById(R.id.dialog_button_Auth);
            btn_Auth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), AuthActivity.class);
                    startActivity(intent);
                }
            });

            // 문제집 수정으로 가기
            btn_workbook_update = linear.findViewById(R.id.dialog_button_upload_workbook);
            btn_workbook_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TeacherUpdateWorkbookActivity.class);
                    startActivity(intent);
                }
            });
            // 문제집 삭제하기
            btn_delete = linear.findViewById(R.id.dialog_button_delete);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(getActivity())
                            .setIcon(R.drawable.mainicon)
                            .setTitle("문제집 삭제")
                            .setMessage("이 문제와 문제집을 삭제하시겠습니까?")
                            .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    deleteQuestions(ShareVar.newWorkbook_id);
                                    deleteWorkbook(ShareVar.newWorkbook_id);
                                    new AlertDialog.Builder(getActivity())
                                            .setMessage("삭제되었습니다!")
                                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    adapter.notifyDataSetChanged();
                                                    dialog.dismiss();
                                                }
                                            })
                                            .show();
                                }
                            })
                            .setNegativeButton("취소", null)
                            .show();
                }
            });
            // Dialog Create
            new AlertDialog.Builder(getActivity())
                    .setView(linear)
                    .setTitle(ShareVar.NewWorkbook_Name)
                    .setIcon(R.drawable.mainicon)
                    .show();
//
        }
    };
    private void deleteQuestions (String wid){
        Log.v("Message", "## METHOD : deleteQustions ... with " + wid);
        String deleteQ_Url = "http://"+myIP+":8080/teacherlist/questionDelete.jsp?wid="+wid;
        Log.v("Message", "deleteUrl : " + deleteQ_Url);
        Question_NetworkTask deleteTask = new Question_NetworkTask(getActivity(), deleteQ_Url, "delete");
    }
    private void deleteWorkbook (String wid){
        Log.v("Message", "## METHOD : deleteWorkbook");
        String deleteW_Url = "http://"+myIP+":8080/teacherlist/workbookDelete.jsp?wid="+wid;
        Workbook_NetworkTask deleteTask2 = new Workbook_NetworkTask(getActivity(), deleteW_Url, "delete");
    }
}