package com.michal_stasinski.tabu.Menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;

import com.michal_stasinski.tabu.Menu.Adapters.AddonsAdapter;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import java.util.ArrayList;

import static com.michal_stasinski.tabu.SplashScreen.pizzaAddons_CheckMark;
import static com.michal_stasinski.tabu.SplashScreen.pizzaCheeseList;
import static com.michal_stasinski.tabu.SplashScreen.pizzaColdCutsMeatList;
import static com.michal_stasinski.tabu.SplashScreen.pizzaSpices;
import static com.michal_stasinski.tabu.SplashScreen.pizzaWegetables;

/**
 * Created by win8 on 18.04.2017.
 */

public class AddonsPopUp extends Activity {


    private static Context contex;
    public static AddonsAdapter addonsPopUpAdapter;
    private BounceListView mListViewMenu;
    private static final String TAG = PizzaSizePopUp.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        contex = this;
        setContentView(R.layout.activity_addons_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.widthPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * 0.8));

        int size = 0;
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        size = bundle.getInt("size");

        pizzaAddons_CheckMark = new ArrayList<Integer>();

        addonsPopUpAdapter = new AddonsAdapter(this);
        addonsPopUpAdapter.setChooseSize(size);
        for (int i = 0; i < pizzaSpices.size(); i++) {
            if (i == 0) {
                String name = ("PRZYPRAWY");
                MenuItemProduct menuItemProduct0 = new MenuItemProduct();
                menuItemProduct0.setName(name);
                addonsPopUpAdapter.addSectionHeaderItem(menuItemProduct0);
            }
            addonsPopUpAdapter.addItem(pizzaSpices.get(i));
        }

        for (int i = 0; i < pizzaWegetables.size(); i++) {
            if (i == 0) {
                String name = ("WARZYWA I OWOCE");
                MenuItemProduct menuItemProduct1 = new MenuItemProduct();
                menuItemProduct1.setName(name);
                addonsPopUpAdapter.addSectionHeaderItem(menuItemProduct1);
            }
            addonsPopUpAdapter.addItem(pizzaWegetables.get(i));
        }
        for (int i = 0; i < pizzaColdCutsMeatList.size(); i++) {
            if (i == 0) {
                String name = ("MIĘSO I WĘDLINY");
                MenuItemProduct menuItemProduct2 = new MenuItemProduct();
                menuItemProduct2.setName(name);
                addonsPopUpAdapter.addSectionHeaderItem(menuItemProduct2);
            }
            addonsPopUpAdapter.addItem(pizzaColdCutsMeatList.get(i));
        }
        for (int i = 0; i < pizzaCheeseList.size(); i++) {
            if (i == 0) {
                String name = ("SERY");
                MenuItemProduct menuItemProduct3 = new MenuItemProduct();
                menuItemProduct3.setName(name);
                addonsPopUpAdapter.addSectionHeaderItem(menuItemProduct3);
            }
            addonsPopUpAdapter.addItem(pizzaCheeseList.get(i));
        }

        mListViewMenu = (BounceListView) findViewById(R.id.headerListView);
        mListViewMenu.setAdapter(addonsPopUpAdapter);
        mListViewMenu.setScrollingCacheEnabled(false);
        mListViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Object listItem = mListViewMenu.getItemAtPosition(position);
                addonsPopUpAdapter.setChoooseHowManyItemYouOrder(position);
                addonsPopUpAdapter.notifyDataSetChanged();
            }
        });

    }


}
