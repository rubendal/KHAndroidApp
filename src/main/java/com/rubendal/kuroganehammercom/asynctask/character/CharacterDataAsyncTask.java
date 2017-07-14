package com.rubendal.kuroganehammercom.asynctask.character;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.rubendal.kuroganehammercom.classes.Attribute;
import com.rubendal.kuroganehammercom.classes.AttributeList;
import com.rubendal.kuroganehammercom.classes.AttributeRank;
import com.rubendal.kuroganehammercom.fragments.CharacterDataFragment;
import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.classes.CharacterData;
import com.rubendal.kuroganehammercom.classes.Movement;
import com.rubendal.kuroganehammercom.fragments.KHFragment;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.classes.AerialMove;
import com.rubendal.kuroganehammercom.classes.Character;
import com.rubendal.kuroganehammercom.classes.Move;
import com.rubendal.kuroganehammercom.classes.ThrowMove;

import org.json.JSONArray;

import java.util.LinkedList;

public class CharacterDataAsyncTask extends AsyncTask<String, String, CharacterData> {

    private KHFragment context;
    private ProgressDialog dialog;
    private String title;
    private Character character;

    private boolean replace = false;

    public CharacterDataAsyncTask(KHFragment context, Character character){
        this.context = context;
        this.character = character;
        this.title = null;
        this.dialog = null;
    }

    public CharacterDataAsyncTask(KHFragment context, Character character, boolean replace){
        this.context = context;
        this.character = character;
        this.title = null;
        this.dialog = null;
        this.replace = replace;
    }

    public CharacterDataAsyncTask(KHFragment context, Character character, boolean replace, String title){
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

    private String getThrow(){
        try {
            String json = Storage.read(String.valueOf(character.id),"throws.json",context.getActivity());
            return json;
        }
        catch(Exception e){

        }
        return null;
    }

    @Override
    protected CharacterData doInBackground(String... params) {
        try {
            String json = Storage.read(String.valueOf(character.id),"moves.json",context.getActivity());
            LinkedList<Move> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            JSONArray t = new JSONArray(getThrow());
            for(int i=0;i<jsonArray.length();i++){
                Move move = Move.getFromJson(jsonArray.getJSONObject(i));
                switch (move.moveType) {
                    case Aerial:
                        list.add(AerialMove.getFromJson(jsonArray.getJSONObject(i)));
                        break;
                    case Ground:
                        list.add(Move.getFromJson(jsonArray.getJSONObject(i)));
                        break;
                    case Special:
                        list.add(Move.getFromJson(jsonArray.getJSONObject(i)));
                        break;
                    case Throw:
                        list.add(ThrowMove.getFromJson(jsonArray.getJSONObject(i)));
                        break;
                }
            }

            json = Storage.read(String.valueOf(character.id),"attributes.json",context.getActivity());
            LinkedList<Movement> movements = new LinkedList<>();
            jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                movements.add(Movement.fromJson(jsonArray.getJSONObject(i)));
            }

            LinkedList<AttributeList> attributes = new LinkedList<>();
            json = Storage.read(String.valueOf(character.id), "smashAttributes.json", context.getActivity());
            jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                AttributeList a = AttributeList.getFromJson(context.getActivity(), jsonArray.getJSONObject(i));
                attributes.add(a);
            }

            return new CharacterData(character, movements, list, attributes);
        } catch (Exception e) {
            Log.d("nedfvnjf",e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(CharacterData s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }
        if(replace){
            ((MainActivity)context.getActivity()).replaceFragment(CharacterDataFragment.newInstance(s));
        }else {
            ((MainActivity) context.getActivity()).loadFragment(CharacterDataFragment.newInstance(s));
        }
    }
}
