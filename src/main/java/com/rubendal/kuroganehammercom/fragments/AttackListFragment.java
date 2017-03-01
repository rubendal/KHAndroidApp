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

import com.rubendal.kuroganehammercom.asynctask.MoveAsyncTask;
import com.rubendal.kuroganehammercom.classes.Character;
import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.classes.Move;
import com.rubendal.kuroganehammercom.classes.MoveType;

import java.util.LinkedList;

public class AttackListFragment extends KHFragment {

    private Character character;
    private MoveType moveType;
    private LinkedList<Move> moveList;

    public AttackListFragment() {

    }

    @Override
    public void updateData() {
        MoveAsyncTask m = new MoveAsyncTask(this, character, moveType, true);
        m.execute();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.character = (Character)getArguments().getSerializable("character");
            this.moveType = (MoveType)getArguments().getSerializable("type");
            this.moveList = (LinkedList<Move>)getArguments().getSerializable("list");
        }
    }

    private String getType(){
        switch(moveType){
            case Aerial:
                return "Aerial Attacks";
            case Special:
                return "Specials";
            case Throw:
                return "Throws";
            case Ground:
                return "Ground Attacks";
            default:
                return "Attacks";
        }
    }

    @Override
    public String getTitle() {
        return String.format("%s/%s",character.getCharacterTitleName(), getType());
    }

    public static AttackListFragment newInstance(Character character, MoveType type, LinkedList<Move> moveList){
        AttackListFragment fragment = new AttackListFragment();
        Bundle args = new Bundle();
        args.putSerializable("character", character);
        args.putSerializable("type", type);
        args.putSerializable("list", moveList);
        fragment.setArguments(args);
        fragment.character = character;
        fragment.moveType = type;
        fragment.moveList = moveList;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        if(moveType == MoveType.Throw){
            view = inflater.inflate(R.layout.activity_attack_list_throws, container, false);
        }else if(moveType == MoveType.Aerial){
            view = inflater.inflate(R.layout.activity_attack_list_aerials, container, false);
        }else if(moveType == MoveType.Special || moveType == MoveType.Ground) {
            view = inflater.inflate(R.layout.activity_attack_list, container, false);
        }else{
            view = inflater.inflate(R.layout.activity_attacks_all, container, false);
        }


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadData();
    }

    private void loadData(){
        int o=0;

        if(moveType != MoveType.Any) {

            TableLayout layout = (TableLayout)getView().findViewById(R.id.table);

            layout.setPadding(15, 15, 15, 15);

            TableRow header = (TableRow) layout.findViewById(R.id.header);

            for (int i = 0; i < header.getChildCount(); i++) {
                TextView t = (TextView) header.getChildAt(i);
                t.setBackgroundColor(Color.parseColor(character.color));
            }


            for (Move move : moveList) {
                if (move != null) {
                    o++;
                    layout.addView(move.asRow(this.getActivity(), o % 2 == 1));
                }
            }
        }else{
            TableLayout layout = (TableLayout)getView().findViewById(R.id.groundtable);

            layout.setPadding(15, 15, 15, 15);

            TableRow header = (TableRow) layout.findViewById(R.id.groundheader);

            for (int i = 0; i < header.getChildCount(); i++) {
                TextView t = (TextView) header.getChildAt(i);
                t.setBackgroundColor(Color.parseColor(character.color));
            }
            for (Move move : moveList) {
                if (move != null) {
                    if(move.moveType == MoveType.Ground) {
                        o++;
                        layout.addView(move.asRow(this.getActivity(), o % 2 == 1));
                    }
                }
            }

            o=0;
            layout = (TableLayout)getView().findViewById(R.id.throwtable);

            layout.setPadding(15, 15, 15, 15);

            header = (TableRow) layout.findViewById(R.id.throwheader);

            for (int i = 0; i < header.getChildCount(); i++) {
                TextView t = (TextView) header.getChildAt(i);
                t.setBackgroundColor(Color.parseColor(character.color));
            }
            for (Move move : moveList) {
                if (move != null) {
                    if(move.moveType == MoveType.Throw) {
                        o++;
                        layout.addView(move.asRow(this.getActivity(), o % 2 == 1));
                    }
                }
            }

            o=0;
            layout = (TableLayout)getView().findViewById(R.id.aerialtable);

            layout.setPadding(15, 15, 15, 15);

            header = (TableRow) layout.findViewById(R.id.aerialheader);

            for (int i = 0; i < header.getChildCount(); i++) {
                TextView t = (TextView) header.getChildAt(i);
                t.setBackgroundColor(Color.parseColor(character.color));
            }
            for (Move move : moveList) {
                if (move != null) {
                    if(move.moveType == MoveType.Aerial) {
                        o++;
                        layout.addView(move.asRow(this.getActivity(), o % 2 == 1));
                    }
                }
            }

            o=0;
            layout = (TableLayout)getView().findViewById(R.id.specialtable);

            layout.setPadding(15, 15, 15, 15);

            header = (TableRow) layout.findViewById(R.id.specialheader);

            for (int i = 0; i < header.getChildCount(); i++) {
                TextView t = (TextView) header.getChildAt(i);
                t.setBackgroundColor(Color.parseColor(character.color));
            }
            for (Move move : moveList) {
                if (move != null) {
                    if(move.moveType == MoveType.Special) {
                        o++;
                        layout.addView(move.asRow(this.getActivity(), o % 2 == 1));
                    }
                }
            }

            if(character.name.equals("Little Mac")){
                TextView aerial = (TextView)getView().findViewById(R.id.aerial_label);
                aerial.setText("Aerial Attacks (Please don't actually use these)");
            }
        }
    }
}
