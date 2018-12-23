package com.rubendal.kuroganehammercom.smash4.calculator.classes;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

public class Attacker implements Serializable {

    public String character, modifier;
    public float percent;
    public Aura aura;

    public Attacker(){
        character = "Bayonetta";
        percent = 0;
        aura = new Aura();
    }

    public JSONObject toJson(){
        try {
            return new JSONObject(new Gson().toJson(this));
        }catch (Exception e){

        }
        return null;
    }
}
