package com.rubendal.kuroganehammercom.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.util.params.Params;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class Attribute implements Serializable {

    public String name, formattedName="";
    public List<AttributeValue> attributes;
    public int ownerId;

    public Attribute(String name, List<AttributeValue> attributes, int ownerId){
        this.name = name;
        String[] w = name.split("(?<=.)(?=\\p{Lu})");
        for(String s : w){
            if(s!=null)
                this.formattedName += s + " ";
        }
        this.formattedName = this.formattedName.trim();
        this.attributes = attributes;
        this.ownerId = ownerId;
    }



    public LinkedList<String> getAttributeValues(){
        if(attributes.size()==0)
            return null;

        LinkedList<String> s = new LinkedList<>();
        for(AttributeValue a : attributes){
            s.add(a.name);
        }
        return s;
    }

    public static Attribute getFromJson(Context context, JSONObject jsonObject){
        try {
            int owner = jsonObject.getInt(("OwnerId"));
            String name = StringEscapeUtils.unescapeHtml4(jsonObject.getString("Name"));
            LinkedList<AttributeValue> attributes = new LinkedList<>();
            JSONArray values = jsonObject.getJSONArray("Values");
            for(int i=0;i<values.length();i++){
                attributes.add(AttributeValue.getFromJson(context, values.getJSONObject(i)));
            }

            return new Attribute(name, attributes, owner);
        }catch(Exception e){

        }
        return null;
    }

    public LinkedList<TableRow> asRow(Context context, int id){
        LinkedList<TableRow> rows = new LinkedList<>();

        for(AttributeValue a : attributes){
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.attribute_row, null);
            TableRow tableRow = (TableRow)v.findViewById(R.id.row);

            TextView attributeView = (TextView)tableRow.findViewById(R.id.attribute);
            TextView valueView = (TextView)tableRow.findViewById(R.id.value);
            TextView typeView = (TextView)tableRow.findViewById(R.id.type);

            attributeView.setText(formattedName);
            typeView.setText(a.name);
            valueView.setText(a.value);

            int padding = Params.PADDING;
            attributeView.setPadding(padding,padding,padding,padding);
            valueView.setPadding(padding,padding,padding,padding);
            typeView.setPadding(padding,padding,padding,padding);


            if(id % 2 == 1){
                attributeView.setBackgroundColor(Color.parseColor("#D9D9D9"));
                valueView.setBackgroundColor(Color.parseColor("#D9D9D9"));
                typeView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            }
            id++;

            rows.add(tableRow);
        }

        return rows;
    }

}
