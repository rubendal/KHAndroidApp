package com.rubendal.kuroganehammercom.calculator.classes;


import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

public class Modifiers implements Serializable {

    public int di;
    public boolean no_di, grounded_meteor, crouch_cancel, interrupted_smash_charge;
    public float launch_rate;

    public Modifiers(){
        di = 0;
        no_di = grounded_meteor = crouch_cancel = interrupted_smash_charge = false;
        launch_rate = 1;
    }

    public JSONObject toJson(){
        try {
            return new JSONObject(new Gson().toJson(this));
        }catch (Exception e){

        }
        return null;
    }
}
