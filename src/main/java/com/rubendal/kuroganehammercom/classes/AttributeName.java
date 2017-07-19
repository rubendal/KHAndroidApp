package com.rubendal.kuroganehammercom.classes;


import org.json.JSONObject;

import java.io.Serializable;

public class AttributeName implements Serializable {

    public String name;

    public AttributeName(String name){
        this.name = name;
    }

    public static AttributeName fromJson(JSONObject jsonObject){
        try{
            return new AttributeName(jsonObject.getString("Name"));
        }catch(Exception e){

        }
        return null;
    }
}
