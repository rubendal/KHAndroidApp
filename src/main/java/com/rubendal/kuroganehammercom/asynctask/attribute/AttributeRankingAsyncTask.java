package com.rubendal.kuroganehammercom.asynctask.attribute;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.classes.Attribute;
import com.rubendal.kuroganehammercom.classes.AttributeRank;
import com.rubendal.kuroganehammercom.classes.AttributeRankWrapper;
import com.rubendal.kuroganehammercom.classes.Character;
import com.rubendal.kuroganehammercom.fragments.AttributeFragment;
import com.rubendal.kuroganehammercom.fragments.AttributeRankingFragment;
import com.rubendal.kuroganehammercom.fragments.KHFragment;
import com.rubendal.kuroganehammercom.util.Storage;

import org.json.JSONArray;

import java.util.Collections;
import java.util.LinkedList;

public class AttributeRankingAsyncTask extends AsyncTask<String, String, AttributeRankWrapper> {

    private KHFragment context;
    private ProgressDialog dialog;
    private String title;
    private Attribute attribute;
    private LinkedList<Character> characters = new LinkedList<>();

    private boolean replace = false;

    public AttributeRankingAsyncTask(KHFragment context, Attribute attribute) {
        this.context = context;
        this.attribute = attribute;
        this.title = null;
        this.dialog = null;
    }

    public AttributeRankingAsyncTask(KHFragment context, Attribute attribute, boolean replace) {
        this.context = context;
        this.attribute = attribute;
        this.replace = replace;
        this.title = null;
        this.dialog = null;
    }

    public AttributeRankingAsyncTask(KHFragment context, Attribute attribute, boolean replace, String title) {
        this(context, attribute, replace);
        this.title = title;
        this.dialog = new ProgressDialog(context.getActivity());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (dialog != null) {
            dialog.setTitle(title);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    private Character getCharacter(int id){
        for(Character character : characters){
            if(character.id == id){
                return character;
            }
        }
        return null;
    }

    @Override
    protected AttributeRankWrapper doInBackground(String... params) {
        try {
            String json = Storage.read("data","characters.json",context.getActivity());
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                characters.add(Character.fromJson(context.getActivity(), jsonArray.getJSONObject(i)));
            }
            json = Storage.read("data", "attributes.json", context.getActivity());
            LinkedList<AttributeRank> list = new LinkedList<>();
            LinkedList<String> types = new LinkedList<>();
            jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                /*Attribute a = Attribute.getFromJson(context.getActivity(), jsonArray.getJSONObject(i));
                if (a.attributeId == attribute.id) {
                    if (!types.contains(a.name)) {
                        types.add(a.name);
                    }
                    if(!list.contains(new AttributeRank(a.id, a.attributeId, a.name, getCharacter(a.owner), a.rank))) {
                        AttributeRank ar = new AttributeRank(a.id, a.attributeId, a.name, getCharacter(a.owner), a.rank);
                        ar.add(a.name, a.value);
                        list.add(ar);
                    }else{
                        for(AttributeRank ar : list){
                            if(ar.equals(new AttributeRank(a.id, a.attributeId, a.name, getCharacter(a.owner), a.rank))){
                                ar.add(a.name, a.value);
                                break;
                            }
                        }
                    }
                }*/
            }
            Collections.sort(list, AttributeRank.getComparator());


            return new AttributeRankWrapper(list, types);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(AttributeRankWrapper s) {
        super.onPostExecute(s);
        if (dialog != null) {
            dialog.dismiss();
        }
        /*
        try {
            if (replace) {
                ((MainActivity) context.getActivity()).replaceFragment(AttributeRankingFragment.newInstance(s));
            } else {
                ((MainActivity) context.getActivity()).loadFragment(AttributeRankingFragment.newInstance(s));
            }
        }catch(Exception e){
            //The user pressed back while fragment asynctask is being executed and makes the app crash, that's why here is a try catch lol
        }
        */
    }
}