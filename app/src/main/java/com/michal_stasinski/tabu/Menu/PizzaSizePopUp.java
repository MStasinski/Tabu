package com.michal_stasinski.tabu.Menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.michal_stasinski.tabu.Menu.Adapters.PizzaSizeAdapter;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import static com.michal_stasinski.tabu.SplashScreen.pizzaList;
import static com.michal_stasinski.tabu.SplashScreen.pizzaSizes_CheckMark;
import static com.michal_stasinski.tabu.SplashScreen.pizzaSizesList;

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
        int height = dm.widthPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * 0.4));

        int size = 0;
        int positionIteminMenuListView= 0;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            for (int i = 0; i < pizzaSizes_CheckMark.size(); i++) {
                pizzaSizes_CheckMark.set(i, 0);
            }
            positionIteminMenuListView = bundle.getInt("position");
            size = bundle.getInt("size");
            OrderComposerPizza.setSize(size);
            pizzaSizes_CheckMark.set(size, 1);
        }

        adapterek = new PizzaSizeAdapter(contex,positionIteminMenuListView ,pizzaList,pizzaSizesList, pizzaSizes_CheckMark);

        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        mListViewMenu.setAdapter(adapterek);
        mListViewMenu.setScrollingCacheEnabled(false);

        mListViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                for (int i = 0; i < pizzaSizes_CheckMark.size(); i++) {
                    pizzaSizes_CheckMark.set(i, 0);
                }
                OrderComposerPizza.setSize(position);
                pizzaSizes_CheckMark.set(position, 1);
                adapterek.notifyDataSetChanged();

                Object listItem = mListViewMenu.getItemAtPosition(position);
            }
        });

    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("informacja", "PopUp_onDestroy");
    }



    public int getChooseSize() {
        return chooseSize;
    }

    public void setChooseSize(int chooseSize) {
        this.chooseSize = chooseSize;
    }


}
