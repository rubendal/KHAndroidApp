package com.rubendal.kuroganehammercom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rubendal.kuroganehammercom.dbfz.classes.DBCharacter;
import com.rubendal.kuroganehammercom.dbfz.fragments.DBCharacterMainFragment;
import com.rubendal.kuroganehammercom.rivals.fragments.RivalsCharacterMainFragment;
import com.rubendal.kuroganehammercom.util.KHUpdate;
import com.rubendal.kuroganehammercom.smash4.calculator.CalculatorFragment;
import com.rubendal.kuroganehammercom.smash4.calculator.asynctask.StartCalculatorAsyncTask;
//import com.rubendal.kuroganehammercom.smash4.fragments.AttributeMainFragment;
import com.rubendal.kuroganehammercom.smash4.fragments.AttributeMainFragment;
import com.rubendal.kuroganehammercom.smash4.fragments.FormulaFragment;
import com.rubendal.kuroganehammercom.interfaces.KHFragment;
import com.rubendal.kuroganehammercom.smash4.fragments.MainFragment;
import com.rubendal.kuroganehammercom.interfaces.NavigationFragment;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.util.UserPref;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public KHFragment currentFragment;
    private int id = 0;

    private MenuItem smash4Nav;
    private MenuItem dbfzNav;
    private MenuItem roaNav;

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

        Menu navMenu = navigationView.getMenu();

        smash4Nav = navMenu.findItem(R.id.smash4);
        dbfzNav = navMenu.findItem(R.id.dbfz);
        roaNav = navMenu.findItem(R.id.roa);

        Storage.Initialize(this);
        UserPref.Initialize(this);

        switch(UserPref.getInitialGame()){
            case "RoA":
                loadInitialFragment(RivalsCharacterMainFragment.newInstance());
                break;
            case "DBFZ":
                loadInitialFragment(DBCharacterMainFragment.newInstance());
                break;
            default:
                loadInitialFragment(MainFragment.newInstance());
        }
        SetInitialGameIcon();

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
            }else{
                UserPref.setInitialGame(getApplicationContext(), "Smash 4");
                SetInitialGameIcon();
            }
        }
        else if (id == R.id.attributes) {
            if(!(currentFragment instanceof AttributeMainFragment)){
                if(currentFragment instanceof NavigationFragment){
                    replaceFragment(AttributeMainFragment.newInstance());
                }else{
                    loadFragment(AttributeMainFragment.newInstance());
                }
            }
        }
        else if (id == R.id.formulas) {
            if(!(currentFragment instanceof FormulaFragment)){
                if(currentFragment instanceof NavigationFragment){
                    replaceFragment(FormulaFragment.newInstance());
                }else{
                    loadFragment(FormulaFragment.newInstance());
                }
            }
        }
        else if (id == R.id.calculator) {
            if(!(currentFragment instanceof CalculatorFragment)){
                StartCalculatorAsyncTask s = new StartCalculatorAsyncTask(this, currentFragment instanceof NavigationFragment, "Loading calculator");
                s.execute();
            }
        }
        else if(id == R.id.dbfz_characters){
            if(!(currentFragment instanceof DBCharacterMainFragment)){
                if(currentFragment instanceof NavigationFragment){
                    replaceFragment(DBCharacterMainFragment.newInstance());
                }else{
                    loadFragment(DBCharacterMainFragment.newInstance());
                }
            }else{
                UserPref.setInitialGame(getApplicationContext(), "DBFZ");
                SetInitialGameIcon();
            }
        }
        else if(id == R.id.rivals_characters){
            if(!(currentFragment instanceof RivalsCharacterMainFragment)){
                if(currentFragment instanceof NavigationFragment){
                    replaceFragment(RivalsCharacterMainFragment.newInstance());
                }else{
                    loadFragment(RivalsCharacterMainFragment.newInstance());
                }
            }else{
                UserPref.setInitialGame(getApplicationContext(), "RoA");
                SetInitialGameIcon();
            }
        }
        else if (id == R.id.about_credits) {
            startActivity(new Intent(this,AboutActivity.class));
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
        if(getSupportFragmentManager().getBackStackEntryCount() != 0) {
            currentFragment = fragment;
            getSupportFragmentManager().popBackStack();
            id--;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_viewer, fragment, String.valueOf(id)).addToBackStack("").commit();
            setTitle(fragment.getTitle());
            id++;
        }else{
            id--;
            loadInitialFragment(fragment);
        }

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
                switch(UserPref.getInitialGame()){
                    case "DBFZ":
                        if(currentFragment instanceof DBCharacterMainFragment) {
                            super.onBackPressed();
                        }else{
                            loadInitialFragment(DBCharacterMainFragment.newInstance());
                        }
                        break;
                    case "RoA":
                        if(currentFragment instanceof RivalsCharacterMainFragment) {
                            super.onBackPressed();
                        }else{
                            loadInitialFragment(RivalsCharacterMainFragment.newInstance());
                        }
                        break;
                    default:
                        if(currentFragment instanceof MainFragment) {
                            super.onBackPressed();
                        }else{
                            loadInitialFragment(MainFragment.newInstance());
                        }
                }

            } else {
                id--;
                currentFragment = (KHFragment)getSupportFragmentManager().findFragmentByTag(String.valueOf(id-1));
                getSupportFragmentManager().popBackStack();
                setTitle(currentFragment.getTitle());
            }
        }
    }

    private void SetInitialGameIcon(){
        switch(UserPref.getInitialGame()){
            case "DBFZ":
                smash4Nav.setTitle("Smash 4");
                roaNav.setTitle("Rivals of Aether");
                dbfzNav.setTitle("Dragon Ball FighterZ (Startup)");
                //smash4Nav.setIcon(null);
                //roaNav.setIcon(null);
                //dbfzNav.setIcon(R.drawable.ic_star_black_24dp);
                break;
            case "RoA":
                smash4Nav.setTitle("Smash 4");
                roaNav.setTitle("Rivals of Aether (Startup)");
                dbfzNav.setTitle("Dragon Ball FighterZ");
                //smash4Nav.setIcon(null);
                //roaNav.setIcon(R.drawable.ic_star_black_24dp);
                //dbfzNav.setIcon(null);
                break;
            default:
                smash4Nav.setTitle("Smash 4 (Startup)");
                roaNav.setTitle("Rivals of Aether");
                dbfzNav.setTitle("Dragon Ball FighterZ");
                //smash4Nav.setIcon(R.drawable.ic_star_black_24dp);
                //roaNav.setIcon(null);
                //dbfzNav.setIcon(null);
        }
    }
}
