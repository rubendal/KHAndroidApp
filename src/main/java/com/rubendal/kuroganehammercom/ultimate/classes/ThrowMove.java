package com.rubendal.kuroganehammercom.ultimate.classes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.smash4.classes.Move;
import com.rubendal.kuroganehammercom.smash4.classes.MoveType;
import com.rubendal.kuroganehammercom.util.Tooltip;
import com.rubendal.kuroganehammercom.util.params.Params;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;


public class ThrowMove extends Move {


    public ThrowMove(MoveType moveType, String name, String hitboxActive, String FAF, String baseDamage, String angle, String bkb, String kbg, String baseDamageTooltip) {
        super(moveType, name, hitboxActive, FAF, baseDamage, angle, bkb, kbg, null, baseDamageTooltip);
    }

    public TableRow asRow(Context context, boolean odd, String color){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.attack_list_row_throw_ssbu, null);
        TableRow tableRow = (TableRow)v.findViewById(R.id.row);

        TextView nameView = (TextView)tableRow.findViewById(R.id.name);
        TextView baseDamageView = (TextView)tableRow.findViewById(R.id.damage);
        TextView angleView = (TextView)tableRow.findViewById(R.id.angle);
        TextView bkbView = (TextView)tableRow.findViewById(R.id.bkb);
        TextView kbgView = (TextView)tableRow.findViewById(R.id.kbg);

        nameView.setText(name);
        baseDamageView.setText(baseDamage);
        angleView.setText(angle);
        bkbView.setText(bkb);
        kbgView.setText(kbg);

        if(baseDamageTooltip != null){
            baseDamageView.setPaintFlags(baseDamageView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            baseDamageView.setOnClickListener(new Tooltip(context, baseDamageTooltip));
        }

        int padding = Params.PADDING;
        nameView.setPadding(padding,padding,padding,padding);
        baseDamageView.setPadding(padding,padding,padding,padding);
        angleView.setPadding(padding,padding,padding,padding);
        bkbView.setPadding(padding,padding,padding,padding);
        kbgView.setPadding(padding,padding,padding,padding);

        if(!odd){
            nameView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            baseDamageView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            angleView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            bkbView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            kbgView.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        //nameView.setBackgroundColor(Color.parseColor(color));

        return tableRow;


    }

    public static ThrowMove getFromJson(JSONObject moveData){
        try {
            MoveType moveType = MoveType.fromValue(moveData.getString("MoveType"));
            String name = StringEscapeUtils.unescapeHtml4(moveData.getString("Name"));
            String hitboxActive = StringEscapeUtils.unescapeHtml4(moveData.getString("HitboxActive"));
            String FAF = StringEscapeUtils.unescapeHtml4(moveData.getString("FirstActionableFrame"));
            String baseDamage = StringEscapeUtils.unescapeHtml4(moveData.getString("BaseDamage"));
            String angle = StringEscapeUtils.unescapeHtml4(moveData.getString("Angle"));
            String bkb = StringEscapeUtils.unescapeHtml4(moveData.getString("BaseKnockBackSetKnockback"));
            String kbg = StringEscapeUtils.unescapeHtml4(moveData.getString("KnockbackGrowth"));
            boolean weightDependent = moveData.getBoolean("IsWeightDependent");
            if(moveData.isNull("HitboxActive"))
                hitboxActive="";
            if(moveData.isNull("FirstActionableFrame"))
                FAF = "";
            if(moveData.isNull("BaseDamage"))
                baseDamage = "";
            if(moveData.isNull("Angle"))
                angle = "";
            if(moveData.isNull("BaseKnockBackSetKnockback"))
                bkb = "";
            if(moveData.isNull("KnockbackGrowth"))
                kbg = "";
            String baseDamageTooltip = null;
            if(moveData.has("BaseDamageTooltip") && !moveData.isNull("BaseDamageTooltip")){
                baseDamageTooltip = moveData.getString("BaseDamageTooltip");
            }
            return new ThrowMove(moveType, name, hitboxActive, FAF, baseDamage, angle, bkb, kbg, baseDamageTooltip);
        }catch(Exception e){
            return null;
        }
    }
}

