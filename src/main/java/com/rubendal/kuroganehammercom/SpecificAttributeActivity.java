package com.rubendal.kuroganehammercom;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.classes.Character;
import com.rubendal.kuroganehammercom.classes.RowValue;

import java.util.List;

public class SpecificAttributeActivity extends AppCompatActivity {

    private Character character;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        character = (Character)getIntent().getSerializableExtra("character");

        if(character.specificAttribute.isSimple){
            setContentView(R.layout.activity_specific_attribute);

            TableLayout layout = (TableLayout) findViewById(R.id.table);

            layout.setPadding(15, 15, 15, 15);

            TableRow header = (TableRow) layout.findViewById(R.id.header);

            for (int i = 0; i < header.getChildCount(); i++) {
                TextView t = (TextView) header.getChildAt(i);
                t.setBackgroundColor(Color.parseColor(character.color));
            }

            TextView a = (TextView)findViewById(R.id.attribute);
            a.setText(character.specificAttribute.attribute);

            int o = 0;
            for(RowValue r : character.specificAttribute.valueList){
                o++;
                layout.addView(r.asRow(this,o%2==1));
            }

        }else{
            setContentView(R.layout.activity_specific_attribute_complex);

            TableLayout layout = (TableLayout) findViewById(R.id.table);

            layout.setPadding(15, 15, 15, 15);

            List<TableRow> rows = character.specificAttribute.asRows(this, character.color);
            for(TableRow tr : rows){
                if(tr!=null) {
                    layout.addView(tr);
                }
            }
        }

        setTitle(String.format("%s/%s",character.name, character.specificAttribute.attribute));


    }
}
