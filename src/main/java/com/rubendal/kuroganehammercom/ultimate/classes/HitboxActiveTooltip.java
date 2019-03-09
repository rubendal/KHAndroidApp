package com.rubendal.kuroganehammercom.ultimate.classes;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

import java.io.Serializable;

public class HitboxActiveTooltip implements Serializable {

    public String Frames;
    public String Adv = "";
    public String ShieldstunMultiplier = "";
    public String RehitRate = "";
    public String FacingRestrict = "";
    public String SuperArmor = "";
    public String HeadMultiplier = "";
    public String Intangible = "";
    public boolean SetWeight = false;
    public boolean GroundOnly = false;

    public HitboxActiveTooltip(){

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if(!Adv.equals(""))
            sb.append("Adv: " + Adv + ", ");
        if(!ShieldstunMultiplier.equals(""))
            sb.append(ShieldstunMultiplier + ", ");
        if(!RehitRate.equals(""))
            sb.append(RehitRate + ", ");
        if(!FacingRestrict.equals(""))
            sb.append(FacingRestrict + ", ");
        if(!SuperArmor.equals(""))
            sb.append(SuperArmor + ", ");
        if(!HeadMultiplier.equals(""))
            sb.append(HeadMultiplier + ", ");
        if(!Intangible.equals(""))
            sb.append(Intangible + ", ");
        if(SetWeight)
            sb.append("Set Weight, ");
        if(GroundOnly)
            sb.append("Ground Only, ");
        String s = sb.toString();
        if(s.length() > 2){
            s = s.substring(0, s.length()-2); //Remove last " ,"
        }
        return s;
    }

    public static HitboxActiveTooltip getFromJson(JSONObject jsonObject){
        HitboxActiveTooltip hitboxActiveTooltip = new HitboxActiveTooltip();

        try{
            hitboxActiveTooltip.Frames = StringEscapeUtils.unescapeHtml4(jsonObject.getString("Frames"));

            if(jsonObject.has("Adv")){
                hitboxActiveTooltip.Adv = StringEscapeUtils.unescapeHtml4(jsonObject.getString("Adv").replace("|","").replace(" ", ""));
            }

            if(jsonObject.has("ShieldstunMultiplier")){
                hitboxActiveTooltip.ShieldstunMultiplier = StringEscapeUtils.unescapeHtml4(jsonObject.getString("ShieldstunMultiplier").replace("|",""));
            }

            if(jsonObject.has("RehitRate")){
                hitboxActiveTooltip.RehitRate = StringEscapeUtils.unescapeHtml4(jsonObject.getString("RehitRate").replace("|",""));
            }

            if(jsonObject.has("FacingRestrict")){
                hitboxActiveTooltip.FacingRestrict = StringEscapeUtils.unescapeHtml4(jsonObject.getString("FacingRestrict").replace("|",""));
            }

            if(jsonObject.has("SuperArmor")){
                hitboxActiveTooltip.SuperArmor = StringEscapeUtils.unescapeHtml4(jsonObject.getString("SuperArmor").replace("|",""));
            }

            if(jsonObject.has("HeadMultiplier")){
                hitboxActiveTooltip.HeadMultiplier = StringEscapeUtils.unescapeHtml4(jsonObject.getString("HeadMultiplier").replace("|",""));
            }

            if(jsonObject.has("SetWeight")){
                hitboxActiveTooltip.SetWeight = jsonObject.getBoolean("SetWeight");
            }

            if(jsonObject.has("GroundOnly")){
                hitboxActiveTooltip.GroundOnly = jsonObject.getBoolean("GroundOnly");
            }
        }
        catch(Exception e){
            return null;
        }

        return hitboxActiveTooltip;
    }
}
