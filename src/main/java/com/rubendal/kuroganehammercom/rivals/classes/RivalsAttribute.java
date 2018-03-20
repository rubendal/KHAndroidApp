package com.rubendal.kuroganehammercom.rivals.classes;

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

public class RivalsAttribute implements Serializable {

    public String name;
    public String value;

    public RivalsAttribute(){

    }

    public RivalsAttribute(String name, String value){
        this.name = name;
        this.value = value;
    }

    public static RivalsAttribute fromJson(JSONObject jsonObject){
        try{

            String name = jsonObject.getString("Name"), value = jsonObject.getString("Value");

            return new RivalsAttribute(name, value);

        }
        catch(Exception e){

        }
        return null;
    }

    public TableRow asRow(Context context, boolean odd){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v =vi.inflate(R.layout.result_table_row, null);
        TableRow tableRow = (TableRow)v.findViewById(R.id.row);

        TextView nameView = (TextView)tableRow.findViewById(R.id.parameter);



        TextView valueView = (TextView)tableRow.findViewById(R.id.value);

        nameView.setText(name);
        valueView.setText(value);

        int padding = Params.PADDING;
        nameView.setPadding(padding,padding,padding,padding);
        valueView.setPadding(padding,padding,padding,padding);

        if(!odd){
            nameView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            valueView.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }


        return tableRow;


    }


}
