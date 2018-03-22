package com.rubendal.kuroganehammercom.smash4.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.util.UserPref;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Comparator;

public class Character implements Serializable {
    public String name;
    public int id;
    public String thumbnailUrl;
    //public transient Bitmap thumbnail;
    public String color;
    public String gameName;
    public boolean hasSpecificAttributes;
    public SpecificAttribute specificAttribute;
    public boolean favorite = false;

    public Character(int id, String name){
        this.id = id;
        this.name = name;
        if(name.equals("Game & Watch")){
            this.name = "Mr. " + name;
        }

        setGameName();
        System.out.println(gameName);
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

    public void setGameName(){
        switch(this.name){
            case "Captain Falcon":
                gameName = "captain";
                break;
            case "Charizard":
                gameName = "lizardon";
                break;
            case "Diddy Kong":
                gameName = "diddy";
                break;
            case "Donkey Kong":
                gameName = "donkey";
                break;
            case "King Dedede":
                gameName = "dedede";
                break;
            case "Mr. Game & Watch":
                gameName = "gamewatch";
                break;
            case "Ganondorf":
                gameName = "ganon";
                break;
            case "Corrin":
                gameName = "kamui";
                break;
            case "Robin":
                gameName = "reflet";
                break;
            case "Bowser":
                gameName = "koopa";
                break;
            case "Bowser Jr.":
                gameName = "koopajr";
                break;
            case "Dr. Mario":
                gameName = "mariod";
                break;
            case "Mii Brawler":
                gameName = "miifighter";
                break;
            case "Mii Swordfighter":
                gameName = "miiswordsman";
                break;
            case "Villager":
                gameName = "murabito";
                break;
            case "PAC-MAN":
                gameName = "pacman";
                break;
            case "Olimar":
                gameName = "pikmin";
                break;
            case "Greninja":
                gameName = "gekkouga";
                break;
            case "Dark Pit":
                gameName = "pitb";
                break;
            case "Jigglypuff":
                gameName = "purin";
                break;
            case "Mega Man":
                gameName = "rockman";
                break;
            case "Rosalina & Luma":
                gameName = "rosetta";
                break;
            case "R.O.B.":
                gameName = "robot";
                break;
            case "Zero Suit Samus":
                gameName = "szerosuit";
                break;
            case "Wii Fit Trainer":
                gameName = "wiifit";
                break;
            default:
                gameName = this.name.toLowerCase().replace(" ","");
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
            String name = jsonObject.getString("DisplayName"), thumbnail = jsonObject.getString("ThumbnailUrl"), color = jsonObject.getString("ColorTheme");
            int id = jsonObject.getInt("OwnerId");
            Character c = new Character(context, id, name, thumbnail, color);
            c.favorite = UserPref.checkCharacterFavorites(name);
            return c;
        }catch(Exception e){
            return null;
        }
    }

    public static Comparator<Character> getComparator(){
        Comparator<Character> comparator = new Comparator<Character>() {
            @Override
            public int compare(Character c1, Character c2) {
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
