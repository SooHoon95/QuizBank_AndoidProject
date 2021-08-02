package com.aosproject.project_team6.TeacherFragment;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.aosproject.project_team6.Adapter.TeacherListAdapter;
import com.aosproject.project_team6.Adapter.TeacherListDoneAdapter;
import com.aosproject.project_team6.Bean.TeacherListBean;
import com.aosproject.project_team6.Common.ShareVar;
import com.aosproject.project_team6.NetworkTask.Workbook_NetworkTask;
import com.aosproject.project_team6.R;
import java.util.ArrayList;
public class FragmentTeacherDoneQuiz extends Fragment {
    private View view;
    String myIP = ShareVar.IPAddress;
    String urlAddr = "http://"+myIP+":8080/teacherlist/workbookSelectDoneList.jsp?";
    ArrayList<TeacherListBean> workbooks;
    TeacherListDoneAdapter adapter;
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
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_teacher_done_quiz, container, false);
        recyclerView = view.findViewById(R.id.rv_teacher_lists_after);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        connectGetData();
        return view;
    }
    private void connectGetData(){
        try{

            urlAddr = urlAddr + "tid=" + ShareVar.LoginID;

            Log.v("Message", "  - Before start NetworkTask");
            Workbook_NetworkTask networkTask = new Workbook_NetworkTask(getActivity(), urlAddr, "select");
            Object obj = networkTask.execute().get();
            workbooks = (ArrayList<TeacherListBean>) obj;
            Log.v("Message", "  - workbooks(arraylist) : " + workbooks);
            adapter = new TeacherListDoneAdapter(getActivity(), R.layout.custom_cardview_teacher_done_quiz, workbooks);
            Log.v("Message", "  - adapter is... : " + adapter);
            recyclerView.setAdapter(adapter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}