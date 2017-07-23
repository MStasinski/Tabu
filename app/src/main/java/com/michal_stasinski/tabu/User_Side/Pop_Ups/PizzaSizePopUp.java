package com.michal_stasinski.tabu.User_Side.Pop_Ups;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.michal_stasinski.tabu.User_Side.Adapters.PizzaSizeAdapter;
import com.michal_stasinski.tabu.User_Side.Order_Composer_details_Pizza;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import static com.michal_stasinski.tabu.SplashScreen.pizzaList;
import static com.michal_stasinski.tabu.SplashScreen.pizzaSizesList;
import static com.michal_stasinski.tabu.SplashScreen.pizzaSizes_CheckMark;

/**
 * Created by win8 on 18.04.2017.
 */

public class PizzaSizePopUp extends Activity {

    private static int size = 0;
    private static Context contex;
    private PizzaSizeAdapter adapterek;
    private BounceListView mListViewMenu;
    private int chooseSize = 0;
    private static final String TAG = PizzaSizePopUp.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Log.i("informacja", "onCreate");
        super.onCreate(savedInstanceState);
        contex = this;
        setContentView(R.layout.bounce_list_view);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //getWindow().setLayout((int) (width * .8), (int) (height * 0.4));
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        int size = 0;
        int positionIteminMenuListView = 0;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            for (int i = 0; i < pizzaSizes_CheckMark.size(); i++) {
                pizzaSizes_CheckMark.set(i, 0);
            }
            positionIteminMenuListView = bundle.getInt("position");
            size = bundle.getInt("size");
            Order_Composer_details_Pizza.setSize(size);
            pizzaSizes_CheckMark.set(size, 1);
        }

        adapterek = new PizzaSizeAdapter(contex, positionIteminMenuListView, pizzaList, pizzaSizesList, pizzaSizes_CheckMark);

        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        mListViewMenu.setAdapter(adapterek);
        mListViewMenu.setScrollingCacheEnabled(false);

        mListViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                for (int i = 0; i < pizzaSizes_CheckMark.size(); i++) {
                    pizzaSizes_CheckMark.set(i, 0);
                }


                /*przekazanaie rozmiaru do OrderCompozerPizza*/
                Order_Composer_details_Pizza.setSize(position);


                Intent intent = new Intent();
                intent.setAction(Order_Composer_details_Pizza.ORDER_COMPOSER_CHANGE);
                sendBroadcast(intent);


                pizzaSizes_CheckMark.set(position, 1);
               // chooseSize = position;
                adapterek.notifyDataSetChanged();

                Object listItem = mListViewMenu.getItemAtPosition(position);
            }
        });

    }

/*
    public int getChooseSize() {
        return chooseSize;
    }

    public void setChooseSize(int chooseSize) {
        this.chooseSize = chooseSize;
    }*/


}
