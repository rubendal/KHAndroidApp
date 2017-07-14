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

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class AttributeList implements Serializable {

    public String id;
    public String name;
    public List<Attribute> attributes;
    public int ownerId;

    private AttributeList(String id, String name, List<Attribute> attributes){
        this.id = id;
        this.name = name;
        this.attributes = attributes;
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

    public static AttributeList getFromJson(Context context, JSONObject jsonObject){
        try {
            String id = jsonObject.getString("InstanceId");
            int owner = jsonObject.getInt(("OwnerId"));
            String name = jsonObject.getString("Name");
            LinkedList<Attribute> attributes = new LinkedList<>();
            JSONArray values = jsonObject.getJSONArray("Values");
            for(int i=0;i<values.length();i++){
                attributes.add(Attribute.getFromJson(context, values.getJSONObject(i)));
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
            return new AttributeList(id, name, attributes);
        }catch(Exception e){

        }
        return null;
    }

    public TableRow asRow(Context context, boolean odd){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.attribute_row, null);
        TableRow tableRow = (TableRow)v.findViewById(R.id.row);

        TextView attributeView = (TextView)tableRow.findViewById(R.id.attribute);
        TextView valueView = (TextView)tableRow.findViewById(R.id.value);
        TextView rankView = (TextView)tableRow.findViewById(R.id.rank);

        attributeView.setText(name);
        //valueView.setText(value);

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
    }

}
