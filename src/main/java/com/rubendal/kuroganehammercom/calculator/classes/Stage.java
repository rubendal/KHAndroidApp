package com.rubendal.kuroganehammercom.calculator.classes;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

public class Stage implements Serializable {

    public String name;
    public Point position;
    public boolean inverse_x;

    public Stage(){
        name = null;
        position = new Point();
        inverse_x = false;
    }


    public JSONObject toJson(){
        try {
            return new JSONObject(new Gson().toJson(this));
        }catch (Exception e){

        }
        return null;
    }
}
