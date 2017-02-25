package com.rubendal.kuroganehammercom.classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class SpecificAttribute implements Serializable {

    public String attribute;
    public boolean isSimple = true;
    public List<RowValue> valueList;
    public List<String> headers;
    public LinkedList<LinkedList<String>> rows;

    public SpecificAttribute(String attribute, List<RowValue> valueList){
        this.attribute = attribute;
        this.isSimple = true;
        this.valueList = valueList;
    }

    public SpecificAttribute(String attribute, List<String> headers, LinkedList<LinkedList<String>> rows){
        this.attribute = attribute;
        this.isSimple = false;
        this.headers = headers;
        this.rows = rows;
    }

    public static SpecificAttribute fromJson(JSONObject jsonObject){
        try {
            String attribute = jsonObject.getString("attribute");
            if(jsonObject.has("NameValueTable")){
                //Simple table
                LinkedList<RowValue> values = new LinkedList<>();
                JSONArray jsonArray = jsonObject.getJSONArray("NameValueTable");
                for(int i=0;i<jsonArray.length();i++){
                    values.add(RowValue.fromJson(jsonArray.getJSONObject(i)));
                }
                return new SpecificAttribute(attribute, values);
            }else{
                //Custom table
                LinkedList<String> headers = new LinkedList<>();
                LinkedList<LinkedList<String>> rows = new LinkedList<>();
                JSONArray jsonArray = jsonObject.getJSONArray("headers");
                for(int i=0;i<jsonArray.length();i++){
                    headers.add(jsonArray.getString(i));
                }
                jsonArray = jsonObject.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++){
                    LinkedList<String> l = new LinkedList<>();
                    for(int x =0;x<jsonArray.getJSONArray(i).length();x++){
                        l.add(jsonArray.getJSONArray(i).getString(x));
                    }
                    rows.add(l);
                }
                return new SpecificAttribute(attribute, headers, rows);
            }
        }catch(Exception e){
            return null;
        }
    }


    public List<TableRow> asRows(Context context, String headerColor) {
        LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.complex_specific_attr_row, null);
        TableRow tableRow = (TableRow) v.findViewById(R.id.row);
        LinkedList<TableRow> rowList = new LinkedList<>();

        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        int padding = 15;

        for(int i =0;i<headers.size();i++){
            TextView t = new TextView(context);
            t.setLayoutParams(layoutParams);
            t.setText(headers.get(i));
            t.setPadding(padding,padding,padding,padding);
            t.setBackgroundColor(Color.parseColor(headerColor));
            tableRow.addView(t);
        }
        rowList.add(tableRow);
        for(int i=0;i<rows.size();i++){
            v = vi.inflate(R.layout.complex_specific_attr_row, null);
            tableRow = (TableRow) v.findViewById(R.id.row);
            for(int x=0;x<rows.get(i).size();x++){
                TextView t = new TextView(context);
                t.setLayoutParams(layoutParams);
                t.setText(rows.get(i).get(x));
                t.setPadding(padding,padding,padding,padding);
                if(i%2==1){
                    t.setBackgroundColor(Color.parseColor("#D9D9D9"));
                }
                tableRow.addView(t);
            }
            rowList.add(tableRow);
        }

        return rowList;
    }
}
