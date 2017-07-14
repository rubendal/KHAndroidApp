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

public class Movement implements Serializable {

    public String id;
    public String name, value;

    public Movement(String id, String name, String value){
        this.id = id;
        this.name = name;
        this.value = value; //.replace("frames"," frames");
    }

    public static Movement fromJson(JSONObject jsonObject){
        try {
            String name = jsonObject.getString("Name"), value = jsonObject.getString("Value");
            String id = jsonObject.getString("InstanceId");
            return new Movement(id,name,value);
        }catch(Exception e){
            return null;
        }
    }

    public TableRow asRow(Context context, boolean odd){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.movement_row, null);
        TableRow tableRow = (TableRow)v.findViewById(R.id.row);

        TextView movementView = (TextView)tableRow.findViewById(R.id.attribute);
        TextView valueView = (TextView)tableRow.findViewById(R.id.value);

        movementView.setText(name);
        valueView.setText(value);

        int padding = Params.PADDING;
        movementView.setPadding(padding,padding,padding,padding);
        valueView.setPadding(padding,padding,padding,padding);

        if(!odd){
            movementView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            valueView.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        return tableRow;


    }
}
