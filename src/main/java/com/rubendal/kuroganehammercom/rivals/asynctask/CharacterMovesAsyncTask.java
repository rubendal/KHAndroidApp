package com.rubendal.kuroganehammercom.rivals.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.rivals.classes.RivalsCharacter;
import com.rubendal.kuroganehammercom.rivals.fragments.RivalsAttackListFragment;
import com.rubendal.kuroganehammercom.interfaces.KHFragment;
import com.rubendal.kuroganehammercom.rivals.classes.RivalsMove;
import com.rubendal.kuroganehammercom.util.Storage;

import org.json.JSONArray;

import java.util.Collections;
import java.util.LinkedList;

public class CharacterMovesAsyncTask extends AsyncTask<String, String, LinkedList<RivalsMove>> {

    private KHFragment context;
    private ProgressDialog dialog;
    private String title;
    private RivalsCharacter character;
    //private MoveSort moveSort = MoveSort.NONE;

    private boolean replace = false;

    public CharacterMovesAsyncTask(KHFragment context, RivalsCharacter character){
        this.context = context;
        this.character = character;
        this.title = null;
        this.dialog = null;
    }

    public CharacterMovesAsyncTask(KHFragment context, RivalsCharacter character, boolean replace){
        this.context = context;
        this.character = character;
        this.title = null;
        this.dialog = null;
        this.replace = replace;
    }


    public CharacterMovesAsyncTask(KHFragment context, RivalsCharacter character, boolean replace, String title){
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
    protected LinkedList<RivalsMove> doInBackground(String... params) {
        try {
            String json = Storage.read("RoA_" + String.valueOf(character.id),"moves.json",context.getActivity());
            LinkedList<RivalsMove> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                RivalsMove move = RivalsMove.fromJson(jsonArray.getJSONObject(i));
                list.add(move);

            }




            return list;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<RivalsMove> s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }
        if(s==null){
            Toast.makeText(context.getContext(), "Character data isn't available yet", Toast.LENGTH_LONG).show();
            return;
        }
        if(replace){
            ((MainActivity)context.getActivity()).replaceFragment(RivalsAttackListFragment.newInstance(character,s));
        }else {
            ((MainActivity) context.getActivity()).loadFragment(RivalsAttackListFragment.newInstance(character, s));
        }
    }
}