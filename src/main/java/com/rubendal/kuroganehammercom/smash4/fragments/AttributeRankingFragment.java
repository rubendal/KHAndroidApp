package com.rubendal.kuroganehammercom.smash4.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.interfaces.KHFragment;
import com.rubendal.kuroganehammercom.smash4.asynctask.attribute.AttributeRankingAsyncTask;
import com.rubendal.kuroganehammercom.smash4.classes.AttributeName;
import com.rubendal.kuroganehammercom.smash4.classes.AttributeRank;
import com.rubendal.kuroganehammercom.util.params.Params;

import java.util.LinkedList;
import java.util.List;

public class AttributeRankingFragment extends KHFragment {

    private List<AttributeRank> ranks;
    private AttributeName attributeName;

    public AttributeRankingFragment() {

    }

    @Override
    public void updateData() {
        AttributeRankingAsyncTask a = new AttributeRankingAsyncTask(this, attributeName, true);
        a.execute();
    }

    @Override
    public String getTitle() {
        return String.format("%s", attributeName.formattedName);
    }

    public static AttributeRankingFragment newInstance(AttributeName attributeName, LinkedList<AttributeRank> ranks){
        AttributeRankingFragment fragment = new AttributeRankingFragment();
        Bundle args = new Bundle();
        args.putSerializable("attributeName", attributeName);
        args.putSerializable("ranks", ranks);
        fragment.attributeName = attributeName;
        fragment.ranks = ranks;
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_attribute_ranking, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.attributeName = (AttributeName) getArguments().getSerializable("attributeName");
            this.ranks = (List<AttributeRank>) getArguments().getSerializable("ranks");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadData();
    }

    private void loadData(){
        TableLayout layout  = (TableLayout)getView().findViewById(R.id.table);

        layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

        TableRow header = (TableRow)layout.findViewById(R.id.header);

        if(ranks == null)
            return;

        for(int i=0;i<ranks.get(0).types.size();i++){
            TextView a = new TextView(getContext());
            a.setText(ranks.get(0).types.get(i));
            a.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
            header.addView(a);
        }

        for(int i = 0; i < header.getChildCount(); i++){
            TextView t = (TextView)header.getChildAt(i);
            t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
            t.setBackgroundColor(Color.parseColor("#D0EAFF"));
        }

        int o = 0;

        for(AttributeRank a : ranks){
            if(a!=null){
                o++;
                layout.addView(a.asRow(this.getActivity(), o % 2 == 1));
            }
        }
    }

}
