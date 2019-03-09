package com.rubendal.kuroganehammercom.smash4.classes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.ultimate.classes.BaseDamageTooltip;
import com.rubendal.kuroganehammercom.ultimate.classes.HitboxActiveTooltip;
import com.rubendal.kuroganehammercom.util.Tooltip;
import com.rubendal.kuroganehammercom.util.params.Params;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;


public class AerialMove extends Move {

    public String landingLag;
    public String autoCancel;

    public AerialMove(MoveType moveType, String name, String hitboxActive, String FAF, String baseDamage, String angle, String bkb, String kbg, String landingLag, String autoCancel) {
        super(moveType, name, hitboxActive, FAF, baseDamage, angle, bkb, kbg);
        this.landingLag = landingLag;
        this.autoCancel = autoCancel;
    }

    public AerialMove(MoveType moveType, String name, String hitboxActive, String FAF, String baseDamage, String angle, String bkb, String kbg, String landingLag, String autoCancel, HitboxActiveTooltip hitboxActiveTooltip, BaseDamageTooltip baseDamageTooltip) {
        super(moveType, name, hitboxActive, FAF, baseDamage, angle, bkb, kbg, hitboxActiveTooltip, baseDamageTooltip);
        this.landingLag = landingLag;
        this.autoCancel = autoCancel;
    }

    public TableRow asRow(Context context, boolean odd, String color){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.attack_list_row_aerial, null);
        TableRow tableRow = (TableRow)v.findViewById(R.id.row);

        TextView nameView = (TextView)tableRow.findViewById(R.id.name);
        TextView hitboxActiveView = (TextView)tableRow.findViewById(R.id.hitboxActive);
        TextView fafView = (TextView)tableRow.findViewById(R.id.faf);
        TextView baseDamageView = (TextView)tableRow.findViewById(R.id.damage);
        TextView angleView = (TextView)tableRow.findViewById(R.id.angle);
        TextView bkbView = (TextView)tableRow.findViewById(R.id.bkb);
        TextView kbgView = (TextView)tableRow.findViewById(R.id.kbg);
        TextView landingLagView = (TextView)tableRow.findViewById(R.id.landingLag);
        TextView autoCancelView = (TextView)tableRow.findViewById(R.id.autoCancel);

        nameView.setText(name);
        hitboxActiveView.setText(hitboxActive);
        fafView.setText(FAF);
        baseDamageView.setText(baseDamage);
        angleView.setText(angle);
        bkbView.setText(bkb);
        kbgView.setText(kbg);
        landingLagView.setText(landingLag);
        autoCancelView.setText(autoCancel);

