package com.rubendal.kuroganehammercom.classes;

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
import java.util.List;

public class Attribute implements Serializable{

    public int ownerId;
    public String name;
    public String value;

    public Attribute(int ownerId, String name, String value){
        this.ownerId = ownerId;
        this.name = name;
        this.value = value;
    }

    public static Attribute getFromJson(Context context, JSONObject jsonObject){
        try {

            int ownerId = jsonObject.getInt("OwnerId");
            String name = jsonObject.getString("name");
            String value = jsonObject.getString("value");
            Attribute attribute = new Attribute(ownerId, name, value);

            /*for(AttributeList al : attributeList){
                if(attribute.attributeId == al.id){
                    if(attribute.formattedName.equals("VALUE") || attribute.formattedName.equals("INTANGIBILITY") || attribute.formattedName.equals("INTANGIBLE") || attribute.formattedName.equals("FAF") || attribute.formattedName.equals("HEIGHT") || attribute.formattedName.equals("SIZE")){
                        if(!attribute.name.equals("VALUE")) {
                            attribute.formattedName = al.name + " " + attribute.name;
                        }else{
                            attribute.formattedName = al.name;
                        }
                        return attribute;
                    }
                }
            }*/
            return attribute;
        }catch(Exception e){

        }
        return null;
    }



    /*public TableRow asRow(Context context, boolean odd){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.attribute_row, null);
        TableRow tableRow = (TableRow)v.findViewById(R.id.row);

        TextView attributeView = (TextView)tableRow.findViewById(R.id.attribute);
        TextView valueView = (TextView)tableRow.findViewById(R.id.value);
        TextView rankView = (TextView)tableRow.findViewById(R.id.rank);

        attributeView.setText(name);
        valueView.setText(value);

        int padding = Params.PADDING;
        attributeView.setPadding(padding,padding,padding,padding);
        valueView.setPadding(padding,padding,padding,padding);
        rankView.setPadding(padding,padding,padding,padding);


        if(!odd){
            attributeView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            valueView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            rankView.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        return tableRow;
    }*/



}
