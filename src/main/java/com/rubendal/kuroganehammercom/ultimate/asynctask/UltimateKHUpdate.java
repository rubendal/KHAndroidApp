package com.rubendal.kuroganehammercom.ultimate.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
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

public class UltimateKHUpdate extends AsyncTask<String, String, Boolean> {
    private KHFragment context;
    private ProgressDialog dialog;
    private String title;

    private static String HOST = "https://api.kuroganehammer.com";

    public UltimateKHUpdate(KHFragment context, String title){
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

    private String sendRequest(String link){
        StringBuilder builder = new StringBuilder();
        HttpURLConnection connection = null;

        try
        {
            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Charset", "utf-8");
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(false);

            connection.connect();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null)
            {
                builder.append(line);
            }
        }catch (Exception ex)
        {

        }
        finally
        {
            if(connection!=null) {
                connection.disconnect();
            }
        }
        return builder.toString();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        //Get all data needed from the API
        try {
            String characters = sendRequest(HOST + "/api/Characters?game=ultimate");
            String attributeNames = sendRequest(HOST +"/api/characterattributes/types?game=ultimate");

            if(characters.isEmpty()){
                return false;
            }
            if(attributeNames.isEmpty()){
                return false;
            }

            Storage.write("ssbu","characters.json",context.getActivity(),characters);
            Storage.write("ssbu","attributeNames.json", context.getActivity(), attributeNames);

            LinkedList<Character> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(characters);
            for(int i=0;i<jsonArray.length();i++){
                list.add(Character.fromJson(context.getActivity(), jsonArray.getJSONObject(i)));
            }

            for(Character c : list){
                String moves = sendRequest(HOST +"/api/Characters/" + c.id + "/moves?game=ultimate&expand=true");
                String attributes = sendRequest(HOST +"/api/Characters/" + c.id + "/characterattributes?game=ultimate");
                String movements = sendRequest(HOST +"/api/Characters/" + c.id + "/movements?game=ultimate");
                if(moves.isEmpty()){
                    return false;
                }
                if(attributes.isEmpty()){
                    return false;
                }
                if(movements.isEmpty()){
                    return false;
                }

                Storage.write("SSBU_" + String.valueOf(c.id),"moves.json",context.getActivity(),moves);
                Storage.write("SSBU_" +String.valueOf(c.id),"attributes.json",context.getActivity(),attributes);
                Storage.write("SSBU_" +String.valueOf(c.id),"movements.json",context.getActivity(),movements);
            }

            LinkedList<AttributeName> attrNames = new LinkedList<>();
            jsonArray = new JSONArray(attributeNames);
            for(int i=0;i<jsonArray.length();i++){
                attrNames.add(AttributeName.getFromJson(jsonArray.getJSONObject(i)));
            }

            for(AttributeName a : attrNames){
                String attrData = sendRequest(HOST + "/api/characterattributes/name/" + a.name + "?game=ultimate");

                if(attrData.isEmpty())
                    return false;

                Storage.write("SSBU_" +a.name.toLowerCase(),"attributes.json", context.getActivity(), attrData);
            }

            return true;
        }catch(Exception e){
            Log.d("syncd", e.getMessage());
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
