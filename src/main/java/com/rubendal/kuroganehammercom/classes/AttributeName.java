package com.rubendal.kuroganehammercom.classes;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class AttributeName implements Serializable {

    public String name;

    public AttributeName(String name){
        this.name = name;
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
}
