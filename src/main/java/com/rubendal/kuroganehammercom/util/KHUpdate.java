package com.rubendal.kuroganehammercom.util;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.rubendal.kuroganehammercom.smash4.classes.AttributeName;
import com.rubendal.kuroganehammercom.interfaces.KHFragment;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.smash4.classes.Character;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

public class KHUpdate extends AsyncTask<String, String, Boolean> {
    private KHFragment context;
    private ProgressDialog dialog;
    private String title;

    private static String HOST = "http://beta-api-kuroganehammer.azurewebsites.net";

    public KHUpdate(KHFragment context, String title){
        this.context = context;
        this.title = title;
        this.dialog = new ProgressDialog(this.context.getActivity());
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
    protected Boolean doInBackground(String... params) {
        //Get all data needed from the API
        try {
            String characters = HttpRequest.get(HOST + "/api/Characters");
            String attributeNames = HttpRequest.get(HOST +"/api/characterattributes/types");

            if(characters.isEmpty()){
                return false;
            }
            if(attributeNames.isEmpty()){
                return false;
            }

            Storage.write("data","characters.json",context.getActivity(),characters);
            Storage.write("data","attributeNames.json", context.getActivity(), attributeNames);

            LinkedList<Character> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(characters);
            for(int i=0;i<jsonArray.length();i++){
                list.add(Character.fromJson(context.getActivity(), jsonArray.getJSONObject(i)));
            }

            for(Character c : list){
                String moves = HttpRequest.get(HOST +"/api/Characters/" + c.id + "/moves");
                String attributes = HttpRequest.get(HOST +"/api/Characters/" + c.id + "/characterattributes");
                String movements = HttpRequest.get(HOST +"/api/Characters/" + c.id + "/movements");
                if(moves.isEmpty()){
                    return false;
                }
                if(attributes.isEmpty()){
                    return false;
                }
                if(movements.isEmpty()){
                    return false;
                }

                Storage.write(String.valueOf(c.id),"moves.json",context.getActivity(),moves);
                Storage.write(String.valueOf(c.id),"attributes.json",context.getActivity(),attributes);
                Storage.write(String.valueOf(c.id),"movements.json",context.getActivity(),movements);
            }

            LinkedList<AttributeName> attrNames = new LinkedList<>();
            jsonArray = new JSONArray(attributeNames);
            for(int i=0;i<jsonArray.length();i++){
                attrNames.add(AttributeName.getFromJson(jsonArray.getJSONObject(i)));
            }

            for(AttributeName a : attrNames){
                String attrData = HttpRequest.get(HOST + "/api/characterattributes/name/" + a.name);

                if(attrData.isEmpty())
                    return false;

                Storage.write(a.name.toLowerCase(),"attributes.json", context.getActivity(), attrData);
            }

            return true;
        }catch(Exception e){

        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }
        if(s){
            Toast.makeText(context.getActivity(), "Sync successful", Toast.LENGTH_LONG).show();
            context.updateData();
        }else{
            Toast.makeText(context.getActivity(), "Error syncing data with KH API", Toast.LENGTH_LONG).show();
        }
    }
}
