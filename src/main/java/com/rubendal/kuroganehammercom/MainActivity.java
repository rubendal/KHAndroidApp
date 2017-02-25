package com.rubendal.kuroganehammercom;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import com.rubendal.kuroganehammercom.asynctask.CharacterAsyncTask;
import com.rubendal.kuroganehammercom.asynctask.KHUpdate;
import com.rubendal.kuroganehammercom.util.Storage;

public class MainActivity extends AppCompatActivity {

    public GridView grid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle(getString(R.string.app_name));

        Storage.Initialize(this);

        updateData();
    }

    public void updateData(){
        grid = (GridView)findViewById(R.id.grid);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        int x = 300;

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (width <= 800) {
                x = (width / 2) - 5;
            } else if (width > 800 && width <= 1800) {
                x = 350;
            } else {
                x = 400;
            }
        }else{
            if (height <= 800) {
                x = (height / 2) - 5;
            } else if (height > 800 && height <= 1800) {
                x = 350;
            } else {
                x = 400;
            }
        }

        grid.setNumColumns(width / x);

        CharacterAsyncTask c = new CharacterAsyncTask(this,x);
        c.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.update:
                KHUpdate kh = new KHUpdate(this,"Syncing with KH API...");
                kh.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
