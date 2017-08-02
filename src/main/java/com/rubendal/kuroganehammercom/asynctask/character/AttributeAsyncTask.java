package com.rubendal.kuroganehammercom.asynctask.character;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.fragments.AttributeFragment;
import com.rubendal.kuroganehammercom.fragments.KHFragment;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.classes.Attribute;
import com.rubendal.kuroganehammercom.classes.Character;

import org.json.JSONArray;

import java.util.LinkedList;


public class AttributeAsyncTask extends AsyncTask<String, String, LinkedList<Attribute>> {

    private KHFragment context;
    private ProgressDialog dialog;
    private String title;
    private Character character;

    private boolean replace = false;

    public AttributeAsyncTask(KHFragment context, Character character){
        this.context = context;
        this.character = character;
        this.title = null;
        this.dialog = null;
    }

    public AttributeAsyncTask(KHFragment context, Character character, boolean replace){
        this.context = context;
        this.character = character;
        this.replace = replace;
        this.title = null;
        this.dialog = null;
    }

    public AttributeAsyncTask(KHFragment context, Character character, boolean replace, String title){
        this(context, character, replace);
        this.title = title;
        this.dialog = new ProgressDialog(context.getActivity());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(dialog!=null) {
            dialog.setTitle(title);
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    @Override
    protected LinkedList<Attribute> doInBackground(String... params) {
        try {
            String json = Storage.read(String.valueOf(character.id),"attributes.json",context.getActivity());
            LinkedList<Attribute> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                list.add(Attribute.getFromJson(context.getActivity(), jsonArray.getJSONObject(i)));
            }
            return list;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<Attribute> s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }
        if(replace){
            ((MainActivity) context.getActivity()).replaceFragment(AttributeFragment.newInstance(character, s));
        }else {
            ((MainActivity) context.getActivity()).loadFragment(AttributeFragment.newInstance(character, s));
        }
    }
}
