package com.rubendal.kuroganehammercom.asynctask.character;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.fragments.AttackListFragment;
import com.rubendal.kuroganehammercom.fragments.KHFragment;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.classes.AerialMove;
import com.rubendal.kuroganehammercom.classes.Character;
import com.rubendal.kuroganehammercom.classes.Move;
import com.rubendal.kuroganehammercom.classes.MoveType;
import com.rubendal.kuroganehammercom.classes.ThrowMove;

import org.json.JSONArray;

import java.util.LinkedList;

public class MoveAsyncTask extends AsyncTask<String, String, LinkedList<Move>> {

    private KHFragment context;
    private ProgressDialog dialog;
    private String title;
    private MoveType type;
    private Character character;

    private boolean replace = false;

    public MoveAsyncTask(KHFragment context, Character character, MoveType type){
        this.context = context;
        this.type = type;
        this.character = character;
        this.title = null;
        this.dialog = null;
    }

    public MoveAsyncTask(KHFragment context, Character character, MoveType type, boolean replace){
        this.context = context;
        this.type = type;
        this.character = character;
        this.title = null;
        this.dialog = null;
        this.replace = replace;
    }

    public MoveAsyncTask(KHFragment context, Character character, MoveType type, boolean replace, String title){
        this(context, character, type, replace);
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
    protected LinkedList<Move> doInBackground(String... params) {
        try {
            String json = Storage.read(String.valueOf(character.id),"moves.json",context.getActivity());
            LinkedList<Move> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            JSONArray t = null;
            if(type == MoveType.Throw || type == MoveType.Any){
                t = new JSONArray(getThrow());
            }
            for(int i=0;i<jsonArray.length();i++){
                Move move = Move.getFromJson(jsonArray.getJSONObject(i));
                if(type == MoveType.Any){
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
                            list.add(ThrowMove.getFromJson(jsonArray.getJSONObject(i), t));
                            break;
                    }
                }else{
                    if(move.moveType == type) {
                        switch (type) {
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
                                list.add(ThrowMove.getFromJson(jsonArray.getJSONObject(i), t));
                                break;
                        }
                    }
                }

            }
            return list;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<Move> s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }
        if(replace){
            ((MainActivity)context.getActivity()).replaceFragment(AttackListFragment.newInstance(character,type,s));
        }else {
            ((MainActivity) context.getActivity()).loadFragment(AttackListFragment.newInstance(character, type, s));
        }
    }
}
