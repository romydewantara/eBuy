package com.example.mobile.ebuy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobile.ebuy.AboutActivity;
import com.example.mobile.ebuy.R;
import com.example.mobile.ebuy.fragment.StoreFragment;

public class MainActivity extends AppCompatActivity {

    MenuItem mMenuItem;
    DrawerLayout mDrawerLayout;
    //textView utk Search Result

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        setSupportActionBar(toolbar);

        mDrawerLayout = findViewById(R.id.dl_main);
        final NavigationView navigationView = findViewById(R.id.nv_main);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {
                mMenuItem = menuItem;
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                return true;
            }
        });

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(final View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(final View drawerView) {
                super.onDrawerClosed(drawerView);

                if (null != mMenuItem) {
                    switchNavigationMenu(mMenuItem.getItemId());
                }
            }

            @Override
            public void onDrawerSlide(final View drawerView, final float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);

                if (null != drawerView && drawerView == navigationView) {
                    super.onDrawerSlide(drawerView, 0);
                } else {
                    super.onDrawerSlide(drawerView, slideOffset);
                }
            }
        };

        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
    }

    @Override
    protected void onPostCreate(@Nullable final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarDrawerToggle.syncState();
        showStoreFragment();
    }

    //Navigation Menu option
    public void switchNavigationMenu(final int itemId) {
        switch (itemId) {
            case R.id.menu_store: {
                showStoreFragment();
                break;
            }
            case R.id.menu_transaksi: {
                showTransaksiActivity();
                break;
            }
            case R.id.menu_logout: {
                showLogoutActivity();
                break;
            }
            case R.id.menu_about: {
                showAboutActivity();
                break;
            }
        }
    }

    private int getStatusBarHeight() {
        final int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? getResources().getDimensionPixelSize(resourceId) : 0;
    }

    //open store fragment when activity start
    private void showStoreFragment() {
        final String tag = StoreFragment.class.getSimpleName();

        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            final Fragment fragment = new StoreFragment();
            final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fl_main, fragment, tag);
            fragmentTransaction.commit();
        }
    }

    private void showTransaksiActivity(){
        Intent intent = new Intent(getApplicationContext(), TransaksiActivity.class);
        startActivity(intent);
    }

    private void showAboutActivity(){
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }

    private void showLogoutActivity() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_exit, null);
        Button btn_yes = view.findViewById(R.id.btn_yes);
        Button btn_no = view.findViewById(R.id.btn_no);
        alertDialog.setView(view);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finishAffinity();
                dialog.dismiss();
            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.dialog_exit, null);
        Button btn_yes = view.findViewById(R.id.btn_yes);
        Button btn_no = view.findViewById(R.id.btn_no);
        alertDialog.setView(view);
        final AlertDialog dialog = alertDialog.create();
        dialog.show();

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
                dialog.dismiss();
            }
        });
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}