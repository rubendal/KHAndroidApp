package com.rubendal.kuroganehammercom.smash4.calculator.classes;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

public class Point implements Serializable {
    public float x,y;

    public Point(){
        x = y = 0;
    }

    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }

    public JSONObject toJson(){
        try {
            return new JSONObject(new Gson().toJson(this));
        }catch (Exception e){

        }
        return null;
    }
}
