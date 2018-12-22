package com.rubendal.kuroganehammercom.smash4.asynctask.formula;


import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.rubendal.kuroganehammercom.smash4.classes.Formula;
import com.rubendal.kuroganehammercom.smash4.fragments.FormulaFragment;
import com.rubendal.kuroganehammercom.util.Storage;

import org.json.JSONArray;

import java.util.LinkedList;

public class FormulaAsyncTask extends AsyncTask<String, String, LinkedList<Formula>> {

    private FormulaFragment context;
    private ProgressDialog dialog;
    private String title;
    private boolean replace = false;

    public FormulaAsyncTask(FormulaFragment context) {
        this.context = context;
        this.title = null;
        this.dialog = null;
    }

    public FormulaAsyncTask(FormulaFragment context, boolean replace) {
        this.context = context;
        this.replace = replace;
        this.title = null;
        this.dialog = null;
    }

    public FormulaAsyncTask(FormulaFragment context, boolean replace, String title) {
        this(context, replace);
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
    protected LinkedList<Formula> doInBackground(String... params) {
        try {
            String json = Storage.read("data", "formulas.json", context.getActivity());
            LinkedList<Formula> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(Formula.fromJson(jsonArray.getJSONObject(i)));
            }
            return list;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<Formula> s) {
        super.onPostExecute(s);
        if (dialog != null) {
            dialog.dismiss();
        }
        context.setData(s);
    }
}
