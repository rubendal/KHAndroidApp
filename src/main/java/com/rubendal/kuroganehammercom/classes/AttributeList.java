package com.rubendal.kuroganehammercom.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.rubendal.kuroganehammercom.util.Storage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class AttributeList implements Serializable {

    public int id;
    public String name;

    private AttributeList(int id, String name){
        this.id = id;
        this.name = name;
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

    public static LinkedList<AttributeList> list = new LinkedList<>();

    public static LinkedList<AttributeList> getList(Context context){
        if(!list.isEmpty()){
            return list;
        }else{
            try{
                String data = Storage.read("data","smashAttributes.json",context);
                JSONArray jsonArray = new JSONArray(data);
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject o = jsonArray.getJSONObject(i);
                    list.add(new AttributeList(o.getInt("id"), o.getString("name")));
                }
            }catch(Exception e){

            }
            return list;
        }
    }
}
