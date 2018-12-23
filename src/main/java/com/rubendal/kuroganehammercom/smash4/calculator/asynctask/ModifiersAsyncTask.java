package com.rubendal.kuroganehammercom.smash4.calculator.asynctask;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.rubendal.kuroganehammercom.smash4.calculator.DataFragment;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.widget.Toast;

import java.util.LinkedList;

public class ModifiersAsyncTask extends AsyncTask<String, String, LinkedList<String>> {

    private DataFragment context;
    private ProgressDialog dialog;
    private String character, title;
    private boolean attacker;

    public ModifiersAsyncTask(DataFragment context, String character, boolean attacker){
        this.context = context;
        this.title = null;
        this.character = Uri.encode(character);
        this.attacker = attacker;
        this.dialog = null;
    }

    public ModifiersAsyncTask(DataFragment context, String character, boolean attacker, String title){
        this(context, character, attacker);
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
    protected LinkedList<String> doInBackground(String... params) {
        try {
            String modifiers = sendRequest("http://calculator.kuroganehammer.com/api/characters/" + character + "/modifiers/names");

            LinkedList<String> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(modifiers);
            for(int i=0;i<jsonArray.length();i++){
                list.add(jsonArray.getString(i));
            }
            return list;

        } catch (Exception e) {
            Log.d("startCalc",e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<String> s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }

        if(s == null){
            Toast.makeText(context.getActivity(), "Unable to connect to Sm4sh Calculator API", Toast.LENGTH_LONG).show();
            return;
        }

        if(attacker){
            context.apiList.attacker_modifiers = s;
            context.updateAttackerModifier(context.getView());
        }else{
            context.apiList.target_modifiers = s;
            context.updateTargetModifier(context.getView());
        }

    }

}
