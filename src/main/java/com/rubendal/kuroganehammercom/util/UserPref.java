package com.rubendal.kuroganehammercom.util;

import android.content.Context;

import org.json.JSONArray;

import java.util.LinkedList;

public class UserPref {

    private static LinkedList<String> favoriteCharacters = new LinkedList<>();
    private static LinkedList<String> favoriteAttributes = new LinkedList<>();

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

        list = new LinkedList<>();
        try {
            String json = Storage.read("user", "favoriteAttributes.json", context);
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                list.add(jsonArray.getString(i));
            }
        }catch(Exception e){
            list = new LinkedList<>();
        }
        favoriteAttributes = list;
    }

    private static void saveFavoriteCharacters(Context context){
        try{
            JSONArray jsonArray = new JSONArray(favoriteCharacters);
            String json = jsonArray.toString();
            Storage.write("user","favoriteCharacters.json",context,json);
        }catch(Exception e){

        }
    }

    public static boolean checkCharacterFavorites(String name){
        return favoriteCharacters.contains(name);
    }

    public static void addFavoriteCharacter(Context context, String name){
        favoriteCharacters.add(name);
        saveFavoriteCharacters(context);
    }

    public static void removeFavoriteCharacter(Context context, String name){
        favoriteCharacters.remove(name);
        saveFavoriteCharacters(context);
    }

    private static void saveAttributesFavorite(Context context){
        try{
            JSONArray jsonArray = new JSONArray(favoriteAttributes);
            String json = jsonArray.toString();
            Storage.write("user","favoriteAttributes.json",context,json);
        }catch(Exception e){

        }
    }

    public static boolean checkAttributeFavorites(String name){
        return favoriteAttributes.contains(name);
    }

    public static void addFavoriteAttribute(Context context, String name){
        favoriteAttributes.add(name);
        saveAttributesFavorite(context);
    }

    public static void removeFavoriteAttribute(Context context, String name){
        favoriteAttributes.remove(name);
        saveAttributesFavorite(context);
    }


}
