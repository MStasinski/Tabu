package com.michal_stasinski.tabu.Utils;

import android.app.Application;

import com.michal_stasinski.tabu.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by win8 on 26.07.2017.
 */

public class CustomFontApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // initalize Calligraphy
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("AvenirNextCondensed-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}