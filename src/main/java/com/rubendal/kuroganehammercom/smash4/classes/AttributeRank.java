package com.rubendal.kuroganehammercom.smash4.classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.util.params.Params;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class AttributeRank implements Serializable {

    public Character character;
    public Attribute attribute;
    public int rank;
    public String name;
    public LinkedList<String> types = new LinkedList<>();
    public HashMap<String, String> values = new HashMap<>();

    public AttributeRank(String name, Attribute attribute, Character character){
        this.attribute = attribute;
        this.name = name;
        this.character = character;
        this.rank = -1;

        for(AttributeValue v : attribute.attributes){
            types.add(v.name);
            values.put(v.name, v.value);
        }
    }

    public AttributeRank(String name, Attribute attribute, Character character, int rank){
        this(name, attribute, character);
        this.rank = rank;
    }

    public void add(String type, String value){
        values.put(type, value);
    }

    public TableRow asRow(Context context, boolean odd){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.attribute_ranking_row, null);
        TableRow tableRow = (TableRow)v.findViewById(R.id.row);

        TextView rankView = (TextView)tableRow.findViewById(R.id.rank);
        TextView characterView = (TextView)tableRow.findViewById(R.id.character);

        int padding = Params.PADDING;

        for(int i=0;i<types.size();i++){
            TextView valueView = new TextView(context);
            valueView.setTextColor(Color.BLACK);
            valueView.setText(values.get(types.get(i)));
            valueView.setPadding(padding,padding,padding,padding);
            if(!odd){
                valueView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            }
            tableRow.addView(valueView);
        }

        rankView.setText(String.valueOf(rank));
        if(attribute.equals("Gravity")){
            String name = character.name;
            switch (name){
                case "Marth":
                    characterView.setText("Lucina's worthless grandfather");
                    break;
                case "Lucina":
                    characterView.setText("Marth's worthless granddaughter");
                    break;
                default:
                    characterView.setText(character.name);
            }
        }else if(attribute.equals("RunSpeed")) {
            String name = character.name;
            switch (name){
                case "Ness":
                    characterView.setText("Ebola Back Throw");
                    break;
                default:
                    characterView.setText(character.name);
            }
        }else if(attribute.equals("Gravity")) {
            String name = character.name;
            switch (name){
                case "Mii Swordfighter":
                    characterView.setText("Mii Swordspider");
                    break;
                default:
                    characterView.setText(character.name);
            }
        }else {
            characterView.setText(character.name);
        }

        characterView.setPadding(padding,padding,padding,padding);
        rankView.setPadding(padding,padding,padding,padding);

        if(!odd){
            characterView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            rankView.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        return tableRow;


    }



    public static Comparator<AttributeRank> getComparator(){
        Comparator<AttributeRank> comparator = new Comparator<AttributeRank>() {
            @Override
            public int compare(AttributeRank a, AttributeRank b) {
                if(a.rank < b.rank){
                    return -1;
                }
                if(a.rank > b.rank){
                    return 1;
                }
                return 0;
            }
        };
        return comparator;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof AttributeRank){
            AttributeRank or = (AttributeRank)o;
            if(or.character.id == character.id && or.attribute.equals(attribute)){
                return true;
            }
        }
        return false;
    }
}
