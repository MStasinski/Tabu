package com.michal_stasinski.tabu.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.michal_stasinski.tabu.Menu.Adapters.ShopingCardAdapter;
import com.michal_stasinski.tabu.Menu.Models.ShopingCardItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import static com.michal_stasinski.tabu.SplashScreen.orderList;

public class ShopingCardListView extends SwipeBackActivity {
    private ShopingCardAdapter adapter;

    private String[] titleText = {
            "Sposób Odbioru",
            "Adres Odbioru",
            "Czas Realizacji",
            "Sposób Zapłaty",
            "Uwagi"
    };

    private String[] order = {

            "Razem",
            "Koszt dostawy",
            "Łącznie do zapłaty"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (orderList.size() == 0) {
            finish();
        }
        final ShopingCardItem[] items = new ShopingCardItem[40];

        setContentView(R.layout.activity_shoping_card_list_view);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        final BounceListView listView = (BounceListView) findViewById(R.id.shoping_card_listView);

        adapter = new ShopingCardAdapter(this);

        Log.i("informacja", "onCreateonCreateonCreate" + adapter.getCount());
        ShopingCardItem produkt = new ShopingCardItem();
        produkt.setTitle("imie");
        produkt.setDesc("");
        produkt.setNr(1);
        produkt.setType(ShopingCardAdapter.TYPE_PURCHASER);
        adapter.addItem(produkt);


        ShopingCardItem produktSep = new ShopingCardItem();
        produktSep.setTitle("");
        produktSep.setType(ShopingCardAdapter.TYPE_SEPARATOR);
        adapter.addItem(produktSep);


        for (int i = 0; i < titleText.length; i++) {

            ShopingCardItem produkt0 = new ShopingCardItem();
            produkt0.setTitle(titleText[i]);
            produkt0.setDesc("");
            produkt0.setNr(1);
            produkt0.setType(ShopingCardAdapter.TYPE_ORDER_RULE);

            adapter.addItem(produkt0);
        }

        ShopingCardItem produktSep1 = new ShopingCardItem();
        produktSep1.setTitle("");
        produktSep1.setType(ShopingCardAdapter.TYPE_SEPARATOR);
        adapter.addItem(produktSep1);

        for (int i = 0; i < orderList.size(); i++) {

            ShopingCardItem produkt2 = new ShopingCardItem();
            produkt2.setSumOfPrices(orderList.get(i).getQuantity() * orderList.get(i).getSumOfPrices());
            produkt2.setTitle(orderList.get(i).getName());
            produkt2.setDesc(orderList.get(i).getSize() + " " + orderList.get(i).getAddon());
            produkt2.setNr(orderList.get(i).getQuantity());
            produkt2.setType(ShopingCardAdapter.TYPE_ORDER_ITEM);

            adapter.addItem(produkt2);
        }

        ShopingCardItem produktSep2 = new ShopingCardItem();
        produktSep2.setTitle("");
        produktSep2.setType(ShopingCardAdapter.TYPE_SEPARATOR);
        adapter.addItem(produktSep2);

        for (int i = 0; i < order.length; i++) {

            ShopingCardItem produkt1 = new ShopingCardItem();
            produkt1.setTitle(order[i]);
            produkt1.setDesc("");
            produkt1.setNr(1);
            produkt1.setType(ShopingCardAdapter.TYPE_SUMMARY);

            adapter.addItem(produkt1);
        }

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterek, View view, int position, long id) {
                Object listItem = listView.getItemAtPosition(position);
                Intent intent = new Intent();

                if (position == 2) {
                    ShopingCardItem selectedItem = (ShopingCardItem) adapter.getItem(2);
                    if (selectedItem.getDesc() == "Odbiór osobisty") {
                        selectedItem.setDesc("Dostawa");
                    } else {
                        selectedItem.setDesc("Odbiór osobisty");
                    }
                    adapter.notifyDataSetChanged();
                    Log.i("informacja", "dostawa");
                }

                if (position == 3 || position == 4||position == 5||position == 6) {

                    Log.i("informacja", "czas realaizacji");
                    intent.setClass(view.getContext(), TimeOfDeliveryPopUp.class);
                    startActivity(intent);
                }
                if (position > 7 && position < 8 + orderList.size()) {

                    intent.putExtra("position", position);
                    intent.putExtra("quantity", orderList.get(position - 8).getQuantity());
                    intent.putExtra("name", orderList.get(position - 8).getName());
                    intent.putExtra("price", orderList.get(position - 8).getSumOfPrices());
                    Log.i("informacja", "quantity" + orderList.get(position - 8).getQuantity());
                    intent.setClass(view.getContext(), AddRemoveOrderPopUp.class);
                    startActivity(intent);
                }

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
