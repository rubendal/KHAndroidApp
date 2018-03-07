package com.rubendal.kuroganehammercom.smash4.classes;

import android.content.Context;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

import java.io.Serializable;

public class AttributeValue implements Serializable{

    public int ownerId;
    public String name;
    public String value;

    public AttributeValue(int ownerId, String name, String value){
        this.ownerId = ownerId;
        this.name = name;
        this.value = value;
    }

    public static AttributeValue getFromJson(Context context, JSONObject jsonObject){
        try {

            int ownerId = jsonObject.getInt("OwnerId");
            String name = StringEscapeUtils.unescapeHtml4(jsonObject.getString("Name"));
            String value = StringEscapeUtils.unescapeHtml4(jsonObject.getString("Value"));
            AttributeValue attribute = new AttributeValue(ownerId, name, value);

            return attribute;
        }catch(Exception e){
            System.out.println(e.getMessage());
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
