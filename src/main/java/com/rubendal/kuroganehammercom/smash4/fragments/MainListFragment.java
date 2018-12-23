package com.rubendal.kuroganehammercom.smash4.fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.interfaces.NavigationFragment;
import com.rubendal.kuroganehammercom.interfaces.Smash4MainFragment;
import com.rubendal.kuroganehammercom.smash4.asynctask.character.CharacterAsyncTask;

public class MainListFragment extends Smash4MainFragment {

    public ListView list;
    public int selectedItem = -1;

    public MainListFragment() {

    }

    public static MainListFragment newInstance(){
        MainListFragment fragment = new MainListFragment();
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_list, container, false);

        list = (ListView)view.findViewById(R.id.list);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateData();
    }

    public void updateData(){
        CharacterAsyncTask c = new CharacterAsyncTask(this, 0);
        c.execute();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateData();
    }

    @Override
    public String getTitle() {
        return "Kurogane Hammer";
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("index",selectedItem);
        super.onSaveInstanceState(outState);
    }

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            selectedItem = savedInstanceState.getInt("index");
        }
        super.onCreate(savedInstanceState);
    }
}
