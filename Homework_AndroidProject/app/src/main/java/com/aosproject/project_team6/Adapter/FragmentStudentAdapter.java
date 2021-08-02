package com.aosproject.project_team6.Adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.aosproject.project_team6.StudentFragment.FragmentStudentDoQuiz;
import com.aosproject.project_team6.StudentFragment.FragmentStudentDoneQuiz;

public class FragmentStudentAdapter extends FragmentStateAdapter {


    public FragmentStudentAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1:
                Log.v("Message", "createFragment : doneQuiz");
                return new FragmentStudentDoneQuiz();
        }

        Log.v("Message", "createFragment : doQuiz");
        return new FragmentStudentDoQuiz();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
