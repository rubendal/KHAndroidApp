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
import com.rubendal.kuroganehammercom.smash4.asynctask.character.AttributeAsyncTask;
import com.rubendal.kuroganehammercom.smash4.classes.Attribute;
import com.rubendal.kuroganehammercom.smash4.classes.Character;
import com.rubendal.kuroganehammercom.util.params.Params;

import java.util.LinkedList;
import java.util.List;

public class AttributeFragment extends KHFragment {

    private Character character;
    private List<Attribute> attributes;

    public AttributeFragment() {

    }

    @Override
    public void updateData() {
        AttributeAsyncTask a = new AttributeAsyncTask(this,character, true);
        a.execute();
    }

    @Override
    public String getTitle() {
        return String.format("%s/%s",character.getCharacterTitleName(), "Detailed attributes");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.character = (Character)getArguments().getSerializable("character");
            this.attributes = (List<Attribute>)getArguments().getSerializable("attributes");
        }
    }

    public static AttributeFragment newInstance(Character character, LinkedList<Attribute> attributes){
        AttributeFragment fragment = new AttributeFragment();
        Bundle args = new Bundle();
        args.putSerializable("character", character);
        args.putSerializable("attributes", attributes);
        fragment.setArguments(args);
        fragment.character = character;
        fragment.attributes = attributes;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_attribute, container, false);

        return view;
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

        for(int i = 0; i < header.getChildCount(); i++){
            TextView t = (TextView)header.getChildAt(i);
            t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
            t.setBackgroundColor(Color.parseColor(character.color));
        }

        int o = 0;

        for(Attribute a : attributes){
            if(a!=null){
                LinkedList<TableRow> rows = a.asRow(this.getContext(), o);
                for(TableRow row : rows){
                    layout.addView(row);
                    o++;
                }

            }
        }
    }
}
