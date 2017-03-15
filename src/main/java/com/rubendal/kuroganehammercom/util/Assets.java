package com.rubendal.kuroganehammercom.util;

import android.content.res.AssetManager;

import java.io.InputStream;

public class Assets {
    public static String getAsset(AssetManager assets, String file){
        InputStream is;
        try {
            is = assets.open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String content = new String(buffer, "UTF-8");

            //Check for UTF-8 BOM and remove it, it crashes the JSONObject/Array constructors on Android 2.3 - 3.2
            String check = content.substring(0,3);
            byte[] bom = new byte[3];
            bom[0] = (byte)0xEF;
            bom[1] = (byte)0xBB;
            bom[2] = (byte)0xBF;

            if(check.contains(new String(bom))){
                content = content.replace(new String(bom),"");
            }

            return content;
        }catch(Exception e){

        }
        return null;
    }
}
