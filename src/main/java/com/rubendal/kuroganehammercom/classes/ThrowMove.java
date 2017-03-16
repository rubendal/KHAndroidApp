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
import org.json.JSONArray;
import org.json.JSONObject;


public class ThrowMove extends Move {

    public boolean weightDependent;

    public ThrowMove(int id, MoveType moveType, String name, String hitboxActive, String FAF, String baseDamage, String angle, String bkb, String kbg, boolean weightDependent) {
        super(id, moveType, name, hitboxActive, FAF, baseDamage, angle, bkb, kbg);
        this.weightDependent = weightDependent;
    }

    public TableRow asRow(Context context, boolean odd){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.attack_list_row_throw, null);
        TableRow tableRow = (TableRow)v.findViewById(R.id.row);

        TextView nameView = (TextView)tableRow.findViewById(R.id.name);
        TextView weightDependentView = (TextView)tableRow.findViewById(R.id.weightDependent);
        TextView baseDamageView = (TextView)tableRow.findViewById(R.id.damage);
        TextView angleView = (TextView)tableRow.findViewById(R.id.angle);
        TextView bkbView = (TextView)tableRow.findViewById(R.id.bkb);
        TextView kbgView = (TextView)tableRow.findViewById(R.id.kbg);

        nameView.setText(name);
        weightDependentView.setText(weightDependent ? "Yes" : "No");
        baseDamageView.setText(baseDamage);
        angleView.setText(angle);
        bkbView.setText(bkb);
        kbgView.setText(kbg);

        int padding = Params.PADDING;
        nameView.setPadding(padding,padding,padding,padding);
        weightDependentView.setPadding(padding,padding,padding,padding);
        baseDamageView.setPadding(padding,padding,padding,padding);
        angleView.setPadding(padding,padding,padding,padding);
        bkbView.setPadding(padding,padding,padding,padding);
        kbgView.setPadding(padding,padding,padding,padding);

        if(!odd){
            nameView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            weightDependentView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            baseDamageView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            angleView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            bkbView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            kbgView.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        return tableRow;


    }

    public static ThrowMove getFromJson(JSONObject moveData, JSONArray throwsData){
        try {
            int id = moveData.getInt("id");
            MoveType moveType = MoveType.fromValue(moveData.getInt("type"));
            String name = StringEscapeUtils.unescapeHtml4(moveData.getString("name"));
            String hitboxActive = StringEscapeUtils.unescapeHtml4(moveData.getString("hitboxActive"));
            String FAF = StringEscapeUtils.unescapeHtml4(moveData.getString("firstActionableFrame"));
            String baseDamage = StringEscapeUtils.unescapeHtml4(moveData.getString("baseDamage"));
            String angle = StringEscapeUtils.unescapeHtml4(moveData.getString("angle"));
            String bkb = StringEscapeUtils.unescapeHtml4(moveData.getString("baseKnockBackSetKnockback"));
            String kbg = StringEscapeUtils.unescapeHtml4(moveData.getString("knockbackGrowth"));
            boolean weightDependent = false;
            for(int i =0;i<throwsData.length();i++){
                if(id == throwsData.getJSONObject(i).getInt("moveId")){
                    weightDependent = throwsData.getJSONObject(i).getBoolean("weightDependent");
                }
            }
            return new ThrowMove(id, moveType, name, hitboxActive, FAF, baseDamage, angle, bkb, kbg, weightDependent);
        }catch(Exception e){
            return null;
        }
    }
}
