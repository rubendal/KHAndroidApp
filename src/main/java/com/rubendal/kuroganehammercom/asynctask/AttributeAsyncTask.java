package com.rubendal.kuroganehammercom.asynctask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.rubendal.kuroganehammercom.AttributeActivity;
import com.rubendal.kuroganehammercom.CharacterActivity;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.classes.Attribute;
import com.rubendal.kuroganehammercom.classes.Character;

import org.json.JSONArray;

import java.util.LinkedList;


public class AttributeAsyncTask extends AsyncTask<String, String, LinkedList<Attribute>> {

    private CharacterActivity context;
    private ProgressDialog dialog;
    private String title;
    private Character character;

    public AttributeAsyncTask(CharacterActivity context, Character character){
        this.context = context;
        this.character = character;
        this.title = null;
        this.dialog = null;
    }

    public AttributeAsyncTask(CharacterActivity context, Character character, String title){
        this(context, character);
        this.title = title;
        this.dialog = new ProgressDialog(context);
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
            String json = Storage.read(String.valueOf(character.id),"smashAttributes.json",context);
            LinkedList<Attribute> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                list.add(Attribute.getFromJson(context, jsonArray.getJSONObject(i)));
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
        Intent i = new Intent(context, AttributeActivity.class);
        i.putExtra("character", character);
        i.putExtra("attributes", s);
        context.startActivity(i);
    }
}
