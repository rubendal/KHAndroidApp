package com.rubendal.kuroganehammercom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rubendal.kuroganehammercom.adapter.CharacterOptionsAdapter;
import com.rubendal.kuroganehammercom.asynctask.AttributeAsyncTask;
import com.rubendal.kuroganehammercom.asynctask.MoveAsyncTask;
import com.rubendal.kuroganehammercom.asynctask.MovementAsyncTask;
import com.rubendal.kuroganehammercom.classes.Character;
import com.rubendal.kuroganehammercom.classes.CharacterOption;
import com.rubendal.kuroganehammercom.classes.MoveType;

import java.util.LinkedList;
import java.util.List;

public class CharacterActivity extends AppCompatActivity {

    private Character character;

    private CharacterOptionsAdapter adapter;

    private ListView listView;

    private final CharacterActivity ref = this;

    private List<CharacterOption> list = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        character = (Character)getIntent().getSerializableExtra("character");

        resetView();

    }

    private void resetView(){
        setTitle(character.getCharacterTitleName());

        list = new LinkedList<>();
        list.add(new CharacterOption("Attributes"));
        list.add(new CharacterOption("Attribute ranking"));
        list.add(new CharacterOption("All Attacks"));
        list.add(new CharacterOption("Ground Attacks"));
        list.add(new CharacterOption("Throws"));
        if(character.name.equals("Little Mac")){
            list.add(new CharacterOption("Aerial Attacks (Please don't actually use these)"));
        }else {
            list.add(new CharacterOption("Aerial Attacks"));
        }
        list.add(new CharacterOption("Specials"));

        if(character.hasSpecificAttributes){
            list.add(new CharacterOption(character.specificAttribute.attribute));
        }


        adapter = new CharacterOptionsAdapter(this, list);

        listView = (ListView)findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MoveAsyncTask m = null;
                CharacterOption o = adapter.getItem(position);
                if(o.isImage){
                    return;
                }
                if(character.hasSpecificAttributes) {
                    if (o.text.equals(character.specificAttribute.attribute)){
                        Intent i = new Intent(ref, SpecificAttributeActivity.class);
                        i.putExtra("character", character);
                        startActivity(i);
                        return;
                    }
                }
                switch (o.text){
                    case "Attributes":
                        MovementAsyncTask at = new MovementAsyncTask(ref, character);
                        at.execute();
                        break;
                    case "Attribute ranking":
                        AttributeAsyncTask a = new AttributeAsyncTask(ref, character);
                        a.execute();
                        break;
                    case "All Attacks":
                        m = new MoveAsyncTask(ref, character, MoveType.Any);
                        m.execute();
                        break;
                    case "Throws":
                        m = new MoveAsyncTask(ref, character, MoveType.Throw);
                        m.execute();
                        break;
                    case "Aerial Attacks": case "Aerial Attacks (Please don't actually use these)":
                        m = new MoveAsyncTask(ref, character, MoveType.Aerial);
                        m.execute();
                        break;
                    case "Specials":
                        m = new MoveAsyncTask(ref, character, MoveType.Special);
                        m.execute();
                        break;
                    case "Ground Attacks":
                        m = new MoveAsyncTask(ref, character, MoveType.Ground);
                        m.execute();
                        break;
                }
            }
        });
    }

}
