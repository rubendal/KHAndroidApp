package com.rubendal.kuroganehammercom.smash4.calculator.classes;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

public class Target implements Serializable {

    public String character, modifier;
    public float percent, luma_percent;

    public Target(){
        character = "Bayonetta";
        percent = 0;
        luma_percent = 0;
    }

    public JSONObject toJson(){
        try {
            return new JSONObject(new Gson().toJson(this));
        }catch (Exception e){

        }
        return null;
    }
}
