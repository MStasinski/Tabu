package com.michal_stasinski.tabu.Menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.michal_stasinski.tabu.Menu.Adapters.SauceAdapter;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import static com.michal_stasinski.tabu.SplashScreen.pizzaSauces;

/**
 * Created by win8 on 28.05.2017.
 */

public class EditTextPopUp extends Activity {



    private static Context contex;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        contex = this;
        setContentView(R.layout.edit_text_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.widthPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * 0.8));




    }
}
