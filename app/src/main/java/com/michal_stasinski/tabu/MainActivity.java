package com.michal_stasinski.tabu;


import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.michal_stasinski.tabu.CRM.CRM_MainMenu_ListView;
import com.michal_stasinski.tabu.Menu.Adapters.CustomDrawerAdapter;
import com.michal_stasinski.tabu.Menu.ShopingCard;
import com.michal_stasinski.tabu.Utils.Check_if_the_restaurant_is_open;
import com.michal_stasinski.tabu.Menu.DataForDelivery;
import com.michal_stasinski.tabu.Menu.LeftDrawerMenu.MenuFragment;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.CustomDialogClass;
import com.michal_stasinski.tabu.Utils.FontFitTextView;
import com.michal_stasinski.tabu.Utils.OrderComposerUtils;

import java.util.ArrayList;

import static com.michal_stasinski.tabu.SplashScreen.ACTULAL_STATE_OF_LOGGED;
import static com.michal_stasinski.tabu.SplashScreen.IS_LOGGED_IN;
import static com.michal_stasinski.tabu.SplashScreen.orderList;


public class MainActivity extends AppCompatActivity {
    public static final String FIREBASE_CHANGED = "firebase_changed";
    public static int CHOICE_ACTIVITY;

    private DatabaseReference myRef;
    private LinearLayout content;
    private ArrayList<MenuItemProduct> menuItem;
    private ImageView drawerBackgroud;
    private MenuFragment fragment;
    private MyReceiver myReceiver;

    protected Toolbar mToolBar;

    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle mToggle;
    protected int currentActivity = 0;
    protected int choiceActivity = 0;
    private Boolean menuIsClick = false;
    protected BounceListView mListViewDrawer;
    protected BounceListView mListViewMenu;


