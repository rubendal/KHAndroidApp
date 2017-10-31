package com.rubendal.kuroganehammercom.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.asynctask.formula.FormulaAsyncTask;
import com.rubendal.kuroganehammercom.classes.Formula;

import java.util.LinkedList;
import java.util.List;

public class FormulaFragment extends NavigationFragment {

    private List<Formula> formulas = new LinkedList<>();

    public FormulaFragment() {

    }

    public static FormulaFragment newInstance(){
        FormulaFragment fragment = new FormulaFragment();

        return fragment;
    }

    @Override
    public void updateData() {

    }

    @Override
    public String getTitle() {
        return "Formulas";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.formulas = (List<Formula>) getArguments().getSerializable("formulas");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_formula, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FormulaAsyncTask f = new FormulaAsyncTask(this);
        f.execute();
    }

    public void setData(LinkedList<Formula> formulas){
        this.formulas = formulas;

        loadData();
    }

    private void loadData(){
        LinearLayout layout = (LinearLayout)getView().findViewById(R.id.container);

        for(int i=0;i<formulas.size();i++){
            layout.addView(formulas.get(i).asLayout(getContext()));
        }
    }

}
