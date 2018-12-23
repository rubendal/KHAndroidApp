package com.rubendal.kuroganehammercom.smash4.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.util.params.Params;

import org.json.JSONObject;

import java.io.Serializable;

public class Formula implements Serializable {

    public String formula;
    public String equation;
    public String notes;

    public Formula(String formula, String equation, String notes) {
        this.formula = formula;
        this.equation = equation;
        this.notes = notes;
    }

    public static Formula fromJson(JSONObject jsonObject){
        try {
            String formula = jsonObject.getString("formula"), equation = jsonObject.getString("equation"), notes = jsonObject.getString("notes");
            return new Formula(formula,equation,notes);
        }catch(Exception e){

        }
        return null;
    }

    public TableLayout asLayout(Context context){
        LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.table_formula, null);

        TableLayout layout =(TableLayout)v.findViewById(R.id.formula_table);

        TableRow header = (TableRow)layout.findViewById(R.id.header);
        TableRow formulaRow = (TableRow)layout.findViewById(R.id.row_formula);
        TableRow notesRow = (TableRow)layout.findViewById(R.id.row_notes);

        int padding = Params.PADDING;

        for (int i = 0; i < header.getChildCount(); i++) {
            TextView t = (TextView) header.getChildAt(i);
            t.setPadding(padding,padding,padding,padding);
        }
        for (int i = 0; i < formulaRow.getChildCount(); i++) {
            TextView t = (TextView) formulaRow.getChildAt(i);
            t.setPadding(padding,padding,padding,padding);
        }
        for (int i = 0; i < notesRow.getChildCount(); i++) {
            TextView t = (TextView) notesRow.getChildAt(i);
            t.setPadding(padding,padding,padding,padding);
        }

        TextView formulaView = (TextView)layout.findViewById(R.id.formula);
        TextView equationView = (TextView)layout.findViewById(R.id.equation);
        TextView notesView = (TextView)layout.findViewById(R.id.notes);

        formulaView.setText(formula);
        equationView.setText(equation);
        notesView.setText(notes);

        layout.setPadding(0,0,0,20);

        return layout;
    }
}
