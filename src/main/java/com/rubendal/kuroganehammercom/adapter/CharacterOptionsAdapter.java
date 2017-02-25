package com.rubendal.kuroganehammercom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.classes.CharacterOption;
import com.rubendal.kuroganehammercom.R;

import java.util.List;


public class CharacterOptionsAdapter extends ArrayAdapter<CharacterOption>{
    public CharacterOptionsAdapter(Context context, List<CharacterOption> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CharacterOption o = getItem(position);

        if(convertView == null){
            if(o.isImage) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.character_image_row, parent, false);
                ImageView img = (ImageView)convertView.findViewById(R.id.image);
                img.setImageBitmap(o.image);
            }else{
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
                TextView textView = (TextView)convertView.findViewById(R.id.text);

                textView.setText(o.text);

            }
        }



        return convertView;
    }
}
