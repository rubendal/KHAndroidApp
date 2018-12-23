package com.rubendal.kuroganehammercom.smash4.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.smash4.classes.MoveAnimation;

import java.util.List;

public class AnimationSpinnerAdapter extends ArrayAdapter<MoveAnimation> {

    public AnimationSpinnerAdapter(Context context, List<MoveAnimation> objects) {
        super(context, 0, objects);
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MoveAnimation s = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_list_item, parent, false);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.text);

        textView.setText(s.name);


        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MoveAnimation s = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.spinner_list_item, parent, false);
        }
        TextView textView = (TextView)convertView.findViewById(R.id.text);

        textView.setText(s.name);


        return convertView;
    }
}
