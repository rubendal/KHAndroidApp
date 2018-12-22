package com.rubendal.kuroganehammercom.ultimate.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.ultimate.adapter.CharacterAdapter;
import com.rubendal.kuroganehammercom.ultimate.classes.SSBUCharacter;
import com.rubendal.kuroganehammercom.ultimate.fragments.SSBUCharacterMainFragment;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.util.UserPref;

import org.json.JSONArray;

import java.util.Collections;
import java.util.LinkedList;

public class CharacterListAsyncTask extends AsyncTask<String, String, LinkedList<SSBUCharacter>> {

    private SSBUCharacterMainFragment context;
    private ProgressDialog dialog;
    private String title;
    private int x;

    public CharacterListAsyncTask(SSBUCharacterMainFragment context, int x){
        this.context = context;
        this.title = null;
        this.dialog = null;
        this.x = x;
    }

    public CharacterListAsyncTask(SSBUCharacterMainFragment context, int x, String title){
        this(context, x);
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
    protected LinkedList<SSBUCharacter> doInBackground(String... params) {
        try {
            String json = Storage.read("ssbu","characters.json",context.getActivity());
            LinkedList<SSBUCharacter> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){

                SSBUCharacter c = SSBUCharacter.fromJson(context.getActivity(), jsonArray.getJSONObject(i));
                c.hasMoveData = Storage.exists("SSBU_" + String.valueOf(c.id),"moves.json",context.getActivity());
                list.add(c);
            }
            Collections.sort(list, SSBUCharacter.getComparator());
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<SSBUCharacter> s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }
        CharacterAdapter adapter = new CharacterAdapter(context.getActivity(), s, x);
        if(s != null) {
            context.grid.setAdapter(adapter);
            if(context.selectedItem != -1){
                context.grid.smoothScrollToPosition(context.selectedItem);
            }
            context.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    context.selectedItem = position;
                    SSBUCharacter character = (SSBUCharacter) parent.getItemAtPosition(position);
                    CharacterDataAsyncTask movesAsyncTask = new CharacterDataAsyncTask(context, character);
                    movesAsyncTask.execute();

                }
            });

            context.grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    SSBUCharacter character = (SSBUCharacter) adapterView.getItemAtPosition(i);
                    ImageView fav = (ImageView)view.findViewById(R.id.fav);
                    if(UserPref.checkUltCharacterFavorites(character.name)){
                        UserPref.removeFavoriteUltCharacter(context.getActivity(),character.name);
                        fav.setVisibility(View.INVISIBLE);
                        character.favorite = false;
                    }else{
                        UserPref.addFavoriteUltCharacter(context.getActivity(),character.name);
                        fav.setVisibility(View.VISIBLE);
                        character.favorite = true;
                    }
                    return true;
                }
            });
        }
    }
}