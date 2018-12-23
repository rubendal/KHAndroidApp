package com.rubendal.kuroganehammercom.smash4.asynctask.attribute;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.smash4.adapter.AttributeAdapter;
import com.rubendal.kuroganehammercom.smash4.classes.AttributeName;
import com.rubendal.kuroganehammercom.smash4.fragments.AttributeMainFragment;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.util.UserPref;

import org.json.JSONArray;

import java.util.Collections;
import java.util.LinkedList;

public class AttributeListAsyncTask extends AsyncTask<String, String, LinkedList<AttributeName>> {

    private AttributeMainFragment context;
    private ProgressDialog dialog;
    private String title;
    private int x;
    private LinkedList<AttributeName> attributes = new LinkedList<>();

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
    protected LinkedList<AttributeName> doInBackground(String... params) {
        try {
            String json = Storage.read("data","attributeNames.json",context.getActivity());
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                attributes.add(AttributeName.getFromJson(jsonArray.getJSONObject(i)));
            }
            Collections.sort(attributes, AttributeName.getComparator());
            return attributes;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    protected void onPostExecute(LinkedList<AttributeName> s) {
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
                    AttributeName attribute = (AttributeName) parent.getItemAtPosition(position);
                    AttributeRankingAsyncTask a = new AttributeRankingAsyncTask(context, attribute);
                    a.execute();
                }
            });
            context.grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    AttributeName attribute = (AttributeName) adapterView.getItemAtPosition(i);
                    ImageView fav = (ImageView)view.findViewById(R.id.fav);
                    if(UserPref.checkAttributeFavorites(attribute.name)){
                        UserPref.removeFavoriteAttribute(context.getActivity(),attribute.name);
                        fav.setVisibility(View.INVISIBLE);
                        attribute.favorite = false;
                    }else{
                        UserPref.addFavoriteAttribute(context.getActivity(),attribute.name);
                        fav.setVisibility(View.VISIBLE);
                        attribute.favorite = true;
                    }
                    return true;
                }
            });
        }
    }
}
