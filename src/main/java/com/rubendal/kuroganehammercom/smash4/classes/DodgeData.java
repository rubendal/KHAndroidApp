package com.rubendal.kuroganehammercom.smash4.classes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.util.Tooltip;
import com.rubendal.kuroganehammercom.util.params.Params;

public class DodgeData {

    public String attribute, intangibility, faf;

    public DodgeData(String attribute, String intangibility, String faf){
        this.attribute = attribute;
        this.intangibility = intangibility;
        this.faf = faf;
    }

    public DodgeData(Move move){
        this.attribute = move.name;
        this.intangibility = move.hitboxActive;
        this.faf = move.FAF;
    }

    public TableRow asRow(Context context, boolean odd, String color){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.dodge_row, null);
        TableRow tableRow = (TableRow)v.findViewById(R.id.row);

        TextView attributeView = (TextView)tableRow.findViewById(R.id.attribute);
        TextView intangibilityView = (TextView)tableRow.findViewById(R.id.intangibility);
        TextView fafView = (TextView)tableRow.findViewById(R.id.faf);

        attributeView.setText(attribute);
        intangibilityView.setText(intangibility);
        fafView.setText(faf);

        int padding = Params.PADDING;
        attributeView.setPadding(padding,padding,padding,padding);
        intangibilityView.setPadding(padding,padding,padding,padding);
        fafView.setPadding(padding,padding,padding,padding);

        if(!odd){
            attributeView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            intangibilityView.setBackgroundColor(Color.parseColor("#D9D9D9"));
            fafView.setBackgroundColor(Color.parseColor("#D9D9D9"));
        }

        //attributeView.setBackgroundColor(Color.parseColor(color));

        return tableRow;


    }

    public LinearLayout asSection(Context context, String color){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.attack_dodge_data_mobile, null);

        LinearLayout layout = (LinearLayout)v.findViewById(R.id.attack_data_container);

        TextView nameView = (TextView)layout.findViewById(R.id.name);
        TextView hitboxActiveView = (TextView)layout.findViewById(R.id.hitboxActive);
        TextView fafView = (TextView)layout.findViewById(R.id.faf);

        nameView.setText(attribute);
        hitboxActiveView.setText(intangibility);
        fafView.setText(faf);


        int padding = Params.PADDING;
        nameView.setPadding(padding,padding,padding,padding);
        hitboxActiveView.setPadding(padding,padding,padding,padding);
        fafView.setPadding(padding,padding,padding,padding);

        nameView.setBackgroundColor(Color.parseColor(Params.MOVENAME_TRANSPARENCY + color));

        TableRow tableRow = (TableRow)layout.findViewById(R.id.header1);
        tableRow.setBackgroundColor(Color.parseColor(Params.MOVEHEADER_TRANSPARENCY + color));

        return layout;
    }
}
