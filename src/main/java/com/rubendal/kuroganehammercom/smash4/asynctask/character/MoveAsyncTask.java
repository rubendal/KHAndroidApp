package com.rubendal.kuroganehammercom.smash4.asynctask.character;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.smash4.classes.MoveSort;
import com.rubendal.kuroganehammercom.smash4.fragments.AttackListFragment;
import com.rubendal.kuroganehammercom.interfaces.KHFragment;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.smash4.classes.AerialMove;
import com.rubendal.kuroganehammercom.smash4.classes.Character;
import com.rubendal.kuroganehammercom.smash4.classes.Move;
import com.rubendal.kuroganehammercom.smash4.classes.MoveType;
import com.rubendal.kuroganehammercom.smash4.classes.ThrowMove;

import org.json.JSONArray;

import java.util.Collections;
import java.util.LinkedList;

public class MoveAsyncTask extends AsyncTask<String, String, LinkedList<Move>> {

    private KHFragment context;
    private ProgressDialog dialog;
    private String title;
    private MoveType type;
    private Character character;
    private LinkedList<Move> evasion;
    private MoveSort moveSort = MoveSort.NONE;

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

    public MoveAsyncTask(KHFragment context, Character character, MoveType type, boolean replace, MoveSort moveSort){
        this.context = context;
        this.type = type;
        this.character = character;
        this.title = null;
        this.dialog = null;
        this.replace = replace;
        this.moveSort = moveSort;
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


    /*private void getAttributes(){
        try {
            String json = Storage.read(String.valueOf(character.id), "attributes.json", context.getActivity());
            attributes = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                Attribute a = Attribute.getFromJson(context.getActivity(), jsonArray.getJSONObject(i));
                if(a.name.contains("AirDodge") || a.name.contains("Rolls") || a.name.contains("Spotdodge")) {
                    attributes.add(a);
                }
            }
        }catch(Exception e){

        }
    }*/

    //Used for now until API removes MoveType:"ground" for rolls/spotdodge/airdodge
    private boolean isEvasion(String name){
        return name.equals("Forward Roll") || name.equals("Back Roll") || name.equals("Spotdodge") || name.equals("Airdodge");
    }

    @Override
    protected LinkedList<Move> doInBackground(String... params) {
        try {
            String json = Storage.read(String.valueOf(character.id),"moves.json",context.getActivity());
            LinkedList<Move> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            evasion = new LinkedList<>();
            for(int i=0;i<jsonArray.length();i++){
                Move move = Move.getFromJson(jsonArray.getJSONObject(i));
                if(type == MoveType.Any){
                    switch (move.moveType) {
                        case Aerial:
                            list.add(AerialMove.getFromJson(jsonArray.getJSONObject(i)));
                            break;
                        case Ground:
                            if(!isEvasion(move.name)) {
                                list.add(move);
                            }else{
                                evasion.add(move);
                            }
                            break;
                        case Special:
                            list.add(move);
                            break;
                        case Throw:
                            list.add(ThrowMove.getFromJson(jsonArray.getJSONObject(i)));
                            break;
                        case Evasion:
                            evasion.add(move);
                            break;
                    }
                    //getAttributes();
                }else{
                    if(move.moveType == type) {
                        switch (type) {
                            case Aerial:
                                list.add(AerialMove.getFromJson(jsonArray.getJSONObject(i)));
                                break;
                            case Ground:
                                if(!isEvasion(move.name)) {
                                    list.add(move);
                                }else{
                                    evasion.add(move);
                                }
                                break;
                            case Special:
                                list.add(move);
                                break;
                            case Throw:
                                list.add(ThrowMove.getFromJson(jsonArray.getJSONObject(i)));
                                break;
                        }
                    }else if(move.moveType == MoveType.Evasion && type == MoveType.Ground){
                        evasion.add(move);
                    }
                }

            }

            if(moveSort != MoveSort.NONE){

                switch (moveSort){
                    case HITBOX_ACTIVE:
                        Collections.sort(list, MoveSort.HitboxActiveComparator());
                        break;
                    case FAF:
                        Collections.sort(list, MoveSort.FAFComparator());
                        break;
                    case DAMAGE:
                        Collections.sort(list, MoveSort.DamageComparator());
                        break;
                    case ANGLE:
                        Collections.sort(list, MoveSort.AngleComparator());
                        break;
                    case BKB:
                        Collections.sort(list, MoveSort.BKBComparator());
                        break;
                    case KBG:
                        Collections.sort(list, MoveSort.KBGComparator());
                        break;
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
        if(s==null){
            Toast.makeText(context.getContext(), "An error ocurred while reading move data", Toast.LENGTH_LONG).show();
            return;
        }
        if(replace){
            ((MainActivity)context.getActivity()).replaceFragment(AttackListFragment.newInstance(character,type,s, evasion, moveSort));
        }else {
            ((MainActivity) context.getActivity()).loadFragment(AttackListFragment.newInstance(character, type, s, evasion, moveSort));
        }
    }
}
