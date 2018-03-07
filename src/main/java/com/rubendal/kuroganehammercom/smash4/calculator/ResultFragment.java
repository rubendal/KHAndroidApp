package com.rubendal.kuroganehammercom.smash4.calculator;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.smash4.calculator.classes.KBResponse;
import com.rubendal.kuroganehammercom.util.params.Params;

public class ResultFragment extends Fragment {

    private KBResponse response;

    public ResultFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);

        reloadView(view);

        return view;
    }

    public static ResultFragment newInstance(KBResponse response){
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putSerializable("kbResponse", response);
        fragment.setArguments(args);
        fragment.response = response;
        return fragment;
    }

    public void setResponse(KBResponse response){
        this.response = response;
    }

    public void reloadView(View view){
        if(view!=null) {
            int o=0;
            TableLayout layout = (TableLayout)view.findViewById(R.id.table);
            o++;
            layout.addView(buildRow("Damage", response.damage, o % 2 == 1));
            if(response.attacker_hitlag != null) {
                o++;
                layout.addView(buildRow("Attacker Hitlag", String.valueOf(response.attacker_hitlag) + " frames", o % 2 == 1));
            }
            if(response.target_hitlag != null) {
                o++;
                layout.addView(buildRow("Target Hitlag", String.valueOf(response.target_hitlag) + " frames", o % 2 == 1));
            }
            if(response.effect_time != null) {
                o++;
                layout.addView(buildRow("Effect time", String.valueOf(response.effect_time) + " frames", o % 2 == 1));
            }
            if(response.rage != 1){
                o++;
                layout.addView(buildRow("Rage", response.rage, o % 2 == 1));
            }
            o++;
            layout.addView(buildRow("KB", response.kb, o % 2 == 1));
            o++;
            layout.addView(buildRow("Launch angle", response.launch_angle, o % 2 == 1));
            o++;
            layout.addView(buildRow("Hitstun", String.valueOf(response.hitstun) + " frames", o % 2 == 1));
            o++;
            layout.addView(buildRow("FAF", "Frame " +  String.valueOf(response.hitstun_faf), o % 2 == 1));
            if(response.airdodge_hitstun_cancel != null){
                o++;
                layout.addView(buildRow("Airdodge hitstun cancel", "Frame " +  String.valueOf(response.airdodge_hitstun_cancel), o % 2 == 1));
            }
            if(response.aerial_hitstun_cancel != null){
                o++;
                layout.addView(buildRow("Aerial hitstun cancel", "Frame " +  String.valueOf(response.aerial_hitstun_cancel), o % 2 == 1));
            }
            if(response.reeling) {
                o++;
                layout.addView(buildRow("Reeling", "30%", o % 2 == 1));
            }
            if(response.lsi != 1) {
                o++;
                layout.addView(buildRow("LSI", response.lsi, o % 2 == 1));
            }
            o++;
            layout.addView(buildRow("Horizontal Launch Speed", response.horizontal_launch_speed, o % 2 == 1));
            if(response.gravity_boost != 0) {
                o++;
                layout.addView(buildRow("Gravity boost", response.gravity_boost, o % 2 == 1));
            }
            o++;
            layout.addView(buildRow("Vertical Launch Speed", response.vertical_launch_speed, o % 2 == 1));
            if(response.hit_advantage != null){
                o++;
                layout.addView(buildRow("Hit advantage", String.valueOf(response.hit_advantage) + " frames", o % 2 == 1));
            }
            o++;
            layout.addView(buildRow("Can jab lock", response.can_jab_lock ? "Yes" : "No", o % 2 == 1));
            if(response.unblockable){
                o++;
                layout.addView(buildRow("Unblockable", "Yes", o % 2 == 1));
            }else{
                if(response.shield_hitlag != null){
                    o++;
                    layout.addView(buildRow("Shield Hitlag", String.valueOf(response.shield_hitlag) + " frames", o % 2 == 1));
                }
                if(response.shield_stun != null){
                    o++;
                    layout.addView(buildRow("Shield Stun", String.valueOf(response.shield_stun) + " frames", o % 2 == 1));
                }
                if(response.shield_advantage != null){
                    o++;
                    layout.addView(buildRow("Shield Advantage", String.valueOf(response.shield_advantage) + " frames", o % 2 == 1));
                }
            }
        }
    }

    public TableRow buildRow(String parameter, Object value, boolean odd){
        LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.result_table_row, null);
        TableRow tableRow = (TableRow) v.findViewById(R.id.row);

        TextView parameterView = (TextView) tableRow.findViewById(R.id.parameter);
        TextView valueView = (TextView) tableRow.findViewById(R.id.value);

        parameterView.setText(parameter);
        valueView.setText(String.valueOf(value));

        int padding = Params.PADDING;
        parameterView.setPadding(padding,padding,padding,padding);
        valueView.setPadding(padding,padding,padding,padding);

        if(!odd){
            parameterView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            valueView.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        return tableRow;
    }
}
