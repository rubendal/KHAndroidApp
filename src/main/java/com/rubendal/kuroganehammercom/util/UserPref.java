package com.rubendal.kuroganehammercom.util;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;

import java.util.LinkedList;

public class UserPref {

    private static LinkedList<String> favoriteCharacters = new LinkedList<>();

    public static void Initialize(Context context){
        LinkedList<String> list = new LinkedList<>();
        try {
            String json = Storage.read("user", "favoriteCharacters.json", context);
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                list.add(jsonArray.getString(i));
            }
        }catch(Exception e){
            list = new LinkedList<>();
        }
        favoriteCharacters = list;
    }

    private static void saveFavorites(Context context){
        try{
            JSONArray jsonArray = new JSONArray(favoriteCharacters);
            String json = jsonArray.toString();
            Storage.write("user","favoriteCharacters.json",context,json);
        }catch(Exception e){
            
        }
    }

    public static boolean checkFavorites(String name){
        return favoriteCharacters.contains(name);
    }

    public static void addFavoriteCharacter(Context context, String name){
        favoriteCharacters.add(name);
        saveFavorites(context);
    }

    public static void removeFavoriteCharacter(Context context, String name){
        favoriteCharacters.remove(name);
        saveFavorites(context);
    }


}
