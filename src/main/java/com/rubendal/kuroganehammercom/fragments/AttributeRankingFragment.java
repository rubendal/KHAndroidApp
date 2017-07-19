package com.rubendal.kuroganehammercom.fragments;


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
import com.rubendal.kuroganehammercom.classes.AttributeRank;
import com.rubendal.kuroganehammercom.classes.AttributeRankWrapper;
import com.rubendal.kuroganehammercom.util.params.Params;

public class AttributeRankingFragment {
/*
public class AttributeRankingFragment extends KHFragment {

    private AttributeRankWrapper attribute;

    public AttributeRankingFragment() {

    }

    @Override
    public void updateData() {
        /*AttributeRankingAsyncTask a = new AttributeRankingAsyncTask(this, attribute.attributeList, true);
        a.execute();
    }

    @Override
    public String getTitle() {
        return String.format("%s", attribute.attributeList.name);
    }

    public static AttributeRankingFragment newInstance(AttributeRankWrapper attribute){
        AttributeRankingFragment fragment = new AttributeRankingFragment();
        Bundle args = new Bundle();
        args.putSerializable("attribute", attribute);
        fragment.attribute = attribute;
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
            this.attribute = (AttributeRankWrapper) getArguments().getSerializable("attribute");
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

        for(int i=0;i<attribute.valueTypes.size();i++){
            TextView a = new TextView(getContext());
            a.setText(attribute.valueTypes.get(i));
            a.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
            header.addView(a);
        }

        for(int i = 0; i < header.getChildCount(); i++){
            TextView t = (TextView)header.getChildAt(i);
            t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
            t.setBackgroundColor(Color.parseColor("#D0EAFF"));
        }

        int o = 0;

        for(AttributeRank a : attribute.attributes){
            if(a!=null){
                o++;
                layout.addView(a.asRow(this.getActivity(),attribute.valueTypes, attribute.attributeList.name, o % 2 == 1));
            }
        }
    }*/

}
