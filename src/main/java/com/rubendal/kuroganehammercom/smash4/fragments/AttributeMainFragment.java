package com.rubendal.kuroganehammercom.smash4.fragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.interfaces.NavigationFragment;
import com.rubendal.kuroganehammercom.smash4.asynctask.attribute.AttributeListAsyncTask;

public class AttributeMainFragment extends NavigationFragment {

    public GridView grid;
    public int selectedItem = -1;

    public AttributeMainFragment() {

    }

    public static AttributeMainFragment newInstance(){
        AttributeMainFragment fragment = new AttributeMainFragment();
        return fragment;
    }

    @Override
    public void updateData() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        int x = 300;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (width <= 800) {
                x = (width / 2) - 5;
            } else if (width > 800 && width <= 1800) {
                x = 350;
            } else {
                x = 400;
            }
        }else{
            if (height <= 800) {
                x = (height / 2) - 5;
            } else if (height > 800 && height <= 1800) {
                x = 350;
            } else {
                x = 400;
            }
        }

        grid.setNumColumns(width / x);

        AttributeListAsyncTask a = new AttributeListAsyncTask(this, x);
        a.execute();
    }

    @Override
    public String getTitle() {
        return "Kurogane Hammer";
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_attribute_main, container, false);

        grid = (GridView)view.findViewById(R.id.grid);

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
    }

}
