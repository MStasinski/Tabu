package com.michal_stasinski.tabu.Menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.michal_stasinski.tabu.Menu.Adapters.OrderComposerListViewAdapter;
import com.michal_stasinski.tabu.Menu.Models.OrderListItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.CustomDialogClass;
import com.michal_stasinski.tabu.Utils.FontFitTextView;
import com.michal_stasinski.tabu.Utils.MathUtils;
import com.michal_stasinski.tabu.Utils.OrderComposerUtils;

import static com.michal_stasinski.tabu.SplashScreen.orderList;

public class OrderComposerOthers extends SwipeBackActivity {

    private String price;
    private int itemPositionInMenuListView;
    private OrderComposerListViewAdapter adapter;
    private static int size = 0;
    private int quantity = 1;
    private float sum = 0;
    private String pizzaName;
    private String rank;
    private String[] titleText = {
            "Uwagi",
            "addRemoveButton"
    };
    private String[] descText = {

            "Dodaj swoje uwagi",
            "addRemoveButton"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeStaffLogged);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_composer);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        //************************* przycisk close**********************

        Button closeButton = (Button) findViewById(R.id.bClose);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.from_left, R.anim.to_right);
            }
        });

        //************************* botttom menu ************************************

        ButtonBarLayout bottom_action_bar_btn1 = (ButtonBarLayout) findViewById(R.id.bottom_action_bar_btn1);
        ButtonBarLayout bottom_action_bar_btn0 = (ButtonBarLayout) findViewById(R.id.bottom_action_bar_btn0);
        bottom_action_bar_btn0.setVisibility(View.INVISIBLE);
        bottom_action_bar_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (orderList.size() == 0) {
                    CustomDialogClass customDialog = new CustomDialogClass(OrderComposerOthers.this);
                    customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    customDialog.show();
                    customDialog.setTitleDialogText("Twoj koszyk jest pusty");
                    customDialog.setDescDialogText("Wybierz coś z menu");
                } else {
                    Intent intent = new Intent();
                    intent.setClass(getBaseContext(), ShopingCardListView.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.from_right, R.anim.to_left);

                }
            }
        });


        //********************************************************************************

        // CustomFont_Avenir_Medium title = (CustomFont_Avenir_Medium) findViewById(R.id.order_composer_positionInList);
        TextView nameTxt = (TextView) findViewById(R.id.order_composer_titleItem);
        TextView descTxt = (TextView) findViewById(R.id.order_composer_desc);
        TextView priceTxt = (TextView) findViewById(R.id.order_composer_price);


        String output = MathUtils.formatDecimal(sum, 2);

        final BounceListView listView = (BounceListView) findViewById(R.id.order_composer_listView);

        Intent intent = getIntent();

        String names = intent.getExtras().getString("name");
        String desc = intent.getExtras().getString("desc");
        itemPositionInMenuListView = intent.getExtras().getInt("position");
        rank = intent.getExtras().getString("rank");
        price = intent.getExtras().getString("price");
        size = 0;//intent.getExtras().getInt("size");

        // title.setText("-" + String.valueOf(itemPositionInMenuListView + 1) + "-");
        nameTxt.setText(names.toUpperCase());
        descTxt.setText(desc);
        // priceTxt.setText(pizzaList.get(itemPositionInMenuListView).getPriceArray().get(size).toString());
        priceTxt.setText(price);
        pizzaName = names.toUpperCase();
        //descText[0] = String.valueOf(20 + size * 10) + " cm";

        adapter = new OrderComposerListViewAdapter(this, titleText, descText);
        adapter.notifyDataSetChanged();

        //*********************************** addToCartBtn*********************************************

        // final Button addToCartBtn = (Button) findViewById(R.id.order_composer_button);
        //addToCartBtn.setText("DODAJ " + quantity + " DO KOSZYKA " + output + " zł");
        // addToCartBtn.setTransformationMethod(null);

        final TextView addToCartBtn_txt = (TextView) findViewById(R.id.order_composer_button_txt);
        final ButtonBarLayout addToCartBtn = (ButtonBarLayout) findViewById(R.id.order_composer_button);
        addToCartBtn_txt.setText("DODAJ " + quantity + " DO KOSZYKA " + output + " zł");
        addToCartBtn_txt.setTransformationMethod(null);

        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.setEnabled(false);
                v.animate()

                        .alpha(0.0f)
                        .setDuration(500)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                v.setAlpha(1.0f);
                                v.setEnabled(true);
                            }
                        });


                OrderListItem order = new OrderListItem();
                order.setName(pizzaName);
                order.setNr(Integer.parseInt(rank));
                // order.setSize(descText[0]);
                order.setPrice(sum);
                //  String addon = descText[1];
                //  String sauce = descText[2];
                String note = descText[0];



                if (descText[0] != "Dodaj swoje uwagi" && descText[0] != null) {
                    order.setNote("UWAGI: " + descText[0]);
                } else {
                    note = "";
                    order.setNote(note);
                }

                String actualOrder = pizzaName + " " + note;
                int isAlready = -1;

                Log.i("informacja", "actualOrder "+ actualOrder);
                for (int i = 0; i < orderList.size(); i++) {

                    String st = orderList.get(i).getName() + " " + orderList.get(i).getNote();
                    Log.i("informacja", "st "+ st);
                    if (st.equals(actualOrder)) {

                        isAlready = i;
                    }
                }


                if (isAlready == -1) {

                    order.setQuantity(quantity);
                    orderList.add(order);
                    // orderList.get(0).setQuantity(quantity);
                } else {
                    //orderList.add(order);
                    int quantityOld = orderList.get(isAlready).getQuantity();

                    orderList.get(isAlready).setQuantity(quantity + quantityOld);
                }
                FontFitTextView info_about_price = (FontFitTextView) findViewById(R.id.info_about_price_and_quantity);
                //CustomFont_Avenir_Bold info_about_price = (CustomFont_Avenir_Bold) findViewById(R.id.info_about_price_and_quantity);
                info_about_price.setText("(" + OrderComposerUtils.sum_of_all_quantities() + ") " + OrderComposerUtils.sum_of_all_the_prices() + " zł");

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                String result = data.getStringExtra("edit_text");

                if (!result.equals("")) {
                    descText[0] = result;

                } else {
                    descText[0] = "Dodaj swoje uwagi";
                }

                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        // CustomFont_Avenir_Bold info_about_price = (CustomFont_Avenir_Bold) findViewById(R.id.info_about_price_and_quantity);
        FontFitTextView info_about_price = (FontFitTextView) findViewById(R.id.info_about_price_and_quantity);
        info_about_price.setText("(" + OrderComposerUtils.sum_of_all_quantities() + ") " + OrderComposerUtils.sum_of_all_the_prices() + " zł");


        TextView descTxt = (TextView) findViewById(R.id.order_composer_desc);
        // descText[0] = String.valueOf(20 + getSize() * 10) + " cm";

        //sum = pizzaList.get(itemPositionInMenuListView).getPriceArray().get(getSize()).floatValue();

        sum = Float.valueOf(price);
        // Button addToCartBtn = (Button) findViewById(R.id.order_composer_button);
        String output = MathUtils.formatDecimal(sum, 2);
        // addToCartBtn.setText("DODAJ " + quantity + " DO KOSZYKA " + output + " zł");
        // addToCartBtn.setTransformationMethod(null);

        final TextView addToCartBtn_txt = (TextView) findViewById(R.id.order_composer_button_txt);
        final ButtonBarLayout addToCartBtn = (ButtonBarLayout) findViewById(R.id.order_composer_button);
        addToCartBtn_txt.setText("DODAJ " + quantity + " DO KOSZYKA " + output + " zł");
        addToCartBtn_txt.setTransformationMethod(null);

        TextView priceTxt = (TextView) findViewById(R.id.order_composer_price);

        // String output = MathUtils.formatDecimal(pizzaList.get(itemPositionInMenuListView).getPriceArray().get(getSize()), 2);

        // String output = MathUtils.formatDecimal(sum, 2);
        priceTxt.setText(output + " zł");


        adapter.setDescArr(descText);

        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                quantity = adapter.getNum_value();
                String output = MathUtils.formatDecimal(sum * quantity, 2);

                final TextView addToCartBtn_txt = (TextView) findViewById(R.id.order_composer_button_txt);
                final ButtonBarLayout addToCartBtn = (ButtonBarLayout) findViewById(R.id.order_composer_button);
                addToCartBtn_txt.setText("DODAJ " + quantity + " DO KOSZYKA " + output + " zł");
                addToCartBtn_txt.setTransformationMethod(null);

                // Button addToCartBtn = (Button) findViewById(R.id.order_composer_button);
                // addToCartBtn.setText("DODAJ " + quantity + " DO KOSZYKA " + output + " zł");
                // addToCartBtn.setTransformationMethod(null);
            }
        });

        adapter.notifyDataSetChanged();

        //***********************************listView*****************************
        final BounceListView listView = (BounceListView) findViewById(R.id.order_composer_listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Object listItem = listView.getItemAtPosition(position);

                Intent intent = new Intent();
                listView.setOnItemClickListener(null);
                if (position == 0) {

                    intent.putExtra("position", 13);
                    intent.putExtra("title", "UWAGI");

                    if (descText[0] == "Dodaj swoje uwagi") {
                        intent.putExtra("actualText", "");
                    } else {
                        intent.putExtra("actualText", descText[3]);
                    }
                    intent.setClass(view.getContext(), EditTextPopUp.class);
                    startActivityForResult(intent, 1);
                }
            }
        });

    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        OrderComposerOthers.size = size;
    }
}
