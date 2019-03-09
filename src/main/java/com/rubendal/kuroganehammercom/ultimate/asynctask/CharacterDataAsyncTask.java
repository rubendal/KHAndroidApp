package com.rubendal.kuroganehammercom.ultimate.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.rubendal.kuroganehammercom.smash4.classes.MoveSort;
import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.smash4.classes.Movement;
import com.rubendal.kuroganehammercom.interfaces.KHFragment;
import com.rubendal.kuroganehammercom.ultimate.classes.SSBUCharacter;
import com.rubendal.kuroganehammercom.ultimate.classes.SSBUCharacterData;
import com.rubendal.kuroganehammercom.ultimate.fragments.SSBUCharacterDataFragment;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.smash4.classes.AerialMove;
import com.rubendal.kuroganehammercom.smash4.classes.Move;
import com.rubendal.kuroganehammercom.ultimate.classes.ThrowMove;

import org.json.JSONArray;

import java.util.Collections;
import java.util.LinkedList;

public class CharacterDataAsyncTask extends AsyncTask<String, String, SSBUCharacterData> {

    private KHFragment context;
    private ProgressDialog dialog;
    private String title;
    private SSBUCharacter character;
    private MoveSort moveSort = MoveSort.NONE;

    private boolean replace = false;

    public CharacterDataAsyncTask(KHFragment context, SSBUCharacter character){
        this.context = context;
        this.character = character;
        this.title = null;
        this.dialog = null;
    }

    public CharacterDataAsyncTask(KHFragment context, SSBUCharacter character, boolean replace){
        this.context = context;
        this.character = character;
        this.title = null;
        this.dialog = null;
        this.replace = replace;
    }

    public CharacterDataAsyncTask(KHFragment context, SSBUCharacter character, boolean replace, MoveSort moveSort){
        this.context = context;
        this.character = character;
        this.title = null;
        this.dialog = null;
        this.replace = replace;
        this.moveSort = moveSort;
    }

    public CharacterDataAsyncTask(KHFragment context, SSBUCharacter character, boolean replace, String title){
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


    //Used for now until API removes MoveType:"ground" for rolls/spotdodge/airdodge
    private boolean isEvasion(String name){
        return name.equals("Forward Roll") || name.equals("Back Roll") || name.equals("Spotdodge") || name.equals("Airdodge") || name.equals("Directional Airdodge (F/B)") || name.equals("Directional Airdodge (U)") || name.equals("Directional Airdodge (D)");
    }

    @Override
    protected SSBUCharacterData doInBackground(String... params) {
        try {
            String json = Storage.read("SSBU_" + String.valueOf(character.id),"moves.json",context.getActivity());
            LinkedList<Move> list = new LinkedList<>();
            LinkedList<Move> evasion = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                Move move = Move.ssbuGetFromJson(jsonArray.getJSONObject(i));
                switch (move.moveType) {
                    case Aerial:
                        list.add(AerialMove.ssbuGetFromJson(jsonArray.getJSONObject(i)));
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

            json = Storage.read("SSBU_" + String.valueOf(character.id),"attributes.json",context.getActivity());
            LinkedList<Movement> movements = new LinkedList<>();
            jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                movements.add(Movement.fromJson(jsonArray.getJSONObject(i)));
            }

            return new SSBUCharacterData(character, movements, list, evasion, moveSort);
        } catch (Exception e) {
            Log.d("error",e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(SSBUCharacterData s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }
        if(s==null){
            Toast.makeText(context.getContext(), "Character data isn't available yet", Toast.LENGTH_LONG).show();
            return;
        }
        if(replace){
            ((MainActivity)context.getActivity()).replaceFragment(SSBUCharacterDataFragment.newInstance(s));
        }else {
            ((MainActivity) context.getActivity()).loadFragment(SSBUCharacterDataFragment.newInstance(s));
        }
    }
}
