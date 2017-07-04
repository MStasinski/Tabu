package com.michal_stasinski.tabu.CRM;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;

import com.michal_stasinski.tabu.CRM.Order.SimpleOrderListFB;
import com.michal_stasinski.tabu.R;

public class RestaurantOwner extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crm_restaurant_owner);


        ButtonBarLayout order_cycle  = ( ButtonBarLayout) findViewById(R.id.order_cycle);
        order_cycle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getBaseContext(), SimpleOrderListFB.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_right, R.anim.to_left);


            }
        });

        Button closeButton = (Button) findViewById(R.id.closeBtn);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.from_left, R.anim.to_right);

            }
        });

    }

}
