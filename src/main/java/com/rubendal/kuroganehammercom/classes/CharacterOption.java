package com.rubendal.kuroganehammercom.classes;

import android.graphics.Bitmap;

//Class used in section selection, previously showed the main image but due to copyright issues they were removed
public class CharacterOption {

    public Bitmap image;
    public String text;
    public boolean isImage = false;

    public CharacterOption(String text){
        this.text = text;
        this.isImage = false;
    }

    public CharacterOption(Bitmap image){
        this.image = image;
        this.isImage = true;
    }

}
