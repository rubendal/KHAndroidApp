package com.rubendal.kuroganehammercom.smash4.calculator.asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.rubendal.kuroganehammercom.smash4.calculator.CalculatorFragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import com.rubendal.kuroganehammercom.smash4.calculator.classes.*;

public class KBRequestAsyncTask extends AsyncTask<String, String, KBResponse> {

    private CalculatorFragment context;
    private Data data;
    private ProgressDialog dialog;
    private String title;

    public KBRequestAsyncTask(CalculatorFragment context, Data data){
        this.context = context;
        this.title = null;
        this.data = data;
        this.dialog = null;
    }

    public KBRequestAsyncTask(CalculatorFragment context, Data data, String title){
        this(context, data);
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
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(data.toJson().toString());

            writer.flush();
            writer.close();
            os.close();


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
    protected KBResponse doInBackground(String... params) {
        try {
            String response = sendRequest("http://calculator.kuroganehammer.com/api/calculate/kb");

            JSONObject jsonObject = new JSONObject(response);

            if(jsonObject.has("message")){
                //Error message from calculator
                return null;
            }

            return KBResponse.fromJson(response);

        } catch (Exception e) {
            Log.d("sendRequest",e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(KBResponse s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }

        if(s == null){
            Toast.makeText(context.getContext(), "Unable to connect to Sm4sh Calculator API", Toast.LENGTH_LONG).show();
            return;
        }

        context.displayResults(s);

    }

}