    public static String[] largeTextArr = {
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

        if (IS_LOGGED_IN) {
            setTheme(R.style.AppThemeStaffLogged);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        Log.v("Example", "onCreate");
        getIntent().setAction("Already created");

        setContentView(R.layout.activity_main);


        //-------------------------- bottom menu button------------------------------------
        ButtonBarLayout bottom_action_bar_btn0 = (ButtonBarLayout) findViewById(R.id.bottom_action_bar_btn0);
        bottom_action_bar_btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START, true);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });

        ButtonBarLayout bottom_action_bar_btn1 = (ButtonBarLayout) findViewById(R.id.bottom_action_bar_btn1);
        bottom_action_bar_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (orderList.size() == 0) {

                    CustomDialogClass customDialog = new CustomDialogClass(MainActivity.this);
                    customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    customDialog.show();
                    customDialog.setTitleDialogText("Twoj koszyk jest pusty");
                    customDialog.setDescDialogText("Wybierz coś z menu");

                } else {

                    Intent intent = new Intent();
                    intent.setClass(getBaseContext(), ShopingCard.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.from_right, R.anim.to_left);

                }
            }
        });

        FragmentManager fragmentManager = getFragmentManager();
        fragment = MenuFragment.newInstance(CHOICE_ACTIVITY);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contener, fragment).commit();
        Check_if_the_restaurant_is_open time_open_close = new Check_if_the_restaurant_is_open();

        if (!time_open_close.getRestaurantIsOpen()) {
            CustomDialogClass customDialog = new CustomDialogClass(MainActivity.this);
            customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customDialog.show();
            customDialog.setTitleDialogText("UWAGA");
            customDialog.setDescDialogText("Zamówienia online nieczynne.\nZapraszamy w godzinach otwarcia.");
        }

    }


    @Override
    protected void onResume() {
        Log.i("informacja", "onResume");

        String action = getIntent().getAction();
        // Prevent endless loop by adding a unique action, don't restart if action is present
        if (action == null || !action.equals("Already created")) {

            if (ACTULAL_STATE_OF_LOGGED != IS_LOGGED_IN) {
                recreate();
                Log.i("informacja", ACTULAL_STATE_OF_LOGGED + "Force restart" + IS_LOGGED_IN);
                ACTULAL_STATE_OF_LOGGED= IS_LOGGED_IN;

            }
        }
        // Remove the unique action so the next time onResume is called it will restart
        else
            getIntent().setAction(null);

        super.onResume();


        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter(FIREBASE_CHANGED);
        registerReceiver(myReceiver, intentFilter);

        currentActivity = CHOICE_ACTIVITY;
        FontFitTextView info_about_price = (FontFitTextView) findViewById(R.id.info_about_price_and_quantity);
        info_about_price.setText("(" + OrderComposerUtils.sum_of_all_quantities() + ") " + OrderComposerUtils.sum_of_all_the_prices() + " zł");

        mToolBar = (Toolbar) findViewById(R.id.nav_action);
        mToolBar.setBackgroundResource(R.color.colorWhite);

        setSupportActionBar(mToolBar);

        drawerBackgroud = (ImageView) findViewById(R.id.black_shape_background);
        drawerBackgroud.setAlpha(0.f);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerSlide(View view, float slideOffset) {
                drawerBackgroud.setAlpha(slideOffset);
            }

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mDrawerLayout.setEnabled(false);
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                if (menuIsClick == true) {
                    if (currentActivity != choiceActivity && choiceActivity < 5) {


                        FragmentManager fragmentManager = getFragmentManager();
                        CHOICE_ACTIVITY = choiceActivity;
                        currentActivity = choiceActivity;

                        fragment = MenuFragment.newInstance(choiceActivity);
                        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.from_left, R.anim.to_right);
                        transaction.replace(R.id.fragment_contener, fragment);
                        transaction.commit();

                    } else if (currentActivity != choiceActivity && choiceActivity == 5) {

                        Intent intent = new Intent();
                        currentActivity = 5;
                        intent.setClass(getBaseContext(), DataForDelivery.class);
                        startActivity(intent);

                        overridePendingTransition(R.anim.from_right, R.anim.to_left);
                    }
                    menuIsClick = false;
                }
            }
        };

        TextView toolBarTitle = (TextView) findViewById(R.id.toolBarTitle);
        toolBarTitle.setText((largeTextArr[CHOICE_ACTIVITY]).toString());

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(false); // show or hide the default home button
        getSupportActionBar().setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //getSupportActionBar().hide();

        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
        mListViewDrawer = (BounceListView) findViewById(R.id.left_drawer);
        mListViewDrawer.setScrollingCacheEnabled(false);
        mDrawerLayout.setEnabled(false);


        CustomDrawerAdapter adapter = new CustomDrawerAdapter(this, largeTextArr, imgid);


        ButtonBarLayout button_share = (ButtonBarLayout) findViewById(R.id.bottom_share);
        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBodyText = "Check it out. Your message goes here";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject here");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                startActivity(Intent.createChooser(sharingIntent, "Shearing Option"));
            }
        });


        ButtonBarLayout button_info = (ButtonBarLayout) findViewById(R.id.bottom_info);

        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Rezygnujemy z drawera
                // mDrawerLayout.openDrawer(GravityCompat.END, true);
                //mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                Intent intent = new Intent();
                intent.setClass(getBaseContext(), CRM_MainMenu_ListView.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_right, R.anim.to_left);

            }
        });

        mListViewDrawer.setAdapter(adapter);
        mListViewDrawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, final int position, long arg) {

                menuIsClick = true;
                choiceActivity = position;
                if (currentActivity != choiceActivity) {
                    mDrawerLayout.setEnabled(false);
                    mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                }
                mDrawerLayout.closeDrawer(GravityCompat.START, true);
            }
        });
    }

    @Override
    protected void onPause() {
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
            myReceiver = null;
        }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (myReceiver != null) {
            unregisterReceiver(myReceiver);
            myReceiver = null;
        }
        super.onStop();
    }

    /*chowanie drawera na click outside*/
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                View content = findViewById(R.id.left_drawer);
                mDrawerLayout.closeDrawer(GravityCompat.START, true);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(FIREBASE_CHANGED)) {
                if (fragment != null) {
                    fragment.reloadBase();
                }

            }
        }
    }
}
