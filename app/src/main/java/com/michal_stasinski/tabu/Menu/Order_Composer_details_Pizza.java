package com.michal_stasinski.tabu.Menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.michal_stasinski.tabu.Menu.Pop_Ups.AddonsPopUp;
import com.michal_stasinski.tabu.Menu.Pop_Ups.EditTextPopUp;
import com.michal_stasinski.tabu.Menu.Pop_Ups.PizzaSizePopUp;
import com.michal_stasinski.tabu.Menu.Pop_Ups.SaucePopUp;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.CustomDialogClass;
import com.michal_stasinski.tabu.Utils.FontFitTextView;
import com.michal_stasinski.tabu.Utils.MathUtils;
import com.michal_stasinski.tabu.Utils.OrderComposerUtils;

import static com.michal_stasinski.tabu.Menu.Pop_Ups.AddonsPopUp.addonsPopUpAdapter;
import static com.michal_stasinski.tabu.Menu.Pop_Ups.SaucePopUp.saucePopUpAdapter;
import static com.michal_stasinski.tabu.SplashScreen.orderList;
import static com.michal_stasinski.tabu.SplashScreen.pizzaList;

public class Order_Composer_details_Pizza extends SwipeBackActivity {

    public static final String ORDER_COMPOSER_CHANGE = "pizzasizechange";
    public static final String PIZZA_ADDONS_CHANGE = "pizzaaddonschange";

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
    private PizzaSizeReceiver orderComposerReceiver;

    private String[] titleText = {
            "Rozmiar",
            "Dodatki",
            "Dodatkowy sos",
            "Uwagi",
            "addRemoveButton"
    };
    private String[] descText = {
            "30 cm",
            "Wybierz dodatki",
            "Wybierz dodatkowy sos",
            "Dodaj swoje uwagi",
            "addRemoveButton"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setTheme(R.style.AppThemeStaffLogged);

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
        size = intent.getExtras().getInt("size");

    }

