package com.rubendal.kuroganehammercom.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.adapter.CharacterOptionsAdapter;
import com.rubendal.kuroganehammercom.asynctask.AttributeAsyncTask;
import com.rubendal.kuroganehammercom.asynctask.CharacterDataAsyncTask;
import com.rubendal.kuroganehammercom.asynctask.MoveAsyncTask;
import com.rubendal.kuroganehammercom.asynctask.MovementAsyncTask;
import com.rubendal.kuroganehammercom.classes.Character;
import com.rubendal.kuroganehammercom.classes.CharacterOption;
import com.rubendal.kuroganehammercom.classes.MoveType;

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
