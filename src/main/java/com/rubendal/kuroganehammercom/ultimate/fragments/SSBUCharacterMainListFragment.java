package com.rubendal.kuroganehammercom.ultimate.fragments;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.interfaces.SSBUMainFragment;
import com.rubendal.kuroganehammercom.ultimate.asynctask.CharacterListAsyncTask;

public class SSBUCharacterMainListFragment extends SSBUMainFragment {
    public ListView list;
    public int selectedItem = -1;

    public SSBUCharacterMainListFragment() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        //menu.findItem(R.id.update).setVisible(false);
    }

    public static SSBUCharacterMainListFragment newInstance(){
        SSBUCharacterMainListFragment fragment = new SSBUCharacterMainListFragment();
        return fragment;
    }

    @Override
    public void updateData() {
        CharacterListAsyncTask a = new CharacterListAsyncTask(this, 0);
        a.execute();
    }

    @Override
    public String getTitle() {
        return "Kurogane Hammer";
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateData();
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
        setHasOptionsMenu(true);
    }
}
