package com.rubendal.kuroganehammercom.rivals.classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.util.params.Params;

import org.json.JSONObject;

import java.io.Serializable;

public class RivalsMove implements Serializable {
    public String type, name, hitboxActive, faf, baseDamage, angle, bkb, kbg, priority, hitstunModifier, landingLag, autoCancel;

    public RivalsMove(){

    }

    public RivalsMove(String type, String name, String hitboxActive, String faf, String baseDamage, String angle, String bkb, String kbg, String priority, String hitstunModifier, String landingLag, String autoCancel) {
        this.type = type;
        this.name = name;
        this.hitboxActive = hitboxActive;
        this.faf = faf;
        this.baseDamage = baseDamage;
        this.angle = angle;
        this.bkb = bkb;
        this.kbg = kbg;
        this.priority = priority;
        this.hitstunModifier = hitstunModifier;
        this.landingLag = landingLag;
        this.autoCancel = autoCancel;
    }

    public static RivalsMove fromJson(JSONObject jsonObject){
        try{

            String type = jsonObject.getString("Type"),name = jsonObject.getString("Name"), hitboxActive = jsonObject.getString("HitboxActive"),faf = jsonObject.getString("FAF"),baseDamage = jsonObject.getString("BaseDamage"),
                    angle = jsonObject.getString("Angle"),bkb = jsonObject.getString("BKB"), kbg = jsonObject.getString("KBG"),
                    priority = jsonObject.getString("Priority"), hitstunModifier = jsonObject.getString("HitstunModifier"), landingLag = jsonObject.getString("LandingLag"), autoCancel= jsonObject.getString("AutoCancel");

            return new RivalsMove(type, name, hitboxActive, faf, baseDamage, angle, bkb, kbg, priority, hitstunModifier, landingLag, autoCancel);

        }
        catch(Exception e){

        }
        return null;
    }

    public TableRow asRow(Context context, boolean odd, String color){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v;
        if(type.equals("Aerial")){
            v = vi.inflate(R.layout.rivals_attack_data_aerial, null);
        }else{
            v = vi.inflate(R.layout.rivals_attack_data, null);
        }
        TableRow tableRow = (TableRow)v.findViewById(R.id.row);

        TextView nameView = (TextView)tableRow.findViewById(R.id.name);



        TextView hitboxActiveView = (TextView)tableRow.findViewById(R.id.hitboxActive);
        TextView fafView = (TextView)tableRow.findViewById(R.id.faf);
        TextView damageView = (TextView)tableRow.findViewById(R.id.damage);
        TextView angleView = (TextView)tableRow.findViewById(R.id.angle);
        TextView bkbView = (TextView)tableRow.findViewById(R.id.bkb);
        TextView kbgView = (TextView)tableRow.findViewById(R.id.kbg);
        TextView priorityView = (TextView)tableRow.findViewById(R.id.priority);
        TextView hitstunView = (TextView)tableRow.findViewById(R.id.hitstunModifier);

        nameView.setText(name);
        hitboxActiveView.setText(hitboxActive);
        fafView.setText(faf);
        damageView.setText(baseDamage);
        angleView.setText(angle);
        bkbView.setText(bkb);
        kbgView.setText(kbg);
        priorityView.setText(priority);
        hitstunView.setText(hitstunModifier);

        int padding = Params.PADDING;
        nameView.setPadding(padding,padding,padding,padding);
        hitboxActiveView.setPadding(padding,padding,padding,padding);
        fafView.setPadding(padding,padding,padding,padding);
        damageView.setPadding(padding,padding,padding,padding);
        angleView.setPadding(padding,padding,padding,padding);
        bkbView.setPadding(padding,padding,padding,padding);
        kbgView.setPadding(padding,padding,padding,padding);
        priorityView.setPadding(padding,padding,padding,padding);
        hitstunView.setPadding(padding,padding,padding,padding);

        if(!odd){
            nameView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            hitboxActiveView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            fafView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            damageView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            angleView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            bkbView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            kbgView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            priorityView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            hitstunView.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        if(type.equals("Aerial")){
            TextView landingLagView = (TextView)tableRow.findViewById(R.id.landingLag);
            TextView autoCancelView = (TextView)tableRow.findViewById(R.id.autoCancel);

            landingLagView.setText(landingLag);
            autoCancelView.setText(autoCancel);

            landingLagView.setPadding(padding,padding,padding,padding);
            autoCancelView.setPadding(padding,padding,padding,padding);

            if(!odd){
                landingLagView.setBackgroundColor(Color.parseColor("#D9D9D9"));
                autoCancelView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            }

        }

        nameView.setBackgroundColor(Color.parseColor(color));

        return tableRow;


    }

}
