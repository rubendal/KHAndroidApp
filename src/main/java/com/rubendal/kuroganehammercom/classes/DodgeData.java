package com.rubendal.kuroganehammercom.classes;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
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

    public TableRow asRow(Context context, boolean odd){
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

        return tableRow;


    }
}
