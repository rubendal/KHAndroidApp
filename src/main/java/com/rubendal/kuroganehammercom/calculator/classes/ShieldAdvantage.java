package com.rubendal.kuroganehammercom.calculator.classes;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

public class ShieldAdvantage implements Serializable {

    public Integer hit_frame, faf;
    public boolean power_shield, use_landing_lag, use_autocancel;

    public ShieldAdvantage(){
        hit_frame = null;
        faf = null;
        power_shield = use_landing_lag = use_autocancel = false;
    }

    public JSONObject toJson(){
        try {
            return new JSONObject(new Gson().toJson(this));
        }catch (Exception e){

        }
        return null;
    }
}
