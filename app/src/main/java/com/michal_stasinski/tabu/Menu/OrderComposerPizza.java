package com.michal_stasinski.tabu.Menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.michal_stasinski.tabu.Menu.Adapters.OrderComposerListViewAdapter;
import com.michal_stasinski.tabu.Menu.Models.OrderListItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.CustomFont_Avenir_Bold;
import com.michal_stasinski.tabu.Utils.CustomFont_Avenir_Medium;
import com.michal_stasinski.tabu.Utils.MathUtils;
import com.michal_stasinski.tabu.Utils.OrderComposerUtils;

import static com.michal_stasinski.tabu.Menu.AddonsPopUp.addonsPopUpAdapter;
import static com.michal_stasinski.tabu.Menu.SaucePopUp.saucePopUpAdapter;
import static com.michal_stasinski.tabu.SplashScreen.orderList;
import static com.michal_stasinski.tabu.SplashScreen.pizzaList;

public class OrderComposerPizza extends SwipeBackActivity {


    private int itemPositionInMenuListView;
    private OrderComposerListViewAdapter adapter;
    private static int size = 0;
    private int quantity = 1;
    private float sum = 0;
    private String pizzaName;
    private String rank;

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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_composer);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);



        //************************* przycisk close**********************

        Button closeButton = (Button) findViewById(R.id.bClose);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                finish();
                overridePendingTransition(R.anim.from_left, R.anim.to_right);
            }
        });


        //************************* botttom menu ************************************
        ButtonBarLayout bottom_action_bar_btn1 = (ButtonBarLayout) findViewById(R.id.bottom_action_bar_btn1);
        ButtonBarLayout bottom_action_bar_btn0= (ButtonBarLayout) findViewById(R.id.bottom_action_bar_btn0);
        bottom_action_bar_btn0.setVisibility(View.INVISIBLE);

        bottom_action_bar_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), ShopingCardListView.class);
                startActivity(intent);
            }
        });


        //********************************************************************************


        CustomFont_Avenir_Medium title = (CustomFont_Avenir_Medium) findViewById(R.id.order_composer_positionInList);
        CustomFont_Avenir_Medium nameTxt = (CustomFont_Avenir_Medium) findViewById(R.id.order_composer_titleItem);
        CustomFont_Avenir_Medium descTxt = (CustomFont_Avenir_Medium) findViewById(R.id.order_composer_desc);
        CustomFont_Avenir_Medium priceTxt = (CustomFont_Avenir_Medium) findViewById(R.id.order_composer_price);


        String output = MathUtils.formatDecimal(sum, 2);

        final BounceListView listView = (BounceListView) findViewById(R.id.order_composer_listView);

        Intent intent = getIntent();

        String names = intent.getExtras().getString("name");
        String desc = intent.getExtras().getString("desc");
        itemPositionInMenuListView = intent.getExtras().getInt("position");
        rank = intent.getExtras().getString("rank");
        String price = intent.getExtras().getString("price");
        size = intent.getExtras().getInt("size");


        title.setText("-" + String.valueOf(itemPositionInMenuListView + 1) + "-");
        nameTxt.setText(names.toUpperCase());
        descTxt.setText(desc);
        priceTxt.setText(pizzaList.get(itemPositionInMenuListView).getPriceArray().get(size).toString());
        pizzaName = names.toUpperCase();

        descText[0] = String.valueOf(20 + size * 10) + " cm";

        adapter = new OrderComposerListViewAdapter(this, titleText, descText);
        adapter.notifyDataSetChanged();

        //*********************************** addToCartBtn*********************************************

        final Button addToCartBtn = (Button) findViewById(R.id.order_composer_button);
        addToCartBtn.setText("DODAJ " + quantity + " DO KOSZYKA    " + output + " zł");

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

                if (descText[1] != "Wybierz dodatki"&& descText[1] !=null) {
                    order.setAddon(addon);
                } else {
                    order.setAddon("");
                    addon = "";
                }
                if (descText[2] != "Wybierz dodatkowy sos" && descText[2] !=null) {
                    order.setSauce(descText[2]);
                } else {
                    sauce = "";
                    order.setSauce(sauce);
                }
                if (descText[3] != "Dodaj swoje uwagi"&& descText[3] !=null) {
                    order.setNote("UWAGI: "+descText[3]);
                } else {
                    note = "";
                    order.setNote(note);
                }

                String actualOrder = pizzaName + " " + descText[0] + " " + addon + " " + sauce+ " " +note;
                int isAlready = -1;

                for (int i = 0; i < orderList.size(); i++) {
                    String st = orderList.get(i).getName() + " " + orderList.get(i).getSize() + " " + orderList.get(i).getAddon() + " " + orderList.get(i).getSauce()+" " + orderList.get(i).getNote();
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

                CustomFont_Avenir_Bold info_about_price= (CustomFont_Avenir_Bold) findViewById(R.id.info_about_price_and_quantity);
                info_about_price.setText("("+OrderComposerUtils.sum_of_all_quantities()+") "+ OrderComposerUtils.sum_of_all_the_prices()+" zł");

            }
        });

        //***********************************listView*****************************

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Object listItem = listView.getItemAtPosition(position);

                Intent intent = new Intent();

                if (position == 0) {

                    intent.putExtra("size", getSize());
                    intent.putExtra("position", itemPositionInMenuListView);
                    intent.setClass(view.getContext(), PizzaSizePopUp.class);
                    startActivity(intent);
                }

                if (position == 1) {
                    intent.putExtra("size", getSize());
                    intent.setClass(view.getContext(), AddonsPopUp.class);
                    startActivity(intent);
                }

                if (position == 2) {
                    intent.putExtra("size", getSize());
                    intent.setClass(view.getContext(), SaucePopUp.class);
                    startActivity(intent);
                }

                if (position == 3) {
                    intent.putExtra("position", 13);
                    intent.putExtra("title", "UWAGI");

                    if (descText[3] =="Dodaj swoje uwagi") {
                        intent.putExtra("actualText", "");
                    } else {
                        intent.putExtra("actualText",descText[3]);
                    }
                    intent.setClass(view.getContext(), EditTextPopUp.class);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("informacja", "resultresult 000 " );
        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                String result = data.getStringExtra("edit_text");

                Log.i("informacja", "resultresult " + result);

                if (!result.equals("") ) {
                    descText[3] = result;

                } else {
                    descText[3] ="Dodaj swoje uwagi";
                }

                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();

        CustomFont_Avenir_Bold info_about_price= (CustomFont_Avenir_Bold) findViewById(R.id.info_about_price_and_quantity);
        info_about_price.setText("("+OrderComposerUtils.sum_of_all_quantities()+") "+ OrderComposerUtils.sum_of_all_the_prices()+" zł");


        CustomFont_Avenir_Medium descTxt = (CustomFont_Avenir_Medium) findViewById(R.id.order_composer_desc);
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
        Button addToCartBtn = (Button) findViewById(R.id.order_composer_button);
        String output = MathUtils.formatDecimal(sum, 2);
        addToCartBtn.setText("DODAJ " + quantity + " DO KOSZYKA    " + output + " zł");
        CustomFont_Avenir_Medium priceTxt = (CustomFont_Avenir_Medium) findViewById(R.id.order_composer_price);

        String oputput = MathUtils.formatDecimal(pizzaList.get(itemPositionInMenuListView).getPriceArray().get(getSize()), 2);
        priceTxt.setText(oputput + " zł");


        adapter.setDescArr(descText);

        adapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                quantity = adapter.getNum_value();
                String output = MathUtils.formatDecimal(sum * quantity, 2);

                Button addToCartBtn = (Button) findViewById(R.id.order_composer_button);
                addToCartBtn.setText("DODAJ " + quantity + " DO KOSZYKA    " + output + " zł");
            }
        });


        adapter.notifyDataSetChanged();

    }


    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        OrderComposerPizza.size = size;
    }
}
