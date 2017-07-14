package com.rubendal.kuroganehammercom.asynctask.attribute;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;

import com.rubendal.kuroganehammercom.MainActivity;
import com.rubendal.kuroganehammercom.adapter.AttributeAdapter;
import com.rubendal.kuroganehammercom.adapter.CharacterAdapter;
import com.rubendal.kuroganehammercom.classes.Attribute;
import com.rubendal.kuroganehammercom.classes.AttributeList;
import com.rubendal.kuroganehammercom.classes.Character;
import com.rubendal.kuroganehammercom.fragments.AttributeMainFragment;
import com.rubendal.kuroganehammercom.fragments.CharacterFragment;
import com.rubendal.kuroganehammercom.util.Storage;

import org.json.JSONArray;

import java.util.LinkedList;

public class AttributeListAsyncTask extends AsyncTask<String, String, LinkedList<AttributeList>> {

    private AttributeMainFragment context;
    private ProgressDialog dialog;
    private String title;
    private int x;
    private LinkedList<AttributeList> attributes = new LinkedList<>();

    public AttributeListAsyncTask(AttributeMainFragment context, int x){
        this.context = context;
        this.title = null;
        this.dialog = null;
        this.x = x;
    }

    public AttributeListAsyncTask(AttributeMainFragment context, int x, String title){
        this(context, x);
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

    @Override
    protected LinkedList<AttributeList> doInBackground(String... params) {
        try {
            String json = Storage.read("data","attributes.json",context.getActivity());
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                attributes.add(AttributeList.getFromJson(context.getActivity(), jsonArray.getJSONObject(i)));
            }
            return attributes;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<AttributeList> s) {
        super.onPostExecute(s);
        if(dialog!=null){
            dialog.dismiss();
        }
        AttributeAdapter adapter = new AttributeAdapter(context.getActivity(), attributes, x);
        if(s != null) {
            context.grid.setAdapter(adapter);
            if(context.selectedItem != -1){
                context.grid.smoothScrollToPosition(context.selectedItem);
            }
            context.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    context.selectedItem = position;
                    AttributeList attribute = (AttributeList) parent.getItemAtPosition(position);
                    AttributeRankingAsyncTask a = new AttributeRankingAsyncTask(context, attribute);
                    a.execute();
                }
            });
        }
    }
}
