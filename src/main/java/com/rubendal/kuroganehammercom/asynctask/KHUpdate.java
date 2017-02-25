package com.rubendal.kuroganehammercom.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.classes.Character;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

public class KHUpdate extends AsyncTask<String, String, Boolean> {
    private MainActivity context;
    private ProgressDialog dialog;
    private String title;

    public KHUpdate(MainActivity context, String title){
        this.context = context;
        this.title = title;
        this.dialog = new ProgressDialog(this.context);
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
            String characters = sendRequest("http://api.kuroganehammer.com/api/Characters");
            String smashAttributes = sendRequest("http://api.kuroganehammer.com/api/smashattributetypes");

            if(characters.isEmpty()){
                return false;
            }
            if(smashAttributes.isEmpty()){
                return false;
            }

            Storage.write("data","characters.json",context,characters);
            Storage.write("data","smashAttributes.json",context,smashAttributes);

            LinkedList<Character> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(characters);
            for(int i=0;i<jsonArray.length();i++){
                list.add(Character.fromJson(context, jsonArray.getJSONObject(i)));
            }

            for(Character c : list){
                String moves = sendRequest("http://api.kuroganehammer.com/api/Characters/" + c.id + "/moves");
                String t = sendRequest("http://api.kuroganehammer.com/api/Characters/" + c.id + "/throws");
                String smashattributes = sendRequest("http://api.kuroganehammer.com/api/Characters/" + c.id + "/characterattributes");
                String attributes = sendRequest("http://api.kuroganehammer.com/api/Characters/" + c.id + "/movements");
                if(moves.isEmpty()){
                    return false;
                }
                if(t.isEmpty()){
                    return false;
                }
                if(attributes.isEmpty()){
                    return false;
                }
                if(smashAttributes.isEmpty()){
                    return false;
                }
                Storage.write(String.valueOf(c.id),"moves.json",context,moves);
                Storage.write(String.valueOf(c.id),"throws.json",context,t);
                Storage.write(String.valueOf(c.id),"smashAttributes.json",context,smashattributes);
                Storage.write(String.valueOf(c.id),"attributes.json",context,attributes);
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
            Toast.makeText(context, "Sync successful", Toast.LENGTH_LONG).show();
            context.updateData();
        }else{
            Toast.makeText(context, "Error syncing data with KH API", Toast.LENGTH_LONG).show();
        }
    }
}
