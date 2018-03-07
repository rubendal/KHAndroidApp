package com.rubendal.kuroganehammercom.dbfz.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.dbfz.classes.DBCharacter;
import com.rubendal.kuroganehammercom.dbfz.classes.DBMove;
import com.rubendal.kuroganehammercom.dbfz.fragments.DBAttackListFragment;
import com.rubendal.kuroganehammercom.interfaces.KHFragment;
import com.rubendal.kuroganehammercom.util.Storage;

import org.json.JSONArray;

import java.util.Collections;
import java.util.LinkedList;

public class CharacterMovesAsyncTask extends AsyncTask<String, String, LinkedList<DBMove>> {

    private KHFragment context;
    private ProgressDialog dialog;
    private String title;
    private DBCharacter character;
    //private MoveSort moveSort = MoveSort.NONE;

    private boolean replace = false;

    public CharacterMovesAsyncTask(KHFragment context, DBCharacter character){
        this.context = context;
        this.character = character;
        this.title = null;
        this.dialog = null;
    }

    public CharacterMovesAsyncTask(KHFragment context, DBCharacter character, boolean replace){
        this.context = context;
        this.character = character;
        this.title = null;
        this.dialog = null;
        this.replace = replace;
    }


    public CharacterMovesAsyncTask(KHFragment context, DBCharacter character, boolean replace, String title){
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
    protected LinkedList<DBMove> doInBackground(String... params) {
        try {
            String json = Storage.read("DB_" + String.valueOf(character.id),"moves.json",context.getActivity());
            LinkedList<DBMove> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                DBMove move = DBMove.fromJson(jsonArray.getJSONObject(i));
                list.add(move);

            }




            return list;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<DBMove> s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }
        if(s==null){
            Toast.makeText(context.getContext(), "Character data isn't available yet", Toast.LENGTH_LONG).show();
            return;
        }
        if(replace){
            ((MainActivity)context.getActivity()).replaceFragment(DBAttackListFragment.newInstance(character,s));
        }else {
            ((MainActivity) context.getActivity()).loadFragment(DBAttackListFragment.newInstance(character, s));
        }
    }
}