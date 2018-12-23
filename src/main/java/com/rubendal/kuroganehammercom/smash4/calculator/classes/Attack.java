package com.rubendal.kuroganehammercom.smash4.calculator.classes;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

public class Attack implements Serializable {

    public boolean is_smash_attack, windbox, projectile, set_weight, aerial_opponent, ignore_staleness, mega_man_fsmash, on_witch_time;
    public boolean[] stale_queue = new boolean[9];
    public int charged_frames;
    public float hitlag;
    public String effect;

    public Attack(){
        is_smash_attack = windbox = projectile = set_weight = aerial_opponent = ignore_staleness = mega_man_fsmash = on_witch_time = false;
        stale_queue = new boolean[]{false,false,false,false,false,false,false,false,false};
        charged_frames = 0;
        hitlag = 1;
        effect = null;
    }

    public JSONObject toJson(){
        try {
            return new JSONObject(new Gson().toJson(this));
        }catch (Exception e){

        }
        return null;
    }
}
