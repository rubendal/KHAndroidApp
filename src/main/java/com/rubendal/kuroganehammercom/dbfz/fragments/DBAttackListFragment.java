package com.rubendal.kuroganehammercom.dbfz.fragments;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.dbfz.asynctask.CharacterMovesAsyncTask;
import com.rubendal.kuroganehammercom.dbfz.classes.DBCharacter;
import com.rubendal.kuroganehammercom.dbfz.classes.DBMove;
import com.rubendal.kuroganehammercom.interfaces.KHFragment;
import com.rubendal.kuroganehammercom.util.params.Params;

import java.util.LinkedList;
import java.util.List;

public class DBAttackListFragment extends KHFragment {

    private DBCharacter character;
    private List<DBMove> moveList;

    public DBAttackListFragment() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.findItem(R.id.update).setVisible(false);
    }

    @Override
    public void updateData() {
        CharacterMovesAsyncTask m = new CharacterMovesAsyncTask(this, character, true);
        m.execute();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.character = (DBCharacter)getArguments().getSerializable("character");
            this.moveList = (List<DBMove>)getArguments().getSerializable("list");
        }
        setHasOptionsMenu(true);

    }


    @Override
    public String getTitle() {
        return String.format("%s/Moves",character.getCharacterTitleName());
    }

    public static DBAttackListFragment newInstance(DBCharacter character, LinkedList<DBMove> moveList){
        DBAttackListFragment fragment = new DBAttackListFragment();
        Bundle args = new Bundle();
        args.putSerializable("character", character);
        args.putSerializable("list", moveList);
        fragment.setArguments(args);
        fragment.character = character;
        fragment.moveList = moveList;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.db_attacks_layout, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadData();


    }

    private void loadData(){
        int o=0;

        TableLayout layout = (TableLayout)getView().findViewById(R.id.groundtable);

        layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

        TableRow header = (TableRow) layout.findViewById(R.id.groundheader);

        for (int i = 0; i < header.getChildCount(); i++) {
            TextView t = (TextView) header.getChildAt(i);
            t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
            t.setBackgroundColor(Color.parseColor(character.color));
        }


        for (DBMove move : moveList) {
            if (move != null) {
                if(move.type.equals("Ground")) {
                    o++;
                    layout.addView(move.asRow(this.getActivity(), o % 2 == 1, character.color));
                    layout.addView(move.getNotesRow(this.getActivity(), o % 2 == 1));
                }
            }
        }

        o = 0;

        layout = (TableLayout)getView().findViewById(R.id.aerialtable);

        layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

        header = (TableRow) layout.findViewById(R.id.aerialheader);

        for (int i = 0; i < header.getChildCount(); i++) {
            TextView t = (TextView) header.getChildAt(i);
            t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
            t.setBackgroundColor(Color.parseColor(character.color));
        }


        for (DBMove move : moveList) {
            if (move != null) {
                if(move.type.equals("Aerial")) {
                    o++;
                    layout.addView(move.asRow(this.getActivity(), o % 2 == 1, character.color));
                    layout.addView(move.getNotesRow(this.getActivity(), o % 2 == 1));
                }
            }
        }

        o = 0;

        layout = (TableLayout)getView().findViewById(R.id.specialtable);

        layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

        header = (TableRow) layout.findViewById(R.id.specialheader);

        for (int i = 0; i < header.getChildCount(); i++) {
            TextView t = (TextView) header.getChildAt(i);
            t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
            t.setBackgroundColor(Color.parseColor(character.color));
        }


        for (DBMove move : moveList) {
            if (move != null) {
                if(move.type.equals("Special")) {
                    o++;
                    layout.addView(move.asRow(this.getActivity(), o % 2 == 1, character.color));
                    layout.addView(move.getNotesRow(this.getActivity(), o % 2 == 1));
                }
            }
        }
    }
}