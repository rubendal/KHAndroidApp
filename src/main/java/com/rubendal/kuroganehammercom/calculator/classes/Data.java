package com.rubendal.kuroganehammercom.calculator.classes;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

public class Data implements Serializable {

    public Attacker attacker;
    public Target target;
    public AttackName attack;
    public Modifiers modifiers;
    public ShieldAdvantage shield_advantage;
    public Stage stage;
    public boolean vs_mode;

    public Data(){
        attacker = new Attacker();
        target = new Target();
        attack = new AttackName();
        modifiers = new Modifiers();
        shield_advantage = new ShieldAdvantage();
        stage = new Stage();
        vs_mode = true;
    }

    public JSONObject toJson(){
        try {
            return new JSONObject(new Gson().toJson(this));
        }catch (Exception e){

        }
        return null;
    }
}