        if(hitboxActiveTooltip != null){
            if(!hitboxActiveTooltip.toString().equals("")) {
                hitboxActiveView.setPaintFlags(hitboxActiveView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                hitboxActiveView.setOnClickListener(new Tooltip(context, hitboxActiveTooltip.toString()));
            }
        }

        if(baseDamageTooltip != null){
            if(!baseDamageTooltip.toString().equals("")) {
                baseDamageView.setPaintFlags(baseDamageView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                baseDamageView.setOnClickListener(new Tooltip(context, baseDamageTooltip.toString()));
            }
        }

        int padding = Params.PADDING;
        nameView.setPadding(padding,padding,padding,padding);
        hitboxActiveView.setPadding(padding,padding,padding,padding);
        fafView.setPadding(padding,padding,padding,padding);
        baseDamageView.setPadding(padding,padding,padding,padding);
        angleView.setPadding(padding,padding,padding,padding);
        bkbView.setPadding(padding,padding,padding,padding);
        kbgView.setPadding(padding,padding,padding,padding);
        landingLagView.setPadding(padding,padding,padding,padding);
        autoCancelView.setPadding(padding,padding,padding,padding);

        if(!odd){
            nameView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            hitboxActiveView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            fafView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            baseDamageView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            angleView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            bkbView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            kbgView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            landingLagView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            autoCancelView.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        //nameView.setBackgroundColor(Color.parseColor(color));

        return tableRow;


    }

    public static AerialMove getFromJson(JSONObject jsonObject){
        try {
            MoveType moveType = MoveType.fromValue(jsonObject.getString("MoveType"));
            String name = StringEscapeUtils.unescapeHtml4(jsonObject.getString("Name"));
            String hitboxActive = StringEscapeUtils.unescapeHtml4(jsonObject.getString("HitboxActive"));
            String FAF = StringEscapeUtils.unescapeHtml4(jsonObject.getString("FirstActionableFrame"));
            String baseDamage = StringEscapeUtils.unescapeHtml4(jsonObject.getString("BaseDamage"));
            String angle = StringEscapeUtils.unescapeHtml4(jsonObject.getString("Angle"));
            String bkb = StringEscapeUtils.unescapeHtml4(jsonObject.getString("BaseKnockBackSetKnockback"));
            String kbg = StringEscapeUtils.unescapeHtml4(jsonObject.getString("KnockbackGrowth"));
            String landingLag = StringEscapeUtils.unescapeHtml4(jsonObject.getString("LandingLag"));
            String autoCancel = StringEscapeUtils.unescapeHtml4(jsonObject.getString("AutoCancel"));
            if(jsonObject.isNull("HitboxActive"))
                hitboxActive="";
            if(jsonObject.isNull("FirstActionableFrame"))
                FAF = "";
            if(jsonObject.isNull("BaseDamage"))
                baseDamage = "";
            if(jsonObject.isNull("Angle"))
                angle = "";
            if(jsonObject.isNull("BaseKnockBackSetKnockback"))
                bkb = "";
            if(jsonObject.isNull("KnockbackGrowth"))
                kbg = "";
            if(jsonObject.isNull("LandingLag"))
                landingLag = "";
            if(jsonObject.isNull("AutoCancel"))
                autoCancel = "";
            if(!jsonObject.has("HitboxActiveTooltip")) {
                return new AerialMove(moveType, name, hitboxActive, FAF, baseDamage, angle, bkb, kbg, landingLag, autoCancel);
            }else{
                String hitboxActiveTooltip = null, baseDamageTooltip = null;
                if(jsonObject.has("HitboxActiveTooltip") && !jsonObject.isNull("HitboxActiveTooltip")){
                    hitboxActiveTooltip = jsonObject.getString("HitboxActiveTooltip");
                }
                if(jsonObject.has("BaseDamageTooltip") && !jsonObject.isNull("BaseDamageTooltip")){
                    baseDamageTooltip = jsonObject.getString("BaseDamageTooltip");
                }
                return new AerialMove(moveType, name, hitboxActive, FAF, baseDamage, angle, bkb, kbg, landingLag, autoCancel);
            }
        }catch(Exception e){
            return null;
        }
    }

    public static AerialMove ssbuGetFromJson(JSONObject jsonObject){
        try {
            MoveType moveType = MoveType.fromValue(jsonObject.getString("MoveType"));
            String name = StringEscapeUtils.unescapeHtml4(jsonObject.getString("Name"));
            String FAF = StringEscapeUtils.unescapeHtml4(jsonObject.getString("FirstActionableFrame"));
            String angle = StringEscapeUtils.unescapeHtml4(jsonObject.getString("Angle"));
            String bkb = StringEscapeUtils.unescapeHtml4(jsonObject.getString("BaseKnockBackSetKnockback"));
            String kbg = StringEscapeUtils.unescapeHtml4(jsonObject.getString("KnockbackGrowth"));
            String landingLag = StringEscapeUtils.unescapeHtml4(jsonObject.getString("LandingLag"));
            String autoCancel = StringEscapeUtils.unescapeHtml4(jsonObject.getString("AutoCancel"));

            String hitboxActive= "";
            String baseDamage = "";

            HitboxActiveTooltip hitboxActiveTooltip = null;
            if(!jsonObject.isNull("HitboxActive")){
                hitboxActiveTooltip = HitboxActiveTooltip.getFromJson(jsonObject.getJSONObject("HitboxActive"));
                hitboxActive = hitboxActiveTooltip.Frames;
            }
            BaseDamageTooltip baseDamageTooltip = null;
            if(!jsonObject.isNull("BaseDamage")){
                baseDamageTooltip = BaseDamageTooltip.getFromJson(jsonObject.getJSONObject("BaseDamage"));
                baseDamage = baseDamageTooltip.Normal;
            }

            if(jsonObject.isNull("HitboxActive"))
                hitboxActive="";
            if(jsonObject.isNull("FirstActionableFrame"))
                FAF = "";
            if(jsonObject.isNull("BaseDamage"))
                baseDamage = "";
            if(jsonObject.isNull("Angle"))
                angle = "";
            if(jsonObject.isNull("BaseKnockBackSetKnockback"))
                bkb = "";
            if(jsonObject.isNull("KnockbackGrowth"))
                kbg = "";
            if(jsonObject.isNull("LandingLag"))
                landingLag = "";
            if(jsonObject.isNull("AutoCancel"))
                autoCancel = "";

            return new AerialMove(moveType, name, hitboxActive, FAF, baseDamage, angle, bkb, kbg, landingLag, autoCancel, hitboxActiveTooltip, baseDamageTooltip);
        }catch(Exception e){
            return null;
        }
    }

}
