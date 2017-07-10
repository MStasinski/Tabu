package com.michal_stasinski.tabu.CRM;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michal_stasinski.tabu.R;

import java.util.ArrayList;

import static com.michal_stasinski.tabu.CRM.Order.SimpleOrderListFB.orderFromFB0;
import static com.michal_stasinski.tabu.SplashScreen.dataDeliveryTextFieldName;
import static java.security.AccessController.getContext;

/**
 * Created by win8 on 09.07.2017.
 */

public class OrderZoomPopUp extends Activity {


    private static Context contex;
    private int position;
    private String receiptWay;
    private String orderNumber;
    private int status;
    private int color;
    private String orderNo;
    private String actualText;
    private String title;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        contex = this;
        setContentView(R.layout.crm_order_zoom_popup);


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
            orderNo = bundle.getString("orderNo");
            position = bundle.getInt("position");
            status = bundle.getInt("status");
        }
        ArrayList<Object> getOrder = (ArrayList<Object>) bundle.getSerializable("getOrder" + 0);

        // box.setBackgroundResource(R.drawable.price_shape);

        Button btm = (Button) findViewById(R.id.accept_status);

        btm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //orderFromFB0.get(position);
                Log.i("informacja", "   orderFromFB0.get(position)  " + orderNo);

                String databaseName = "OrdersCurrents";
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child(databaseName).child(orderNo).child("orderStatus").setValue(1);
               changeStatus(1);
            }
        });
        changeStatus(status);




    }

    private void changeStatus(int status_change){
        TextView delivety_method = (TextView) findViewById(R.id.crm_delivety_method_zoom);
        TextView orderNr = (TextView) findViewById(R.id.crm_order_nr_zoom);

        TextView crm_status_zoom = (TextView) findViewById(R.id.crm_status_zoom);
        LinearLayout box = (LinearLayout) findViewById(R.id.crm_status_box_zoom);



        delivety_method.setText(receiptWay);
        orderNr.setText(orderNumber);
        if (status_change == 0) {
            color = R.color.NOWE;
            crm_status_zoom.setText("NOWE");
            orderNr.setText("");
        }

        if (status_change == 1) {
            color = R.color.PRZYJETE;
            crm_status_zoom.setText("PRZYJĘTE");
        }
        if (status_change == 2) {
            color = R.color.WREALIZACJI;
            crm_status_zoom.setText("W REALIZACJI");
        }

        if (status_change == 3) {
            color = R.color.DOODBIORU;
            crm_status_zoom.setText("DO ODBIORU");
        }

        if (status_change == 4) {
            color = R.color.WDOSTAWIE;
            crm_status_zoom.setText("W DOSTAWIE");
        }

          box.getBackground().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
         orderNr.getBackground().setColorFilter(getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);

    }
}