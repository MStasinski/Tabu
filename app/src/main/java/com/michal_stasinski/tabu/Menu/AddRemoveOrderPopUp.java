package com.michal_stasinski.tabu.Menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ButtonBarLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.CustomFont_Avenir_Medium;
import com.michal_stasinski.tabu.Utils.MathUtils;

import static com.michal_stasinski.tabu.SplashScreen.orderList;

/**
 * Created by win8 on 29.04.2017.
 */

public class AddRemoveOrderPopUp extends Activity {


    private static Context contex;
    private int quantity = 0;
    private int position = 0;
    private float price;
    private String name;
    private TextView quantity_num;
    private ButtonBarLayout actualBtn;
    private TextView actualTxt;
    private Button removeAllBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        contex = this;
        setContentView(R.layout.activity_add_remove_order_pop_up);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        position = bundle.getInt("position") - 8;
        quantity = bundle.getInt("quantity");
        name = bundle.getString("name");
        price = bundle.getFloat("price");

        quantity_num = (TextView) findViewById(R.id.quantity_num);
        quantity_num.setText(String.valueOf(quantity));
        // orderList.remove(position);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //getWindow().setLayout((int) (width * .8), (int) (height * 0.4));
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        CustomFont_Avenir_Medium titleTxt = (CustomFont_Avenir_Medium) findViewById(R.id.dish_title);
        titleTxt.setText(name);

        removeAllBtn = (Button) findViewById(R.id.removeAll);
        actualBtn = (ButtonBarLayout) findViewById(R.id.actual_order_button);
        actualTxt = (TextView) findViewById(R.id.actualTxt);

        String output = MathUtils.formatDecimal(quantity * price, 2);
        actualTxt.setText("AKTUALIZUJ KOSZYK " + output);
        Button removeBtn = (Button) findViewById(R.id.removeItem);
        Button addBtn = (Button) findViewById(R.id.addItem);

        actualBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (quantity == 0) {
                    orderList.remove(position);
                } else {
                    orderList.get(position).setQuantity(quantity);
                }
                finish();
            }
        });

        removeAllBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                orderList.get(position).setQuantity(0);
                orderList.remove(position);
                finish();
            }
        });
        addBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                quantity += 1;
                String output = MathUtils.formatDecimal(quantity * price, 2);
                actualTxt.setText("AKTUALIZUJ KOSZYK " + output);
                quantity_num.setText(String.valueOf(quantity));
            }
        });

        removeBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (quantity > 0) {
                    quantity -= 1;
                    String output = MathUtils.formatDecimal(quantity * price, 2);
                    actualTxt.setText("AKTUALIZUJ KOSZYK " + output);
                    quantity_num.setText(String.valueOf(quantity));
                }
            }
        });
    }
}