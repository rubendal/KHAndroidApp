package com.rubendal.kuroganehammercom.dbfz.classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.util.params.Params;

import org.json.JSONObject;

import java.io.Serializable;

public class DBMove implements Serializable {
    public String type, name, firstHitboxFrame, totalFrames, advantage, damage, guard, notes;

    public DBMove(){

    }

    public DBMove(String type, String name, String firstHitboxFrame, String totalFrames, String advantage, String damage, String guard, String notes) {
        this.type = type;
        this.name = name;
        this.firstHitboxFrame = firstHitboxFrame;
        this.totalFrames = totalFrames;
        this.advantage = advantage;
        this.damage = damage;
        this.guard = guard;
        this.notes = notes;
    }

    public static DBMove fromJson(JSONObject jsonObject){
        try{

            String type = jsonObject.getString("Type"),name = jsonObject.getString("Name"), firstHitboxFrame = jsonObject.getString("FirstHitboxFrame"), totalFrames = jsonObject.getString("TotalFrames"),
                    advantage = jsonObject.getString("Advantage"), damage = jsonObject.getString("Damage"), block = jsonObject.getString("Guard"), notes = jsonObject.getString("Notes");

            return new DBMove(type, name, firstHitboxFrame, totalFrames, advantage, damage, block, notes);

        }
        catch(Exception e){

        }
        return null;
    }

    public TableRow asRow(Context context, boolean odd, String color){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.db_attack_data, null);
        TableRow tableRow = (TableRow)v.findViewById(R.id.row);

        TextView nameView = (TextView)tableRow.findViewById(R.id.name);

        nameView.setBackgroundColor(Color.parseColor(color));


        TextView firstHitboxView = (TextView)tableRow.findViewById(R.id.firstHitboxFrame);
        TextView totalFramesView = (TextView)tableRow.findViewById(R.id.totalFrames);
        TextView advantageView = (TextView)tableRow.findViewById(R.id.advantage);
        TextView damageView = (TextView)tableRow.findViewById(R.id.damage);
        TextView guardView = (TextView)tableRow.findViewById(R.id.guard);

        nameView.setText(name);
        firstHitboxView.setText(firstHitboxFrame);
        totalFramesView.setText(totalFrames);
        advantageView.setText(advantage);
        damageView.setText(damage);
        guardView.setText(guard);

        int padding = Params.PADDING;
        nameView.setPadding(padding,padding,padding,padding);
        firstHitboxView.setPadding(padding,padding,padding,padding);
        totalFramesView.setPadding(padding,padding,padding,padding);
        advantageView.setPadding(padding,padding,padding,padding);
        damageView.setPadding(padding,padding,padding,padding);
        guardView.setPadding(padding,padding,padding,padding);

        if(!odd){
            nameView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            firstHitboxView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            totalFramesView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            advantageView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            damageView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            guardView.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        return tableRow;


    }

    public TableRow getNotesRow(Context context, boolean odd){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.db_attack_notes, null);
        TableRow tableRow = (TableRow)v.findViewById(R.id.row);

        TextView notesView = (TextView)tableRow.findViewById(R.id.notes);
        //TextView nView = (TextView)tableRow.findViewById(R.id.note_title);

        notesView.setText(notes); //.replace("Notes:","")

        int padding = Params.PADDING;
        notesView.setPadding(padding,padding,padding,padding);
        //nView.setPadding(padding,padding,padding,padding);

        if(!odd){
            notesView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            //nView.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        return tableRow;


    }
}
