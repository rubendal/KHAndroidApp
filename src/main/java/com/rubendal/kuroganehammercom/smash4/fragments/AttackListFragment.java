package com.rubendal.kuroganehammercom.smash4.fragments;


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

import com.rubendal.kuroganehammercom.interfaces.KHFragment;
import com.rubendal.kuroganehammercom.smash4.asynctask.character.MoveAsyncTask;
import com.rubendal.kuroganehammercom.smash4.classes.Character;
import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.smash4.classes.DodgeData;
import com.rubendal.kuroganehammercom.smash4.classes.Move;
import com.rubendal.kuroganehammercom.smash4.classes.MoveSort;
import com.rubendal.kuroganehammercom.smash4.classes.MoveType;
import com.rubendal.kuroganehammercom.util.params.Params;

import java.util.LinkedList;
import java.util.List;

public class AttackListFragment extends KHFragment {

    private Character character;
    private MoveType moveType;
    private List<Move> moveList;
    private List<Move> evasion;
    private MoveSort moveSort;

    public AttackListFragment() {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.data_menu, menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sort:
                CharSequence items[] = new CharSequence[]{"None", "Hitbox Active","FAF","Damage","Angle","BKB","KBG"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Sort options");
                builder.setSingleChoiceItems(items, moveSort.ordinal(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MoveSort sort = MoveSort.values()[which];

                        if(sort != moveSort) {
                            updateDataWithSort(sort);
                        }

                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void updateData() {
        MoveAsyncTask m = new MoveAsyncTask(this, character, moveType, true);
        m.execute();
    }

    public void updateDataWithSort(MoveSort sort) {
        MoveAsyncTask m = new MoveAsyncTask(this, character, moveType, true, sort);
        m.execute();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.character = (Character)getArguments().getSerializable("character");
            this.moveType = (MoveType)getArguments().getSerializable("type");
            this.moveList = (List<Move>)getArguments().getSerializable("list");
            this.evasion = (List<Move>)getArguments().getSerializable("evasion");
            this.moveSort = (MoveSort)getArguments().getSerializable("sort");
        }
        setHasOptionsMenu(true);

    }

    private String getType(){
        switch(moveType){
            case Aerial:
                return "Aerial Moves";
            case Special:
                return "Special Moves";
            case Throw:
                return "Throws";
            case Ground:
                return "Ground Moves";
            default:
                return "Moves";
        }
    }

    @Override
    public String getTitle() {
        return String.format("%s/%s",character.getCharacterTitleName(), getType());
    }

    public static AttackListFragment newInstance(Character character, MoveType type, LinkedList<Move> moveList, LinkedList<Move> evasion, MoveSort sort){
        AttackListFragment fragment = new AttackListFragment();
        Bundle args = new Bundle();
        args.putSerializable("character", character);
        args.putSerializable("type", type);
        args.putSerializable("list", moveList);
        args.putSerializable("evasion",evasion);
        args.putSerializable("sort", sort);
        fragment.setArguments(args);
        fragment.character = character;
        fragment.moveType = type;
        fragment.moveList = moveList;
        fragment.evasion = evasion;
        fragment.moveSort = sort;
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
        }else if(moveType == MoveType.Special) {
            view = inflater.inflate(R.layout.activity_attack_list, container, false);
        }else if(moveType == MoveType.Ground) {
            view = inflater.inflate(R.layout.attack_list_ground, container, false);
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

            layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

            TableRow header = (TableRow) layout.findViewById(R.id.header);

            for (int i = 0; i < header.getChildCount(); i++) {
                TextView t = (TextView) header.getChildAt(i);
                t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
                t.setBackgroundColor(Color.parseColor(character.color));
            }


            for (Move move : moveList) {
                if (move != null) {
                    o++;
                    layout.addView(move.asRow(this.getActivity(), o % 2 == 1, character.color));
                }
            }

            if(moveType == MoveType.Ground){
                o=0;
                layout = (TableLayout)getView().findViewById(R.id.dodgetable);

                layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

                header = (TableRow) layout.findViewById(R.id.dodgeheader);

                for (int i = 0; i < header.getChildCount(); i++) {
                    TextView t = (TextView) header.getChildAt(i);
                    t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
                    t.setBackgroundColor(Color.parseColor(character.color));
                }

                for (Move move : evasion) {
                    if (move != null) {
                        o++;
                        layout.addView(new DodgeData(move).asRow(this.getActivity(), o % 2 == 1, character.color));
                    }
                }

            }
        }else{
            TableLayout layout = (TableLayout)getView().findViewById(R.id.groundtable);

            layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

            TableRow header = (TableRow) layout.findViewById(R.id.groundheader);

            for (int i = 0; i < header.getChildCount(); i++) {
                TextView t = (TextView) header.getChildAt(i);
                t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
                t.setBackgroundColor(Color.parseColor(character.color));
            }
            for (Move move : moveList) {
                if (move != null) {
                    if(move.moveType == MoveType.Ground) {
                        o++;
                        layout.addView(move.asRow(this.getActivity(), o % 2 == 1, character.color));
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
                t.setBackgroundColor(Color.parseColor(character.color));
            }
            for (Move move : moveList) {
                if (move != null) {
                    if(move.moveType == MoveType.Throw) {
                        o++;
                        layout.addView(move.asRow(this.getActivity(), o % 2 == 1, character.color));
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
                t.setBackgroundColor(Color.parseColor(character.color));
            }

            for (Move move : evasion) {
                if (move != null) {
                    o++;
                    layout.addView(new DodgeData(move).asRow(this.getActivity(), o % 2 == 1, character.color));
                }
            }

            o=0;
            layout = (TableLayout)getView().findViewById(R.id.aerialtable);

            layout.setPadding(Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING,Params.LAYOUT_PADDING);

            header = (TableRow) layout.findViewById(R.id.aerialheader);

            for (int i = 0; i < header.getChildCount(); i++) {
                TextView t = (TextView) header.getChildAt(i);
                t.setPadding(Params.PADDING,Params.PADDING,Params.PADDING,Params.PADDING);
                t.setBackgroundColor(Color.parseColor(character.color));
            }
            for (Move move : moveList) {
                if (move != null) {
                    if(move.moveType == MoveType.Aerial) {
                        o++;
                        layout.addView(move.asRow(this.getActivity(), o % 2 == 1, character.color));
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
                t.setBackgroundColor(Color.parseColor(character.color));
            }
            for (Move move : moveList) {
                if (move != null) {
                    if(move.moveType == MoveType.Special) {
                        o++;
                        layout.addView(move.asRow(this.getActivity(), o % 2 == 1, character.color));
                    }
                }
            }

            if(character.name.equals("Little Mac")){
                TextView aerial = (TextView)getView().findViewById(R.id.aerial_label);
                aerial.setText("Aerial Moves (Please don't actually use these)");
            }
        }
    }
}
