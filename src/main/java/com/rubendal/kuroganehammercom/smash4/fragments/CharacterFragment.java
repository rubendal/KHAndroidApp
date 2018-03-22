package com.rubendal.kuroganehammercom.smash4.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.interfaces.KHFragment;
import com.rubendal.kuroganehammercom.smash4.adapter.CharacterOptionsAdapter;
//import com.rubendal.kuroganehammercom.smash4.asynctask.character.AttributeAsyncTask;
import com.rubendal.kuroganehammercom.smash4.asynctask.character.AttributeAsyncTask;
import com.rubendal.kuroganehammercom.smash4.asynctask.character.CharacterDataAsyncTask;
import com.rubendal.kuroganehammercom.smash4.asynctask.character.MoveAsyncTask;
import com.rubendal.kuroganehammercom.smash4.asynctask.character.MovementAsyncTask;
import com.rubendal.kuroganehammercom.smash4.classes.Character;
import com.rubendal.kuroganehammercom.smash4.classes.CharacterOption;
import com.rubendal.kuroganehammercom.smash4.classes.MoveType;

import java.util.LinkedList;
import java.util.List;

public class CharacterFragment extends KHFragment {

    public Character character;

    private CharacterOptionsAdapter adapter;

    private ListView listView;

    private final CharacterFragment ref = this;

    private List<CharacterOption> list = new LinkedList<>();

    public CharacterFragment() {

    }

    @Override
    public void updateData() {

    }

    @Override
    public String getTitle() {
        return character.getCharacterTitleName();
    }

    public static CharacterFragment newInstance(Character character){
        CharacterFragment fragment = new CharacterFragment();
        Bundle args = new Bundle();
        args.putSerializable("character", character);
        fragment.setArguments(args);
        fragment.character = character;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.character = (Character)getArguments().getSerializable("character");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_character, container, false);

        listView = (ListView)view.findViewById(R.id.list);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadData();
    }

    private void loadData(){
        list = new LinkedList<>();
        list.add(new CharacterOption("All data"));
        list.add(new CharacterOption("Attributes"));
        list.add(new CharacterOption("Detailed attributes"));
        list.add(new CharacterOption("All Moves"));
        list.add(new CharacterOption("Ground Moves"));
        list.add(new CharacterOption("Throws"));
        if(character.name.equals("Little Mac")){
            list.add(new CharacterOption("Aerial Moves (Please don't actually use these)"));
        }else {
            list.add(new CharacterOption("Aerial Moves"));
        }
        list.add(new CharacterOption("Special Moves"));


        if(character.hasSpecificAttributes){
            list.add(new CharacterOption(character.specificAttribute.attribute));
        }

        list.add(new CharacterOption("Hitbox Visualization"));


        adapter = new CharacterOptionsAdapter(this.getActivity(), list);

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
                        ((MainActivity)getActivity()).loadFragment(SpecificAttributeFragment.newInstance(character));
                        return;
                    }
                }
                switch (o.text){
                    case "All data":
                        CharacterDataAsyncTask c = new CharacterDataAsyncTask(ref, character);
                        c.execute();
                        break;
                    case "Attributes":
                        MovementAsyncTask at = new MovementAsyncTask(ref, character);
                        at.execute();
                        break;
                    case "Detailed attributes":
                        AttributeAsyncTask a = new AttributeAsyncTask(ref, character);
                        a.execute();
                        break;
                    case "All Moves":
                        m = new MoveAsyncTask(ref, character, MoveType.Any);
                        m.execute();
                        break;
                    case "Throws":
                        m = new MoveAsyncTask(ref, character, MoveType.Throw);
                        m.execute();
                        break;
                    case "Aerial Moves": case "Aerial Moves (Please don't actually use these)":
                        m = new MoveAsyncTask(ref, character, MoveType.Aerial);
                        m.execute();
                        break;
                    case "Special Moves":
                        m = new MoveAsyncTask(ref, character, MoveType.Special);
                        m.execute();
                        break;
                    case "Ground Moves":
                        m = new MoveAsyncTask(ref, character, MoveType.Ground);
                        m.execute();
                        break;
                    case "Hitbox Visualization":
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://struz.github.io/smash-move-viewer/#/v1/%s", character.gameName)));
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
