package com.michal_stasinski.tabu.CRM;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.michal_stasinski.tabu.R;

import java.util.ArrayList;

import static com.michal_stasinski.tabu.SplashScreen.dataDeliveryTextFieldName;

/**
 * Created by win8 on 09.07.2017.
 */

public class OrderZoomPopUp extends Activity {


    private static Context contex;
    private int pos;
    private String receiptWay;
    private String orderNumber;
    private String status;
    private String orderNo;
    private String actualText;
    private String title;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        contex = this;
        setContentView(R.layout.crm_order_zoom_popup);






       /* LinearLayout list = (LinearLayout) findViewById(R.id.list_of_order_for_one_item);
        TextView price = (TextView) findViewById(R.id.order_fb_item_price);
        View div0 = (View) findViewById(R.id.div0);
        TextView order_number = (TextView) findViewById(R.id.order_nr);
        TextView hour_of_deliver = (TextView) findViewById(R.id.hour_of_deliver);
        TextView time_to_finish = (TextView) findViewById(R.id.time_to_finish);
        TextView delivety_method = (TextView)findViewById(R.id.delivety_method);

        TextView address_txt = (TextView) findViewById(R.id.address_txt);*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            receiptWay = bundle.getString("receiptWay");
            orderNumber = bundle.getString("orderNumber");

            status = bundle.getString("status");
        }
        ArrayList<Object> getOrder = (ArrayList<Object>) bundle.getSerializable("getOrder"+0);

        Log.i("informacja", "  getOrder  " +  getOrder.get(1));
        TextView delivety_method = (TextView) findViewById(R.id.crm_delivety_method_zoom);
        TextView orderNr = (TextView) findViewById(R.id.crm_order_nr_zoom);
        TextView crm_status_zoom = (TextView) findViewById(R.id.crm_status_zoom);

        delivety_method.setText(receiptWay);
        orderNr.setText(orderNumber);
        if (status.equals("0")) {
            crm_status_zoom.setText("NOWE");
        }

        if (status.equals("1")) {
            crm_status_zoom.setText("PRZYJĘTE");
        }

        if (status.equals("2")) {
            crm_status_zoom.setText("W REALIZACJI");
        }

        if (status.equals("3")) {
            crm_status_zoom.setText("DO ODBIORU");
        }
        if (status.equals("4")) {
            crm_status_zoom.setText("W DOSTAWIE");
        }

    }
}