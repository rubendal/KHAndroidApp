package com.rubendal.kuroganehammercom;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;

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
import com.rubendal.kuroganehammercom.ultimate.asynctask.UltimateKHUpdate;
import com.rubendal.kuroganehammercom.ultimate.fragments.SSBUCharacterDataFragment;
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

        final Activity ref = this;
        Menu menu=navigationView.getMenu();

        SwitchCompat drawerSwitch=(SwitchCompat) MenuItemCompat.getActionView(menu.findItem(R.id.display_mode)).findViewById(R.id.display_mode_switch);

        drawerSwitch.setChecked(UserPref.usePicsForCharacterList);

        drawerSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                UserPref.changeCharacterDisplay(ref, isChecked);
                if (isChecked) {
                    if(currentFragment instanceof MainListFragment){
                        replaceFragment(MainFragment.newInstance());
                    }
                    else if(currentFragment instanceof SSBUCharacterMainListFragment){
                        replaceFragment(SSBUCharacterMainFragment.newInstance());
                    }
                } else {
                    if(currentFragment instanceof MainFragment){
                        replaceFragment(MainListFragment.newInstance());
                    }
                    else if(currentFragment instanceof SSBUCharacterMainFragment){
                        replaceFragment(SSBUCharacterMainListFragment.newInstance());
                    }
                }
            }
        });
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
                if(currentFragment instanceof SSBUCharacterMainFragment || currentFragment instanceof  SSBUCharacterMainListFragment || currentFragment instanceof SSBUCharacterDataFragment){
                    UltimateKHUpdate kh = new UltimateKHUpdate(currentFragment,"Syncing with KH API...");
                    kh.execute();
                }else {
                    KHUpdate kh = new KHUpdate(currentFragment, "Syncing with KH API...");
                    kh.execute();
                }
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
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://rubendal.github.io/SSBU-Calculator/?from=KHApp"));
            startActivity(i);
        }
        else if (id == R.id.s4calculator) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://rubendal.github.io/Sm4sh-Calculator/?from=KHApp"));
            startActivity(i);
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
        else if (id == R.id.display_mode) {
            return false;
        }

        if(close)
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
                        if(currentFragment instanceof SSBUMainFragment) {
                            super.onBackPressed();
                        }else{
                            if(UserPref.usePicsForCharacterList)
                                loadInitialFragment(SSBUCharacterMainFragment.newInstance());
                            else
                                loadInitialFragment(SSBUCharacterMainListFragment.newInstance());
                        }
                        break;
                    default:
                        if(currentFragment instanceof Smash4MainFragment) {
                            super.onBackPressed();
                        }else{
                            if(UserPref.usePicsForCharacterList)
                                loadInitialFragment(MainFragment.newInstance());
                            else
                                loadInitialFragment(MainListFragment.newInstance());
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
