package com.rubendal.kuroganehammercom.dbfz.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.dbfz.adapter.CharacterAdapter;
import com.rubendal.kuroganehammercom.dbfz.classes.DBCharacter;
import com.rubendal.kuroganehammercom.dbfz.fragments.DBCharacterMainFragment;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.util.UserPref;

import org.json.JSONArray;

import java.util.Collections;
import java.util.LinkedList;

public class CharacterListAsyncTask extends AsyncTask<String, String, LinkedList<DBCharacter>> {

    private DBCharacterMainFragment context;
    private ProgressDialog dialog;
    private String title;
    private int x;

    public CharacterListAsyncTask(DBCharacterMainFragment context, int x){
        this.context = context;
        this.title = null;
        this.dialog = null;
        this.x = x;
    }

    public CharacterListAsyncTask(DBCharacterMainFragment context, int x, String title){
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
    protected LinkedList<DBCharacter> doInBackground(String... params) {
        try {
            String json = Storage.read("dbfz","characters.json",context.getActivity());
            LinkedList<DBCharacter> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){

                DBCharacter c = DBCharacter.fromJson(context.getActivity(), jsonArray.getJSONObject(i));
                c.hasMoveData = Storage.exists("DB_" + String.valueOf(c.id),"moves.json",context.getActivity());
                list.add(c);
            }
            Collections.sort(list, DBCharacter.getComparator());
            return list;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<DBCharacter> s) {
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
                    DBCharacter character = (DBCharacter) parent.getItemAtPosition(position);
                    CharacterMovesAsyncTask movesAsyncTask = new CharacterMovesAsyncTask(context, character);
                    movesAsyncTask.execute();

                }
            });

            context.grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    DBCharacter character = (DBCharacter) adapterView.getItemAtPosition(i);
                    ImageView fav = (ImageView)view.findViewById(R.id.fav);
                    if(UserPref.checkDBCharacterFavorites(character.name)){
                        UserPref.removeFavoriteDBCharacter(context.getActivity(),character.name);
                        fav.setVisibility(View.INVISIBLE);
                        character.favorite = false;
                    }else{
                        UserPref.addFavoriteDBCharacter(context.getActivity(),character.name);
                        fav.setVisibility(View.VISIBLE);
                        character.favorite = true;
                    }
                    return true;
                }
            });
        }
    }
}