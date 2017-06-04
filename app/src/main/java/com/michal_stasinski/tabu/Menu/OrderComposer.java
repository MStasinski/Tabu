package com.michal_stasinski.tabu.Menu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.michal_stasinski.tabu.Menu.Adapters.OrderComposerListViewAdapter;
import com.michal_stasinski.tabu.Menu.Models.OrderListItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.CustomTextView;
import com.michal_stasinski.tabu.Utils.MathUtils;

import static com.michal_stasinski.tabu.Menu.AddonsPopUp.addonsPopUpAdapter;
import static com.michal_stasinski.tabu.Menu.SaucePopUp.saucePopUpAdapter;
import static com.michal_stasinski.tabu.SplashScreen.orderList;
import static com.michal_stasinski.tabu.SplashScreen.pizzaList;

public class OrderComposer extends SwipeBackActivity {


    private int itemPositionInMenuListView;
    private OrderComposerListViewAdapter adapter;
    private static int size = 0;
    private int quantity = 1;
    private float sum = 0;
    private String pizzaName;


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

        Button closeButton = (Button) findViewById(R.id.bClose);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.from_left, R.anim.to_right);
            }
        });

        ImageButton bottom_action_bar_btn1 = (ImageButton) findViewById(R.id.bottom_action_bar_btn1);
        bottom_action_bar_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), ShopingCardListView.class);
                startActivity(intent);
            }
        });

        CustomTextView title = (CustomTextView) findViewById(R.id.order_composer_positionInList);
        CustomTextView nameTxt = (CustomTextView) findViewById(R.id.order_composer_titleItem);
        CustomTextView descTxt = (CustomTextView) findViewById(R.id.order_composer_desc);
        CustomTextView priceTxt = (CustomTextView) findViewById(R.id.order_composer_price);

        final Button addToCartBtn = (Button) findViewById(R.id.order_composer_button);
        // final Button addToCartBtn = (Button) findViewById(R.id.order_composer_button);
        String output = MathUtils.formatDecimal(sum,2);
        addToCartBtn.setText("DODAJ " + quantity + " DO KOSZYKA    " + output + " zł");

        final BounceListView listView = (BounceListView) findViewById(R.id.order_composer_listView);

        Intent intent = getIntent();
        String names = intent.getExtras().getString("name");
        String desc = intent.getExtras().getString("desc");
        itemPositionInMenuListView = intent.getExtras().getInt("position");
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

        addToCartBtn.setText("DODAJ " + quantity + " DO KOSZYKA    " + output+ " zł");

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
                order.setSumOfPrices(sum);
                String addon = descText[1];
                String sauce = descText[2];

                if (descText[1] != "Wybierz dodatki") {
                    order.setAddon(addon);
                } else {
                    order.setAddon("");
                    addon = "";
                }
                if (descText[2] != "Wybierz dodatkowy sos") {
                    order.setSauce(descText[2]);
                } else {
                    sauce = "";
                    order.setSauce(sauce);
                }


                String actualOrder = pizzaName + " " + descText[0] + " " + addon + " " + sauce;
                int isAlready = -1;

                for (int i = 0; i < orderList.size(); i++) {
                    String st = orderList.get(i).getName() + " " + orderList.get(i).getSize() + " " + orderList.get(i).getAddon() + " " + orderList.get(i).getSauce();
                    if (st.equals(actualOrder)) {

                        isAlready = i;
                    }
                }


                if (isAlready == -1) {
                    order.setQuantity(1);
                    orderList.add(order);
                    // orderList.get(0).setQuantity(1);
                } else {
                    //orderList.add(order);
                    int quantity = orderList.get(isAlready).getQuantity();
                    orderList.get(isAlready).setQuantity(quantity + 1);
                }


            }
        });

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
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        CustomTextView descTxt = (CustomTextView) findViewById(R.id.order_composer_desc);
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
        String output = MathUtils.formatDecimal(sum,2);
        addToCartBtn.setText("DODAJ " + quantity + " DO KOSZYKA    " + output + " zł");
        CustomTextView priceTxt = (CustomTextView) findViewById(R.id.order_composer_price);

        String oputput = MathUtils.formatDecimal(pizzaList.get(itemPositionInMenuListView).getPriceArray().get(getSize()),2);
        priceTxt.setText(oputput+ " zł");


        adapter.setDescArr(descText);
        adapter.notifyDataSetChanged();

    }

    public OrderComposer() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("informacja", "________onStart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("informacja", "________onSTOP");
    }

    @Override
    protected void onDestroy() {
        Log.i("informacja", "________onDESTROY");
        super.onDestroy();

    }

    @Override
    protected void onPause() {
        Log.i("informacja", "________onPAUSE");
        super.onPause();
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        OrderComposer.size = size;
    }
}
