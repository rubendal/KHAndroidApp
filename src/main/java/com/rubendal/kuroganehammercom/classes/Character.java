package com.rubendal.kuroganehammercom.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.rubendal.kuroganehammercom.util.Storage;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

public class Character implements Serializable {
    public String name;
    public int id;
    public String thumbnailUrl;
    //public transient Bitmap thumbnail;
    public String color;
    public boolean hasSpecificAttributes;
    public SpecificAttribute specificAttribute;

    public Character(int id, String name){
        this.id = id;
        this.name = name;
        if(name.equals("Game & Watch")){
            this.name = "Mr. " + name;
        }
    }

    public Character(Context context, int id, String name, String thumbnailUrl, String color){
        this(id,name);
        this.thumbnailUrl = thumbnailUrl;
        this.color = color;
        this.hasSpecificAttributes = Storage.exists(String.valueOf(id),"specificAttributes.json",context);
        if(this.hasSpecificAttributes){
            try {
                String temp = Storage.read(String.valueOf(id), "specificAttributes.json", context);
                this.specificAttribute = SpecificAttribute.fromJson(new JSONObject(temp));
            }catch(Exception e){
                this.specificAttribute = null;
                this.hasSpecificAttributes = false;
            }
        }
    }

    public Bitmap getImage(Context context){
        InputStream is;
        Bitmap thumb = null;
        try {
            is = context.getAssets().open("Images/" + id + "/image.png");
            thumb = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            try {
                is = context.getAssets().open("Images/" + id + "/image.jpg");
                thumb = BitmapFactory.decodeStream(is);
            } catch (IOException e2) {
                thumb = null;
            }
        }
        return thumb;
    }

    @Override
    public String toString() {
        return name;
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
        switch(name){
            case "Lucas":
                return "Ridley";
        }
        return name;
    }

    public static Character fromJson(Context context, JSONObject jsonObject){
        try {
            String name = jsonObject.getString("displayName"), thumbnail = jsonObject.getString("thumbnailUrl"), color = jsonObject.getString("colorTheme");
            int id = jsonObject.getInt("id");
            return new Character(context, id, name, thumbnail, color);
        }catch(Exception e){
            return null;
        }
    }

}
