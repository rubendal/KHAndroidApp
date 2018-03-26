package com.rubendal.kuroganehammercom.smash4.asynctask.character;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.interfaces.KHFragment;
import com.rubendal.kuroganehammercom.smash4.classes.Character;
import com.rubendal.kuroganehammercom.smash4.classes.MoveAnimation;
import com.rubendal.kuroganehammercom.smash4.fragments.HitboxVisualizationFragment;
import com.rubendal.kuroganehammercom.util.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.LinkedList;

public class MoveAnimationsAsyncTask extends AsyncTask<String, String, LinkedList<MoveAnimation>> {

    private KHFragment context;
    private ProgressDialog dialog;
    private String title;
    private Character character;

    private boolean connected = false;

    public MoveAnimationsAsyncTask(KHFragment context, Character character){
        this.context = context;
        this.character = character;
        this.title = null;
        this.dialog = new ProgressDialog(context.getContext());
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
    protected LinkedList<MoveAnimation> doInBackground(String... params) {
        try {

            //Make request to server for device connection test without adding ACCESS_NETWORK_STATE permission, if it catches an exception connected will be false if string is empty
            String test = HttpRequest.get("https://struz.github.io/smash-move-viewer/#/v1");

            if(!test.isEmpty())
                connected = true;
            else
                return null;

            String json = HttpRequest.get(character.getCharacterHitboxVisualizationData());

            LinkedList<MoveAnimation> list = new LinkedList<>();
            JSONArray jsonArray = new JSONObject(json).getJSONArray("moves");
            for(int i=0;i<jsonArray.length();i++){
                MoveAnimation animation = MoveAnimation.fromJson(jsonArray.getJSONObject(i));

                if(animation != null && animation.category.equals("has_hitbox"))
                    list.add(animation);
            }

            return list;

        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<MoveAnimation> s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }
        if(s == null){
            if(connected)
                Toast.makeText(context.getContext(), "Coming soon", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(context.getContext(), "Couldn't connect with Smash Move Viewer", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://struz.github.io/smash-move-viewer/#/v1/%s", character.gameName)));
        context.startActivity(intent);
        //((MainActivity) context.getActivity()).loadFragment(HitboxVisualizationFragment.newInstance(character,s));
    }

}
