package com.rubendal.kuroganehammercom.classes;

import android.content.Context;

import com.rubendal.kuroganehammercom.util.Storage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;


public class AttributeList {

    public int id;
    public String name;

    private AttributeList(int id, String name){
        this.id = id;
        this.name = name;
    }

    public static List<AttributeList> list = new LinkedList<>();

    public static List<AttributeList> getList(Context context){
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
