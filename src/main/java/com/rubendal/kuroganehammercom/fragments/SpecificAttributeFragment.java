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
import com.rubendal.kuroganehammercom.classes.Character;
import com.rubendal.kuroganehammercom.classes.RowValue;
import com.rubendal.kuroganehammercom.util.params.Params;

import java.util.List;

public class SpecificAttributeFragment extends KHFragment {

    private Character character;

    public SpecificAttributeFragment() {
    }

    @Override
    public void updateData() {

    }

    @Override
    public String getTitle() {
        return String.format("%s/%s",character.getCharacterTitleName(), character.specificAttribute.attribute);
    }

    public static SpecificAttributeFragment newInstance(Character character){
        SpecificAttributeFragment fragment = new SpecificAttributeFragment();
        Bundle args = new Bundle();
        args.putSerializable("character", character);
        fragment.setArguments(args);
        fragment.character = character;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.character = (Character)getArguments().getSerializable("character");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(character.specificAttribute.isSimple) {
            view = inflater.inflate(R.layout.activity_specific_attribute, container, false);
        }
        else{
            view = inflater.inflate(R.layout.activity_specific_attribute_complex, container, false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadData();
    }

    private void loadData(){
        if(character.specificAttribute.isSimple){
            TableLayout layout = (TableLayout)getView().findViewById(R.id.table);

            layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

            TableRow header = (TableRow) layout.findViewById(R.id.header);

            for (int i = 0; i < header.getChildCount(); i++) {
                TextView t = (TextView) header.getChildAt(i);
                t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
                t.setBackgroundColor(Color.parseColor(character.color));
            }

            TextView a = (TextView)getView().findViewById(R.id.attribute);
            a.setText(character.specificAttribute.attribute);

            int o = 0;
            for(RowValue r : character.specificAttribute.valueList){
                o++;
                layout.addView(r.asRow(this.getActivity(),o%2==1));
            }
        }else{
            TableLayout layout = (TableLayout)getView().findViewById(R.id.table);

            layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

            List<TableRow> rows = character.specificAttribute.asRows(this.getActivity(), character.color);
            for(TableRow tr : rows){
                if(tr!=null) {
                    layout.addView(tr);
                }
            }
        }
    }
}
