package com.rubendal.kuroganehammercom.classes;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Comparator;

public class AttributeName implements Serializable, Comparable<AttributeName> {

    public String name, formattedName ="";

    public AttributeName(String name){
        this.name = name;
        String[] w = name.split("(?<=.)(?=\\p{Lu})");
        for(String s : w){
            if(s!=null)
                this.formattedName += s + " ";
        }
        this.formattedName = this.formattedName.trim();
    }

    public static AttributeName getFromJson(JSONObject jsonObject){
        try{
            return new AttributeName(jsonObject.getString("Name"));
        }catch(Exception e){

        }
        return null;
    }

    public Bitmap getImage(Context context){
        InputStream is;
        Bitmap thumb = null;
        try {
            is = context.getAssets().open("Images/attributes/" + name.toLowerCase() + "/image.png");
            thumb = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            try {
                is = context.getAssets().open("Images/attributes/" + name.toLowerCase() + "/image.jpg");
                thumb = BitmapFactory.decodeStream(is);
            } catch (IOException e2) {
                thumb = null;
            }
        }
        return thumb;
    }

    @Override
    public int compareTo(@NonNull AttributeName attributeName) {
        return name.compareTo(attributeName.name);
    }
}
