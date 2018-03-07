package com.rubendal.kuroganehammercom.dbfz.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class DBCharacter implements Serializable{
    public int id;
    public String name, color;
    public boolean favorite = false;
    public boolean hasMoveData = false; //temp while characters data is being uploaded to the site

    public DBCharacter(){

    }

    public DBCharacter(int id, String name, String color){
        this.id = id;
        this.name = name;
        this.color = color;
    }

    public String getCharacterTitleName(){
        return name;
    }

    public String getCharacterImageName(){
        return name;
    }

    public Bitmap getImage(Context context){
        InputStream is;
        Bitmap thumb = null;
        try {
            is = context.getAssets().open("DBFZ/Images/" + id + "/image.png");
            if(hasMoveData)
                thumb = BitmapFactory.decodeStream(is);
            else
                thumb = getGrayscale(BitmapFactory.decodeStream(is));

        } catch (IOException e) {
            try {
                is = context.getAssets().open("DBFZ/Images/" + id + "/image.jpg");
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

    public static DBCharacter fromJson(Context context, JSONObject jsonObject){
        try{
            String name = jsonObject.getString("Name"), color = jsonObject.getString("Color");
            int id = jsonObject.getInt("Id");

            return new DBCharacter(id, name, color);
        }
        catch(Exception e){

        }
        return null;
    }
}