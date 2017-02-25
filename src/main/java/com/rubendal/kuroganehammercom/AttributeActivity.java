package com.rubendal.kuroganehammercom;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.classes.Attribute;
import com.rubendal.kuroganehammercom.classes.Character;

import java.util.List;

public class AttributeActivity extends AppCompatActivity {

    private Character character;
    private List<Attribute> attributes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute);

        character = (Character)getIntent().getSerializableExtra("character");

        attributes = (List<Attribute>)getIntent().getSerializableExtra("attributes");

        setTitle(String.format("%s/%s",character.getCharacterTitleName(), "Attributes Ranking"));

        TableLayout layout  = (TableLayout)findViewById(R.id.table);

        layout.setPadding(15,15,15,15);

        TableRow header = (TableRow)layout.findViewById(R.id.header);

        for(int i = 0; i < header.getChildCount(); i++){
            TextView t = (TextView)header.getChildAt(i);
            t.setBackgroundColor(Color.parseColor(character.color));
        }

        int o = 0;

        for(Attribute a : attributes){
            if(a!=null){
                o++;
                layout.addView(a.asRow(this,o % 2 == 1));
            }
        }



    }
}
