package com.rubendal.kuroganehammercom.calculator.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;

import java.util.List;

public class StringSpinnerAdapter extends ArrayAdapter<String> {
    public StringSpinnerAdapter(Context context, List<String> objects) {
        super(context, 0, objects);
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String s = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_list_item, parent, false);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.text);

        textView.setText(s);


        return convertView;
    }
}
