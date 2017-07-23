package com.michal_stasinski.tabu.User_Side.Pop_Ups;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.michal_stasinski.tabu.User_Side.Adapters.SauceAdapter;
import com.michal_stasinski.tabu.User_Side.Models.MenuItemProduct;
import com.michal_stasinski.tabu.User_Side.Order_Composer_details_Pizza;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import static com.michal_stasinski.tabu.SplashScreen.pizzaSauces;

/**
 * Created by win8 on 29.04.2017.
 */

public class SaucePopUp extends Activity {

    public static SauceAdapter saucePopUpAdapter;

    private static Context contex;
    private BounceListView mListViewMenu;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        contex = this;
        setContentView(R.layout.bounce_list_view);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        int size = 0;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        size = bundle.getInt("size");

        saucePopUpAdapter = new SauceAdapter(contex, pizzaSauces);


        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        mListViewMenu.setAdapter(saucePopUpAdapter);
        mListViewMenu.setScrollingCacheEnabled(false);
        mListViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Object listItem = mListViewMenu.getItemAtPosition(position);
                MenuItemProduct item = (MenuItemProduct) saucePopUpAdapter.getItem(position);
                if (!item.getSold()) {
                    saucePopUpAdapter.setChoooseHowManyItemYouOrder(position);
                }

                /*przekazanaie rozmiaru do OrderCompozerPizza*/
                //Order_Composer_details_Pizza.setSize(position);

                Intent intent = new Intent();
                intent.setAction(Order_Composer_details_Pizza.ORDER_COMPOSER_CHANGE);
                sendBroadcast(intent);

                saucePopUpAdapter.notifyDataSetChanged();
            }
        });
    }
}
