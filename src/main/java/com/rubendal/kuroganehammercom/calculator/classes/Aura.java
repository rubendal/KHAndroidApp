package com.rubendal.kuroganehammercom.calculator.classes;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;

public class Aura implements Serializable {

    public String stock_dif, game_mode;

    public Aura(){
        stock_dif = "0";
        game_mode = "Singles";
    }

    public Aura(String stock_dif, String game_mode){
        this.stock_dif = stock_dif;
        this.game_mode = game_mode;
    }

    public JSONObject toJson(){
        try {
            return new JSONObject(new Gson().toJson(this));
        }catch (Exception e){

        }
        return null;
    }
}
