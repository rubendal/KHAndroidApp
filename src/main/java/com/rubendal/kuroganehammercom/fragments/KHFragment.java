package com.rubendal.kuroganehammercom.fragments;

import android.support.v4.app.Fragment;

import com.rubendal.kuroganehammercom.MainActivity;


public abstract class KHFragment extends Fragment {

    public MainActivity activity;

    public KHFragment(){
        super();
    }

    public abstract void updateData();

    public abstract String getTitle();

    public void loadFragment(KHFragment fragment){
        if(getActivity() instanceof MainActivity){
            MainActivity activity = (MainActivity)getActivity();
            activity.loadFragment(fragment);
        }
    }
}
