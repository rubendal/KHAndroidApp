package com.rubendal.kuroganehammercom.smash4.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rubendal.kuroganehammercom.R;
import com.rubendal.kuroganehammercom.interfaces.KHFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MemeDisplayFragment extends KHFragment {

    private String title;
    private String fileName;

    public MemeDisplayFragment(){

    }

    public static MemeDisplayFragment newInstance(String title, String fileName){
        MemeDisplayFragment fragment = new MemeDisplayFragment();
        Bundle args = new Bundle();
        args.putSerializable("title", title);
        args.putSerializable("fileName", fileName);
        fragment.title = title;
        fragment.fileName = fileName;
        return fragment;
    }

    @Override
    public void updateData() {

    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.meme_pic_display, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            this.title = getArguments().getString("title");
            this.fileName = getArguments().getString("fileName");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        load();
    }

    private void load(){
        InputStream is;
        Bitmap image = null;
        try {
            is = getContext().getAssets().open(fileName);
            image = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageView imageView = (ImageView)getView().findViewById(R.id.image);
        imageView.setImageBitmap(image);
    }
}
