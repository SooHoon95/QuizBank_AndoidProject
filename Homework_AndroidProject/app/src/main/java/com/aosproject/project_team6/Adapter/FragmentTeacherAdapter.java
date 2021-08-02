package com.aosproject.project_team6.Adapter;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.aosproject.project_team6.TeacherFragment.FragmentTeacherDoQuiz;
import com.aosproject.project_team6.TeacherFragment.FragmentTeacherDoneQuiz;

public class FragmentTeacherAdapter extends FragmentStateAdapter {


    public FragmentTeacherAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 1:
                Log.v("Message", "createFragment : doneQuiz");
                return new FragmentTeacherDoneQuiz();
        }
        Log.v("Message", "createFragment : doQuiz");
        return new FragmentTeacherDoQuiz();
    }


    @Override
    public int getItemCount() {
        return 2;
    }

}