    @Override
    public void onResume() {
        super.onResume();

        //************************* przycisk close**********************

        Button closeButton = (Button) findViewById(R.id.bClose);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //************************ kasowanie tego co jest wpisane w komorki poniewaz komonowanie zamowienia nie został ozkaonczone

                if (saucePopUpAdapter != null) {
                    for (int i = 0; i < saucePopUpAdapter.getItemArray().size(); i++) {
                        saucePopUpAdapter.getItemArray().get(i).setHowManyItemSelected(0);
                    }
                }

                if (addonsPopUpAdapter != null) {
                    for (int i = 0; i < addonsPopUpAdapter.getItemArray().size(); i++) {
                        addonsPopUpAdapter.getItemArray().get(i).setHowManyItemSelected(0);
                    }
                }

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
                    CustomDialogClass customDialog = new CustomDialogClass(Order_Composer_details_Pizza.this);
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


        //************************ kasowanie tego co jest wpisane w komorki poniewaz komonowanie zamowienia nie został ozkaonczone
        orderComposerReceiver = new PizzaSizeReceiver();
        IntentFilter intentFilter = new IntentFilter(ORDER_COMPOSER_CHANGE);
        registerReceiver(orderComposerReceiver, intentFilter);


        //************************   dodaje elementy  do ADAPTERA   ************************

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

    public void reloadTextInRows() {


        FontFitTextView info_about_price = (FontFitTextView) findViewById(R.id.info_about_price_and_quantity);
        info_about_price.setText("(" + OrderComposerUtils.sum_of_all_quantities() + ") " + OrderComposerUtils.sum_of_all_the_prices() + " zł");


        TextView descTxt = (TextView) findViewById(R.id.order_composer_desc);
        descText[0] = String.valueOf(20 + getSize() * 10) + " cm";
        sum = pizzaList.get(itemPositionInMenuListView).getPriceArray().get(getSize()).floatValue();


        if (addonsPopUpAdapter != null) {
            String txt = "";
            Boolean start = true;
            for (int i = 0; i < addonsPopUpAdapter.getItemArray().size(); i++) {
                if (addonsPopUpAdapter.getItemArray().get(i).getHowManyItemSelected() != 0) {

                    if (!start) {

                        txt += ", ";
                    }
                    start = false;
                    if (addonsPopUpAdapter.getItemArray().get(i).getHowManyItemSelected() == 1) {
                        txt += addonsPopUpAdapter.getItemArray().get(i).getName();
                        sum += addonsPopUpAdapter.getItemArray().get(i).getPriceArray().get(getSize()).floatValue();

                    } else {
                        txt += addonsPopUpAdapter.getItemArray().get(i).getName() + " x2";
                        sum += 2 * addonsPopUpAdapter.getItemArray().get(i).getPriceArray().get(getSize()).floatValue();
                    }
                }
            }
            if (txt != "") {
                descText[1] = txt;
            } else {
                descText[1] = "Wybierz dodatki";
            }

        }

        if (saucePopUpAdapter != null) {
            String txt = "";
            Boolean start = true;
            for (int i = 0; i < saucePopUpAdapter.getItemArray().size(); i++) {
                if (saucePopUpAdapter.getItemArray().get(i).getHowManyItemSelected() != 0) {
                    if (!start) {

                        txt += ", ";
                    }
                    start = false;
                    if (saucePopUpAdapter.getItemArray().get(i).getHowManyItemSelected() == 1) {
                        txt += saucePopUpAdapter.getItemArray().get(i).getName();
                        sum += saucePopUpAdapter.getItemArray().get(i).getPriceArray().get(0).floatValue();
                    } else {
                        txt += saucePopUpAdapter.getItemArray().get(i).getName() + " x2";
                        sum += 2 * saucePopUpAdapter.getItemArray().get(i).getPriceArray().get(0).floatValue();
                    }
                }
            }
            if (txt != "") {
                descText[2] = txt;
            } else {
                descText[2] = "Wybierz dodatkowy sos";
            }

        }

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

                    intent.putExtra("size", getSize());
                    intent.putExtra("position", itemPositionInMenuListView);
                    intent.setClass(view.getContext(), PizzaSizePopUp.class);
                    startActivity(intent);
                }

                if (position == 2) {
                    intent.putExtra("size", getSize());
                    intent.setClass(view.getContext(), AddonsPopUp.class);
                    startActivity(intent);
                }

                if (position == 3) {
                    intent.putExtra("size", getSize());
                    intent.setClass(view.getContext(), SaucePopUp.class);
                    startActivity(intent);
                }

                if (position == 4) {
                    intent.putExtra("position", 13);
                    intent.putExtra("title", "UWAGI");

                    if (descText[3] == "Dodaj swoje uwagi") {
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
                order.setSize(descText[0]);
                order.setPrice(sum);
                order.setNr(Integer.parseInt(rank));
                String addon = descText[1];
                String sauce = descText[2];
                String note = descText[3];

                if (descText[1] != "Wybierz dodatki" && descText[1] != null) {
                    order.setAddon(addon);
                } else {
                    order.setAddon("");
                    addon = "";
                }
                if (descText[2] != "Wybierz dodatkowy sos" && descText[2] != null) {
                    order.setSauce(descText[2]);
                } else {
                    sauce = "";
                    order.setSauce(sauce);
                }
                if (descText[3] != "Dodaj swoje uwagi" && descText[3] != null) {
                    order.setNote("UWAGI: " + descText[3]);
                } else {
                    note = "";
                    order.setNote(note);
                }

                String actualOrder = pizzaName + " " + descText[0] + " " + addon + " " + sauce + " " + note;
                int isAlready = -1;

                for (int i = 0; i < orderList.size(); i++) {
                    String st = orderList.get(i).getName() + " " + orderList.get(i).getSize() + " " + orderList.get(i).getAddon() + " " + orderList.get(i).getSauce() + " " + orderList.get(i).getNote();

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

    //************************** funkcja odbiera Uwagi z EditTextPopap
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                String result = data.getStringExtra("edit_text");

                if (!result.equals("")) {
                    descText[3] = result;

                } else {
                    descText[3] = "Dodaj swoje uwagi";
                }

                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    //************************** ładowanie komorek do Adaptera**********************/
    public void addElementToAdapter() {

        pizzaName = "PIZZA " + names.toUpperCase();
        adapter = new OrderComposerListViewAdapter(this);

        OrderComposerItem header = new OrderComposerItem();
        header.setType(OrderComposerListViewAdapter.TYPE_HEADER);
        header.setTitle(names);
        header.setDesc(desc);


        String oputput = MathUtils.formatDecimal(pizzaList.get(itemPositionInMenuListView).getPriceArray().get(getSize()), 2);
        Log.i("informacja", size+"PIZZA_SIZE_CHANGE"+getSize()+ "  " +oputput);
        header.setPrice(oputput);
        adapter.addItem(header);

        OrderComposerItem pizzaSizeItem = new OrderComposerItem();
        pizzaSizeItem.setType(OrderComposerListViewAdapter.TYPE_PIZZA_SIZE);
        pizzaSizeItem.setTitle(titleText[0]);
        pizzaSizeItem.setDesc(String.valueOf(20 + getSize() * 10) + " cm");
        adapter.addItem(pizzaSizeItem);

        OrderComposerItem pizzaAddonsItem = new OrderComposerItem();
        pizzaAddonsItem.setType(OrderComposerListViewAdapter.TYPE_PIZZA_ADDONS);
        pizzaAddonsItem.setTitle(titleText[1]);
        pizzaAddonsItem.setDesc(descText[1]);
        adapter.addItem(pizzaAddonsItem);

        OrderComposerItem pizzaSouceItem = new OrderComposerItem();
        pizzaSouceItem.setType(OrderComposerListViewAdapter.TYPE_PIZZA_SOUCE);
        pizzaSouceItem.setTitle(titleText[2]);
        pizzaSouceItem.setDesc(descText[2]);
        adapter.addItem(pizzaSouceItem);

        OrderComposerItem pizzaComments = new OrderComposerItem();
        pizzaComments.setType(OrderComposerListViewAdapter.TYPE_COMMENTS);
        pizzaComments.setTitle(titleText[3]);
        pizzaComments.setDesc(descText[3]);
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
        Order_Composer_details_Pizza.size = size;
    }


    @Override
    protected void onStop() {
        if (orderComposerReceiver != null) {
            unregisterReceiver(orderComposerReceiver);
            orderComposerReceiver = null;
        }
        super.onStop();
    }


    public class PizzaSizeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ORDER_COMPOSER_CHANGE)) {
                Log.i("informacja", size+"PIZZA_SIZE_CHANGE"+getSize());

                addElementToAdapter();
                reloadTextInRows();
                addElementToShopingCard();
                clickOnListViewElement();
                addElementToAdapter();
            }
        }
    }
}


