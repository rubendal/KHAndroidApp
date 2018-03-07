package com.rubendal.kuroganehammercom.smash4.asynctask.attribute;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.smash4.classes.Attribute;
import com.rubendal.kuroganehammercom.smash4.classes.AttributeName;
import com.rubendal.kuroganehammercom.smash4.classes.AttributeRank;
import com.rubendal.kuroganehammercom.smash4.classes.AttributeRankMaker;
import com.rubendal.kuroganehammercom.smash4.fragments.AttributeRankingFragment;
import com.rubendal.kuroganehammercom.interfaces.KHFragment;
import com.rubendal.kuroganehammercom.util.Storage;

import org.json.JSONArray;

import java.util.LinkedList;

public class AttributeRankingAsyncTask extends AsyncTask<String, String, LinkedList<AttributeRank>> {

    private KHFragment context;
    private ProgressDialog dialog;
    private String title;
    private AttributeName attribute;

    private boolean replace = false;

    public AttributeRankingAsyncTask(KHFragment context, AttributeName attribute) {
        this.context = context;
        this.attribute = attribute;
        this.title = null;
        this.dialog = null;
    }

    public AttributeRankingAsyncTask(KHFragment context, AttributeName attribute, boolean replace) {
        this.context = context;
        this.attribute = attribute;
        this.replace = replace;
        this.title = null;
        this.dialog = null;
    }

    public AttributeRankingAsyncTask(KHFragment context, AttributeName attribute, boolean replace, String title) {
        this(context, attribute, replace);
        this.title = title;
        this.dialog = new ProgressDialog(context.getActivity());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (dialog != null) {
            dialog.setTitle(title);
            dialog.setCancelable(false);
            dialog.show();
        }
    }


    @Override
    protected LinkedList<AttributeRank> doInBackground(String... params) {
        try {

            String json = Storage.read(attribute.name.toLowerCase(), "attributes.json", context.getActivity());
            LinkedList<Attribute> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(Attribute.getFromJson(context.getContext(), jsonArray.getJSONObject(i)));
            }

            return AttributeRankMaker.buildRankList(context.getContext(), list);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<AttributeRank> s) {
        super.onPostExecute(s);
        if (dialog != null) {
            dialog.dismiss();
        }

        try {
            if (replace) {
                ((MainActivity) context.getActivity()).replaceFragment(AttributeRankingFragment.newInstance(attribute, s));
            } else {
                ((MainActivity) context.getActivity()).loadFragment(AttributeRankingFragment.newInstance(attribute, s));
            }
        }catch(Exception e){
            //The user pressed back while fragment asynctask is being executed and makes the app crash, that's why here is a try catch lol
        }

    }
}