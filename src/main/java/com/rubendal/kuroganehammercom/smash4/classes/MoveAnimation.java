package com.rubendal.kuroganehammercom.smash4.classes;

import org.json.JSONObject;

import java.io.Serializable;

public class MoveAnimation implements Serializable {
    public String name;
    public String rawName;
    public String category;

    public MoveAnimation(String name, String rawName, String category){
        this.name = name;
        this.rawName = rawName;
        this.category = category;
    }

    public static MoveAnimation fromJson(JSONObject jsonObject){
        try {
            String name = jsonObject.getString("prettyName"), rawName = jsonObject.getString("rawName"), category = jsonObject.getString("category");
            return new MoveAnimation(name, rawName, category);
        }catch(Exception e){
            return null;
        }
    }
}
