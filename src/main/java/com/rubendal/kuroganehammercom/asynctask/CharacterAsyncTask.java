package com.rubendal.kuroganehammercom.asynctask;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.rubendal.kuroganehammercom.CharacterActivity;
import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.adapter.CharacterAdapter;
import com.rubendal.kuroganehammercom.classes.Character;

import org.json.JSONArray;

import java.util.LinkedList;


public class CharacterAsyncTask extends AsyncTask<String, String, LinkedList<Character>> {

    private MainActivity context;
    private ProgressDialog dialog;
    private String title;
    private int x;

    public CharacterAsyncTask(MainActivity context, int x){
        this.context = context;
        this.title = null;
        this.dialog = null;
        this.x = x;
    }

    public CharacterAsyncTask(MainActivity context, int x, String title){
        this(context, x);
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
    protected LinkedList<Character> doInBackground(String... params) {
        try {
            String json = Storage.read("data","characters.json",context);
            LinkedList<Character> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                list.add(Character.fromJson(context, jsonArray.getJSONObject(i)));
            }
            return list;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<Character> s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }
        CharacterAdapter adapter = new CharacterAdapter(context, s, x);
        if(s != null) {
            context.grid.setAdapter(adapter);

            context.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Character character = (Character) parent.getItemAtPosition(position);
                    Intent i = new Intent(context, CharacterActivity.class);
                    i.putExtra("character", character);
                    context.startActivity(i);
                }
            });
        }
    }
}
