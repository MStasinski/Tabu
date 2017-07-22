
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
import com.michal_stasinski.tabu.Menu.Models.OrderComposerItem;
import com.michal_stasinski.tabu.Menu.Models.OrderListItem;
import com.michal_stasinski.tabu.Menu.Pop_Ups.EditTextPopUp;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.CustomDialogClass;
import com.michal_stasinski.tabu.Utils.FontFitTextView;
import com.michal_stasinski.tabu.Utils.MathUtils;
import com.michal_stasinski.tabu.Utils.OrderComposerUtils;

import static com.michal_stasinski.tabu.SplashScreen.IS_LOGGED_IN;
import static com.michal_stasinski.tabu.SplashScreen.orderList;
import static com.michal_stasinski.tabu.SplashScreen.pizzaList;

public class Order_Composer_details_Others extends SwipeBackActivity {



    private int itemPositionInMenuListView;
    private OrderComposerListViewAdapter adapter;
    private static int size = 0;
    private String quantity = "1";
    private float sum = 0;
    private String pizzaName;
    private String rank;
    private String names;
    private String desc;
    private String price;


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

        if(IS_LOGGED_IN) {
            setTheme(R.style.AppThemeStaffLogged);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_composer);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        final BounceListView listView = (BounceListView) findViewById(R.id.order_composer_listView);
        Intent intent = getIntent();

        /******************pobranie danych z Menu*********************/

        names = intent.getExtras().getString("name");
        desc = intent.getExtras().getString("desc");
        itemPositionInMenuListView = intent.getExtras().getInt("position");
        rank = intent.getExtras().getString("rank");
        price = intent.getExtras().getString("price");
        size = 0;//intent.getExtras().getInt("size");

    }

    @Override
    public void onResume() {
        super.onResume();

        //************************* przycisk close**********************

        Button closeButton = (Button) findViewById(R.id.bClose);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                addElementToAdapter();
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

                    CustomDialogClass customDialog = new CustomDialogClass(Order_Composer_details_Others.this);
                    customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    customDialog.show();
                    customDialog.setTitleDialogText("Twoj koszyk jest pusty");
                    customDialog.setDescDialogText("Wybierz coś z menu");

                } else {

                    Intent intent = new Intent();
                    intent.setClass(getBaseContext(), ShopingCard.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.from_right, R.anim.to_left);
                }
            }
        });


        addElementToAdapter();
        reloadTextInRows();
        addElementToShopingCard();

        adapter.registerDataSetObserver(new DataSetObserver() {

            @Override
            public void onChanged() {

                TextView num = (TextView) findViewById(R.id.order_compositor_quantity_num);
                quantity = ((String) num.getText());
                String output = MathUtils.formatDecimal(sum * Integer.parseInt(quantity), 2);

                final TextView addToCartBtn_txt = (TextView) findViewById(R.id.order_composer_button_txt);
                final ButtonBarLayout addToCartBtn = (ButtonBarLayout) findViewById(R.id.order_composer_button);
                addToCartBtn_txt.setText("DODAJ " + quantity + " DO KOSZYKA " + output + " zł");
                addToCartBtn_txt.setTransformationMethod(null);


            }
        });
        clickOnListViewElement();
        addElementToAdapter();

    }


    //************************** ponowne uzupełnienie komórek inofrmacjami ***********************

    public void reloadTextInRows() {


        FontFitTextView info_about_price = (FontFitTextView) findViewById(R.id.info_about_price_and_quantity);
        info_about_price.setText("(" + OrderComposerUtils.sum_of_all_quantities() + ") " + OrderComposerUtils.sum_of_all_the_prices() + " zł");
        TextView descTxt = (TextView) findViewById(R.id.order_composer_desc);
        sum = pizzaList.get(itemPositionInMenuListView).getPriceArray().get(getSize()).floatValue();



        String output = MathUtils.formatDecimal(sum, 2);
    }

    public void clickOnListViewElement() {

        final BounceListView listView = (BounceListView) findViewById(R.id.order_composer_listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Object listItem = listView.getItemAtPosition(position);

                Intent intent = new Intent();
                listView.setOnItemClickListener(null);
                if (position == 1) {

                    intent.putExtra("position", 13);
                    intent.putExtra("title", "UWAGI");

                    if (descText[0] == "Dodaj swoje uwagi") {
                        intent.putExtra("actualText", "");
                    } else {
                        intent.putExtra("actualText", descText[0]);
                    }
                    intent.setClass(view.getContext(), EditTextPopUp.class);
                    startActivityForResult(intent, 1);
                }
            }
        });

    }

    public void addElementToShopingCard() {

        String output = MathUtils.formatDecimal(sum, 2);

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
                order.setPrice(sum);

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

                    order.setQuantity(Integer.parseInt(quantity));
                    orderList.add(order);

                } else {

                    int quantityOld = orderList.get(isAlready).getQuantity();

                    orderList.get(isAlready).setQuantity(Integer.parseInt(quantity) + quantityOld);
                }
                FontFitTextView info_about_price = (FontFitTextView) findViewById(R.id.info_about_price_and_quantity);
                info_about_price.setText("(" + OrderComposerUtils.sum_of_all_quantities() + ") " + OrderComposerUtils.sum_of_all_the_prices() + " zł");

            }
        });

    }

    //************************** funkcja odbiera "Uwagi" z EditTextPopap***********************
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

    //************************** ładowanie komorek  "rows" do Adaptera**********************/
    public void addElementToAdapter() {

        pizzaName = names.toUpperCase();
        adapter = new OrderComposerListViewAdapter(this);

        OrderComposerItem header = new OrderComposerItem();
        header.setType(OrderComposerListViewAdapter.TYPE_HEADER);
        header.setTitle(names);
        header.setDesc(desc);


        String oputput = MathUtils.formatDecimal(pizzaList.get(itemPositionInMenuListView).getPriceArray().get(getSize()), 2);
        Log.i("informacja", size+"PIZZA_SIZE_CHANGE"+getSize()+ "  " +oputput);
        header.setPrice(oputput);
        adapter.addItem(header);


        OrderComposerItem pizzaComments = new OrderComposerItem();
        pizzaComments.setType(OrderComposerListViewAdapter.TYPE_COMMENTS);
        pizzaComments.setTitle(titleText[0]);
        pizzaComments.setDesc(descText[0]);
        adapter.addItem(pizzaComments);

        OrderComposerItem add_remove_btn = new OrderComposerItem();
        add_remove_btn.setType(OrderComposerListViewAdapter.TYPE_ADD_REMOVE_PANEL);
        adapter.addItem(add_remove_btn);
        adapter.notifyDataSetChanged();

    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        Order_Composer_details_Others.size = size;
    }


}

