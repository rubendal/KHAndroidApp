package com.rubendal.kuroganehammercom.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.fragments.KHFragment;
import com.rubendal.kuroganehammercom.fragments.MovementFragment;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.classes.Character;
import com.rubendal.kuroganehammercom.classes.Movement;

import org.json.JSONArray;

import java.util.LinkedList;


public class MovementAsyncTask extends AsyncTask<String, String, LinkedList<Movement>> {

    private KHFragment context;
    private ProgressDialog dialog;
    private String title;
    private Character character;

    private boolean replace = false;

    public MovementAsyncTask(KHFragment context, Character character){
        this.context = context;
        this.character = character;
        this.title = null;
        this.dialog = null;
    }

    public MovementAsyncTask(KHFragment context, Character character, boolean replace){
        this.context = context;
        this.character = character;
        this.replace = replace;
        this.title = null;
        this.dialog = null;
    }

    public MovementAsyncTask(KHFragment context, Character character, boolean replace, String title){
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
    protected LinkedList<Movement> doInBackground(String... params) {
        try {
            String json = Storage.read(String.valueOf(character.id),"attributes.json",context.getActivity());
            LinkedList<Movement> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                list.add(Movement.fromJson(jsonArray.getJSONObject(i)));
            }
            return list;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<Movement> s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }
        if(replace){
            ((MainActivity)context.getActivity()).replaceFragment(MovementFragment.newInstance(character,s));
        }else{
            ((MainActivity)context.getActivity()).loadFragment(MovementFragment.newInstance(character,s));
        }
    }
}
