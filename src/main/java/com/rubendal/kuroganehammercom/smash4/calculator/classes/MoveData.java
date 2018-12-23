package com.rubendal.kuroganehammercom.smash4.calculator.classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class MoveData implements Serializable {
    public String name;
    public Integer faf;
    public Integer start_frame;

    public MoveData(){
        name = "";
        faf = 0;
        start_frame = 0;
    }

    public static MoveData fromJson(JSONObject jsonObject){
        try{
            MoveData m = new MoveData();
            m.name = jsonObject.getString("name");
            JSONArray jsonArray = jsonObject.getJSONArray("hitboxActive");
            if(jsonArray.length()>0){
                if(!jsonArray.getJSONObject(0).isNull("start")) {
                    m.start_frame = jsonArray.getJSONObject(0).getInt("start");
                }else{
                    m.start_frame = null;
                }
            }else{
                m.start_frame = null;
            }
            if(!jsonObject.isNull("faf")) {
                m.faf = jsonObject.getInt("faf");
            }else{
                m.faf = null;
            }
            return m;
        }catch (Exception e){
            Log.d("moveData", e.getMessage());
        }
        return null;
    }
}
