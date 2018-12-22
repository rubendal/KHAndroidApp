package com.rubendal.kuroganehammercom.ultimate.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.rubendal.kuroganehammercom.util.UserPref;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Comparator;

public class SSBUCharacter implements Serializable{
    public int id;
    public String name, color;
    public boolean favorite = false;
    public boolean hasMoveData = false; //temp while characters data is being uploaded to the site

    public SSBUCharacter(){

    }

    public SSBUCharacter(int id, String name, String color){
        this.id = id;
        this.name = name;
        this.color = color;

        this.color = "#3F51B5";
    }

    public String getCharacterTitleName(){
        switch (name){
            case "King Dedede":
                return "Perfection Incarnate";
            case "Zero Suit Samus":
                return "Zero Skill Samus";
        }

        return name;
    }

    public String getCharacterImageName(){
        return name;
    }

    public Bitmap getImage(Context context){
        InputStream is;
        Bitmap thumb = null;
        try {
            is = context.getAssets().open("SSBU/Images/" + id + "/image.png");
            if(hasMoveData)
                thumb = BitmapFactory.decodeStream(is);
            else
                thumb = getGrayscale(BitmapFactory.decodeStream(is));

        } catch (IOException e) {
            try {
                is = context.getAssets().open("SSBU/Images/" + id + "/image.jpg");
                if(hasMoveData)
                    thumb = BitmapFactory.decodeStream(is);
                else
                    thumb = getGrayscale(BitmapFactory.decodeStream(is));
            } catch (IOException e2) {
                thumb = null;
            }
        }
        return thumb;
    }

    private Bitmap getGrayscale(Bitmap bitmap){

        //Custom color matrix to convert to GrayScale
        float[] matrix = new float[]{
                0.3f, 0.59f, 0.11f, 0, 0,
                0.3f, 0.59f, 0.11f, 0, 0,
                0.3f, 0.59f, 0.11f, 0, 0,
                0, 0, 0, 1, 0,};

        Bitmap grayscale = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

        Canvas canvas = new Canvas(grayscale);
        Paint paint = new Paint();
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        paint.setColorFilter(filter);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return grayscale;
    }

    public static SSBUCharacter fromJson(Context context, JSONObject jsonObject){
        try{
            String name = jsonObject.getString("Name"), color = jsonObject.getString("Color");
            int id = jsonObject.getInt("Id");
            SSBUCharacter character = new SSBUCharacter(id, name, color);

            character.favorite = UserPref.checkUltCharacterFavorites(character.name);

            return character;
        }
        catch(Exception e){

        }
        return null;
    }

    public static Comparator<SSBUCharacter> getComparator(){
        Comparator<SSBUCharacter> comparator = new Comparator<SSBUCharacter>() {
            @Override
            public int compare(SSBUCharacter c1, SSBUCharacter c2) {
                if(c1.favorite == c2.favorite){
                    if(c1.id < c2.id){
                        return -1;
                    }
                    if(c1.id > c2.id){
                        return 1;
                    }
                    return 0;
                }
                if(c1.favorite){
                    return -1;
                }
                if(c2.favorite){
                    return 1;
                }
                return 0;
            }
        };
        return comparator;
    }
}
