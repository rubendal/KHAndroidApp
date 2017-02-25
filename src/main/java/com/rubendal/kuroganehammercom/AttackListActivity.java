package com.rubendal.kuroganehammercom;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.classes.Character;
import com.rubendal.kuroganehammercom.classes.Move;
import com.rubendal.kuroganehammercom.classes.MoveType;

import java.util.List;

public class AttackListActivity extends AppCompatActivity {

    private Character character;
    private MoveType moveType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        character = (Character)getIntent().getSerializableExtra("character");
        moveType = (MoveType) getIntent().getSerializableExtra("moveType");
        List<Move> moveList = (List<Move>)getIntent().getSerializableExtra("moveList");

        String mt = "Ground Attacks";
        int o=0;

        if(moveType == MoveType.Throw){
            setContentView(R.layout.activity_attack_list_throws);
            mt = "Throws";
        }else if(moveType == MoveType.Aerial){
            setContentView(R.layout.activity_attack_list_aerials);
            mt = "Aerial Attacks";
        }else if(moveType == MoveType.Special || moveType == MoveType.Ground) {
            setContentView(R.layout.activity_attack_list);
            if(moveType==MoveType.Special){
                mt = "Specials";
            }
        }else{
            setContentView(R.layout.activity_attacks_all);
            mt = "Attacks";
        }

        setTitle(String.format("%s/%s",character.getCharacterTitleName(), mt));

        if(moveType != MoveType.Any) {

            TableLayout layout = (TableLayout) findViewById(R.id.table);

            layout.setPadding(15, 15, 15, 15);

            TableRow header = (TableRow) layout.findViewById(R.id.header);

            for (int i = 0; i < header.getChildCount(); i++) {
                TextView t = (TextView) header.getChildAt(i);
                t.setBackgroundColor(Color.parseColor(character.color));
            }


            for (Move move : moveList) {
                if (move != null) {
                    o++;
                    layout.addView(move.asRow(this, o % 2 == 1));
                }
            }
        }else{
            TableLayout layout = (TableLayout) findViewById(R.id.groundtable);

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
                        layout.addView(move.asRow(this, o % 2 == 1));
                    }
                }
            }

            o=0;
            layout = (TableLayout) findViewById(R.id.throwtable);

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
                        layout.addView(move.asRow(this, o % 2 == 1));
                    }
                }
            }

            o=0;
            layout = (TableLayout) findViewById(R.id.aerialtable);

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
                        layout.addView(move.asRow(this, o % 2 == 1));
                    }
                }
            }

            o=0;
            layout = (TableLayout) findViewById(R.id.specialtable);

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
                        layout.addView(move.asRow(this, o % 2 == 1));
                    }
                }
            }
        }
    }
}
