package com.rubendal.kuroganehammercom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.rubendal.kuroganehammercom.interfaces.SSBUMainFragment;
import com.rubendal.kuroganehammercom.interfaces.Smash4MainFragment;
import com.rubendal.kuroganehammercom.smash4.asynctask.KHUpdate;
import com.rubendal.kuroganehammercom.smash4.calculator.CalculatorFragment;
import com.rubendal.kuroganehammercom.smash4.calculator.asynctask.StartCalculatorAsyncTask;
//import com.rubendal.kuroganehammercom.smash4.fragments.AttributeMainFragment;
import com.rubendal.kuroganehammercom.smash4.fragments.AttributeMainFragment;
import com.rubendal.kuroganehammercom.smash4.fragments.FormulaFragment;
import com.rubendal.kuroganehammercom.interfaces.KHFragment;
import com.rubendal.kuroganehammercom.smash4.fragments.MainFragment;
import com.rubendal.kuroganehammercom.interfaces.NavigationFragment;
import com.rubendal.kuroganehammercom.smash4.fragments.MainListFragment;
import com.rubendal.kuroganehammercom.ultimate.fragments.SSBUCharacterMainFragment;
import com.rubendal.kuroganehammercom.ultimate.fragments.SSBUCharacterMainListFragment;
import com.rubendal.kuroganehammercom.util.Storage;
import com.rubendal.kuroganehammercom.util.UserPref;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public KHFragment currentFragment;
    private int id = 0;

    private MenuItem smash4Nav;
    private MenuItem ssbuNav;

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
        ssbuNav = navMenu.findItem(R.id.ssbu);

        Storage.Initialize(this);
        UserPref.Initialize(this);

        switch(UserPref.getInitialGame()){
            case "SSBU":
                if(UserPref.usePicsForCharacterList)
                    loadInitialFragment(SSBUCharacterMainFragment.newInstance());
                else
                    loadInitialFragment(SSBUCharacterMainListFragment.newInstance());
                break;
            default:
                if(UserPref.usePicsForCharacterList)
                    loadInitialFragment(MainFragment.newInstance());
                else
                    loadInitialFragment(MainListFragment.newInstance());
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

        boolean close = true;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (id == R.id.characters) {
            if(!(currentFragment instanceof Smash4MainFragment)){
                if(currentFragment instanceof NavigationFragment){
                    if(UserPref.usePicsForCharacterList)
                        replaceFragment(MainFragment.newInstance());
                    else
                        replaceFragment(MainListFragment.newInstance());
                }else{
                    if(UserPref.usePicsForCharacterList)
                        loadFragment(MainFragment.newInstance());
                    else
                        loadFragment(MainListFragment.newInstance());
                }
            }else{
                UserPref.setInitialGame(getApplicationContext(), "Smash 4");
                SetInitialGameIcon();
                close = false;
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
        else if(id == R.id.ssbu_characters){
            if(!(currentFragment instanceof SSBUMainFragment)){
                if(currentFragment instanceof NavigationFragment){
                    if(UserPref.usePicsForCharacterList)
                        replaceFragment(SSBUCharacterMainFragment.newInstance());
                    else
                        replaceFragment(SSBUCharacterMainListFragment.newInstance());
                }else{
                    if(UserPref.usePicsForCharacterList)
                        loadFragment(SSBUCharacterMainFragment.newInstance());
                    else
                        loadFragment(SSBUCharacterMainListFragment.newInstance());
                }
            }else{
                UserPref.setInitialGame(getApplicationContext(), "SSBU");
                SetInitialGameIcon();
                close = false;
            }
        }
        else if (id == R.id.about_credits) {
            startActivity(new Intent(this,AboutActivity.class));
        }
        else if (id == R.id.help) {
            startActivity(new Intent(this, HelpActivity.class));
        }

        if(close)
            drawer.closeDrawer(GravityCompat.START);

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
                    case "SSBU":
                        if(currentFragment instanceof SSBUCharacterMainFragment) {
                            super.onBackPressed();
                        }else{
                            loadInitialFragment(SSBUCharacterMainFragment.newInstance());
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
            case "SSBU":
                smash4Nav.setTitle("Smash 4");
                ssbuNav.setTitle("Smash Ultimate (Startup)");
                break;
            default:
                smash4Nav.setTitle("Smash 4 (Startup)");
                ssbuNav.setTitle("Smash Ultimate");
        }
    }
}
