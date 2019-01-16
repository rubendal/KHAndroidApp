package com.rubendal.kuroganehammercom.smash4.calculator.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.smash4.calculator.CalculatorFragment;
import com.rubendal.kuroganehammercom.smash4.calculator.classes.APIList;
import com.rubendal.kuroganehammercom.smash4.calculator.classes.Data;
import com.rubendal.kuroganehammercom.smash4.calculator.classes.KBResponse;
import com.rubendal.kuroganehammercom.smash4.calculator.classes.MoveData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;

public class StartCalculatorAsyncTask extends AsyncTask<String, String, APIList> {

    private MainActivity context;
    private ProgressDialog dialog;
    private String title;
    private boolean replace;

    public StartCalculatorAsyncTask(MainActivity context, boolean replace){
        this.context = context;
        this.title = null;
        this.dialog = null;
        this.replace = replace;
    }

    public StartCalculatorAsyncTask(MainActivity context, boolean replace, String title){
        this(context, replace);
        this.title = title;
        this.dialog = new ProgressDialog(context);
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
    protected APIList doInBackground(String... params) {
        try {
            APIList apiList = new APIList();

            String characters = sendRequest("http://calculator.kuroganehammer.com/api/characters/names");
            String moves = sendRequest("http://calculator.kuroganehammer.com/api/moves/Mario");
            String stages = sendRequest("http://calculator.kuroganehammer.com/api/stages/names");
            String effects = sendRequest("http://calculator.kuroganehammer.com/api/moves/effects");

            JSONArray jsonArray = new JSONArray(characters);
            for(int i=0;i<jsonArray.length();i++){
                apiList.characters.add(jsonArray.getString(i));
            }

            Collections.sort(apiList.characters);

            jsonArray = new JSONArray(moves);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject move = jsonArray.getJSONObject(i);
                apiList.moves.add(move.getString("name"));

                apiList.moveData.add(MoveData.fromJson(move));
            }

            jsonArray = new JSONArray(stages);
            for(int i=0;i<jsonArray.length();i++){
                apiList.stages.add(jsonArray.getString(i));
            }

            jsonArray = new JSONArray(effects);
            for(int i=0;i<jsonArray.length();i++){
                apiList.effects.add(jsonArray.getJSONObject(i).getString("name"));
            }

            return apiList;

        } catch (Exception e) {
            Log.d("startCalc",e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(APIList s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }

        if(s==null){
            Toast.makeText(context.getApplicationContext(), "Unable to connect to Sm4sh Calculator API", Toast.LENGTH_LONG).show();
            return;
        }

        if(replace){
            context.replaceFragment(CalculatorFragment.newInstance(new Data(), s, new KBResponse()));
        }else {
            context.loadFragment(CalculatorFragment.newInstance(new Data(), s, new KBResponse()));
        }
    }

}
