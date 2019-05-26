package com.rubendal.kuroganehammercom.util;

import android.content.Context;

import org.json.JSONArray;

import java.util.LinkedList;

public class UserPref {

    private static String InitialGame = "Smash 4";

    private static LinkedList<String> favoriteCharacters = new LinkedList<>();
    private static LinkedList<String> favoriteAttributes = new LinkedList<>();
    private static LinkedList<String> favoriteUltCharacters = new LinkedList<>();

    public static boolean usePicsForCharacterList = true;
    public static boolean useNewMoveDataDesign = true;

    public static void Initialize(Context context){
        try {
            InitialGame = Storage.read("user", "initialGame.bin", context);
            InitialGame = InitialGame.trim();
        }catch(Exception e){
            InitialGame = "Smash 4";
        }

        try {
            usePicsForCharacterList = Boolean.parseBoolean(Storage.read("user", "displayMode.bin", context).trim());
        }catch(Exception e){
            usePicsForCharacterList = true;
        }

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

        list = new LinkedList<>();
        try {
            String json = Storage.read("user", "favoriteUltCharacters.json", context);
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                list.add(jsonArray.getString(i));
            }
        }catch(Exception e){
            list = new LinkedList<>();
        }
        favoriteUltCharacters = list;
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


    private static void saveFavoriteUltCharacters(Context context){
        try{
            JSONArray jsonArray = new JSONArray(favoriteUltCharacters);
            String json = jsonArray.toString();
            Storage.write("user","favoriteUltCharacters.json",context,json);
        }catch(Exception e){

        }
    }

    public static boolean checkUltCharacterFavorites(String name){
        return favoriteUltCharacters.contains(name);
    }

    public static void addFavoriteUltCharacter(Context context, String name){
        favoriteUltCharacters.add(name);
        saveFavoriteUltCharacters(context);
    }

    public static void removeFavoriteUltCharacter(Context context, String name){
        favoriteUltCharacters.remove(name);
        saveFavoriteUltCharacters(context);
    }

    public static String getInitialGame(){
        return InitialGame;
    }

    public static void setInitialGame(Context context, String game){
        InitialGame = game;
        try{
            Storage.write("user","initialGame.bin",context, InitialGame);
        }catch(Exception e){

        }
    }

    public static void changeCharacterDisplay(Context context, boolean usePics){
        usePicsForCharacterList = usePics;

        try{
            Storage.write("user","displayMode.bin",context, String.valueOf(usePicsForCharacterList));
        }catch(Exception e){

        }
    }

    public static void changeCharacterDataDisplay(Context context, boolean useNewDesign){
        useNewMoveDataDesign = useNewDesign;

        try{
            Storage.write("user","dataDisplayDesign.bin",context, String.valueOf(useNewMoveDataDesign));
        }catch(Exception e){

        }
    }

}