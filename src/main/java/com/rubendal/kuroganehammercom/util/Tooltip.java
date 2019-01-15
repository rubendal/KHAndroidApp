package com.rubendal.kuroganehammercom.util;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

public class Tooltip implements View.OnClickListener {

    private Context context;
    private String text;

    public Tooltip(Context context, String text){
        this.context = context;
        this.text = text;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
