package com.michal_stasinski.tabu;


import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.michal_stasinski.tabu.Menu.Adapters.CustomDrawerAdapter;
import com.michal_stasinski.tabu.Menu.DataForDeliveryListView;
import com.michal_stasinski.tabu.Menu.LeftDrawerMenu.MenuFragment;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.Menu.ShopingCardListView;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.CustomTextView;
import com.michal_stasinski.tabu.Utils.OrderComposerUtils;
import com.michal_stasinski.tabu.Utils.TintIcon;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    public static final String FIREBASE_CHANGED = "firebase_changed";
    public static int CHOICE_ACTIVITY;

    private DatabaseReference myRef;
    private LinearLayout content;
    private ArrayList<MenuItemProduct> menuItem;
    private ImageView drawerBackgroud;
    private MenuFragment fragment;

    protected Toolbar mToolBar;
    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mToggle;
    protected int currentActivity = 0;
    protected int choicetActivity = 0;

    protected BounceListView mListViewDrawer;
    protected BounceListView mListViewMenu;

    protected int[] colorToolBar = {
            R.color.color_AKTUALNOSCI,
            R.color.color_KONTAKTY,
            R.color.color_PIZZA,
            R.color.color_STARTERY,
            R.color.color_SALATKI,
            R.color.color_ZUPY
    };

    protected String[] largeTextArr = {
            "START",
            "PIZZA",
            "SAŁATKI",
            "SOSY",
            "KONTAKT",
            "DANE DO DOSTAWY"
    };

    protected Integer[] imgid = {
            R.mipmap.home_icon,
            R.mipmap.pizza_icon,
            R.mipmap.salad_icon,
            R.mipmap.sauce_icon,
            R.mipmap.contact_icon,
            R.mipmap.person_icon

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        MainActivity.MyReceiver myReceiver = new MainActivity.MyReceiver();
        IntentFilter intentFilter = new IntentFilter(FIREBASE_CHANGED);
        registerReceiver(myReceiver, intentFilter);

       /* ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.bounce_list_view);
        View inflated = stub.inflate();*/


        //-------------------------- bottom menu button------------------------------------
        ButtonBarLayout bottom_action_bar_btn0 = (ButtonBarLayout) findViewById(R.id.bottom_action_bar_btn0);
        bottom_action_bar_btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START, true);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                // Intent intent = new Intent();
                //intent.setClass(getBaseContext(), DataForDeliveryListView.class);
                //startActivity(intent);
            }
        });

        ButtonBarLayout bottom_action_bar_btn1 = (ButtonBarLayout) findViewById(R.id.bottom_action_bar_btn1);
        bottom_action_bar_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), ShopingCardListView.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        CustomTextView info_about_price = (CustomTextView) findViewById(R.id.info_about_price_and_quantity);
        info_about_price.setText("(" + OrderComposerUtils.sum_of_all_quantities() + ") " + OrderComposerUtils.sum_of_all_the_prices() + " zł");

        /* }

    @Override
    protected void onStart() {
        super.onStart();*/

        mToolBar = (Toolbar) findViewById(R.id.nav_action);
        mToolBar.setBackgroundResource(R.color.colorWhite);
        setSupportActionBar(mToolBar);
        // content = (LinearLayout) findViewById(R.id.content_frame);
        drawerBackgroud = (ImageView) findViewById(R.id.black_shape_background);
        drawerBackgroud.setAlpha(0.f);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerSlide(View view, float slideOffset) {
                drawerBackgroud.setAlpha(slideOffset);
                // mListViewMenu.setAlpha(1 - slideOffset);
                //content.setAlpha(1 - slideOffset);
                //imageDrawer.setAlpha(slideOffset);
                // mtoolBarLayout.setAlpha(1 - slideOffset);
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mDrawerLayout.setEnabled(false);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                if (currentActivity != choicetActivity && choicetActivity != 5) {

                    FragmentManager fragmentManager = getFragmentManager();
                    CHOICE_ACTIVITY = choicetActivity;
                    fragment = MenuFragment.newInstance(choicetActivity);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_contener, fragment).commit();

                    //TODO dodaj animacje
                    //startActivity(intent);
                }

                if (currentActivity != choicetActivity && choicetActivity == 5) {
                    Intent intent = new Intent();
                    choicetActivity = 0;
                    intent.setClass(getBaseContext(), DataForDeliveryListView.class);
                    startActivity(intent);
                }
            }
        };
        mDrawerLayout.addDrawerListener(mToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true); // show or hide the default home button
        getSupportActionBar().setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        // getSupportActionBar().hide();


        mToggle.syncState();
        mListViewDrawer = (BounceListView) findViewById(R.id.left_drawer);
        mListViewDrawer.setScrollingCacheEnabled(false);
        mDrawerLayout.setEnabled(false);
        CustomDrawerAdapter adapter = new CustomDrawerAdapter(this, largeTextArr, imgid);
        TextView toolBarTitle = (TextView) findViewById(R.id.toolBarTitle);
        toolBarTitle.setText((largeTextArr[currentActivity]).toString());
        mListViewDrawer.setAdapter(adapter);
        mListViewDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, final int position, long arg) {
                choicetActivity = position;
                if (currentActivity != choicetActivity) {
                    //TODO odblokuj to
                    // BounceListView v = (BounceListView) findViewById(R.id.left_drawer);
                    //  v.setEnabled(false);
                    // mDrawerLayout.setEnabled(false);
                    // mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }
                mDrawerLayout.closeDrawer(GravityCompat.START, true);
            }
        });


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
        } else if (id == R.id.right_menu) {
            mDrawerLayout.openDrawer(GravityCompat.END, true);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START, true);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // na stworzenie menu --dodanie przycisków w menu
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem menuItem1 = menu.findItem(R.id.right_menu);
        MenuItem menuItem2 = menu.findItem(R.id.share);
        if (menuItem1 != null) {
            TintIcon.tintMenuIcon(MainActivity.this, menuItem1, R.color.colorPrimary);
        }

        if (menuItem2 != null) {
            TintIcon.tintMenuIcon(MainActivity.this, menuItem2, R.color.colorPrimary);
        }
        return true;
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(FIREBASE_CHANGED)) {
                // String message = intent.getStringExtra(MSG_FIELD);
                // tvMessage.setText(message);
                if(fragment!=null){

                    fragment.reloadBase();
                }
                Log.i("informacja", "no cos tam sie zmieniło ponoć w bazie");
            }
        }
    }
}
