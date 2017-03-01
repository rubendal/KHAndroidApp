package com.rubendal.kuroganehammercom;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rubendal.kuroganehammercom.asynctask.KHUpdate;
import com.rubendal.kuroganehammercom.fragments.KHFragment;
import com.rubendal.kuroganehammercom.fragments.MainFragment;
import com.rubendal.kuroganehammercom.fragments.NavigationFragment;
import com.rubendal.kuroganehammercom.util.Storage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public KHFragment currentFragment;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Storage.Initialize(this);
        loadInitialFragment(MainFragment.newInstance());
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
                KHUpdate kh = new KHUpdate(currentFragment,"Syncing with KH API...");
                kh.execute();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (id == R.id.characters) {
            if(!(currentFragment instanceof MainFragment)){
                if(currentFragment instanceof NavigationFragment){
                    replaceFragment(MainFragment.newInstance());
                }else{
                    loadFragment(MainFragment.newInstance());
                }
            }
        }
        else if (id == R.id.attributes) {

        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadFragment(KHFragment fragment){
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_viewer, fragment, String.valueOf(id)).addToBackStack("").commit();
        setTitle(fragment.getTitle());
        id++;
    }

    public void replaceFragment(KHFragment fragment){
        currentFragment = fragment;
        getSupportFragmentManager().popBackStack();
        id--;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_viewer, fragment, String.valueOf(id)).addToBackStack("").commit();
        setTitle(fragment.getTitle());
        id++;
    }

    public void loadInitialFragment(KHFragment fragment){
        currentFragment = fragment;
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_viewer, fragment, String.valueOf(id)).commit();
        setTitle(fragment.getTitle());
        id++;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0){
                super.onBackPressed();
            } else {
                id--;
                currentFragment = (KHFragment)getSupportFragmentManager().findFragmentByTag(String.valueOf(id-1));
                getSupportFragmentManager().popBackStack();
                setTitle(currentFragment.getTitle());
            }
        }
    }
}
