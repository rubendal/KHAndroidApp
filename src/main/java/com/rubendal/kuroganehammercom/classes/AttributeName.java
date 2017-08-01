package com.rubendal.kuroganehammercom.classes;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;

import com.rubendal.kuroganehammercom.util.UserPref;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Comparator;

public class AttributeName implements Serializable, Comparable<AttributeName> {

    public String name, formattedName ="";
    public boolean favorite = false;

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
            AttributeName a = new AttributeName(jsonObject.getString("Name"));
            a.favorite = UserPref.checkAttributeFavorites(a.name);
            return a;
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

    public static Comparator<AttributeName> getComparator(){
        Comparator<AttributeName> comparator = new Comparator<AttributeName>() {
            @Override
            public int compare(AttributeName c1, AttributeName c2) {
                if(c1.favorite == c2.favorite){
                    return c1.name.compareTo(c2.name);
                }
                if(c1.favorite){
                    return -1;
                }
                if(c2.favorite){
                    return 1;
                }
                return 0;
            }
        };
        return comparator;
    }
}
