package com.rubendal.kuroganehammercom.smash4.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.smash4.classes.Character;
import com.rubendal.kuroganehammercom.R;

import java.util.List;

public class CharacterListAdapter extends BaseAdapter {

    private Context context;
    private List<Character> list;

    public CharacterListAdapter(Context context, List<Character> list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Character character = list.get(position);

        if(convertView == null){
            convertView =  LayoutInflater.from(context).inflate(R.layout.character_list_layout, parent, false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.name);
        ImageView fav = (ImageView)convertView.findViewById(R.id.fav);


        if(character != null) {
            //Previous list interface code
            /*name.setText(character.name);
            name.setVisibility(View.VISIBLE);

            */

            name.setText(character.getCharacterImageName());
            GradientDrawable g = (GradientDrawable)name.getBackground();
            g.setColor(Color.parseColor(character.color.replace("#","#40")));

            if(character.favorite){
                fav.setVisibility(View.VISIBLE);
            }else{
                fav.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }
}
