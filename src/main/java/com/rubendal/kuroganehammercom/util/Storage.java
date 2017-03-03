package com.rubendal.kuroganehammercom.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.rubendal.kuroganehammercom.classes.Character;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class Storage {

    //Initial storage assets version, will change when new assets are added
    private static final String STORAGE_DATA_VERSION = "1.3";

    //Write file in internal storage
    public static void write (String directory, String filename, Context context, String string) throws IOException {
        try {
            File dir = context.getDir(directory, Context.MODE_PRIVATE);
            File file = new File(dir,filename);
            FileOutputStream fos =  new FileOutputStream(file);
            fos.write(string.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
        }
    }


    //Read file from internal storage
    public static String read (String directory, String filename, Context context) throws IOException{
        StringBuffer buffer = new StringBuffer();
        File dir = context.getDir(directory, Context.MODE_PRIVATE);
        File file = new File(dir,filename);
        FileInputStream fis = new FileInputStream(file);
        String line = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        if (fis!=null) {
            while ((line= reader.readLine()) != null) {
                buffer.append(line + "\n" );
            }
        }
        fis.close();
        return buffer.toString();
    }

    //Check if file exists
    public static boolean exists(String directory, String filename, Context context){
        File dir = context.getDir(directory, Context.MODE_PRIVATE);
        File file = new File(dir,filename);
        return file.exists();
    }

    //Check if storage has been initialized based on the app assets version
    public static void Initialize(Context context){
        if(!exists("init","temp.bin",context)){
            writeInitialData(context);
        }else{
            try{
                String ver = read("init","temp.bin",context);
                if(!ver.trim().equals(STORAGE_DATA_VERSION)){
                    writeInitialData(context);
                }
            }catch(Exception e){

            }
        }


    }

    private static String getAsset(AssetManager assets, String file){
        InputStream is;
        try {
            is = assets.open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        }catch(Exception e){

        }
        return null;
    }

    //Internal storage initialization
    private static void writeInitialData(Context context){
        AssetManager assets = context.getAssets();

        try {
            String json = getAsset(assets, "characters.json");
            LinkedList<Character> list = new LinkedList<>();
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                list.add(Character.fromJson(context, jsonArray.getJSONObject(i)));
            }
            write("data","characters.json",context,json);
            json = getAsset(assets, "Data/smashAttributes.json");
            write("data","smashAttributes.json",context,json);
            json = getAsset(assets, "Data/attributes.json");
            write("data","attributes.json",context,json);
            for(Character c : list){
                json = getAsset(assets, "Data/" + c.id + "/moves.json");
                write(String.valueOf(c.id),"moves.json",context,json);
                json = getAsset(assets, "Data/" + c.id + "/throws.json");
                write(String.valueOf(c.id),"throws.json",context,json);
                json = getAsset(assets, "Data/" + c.id + "/attributes.json");
                write(String.valueOf(c.id),"attributes.json",context,json);
                json = getAsset(assets, "Data/" + c.id + "/smashAttributes.json");
                write(String.valueOf(c.id),"smashAttributes.json",context,json);
                try{
                    json = getAsset(assets, "Data/" + c.id + "/specificAttributes.json");
                    write(String.valueOf(c.id),"specificAttributes.json",context,json);
                }catch(Exception ei){

                }
            }
            write("init","temp.bin",context,STORAGE_DATA_VERSION);
        } catch (Exception e) {

        }
    }
}
