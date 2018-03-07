package com.rubendal.kuroganehammercom.interfaces;

import android.support.v4.app.Fragment;


public abstract class KHFragment extends Fragment {


    public KHFragment(){
        super();
    }

    //Update data after sync
    public abstract void updateData();

    //App title while fragment is active
    public abstract String getTitle();
}
