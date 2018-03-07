package com.rubendal.kuroganehammercom.smash4.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.res.ResourcesCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rubendal.kuroganehammercom.R;

import com.rubendal.kuroganehammercom.smash4.classes.*;

import java.util.LinkedList;
import java.util.List;

public class AttributeAdapter extends BaseAdapter {

    private Context context;
    private List<AttributeName> list;
    private int x;

    public AttributeAdapter(Context context, LinkedList<AttributeName> attributes, int x)
    {
        this.context = context;
        this.x = x;
        this.list = attributes;

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
        AttributeName attribute = list.get(position);

        if(convertView == null){
            convertView =  LayoutInflater.from(context).inflate(R.layout.character_grid_layout, parent, false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.name);
        ImageView img = (ImageView)convertView.findViewById(R.id.image);
        ImageView fav = (ImageView)convertView.findViewById(R.id.fav);

        fav.setVisibility(View.INVISIBLE);

        img.getLayoutParams().width = x;
        img.getLayoutParams().height = x;

        //Recalculate text params
        if(x!=300){
            if(x>300) {
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) name.getLayoutParams();
                params.setMargins(0, x - 70, 0, 0);
                name.setLayoutParams(params);
                name.getLayoutParams().width = x-20;
            }else{
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) name.getLayoutParams();
                params.setMargins(0, x - 20, 0, 0);
                name.setLayoutParams(params);
                name.getLayoutParams().width = x-20;
                name.getLayoutParams().height = 20;
                name.setTextSize(TypedValue.COMPLEX_UNIT_PX, 10);
                name.setGravity(Gravity.CENTER);
            }

        }



        if(attribute != null) {
            name.setText(attribute.formattedName);
            Bitmap image = attribute.getImage(context);
            if(image != null) {
                img.setImageBitmap(image);
            }else{
                img.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.row_shape, null));
            }

            if(attribute.favorite){
                fav.setVisibility(View.VISIBLE);
            }else{
                fav.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }

}