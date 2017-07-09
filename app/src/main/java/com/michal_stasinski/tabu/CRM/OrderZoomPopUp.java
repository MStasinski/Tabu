package com.michal_stasinski.tabu.CRM;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.michal_stasinski.tabu.R;

/**
 * Created by win8 on 09.07.2017.
 */

public class OrderZoomPopUp extends Activity {


    private static Context contex;
    private int pos;
    private String actualText;
    private String title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        contex = this;
        setContentView(R.layout.crm_order_zoom_popup);




        /*Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            pos = bundle.getInt("position");

        }*/
        TextView order_fb_payment_method = (TextView) findViewById(R.id.order_fb_payment_method_zoom);
        order_fb_payment_method.setText("t");

        LinearLayout list = (LinearLayout) findViewById(R.id.list_of_order_for_one_item);
        TextView price = (TextView) findViewById(R.id.order_fb_item_price);
        View div0 = (View) findViewById(R.id.div0);
        TextView order_number = (TextView) findViewById(R.id.order_nr);
        TextView hour_of_deliver = (TextView) findViewById(R.id.hour_of_deliver);
        TextView time_to_finish = (TextView) findViewById(R.id.time_to_finish);
        TextView delivety_method = (TextView)findViewById(R.id.delivety_method);

        TextView address_txt = (TextView) findViewById(R.id.address_txt);

    }

    @Override
    protected void onResume() {
        super.onResume();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT,  LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}