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

    public String id;
    public String name;
    public List<AttributeValue> attributes;
    public int ownerId;

    private Attribute(String id, String name, List<AttributeValue> attributes, int ownerId){
        this.id = id;
        this.name = name;
        this.attributes = attributes;
        this.ownerId = ownerId;
    }

    public Bitmap getImage(Context context){
        InputStream is;
        Bitmap thumb = null;
        try {
            is = context.getAssets().open("Images/attributes/" + id + "/image.png");
            thumb = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            try {
                is = context.getAssets().open("Images/attributes/" + id + "/image.jpg");
                thumb = BitmapFactory.decodeStream(is);
            } catch (IOException e2) {
                thumb = null;
            }
        }
        return thumb;
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
            String id = jsonObject.getString("InstanceId");
            int owner = jsonObject.getInt(("OwnerId"));
            String name = StringEscapeUtils.unescapeHtml4(jsonObject.getString("Name"));
            LinkedList<AttributeValue> attributes = new LinkedList<>();
            JSONArray values = jsonObject.getJSONArray("Values");
            for(int i=0;i<values.length();i++){
                attributes.add(AttributeValue.getFromJson(context, values.getJSONObject(i)));
            }


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
            return new Attribute(id, name, attributes, owner);
        }catch(Exception e){
            System.out.println(e.getMessage());
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

            attributeView.setText(name);
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
