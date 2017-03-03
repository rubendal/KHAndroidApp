package com.rubendal.kuroganehammercom.adapter;

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
import com.rubendal.kuroganehammercom.classes.Attribute;
import com.rubendal.kuroganehammercom.classes.AttributeList;
import com.rubendal.kuroganehammercom.classes.Character;

import java.util.LinkedList;
import java.util.List;

public class AttributeAdapter extends BaseAdapter {

    private Context context;
    private List<AttributeList> list;
    private int x;

    public AttributeAdapter(Context context, List<AttributeList> list, int x, LinkedList<Attribute> attributes)
    {
        this.context = context;
        this.list = list;
        this.x = x;

        //Remove all attributes that don't have data
        LinkedList<AttributeList> emptyAttributeList = new LinkedList<>();

        boolean hasOne = false;
        for(AttributeList a : this.list){
            hasOne = false;
            for(Attribute attr : attributes){
                if(attr.attributeId == a.id){
                    hasOne = true;
                    break;
                }
            }
            if(!hasOne){
                emptyAttributeList.add(a);
            }
        }

        this.list.removeAll(emptyAttributeList);
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
        AttributeList attribute = list.get(position);

        if(convertView == null){
            convertView =  LayoutInflater.from(context).inflate(R.layout.character_grid_layout, parent, false);
        }

        TextView name = (TextView)convertView.findViewById(R.id.name);
        ImageView img = (ImageView)convertView.findViewById(R.id.image);

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
            name.setText(attribute.name);
            Bitmap image = attribute.getImage(context);
            if(image != null) {
                img.setImageBitmap(image);
            }else{
                img.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.row_shape, null));
            }
        }

        return convertView;
    }
}