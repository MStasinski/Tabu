package com.michal_stasinski.tabu.Menu;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import java.util.ArrayList;
import java.util.Map;


public class BaseMenu extends AppCompatActivity {

    private DatabaseReference myRef;
    private LinearLayout content;
    private ArrayList<MenuItemProduct> menuItem;

    protected Toolbar mToolBar;
    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mToggle;
    protected int currentActivity = 0;
    protected int choicetActivity = 0;
    protected BounceListView mListViewDrawer;
    protected BounceListView mListViewMenu;

    public int[] colorToolBar = {
            R.color.color_AKTUALNOSCI,
            R.color.color_KONTAKTY,
            R.color.color_PIZZA,
            R.color.color_STARTERY,
            R.color.color_SALATKI,
            R.color.color_ZUPY,
            R.color.color_ALFORNO,
            R.color.color_MAKARONY,
            R.color.color_DRUGIE_DANIE,
            R.color.color_DESERY,
    };

    public String[] largeTextArr = {
            "AKTUALNOŚCI",
            "KONTAKT",
            "PIZZA",
            "STARTERY",
            "SAŁATKI",
            "ZUPY",
            "MAKARONY ZAPIEKANE",
            "MAKARONY",
            "DRUGIE DANIA",
            "NAPOJE i DESERY"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_menu);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
      /*  ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.left_bounce_list_view);
        View inflated = stub.inflate();*/


    }

    @Override
    protected void onStart() {
        super.onStart();
        mToolBar = (Toolbar) findViewById(R.id.nav_action);
        mToolBar.setBackgroundResource(colorToolBar[currentActivity]);
        setSupportActionBar(mToolBar);
        content = (LinearLayout) findViewById(R.id.content_frame);
        //base_menu.xml
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerSlide(View view, float slideOffset) {
               // imgBackground.setAlpha(slideOffset);
                // mListViewMenu.setAlpha(1 - slideOffset);
                content.setAlpha(1 - slideOffset);
                //imageDrawer.setAlpha(slideOffset);
                // mtoolBarLayout.setAlpha(1 - slideOffset);
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);

                if (currentActivity != choicetActivity) {
                    mDrawerLayout.setEnabled(false);

                }
            }
        };
        mDrawerLayout.addDrawerListener(mToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true); // show or hide the default home button
        getSupportActionBar().setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToggle.syncState();
        mListViewDrawer = (BounceListView) findViewById(R.id.left_drawer);
        mListViewDrawer.setScrollingCacheEnabled(false);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBodyText = "Check it out. Your message goes here";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBodyText);
            startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
            return true;
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START, true);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // na stworzenie menu --dodanie przycisków w menu
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
