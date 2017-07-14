package com.rubendal.kuroganehammercom.calculator.classes;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

public class AttackData extends Attack implements Serializable {

    public float damage, hitlag;
    public int angle, bkb, wbkb, kbg, shield_damage;

    public AttackData(){
        super();
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
