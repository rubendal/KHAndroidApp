package com.rubendal.kuroganehammercom.ultimate.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.ultimate.classes.SSBUCharacter;

import java.util.List;

public class CharacterAdapter extends BaseAdapter {

    private Context context;
    private List<SSBUCharacter> list;
    private int x;

    public CharacterAdapter(Context context, List<SSBUCharacter> list, int x)
    {
        this.context = context;
        this.list = list;
        this.x = x;
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
        SSBUCharacter character = list.get(position);

        if(convertView == null){
            convertView =  LayoutInflater.from(context).inflate(R.layout.ult_character_grid_layout, parent, false);
        }

        //TextView name = (TextView)convertView.findViewById(R.id.name);
        ImageView img = (ImageView)convertView.findViewById(R.id.image);
        ImageView fav = (ImageView)convertView.findViewById(R.id.fav);

        img.getLayoutParams().width = x;
        img.getLayoutParams().height = x * 23 / 50;

        /*
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

        }*/



        if(character != null) {
            //Previous list interface code
            /*name.setText(character.name);
            name.setVisibility(View.VISIBLE);

            GradientDrawable g = (GradientDrawable)convertView.findViewById(R.id.row_container).getBackground();
            g.setColor(Color.parseColor(character.color.replace("#","#30")));*/

            //name.setText(character.getCharacterImageName());
            Bitmap image = character.getImage(context);
            if(image != null) {
                img.setImageBitmap(image);
            }else{
                img.setImageDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.row_shape, null));
            }

            if(character.favorite){
                fav.setVisibility(View.VISIBLE);
            }else{
                fav.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }
}
