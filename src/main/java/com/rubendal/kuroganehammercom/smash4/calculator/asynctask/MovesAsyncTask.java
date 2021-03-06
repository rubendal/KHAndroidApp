package com.rubendal.kuroganehammercom.smash4.calculator.asynctask;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.rubendal.kuroganehammercom.smash4.calculator.DataFragment;
import com.rubendal.kuroganehammercom.smash4.calculator.classes.MoveData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

public class MovesAsyncTask extends AsyncTask<String, String, LinkedList<MoveData>> {

    private DataFragment context;
    private ProgressDialog dialog;
    private String character, title;

    public MovesAsyncTask(DataFragment context, String character){
        this.context = context;
        this.title = null;
        this.character = Uri.encode(character);
        this.dialog = null;
    }

    public MovesAsyncTask(DataFragment context, String character, String title){
        this(context, character);
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
    protected LinkedList<MoveData> doInBackground(String... params) {
        try {
            String moves = sendRequest("http://calculator.kuroganehammer.com/api/moves/" + character);

            LinkedList<MoveData> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(moves);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject move = jsonArray.getJSONObject(i);
                list.add(MoveData.fromJson(move));
            }
            return list;

        } catch (Exception e) {
            Log.d("moveObtainCalc",e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<MoveData> s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }

        if(s == null){
            Toast.makeText(context.getContext(), "Unable to connect to Sm4sh Calculator API", Toast.LENGTH_LONG).show();
            return;
        }

        context.apiList.moveData = s;
        LinkedList<String> list = new LinkedList<>();
        for(int i=0;i<s.size();i++){
            list.add(s.get(i).name);
        }
        context.apiList.moves = list;

        context.updateAttacks(context.getView());
    }

}
