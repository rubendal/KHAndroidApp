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

//
public class RowValue implements Serializable {

    public String name;
    public String value;

    public RowValue(String name, String value){
        this.name = name;
        this.value = value;
    }

    public static RowValue fromJson(JSONObject jsonObject){
        try{
            String name = jsonObject.getString("name"), value = jsonObject.getString("value");
            return new RowValue(name,value);
        }catch(Exception e){

        }
        return null;
    }

    public TableRow asRow(Context context, boolean odd) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.simple_specific_attr_row, null);
        TableRow tableRow = (TableRow) v.findViewById(R.id.row);

        TextView nameView = (TextView) tableRow.findViewById(R.id.name);
        TextView valueView = (TextView) tableRow.findViewById(R.id.value);

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
