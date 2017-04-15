package com.rubendal.kuroganehammercom.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.asynctask.character.CharacterDataAsyncTask;
import com.rubendal.kuroganehammercom.classes.CharacterData;
import com.rubendal.kuroganehammercom.classes.DodgeData;
import com.rubendal.kuroganehammercom.classes.Move;
import com.rubendal.kuroganehammercom.classes.MoveType;
import com.rubendal.kuroganehammercom.classes.Movement;
import com.rubendal.kuroganehammercom.classes.RowValue;
import com.rubendal.kuroganehammercom.util.params.Params;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class CharacterDataFragment extends KHFragment {

    private CharacterData data;

    public CharacterDataFragment() {
    }

    @Override
    public void updateData() {
        CharacterDataAsyncTask c = new CharacterDataAsyncTask(this, data.character, true);
        c.execute();
    }

    @Override
    public String getTitle() {
        return String.format("%s",data.character.getCharacterTitleName());
    }

    public static CharacterDataFragment newInstance(CharacterData data){
        CharacterDataFragment fragment = new CharacterDataFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        fragment.setArguments(args);
        fragment.data = data;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.data = (CharacterData)getArguments().getSerializable("data");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_character_data, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadData();
    }

    private void loadData(){

        LinearLayout container = (LinearLayout)getView().findViewById(R.id.layout);

        int o = 0;

        TableLayout layout = (TableLayout)getView().findViewById(R.id.movement_table);

        layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

        TableRow header = (TableRow) layout.findViewById(R.id.mov_header);

        for (int i = 0; i < header.getChildCount(); i++) {
            TextView t = (TextView) header.getChildAt(i);
            t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
            t.setBackgroundColor(Color.parseColor(data.character.color));
        }

        o=0;

        for(Movement a : data.movement){
            if(a!=null){
                o++;
                layout.addView(a.asRow(this.getActivity(),o % 2 == 1));
            }
        }

        if(data.character.hasSpecificAttributes){
            if(data.character.specificAttribute.isSimple){
                container.removeView(getView().findViewById(R.id.specific_attr_complex_table));
                layout = (TableLayout)getView().findViewById(R.id.specific_attr_simple_table);

                layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

                header = (TableRow) layout.findViewById(R.id.specific_attr_simple_header);

                for (int i = 0; i < header.getChildCount(); i++) {
                    TextView t = (TextView) header.getChildAt(i);
                    t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
                    t.setBackgroundColor(Color.parseColor(data.character.color));
                }

                TextView a = (TextView)getView().findViewById(R.id.specific_attr_simple_attribute);
                a.setText(data.character.specificAttribute.attribute);

                o = 0;
                for(RowValue r : data.character.specificAttribute.valueList){
                    o++;
                    layout.addView(r.asRow(this.getActivity(),o%2==1));
                }
            }else{
                container.removeView(getView().findViewById(R.id.specific_attr_simple_table));
                layout = (TableLayout)getView().findViewById(R.id.specific_attr_complex_table);

                layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

                List<TableRow> rows = data.character.specificAttribute.asRows(this.getActivity(), data.character.color);
                for(TableRow tr : rows){
                    if(tr!=null) {
                        layout.addView(tr);
                    }
                }
            }
        }else{
            container.removeView(getView().findViewById(R.id.specific_attr_simple_table));
            container.removeView(getView().findViewById(R.id.specific_attr_complex_table));
        }

        layout = (TableLayout)getView().findViewById(R.id.groundtable);

        layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

        header = (TableRow) layout.findViewById(R.id.groundheader);

        o=0;

        for (int i = 0; i < header.getChildCount(); i++) {
            TextView t = (TextView) header.getChildAt(i);
            t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
            t.setBackgroundColor(Color.parseColor(data.character.color));
        }
        for (Move move : data.moveList) {
            if (move != null) {
                if(move.moveType == MoveType.Ground) {
                    o++;
                    layout.addView(move.asRow(this.getActivity(), o % 2 == 1));
                }
            }
        }

        o=0;
        layout = (TableLayout)getView().findViewById(R.id.throwtable);

        layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

        header = (TableRow) layout.findViewById(R.id.throwheader);

        for (int i = 0; i < header.getChildCount(); i++) {
            TextView t = (TextView) header.getChildAt(i);
            t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
            t.setBackgroundColor(Color.parseColor(data.character.color));
        }
        for (Move move : data.moveList) {
            if (move != null) {
                if(move.moveType == MoveType.Throw) {
                    o++;
                    layout.addView(move.asRow(this.getActivity(), o % 2 == 1));
                }
            }
        }

        o=0;
        layout = (TableLayout)getView().findViewById(R.id.dodgetable);

        layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

        header = (TableRow) layout.findViewById(R.id.dodgeheader);

        for (int i = 0; i < header.getChildCount(); i++) {
            TextView t = (TextView) header.getChildAt(i);
            t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
            t.setBackgroundColor(Color.parseColor(data.character.color));
        }

        LinkedList<DodgeData> dodgeData = new LinkedList<>();

        for(int i=0;i<data.attributes.size();i+=2){
            String name = data.attributes.get(i).formattedName;
            boolean repeatedRolls = false;
            boolean roll = false;
            String atrName = "";
            if(name.contains("AIRDODGE")){
                atrName = "Airdodge";
            }else if(name.contains("ROLLS")){
                if(i+3<data.attributes.size()){
                    if(data.attributes.get(i+2).formattedName.contains("ROLLS")){
                        repeatedRolls = true;
                    }
                    atrName = "Forward Roll";
                    roll = true;
                }
            }else{
                //Spotdodge
                atrName = "Spotdodge";
            }
            String intangibility=data.attributes.get(i).value;
            String faf=data.attributes.get(i+1).value;

            DodgeData d = new DodgeData(atrName, intangibility, faf);
            dodgeData.add(d);
            if(roll){
                DodgeData d2 = new DodgeData("Back Roll", intangibility, faf);
                if(!repeatedRolls){
                    dodgeData.add(d2);
                }else{
                    //Greninja
                    i+=2;
                    d2.intangibility = data.attributes.get(i).value;
                    d2.faf = data.attributes.get(i+1).value;
                    dodgeData.add(d2);

                }
            }
        }

        Collections.swap(dodgeData,0,3);

        for(DodgeData d : dodgeData){
            o++;
            layout.addView(d.asRow(this.getActivity(), o % 2 == 1));
        }

        o=0;
        layout = (TableLayout)getView().findViewById(R.id.aerialtable);

        layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

        header = (TableRow) layout.findViewById(R.id.aerialheader);

        for (int i = 0; i < header.getChildCount(); i++) {
            TextView t = (TextView) header.getChildAt(i);
            t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
            t.setBackgroundColor(Color.parseColor(data.character.color));
        }
        for (Move move : data.moveList) {
            if (move != null) {
                if(move.moveType == MoveType.Aerial) {
                    o++;
                    layout.addView(move.asRow(this.getActivity(), o % 2 == 1));
                }
            }
        }

        o=0;
        layout = (TableLayout)getView().findViewById(R.id.specialtable);

        layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

        header = (TableRow) layout.findViewById(R.id.specialheader);

        for (int i = 0; i < header.getChildCount(); i++) {
            TextView t = (TextView) header.getChildAt(i);
            t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
            t.setBackgroundColor(Color.parseColor(data.character.color));
        }
        for (Move move : data.moveList) {
            if (move != null) {
                if(move.moveType == MoveType.Special) {
                    o++;
                    layout.addView(move.asRow(this.getActivity(), o % 2 == 1));
                }
            }
        }

        if(data.character.name.equals("Little Mac")){
            TextView aerial = (TextView)getView().findViewById(R.id.aerial_label);
            aerial.setText("Aerial Attacks (Please don't actually use these)");
        }
    }

}
