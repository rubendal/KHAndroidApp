package com.rubendal.kuroganehammercom.smash4.calculator.classes;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

public class AttackName extends Attack implements Serializable {

    public String name;

    public AttackName(){
        super();
        name = "";
    }

    @Override
    public JSONObject toJson(){
        try {
            return new JSONObject(new Gson().toJson(this));
        }catch (Exception e){

        }
        return null;
    }
}
