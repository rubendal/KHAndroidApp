package com.rubendal.kuroganehammercom.classes;

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
    public int id, frank, attributeId;
    public String name, rank;
    public HashMap<String, String> values = new HashMap<>();

    public AttributeRank(int id, int attributeId, String name, Character character, String rank){
        this.id = id;
        this.attributeId = attributeId;
        this.name = name;
        this.character = character;
        this.rank = rank;
        this.frank = Integer.parseInt(rank.split("-")[0]);
    }

    public void add(String type, String value){
        values.put(type, value);
    }

    public TableRow asRow(Context context, LinkedList<String> types, String attributeName, boolean odd){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.attribute_ranking_row, null);
        TableRow tableRow = (TableRow)v.findViewById(R.id.row);

        TextView rankView = (TextView)tableRow.findViewById(R.id.rank);
        TextView characterView = (TextView)tableRow.findViewById(R.id.character);

        int padding = Params.PADDING;

        for(int i=0;i<types.size();i++){
            TextView valueView = new TextView(context);
            valueView.setText(values.get(types.get(i)));
            valueView.setPadding(padding,padding,padding,padding);
            if(!odd){
                valueView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            }
            tableRow.addView(valueView);
        }

        rankView.setText(rank);
        if(attributeName.equals("GRAVITY")){
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
        }else {
            if(attributeName.equals("RUNSPEED")){
                String name = character.name;
                switch (name){
                    case "Ness":
                        characterView.setText("Ebola Back Throw");
                        break;
                    default:
                        characterView.setText(character.name);
                }
            }else {
                characterView.setText(character.name);
            }
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
                if(a.frank < b.frank){
                    return -1;
                }
                if(a.frank > b.frank){
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
            if(or.character.id == character.id && or.attributeId == attributeId){
                return true;
            }
        }
        return false;
    }
}
