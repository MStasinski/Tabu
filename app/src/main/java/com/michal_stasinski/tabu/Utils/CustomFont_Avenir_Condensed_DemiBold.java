package com.michal_stasinski.tabu.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by win8 on 23.06.2017.
 */

public class CustomFont_Avenir_Condensed_DemiBold extends android.support.v7.widget.AppCompatTextView {

    public CustomFont_Avenir_Condensed_DemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/AvenirNextCondensed-DemiBold.ttf"));
    }


}