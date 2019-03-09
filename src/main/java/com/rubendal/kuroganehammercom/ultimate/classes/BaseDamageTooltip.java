package com.rubendal.kuroganehammercom.ultimate.classes;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

import java.io.Serializable;

public class BaseDamageTooltip implements Serializable {

    public String Normal;
    public String OneVOne = "";
    public String ShieldstunMultiplier = "";
    public String SD = ""; //ShieldDamage

    public BaseDamageTooltip(){

    }

    public BaseDamageTooltip(String normal, String onevone, String shieldstunMultiplier, String sd){
        Normal = normal;
        OneVOne = onevone;
        ShieldstunMultiplier = shieldstunMultiplier;
        SD = sd;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(!OneVOne.equals(""))
            sb.append("1v1: " + OneVOne + ", ");
        if(!ShieldstunMultiplier.equals(""))
            sb.append(ShieldstunMultiplier + ", ");
        if(!SD.equals(""))
            sb.append(SD + ", ");
        String s = sb.toString();
        if(s.length() > 2){
            s = s.substring(0, s.length()-2); //Remove last " ,"
        }
        return s;
    }

    public static BaseDamageTooltip getFromJson(JSONObject jsonObject){
        BaseDamageTooltip baseDamageTooltip = new BaseDamageTooltip();

        try{
            baseDamageTooltip.Normal = StringEscapeUtils.unescapeHtml4(jsonObject.getString("Normal"));

            if(jsonObject.has("OneVOne")){
                baseDamageTooltip.OneVOne = StringEscapeUtils.unescapeHtml4(jsonObject.getString("OneVOne").replace("|",""));
            }

            if(jsonObject.has("ShieldstunMultiplier")){
                baseDamageTooltip.ShieldstunMultiplier = StringEscapeUtils.unescapeHtml4(jsonObject.getString("ShieldstunMultiplier").replace("|",""));
            }
            if(jsonObject.has("SD")){
                baseDamageTooltip.SD = StringEscapeUtils.unescapeHtml4(jsonObject.getString("SD").replace("|",""));
            }
        }
        catch(Exception e){
            return null;
        }

        return baseDamageTooltip;
    }
}
