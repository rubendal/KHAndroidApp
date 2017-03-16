package com.rubendal.kuroganehammercom.classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.util.params.Params;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

import java.io.Serializable;

public class Move implements Serializable {

    public int id;
    public MoveType moveType;
    public String name;
    public String hitboxActive;
    public String FAF;
    public String baseDamage;
    public String angle;
    public String bkb;
    public String kbg;

    public Move(int id, MoveType moveType, String name, String hitboxActive, String FAF, String baseDamage, String angle, String bkb, String kbg) {
        this.id = id;
        this.moveType = moveType;
        this.name = name;
        this.hitboxActive = hitboxActive;
        this.FAF = FAF;
        this.baseDamage = baseDamage;
        this.angle = angle;
        this.bkb = bkb;
        this.kbg = kbg;
    }

    @Override
    public String toString() {
        return name;
    }

    public TableRow asRow(Context context, boolean odd){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.attack_list_row, null);
        TableRow tableRow = (TableRow)v.findViewById(R.id.row);

        TextView nameView = (TextView)tableRow.findViewById(R.id.name);
        TextView hitboxActiveView = (TextView)tableRow.findViewById(R.id.hitboxActive);
        TextView fafView = (TextView)tableRow.findViewById(R.id.faf);
        TextView baseDamageView = (TextView)tableRow.findViewById(R.id.damage);
        TextView angleView = (TextView)tableRow.findViewById(R.id.angle);
        TextView bkbView = (TextView)tableRow.findViewById(R.id.bkb);
        TextView kbgView = (TextView)tableRow.findViewById(R.id.kbg);

        nameView.setText(name);
        hitboxActiveView.setText(hitboxActive);
        fafView.setText(FAF);
        baseDamageView.setText(baseDamage);
        angleView.setText(angle);
        bkbView.setText(bkb);
        kbgView.setText(kbg);

        int padding = Params.PADDING;
        nameView.setPadding(padding,padding,padding,padding);
        hitboxActiveView.setPadding(padding,padding,padding,padding);
        fafView.setPadding(padding,padding,padding,padding);
        baseDamageView.setPadding(padding,padding,padding,padding);
        angleView.setPadding(padding,padding,padding,padding);
        bkbView.setPadding(padding,padding,padding,padding);
        kbgView.setPadding(padding,padding,padding,padding);

        if(!odd){
            nameView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            hitboxActiveView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            fafView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            baseDamageView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            angleView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            bkbView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            kbgView.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        return tableRow;


    }

    public static Move getFromJson(JSONObject jsonObject){
        try {
            int id = jsonObject.getInt("id");
            MoveType moveType = MoveType.fromValue(jsonObject.getInt("type"));
            String name = StringEscapeUtils.unescapeHtml4(jsonObject.getString("name"));
            String hitboxActive = StringEscapeUtils.unescapeHtml4(jsonObject.getString("hitboxActive"));
            String FAF = StringEscapeUtils.unescapeHtml4(jsonObject.getString("firstActionableFrame"));
            String baseDamage = StringEscapeUtils.unescapeHtml4(jsonObject.getString("baseDamage"));
            String angle = StringEscapeUtils.unescapeHtml4(jsonObject.getString("angle"));
            String bkb = StringEscapeUtils.unescapeHtml4(jsonObject.getString("baseKnockBackSetKnockback"));
            String kbg = StringEscapeUtils.unescapeHtml4(jsonObject.getString("knockbackGrowth"));
            return new Move(id, moveType, name, hitboxActive, FAF, baseDamage, angle, bkb, kbg);
        }catch(Exception e){
            return null;
        }
    }
}
