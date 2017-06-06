package com.michal_stasinski.tabu.Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.michal_stasinski.tabu.Menu.Adapters.ShopingCardAdapter;
import com.michal_stasinski.tabu.Menu.Models.ShopingCardItem;
import com.michal_stasinski.tabu.Menu.Models.TimeListItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import static com.michal_stasinski.tabu.Menu.TimeOfDeliveryPopUp.timeList;
import static com.michal_stasinski.tabu.SplashScreen.DATA_FOR_DELIVERY;
import static com.michal_stasinski.tabu.SplashScreen.RESTAURANT_ADDRES;
import static com.michal_stasinski.tabu.SplashScreen.SHOPING_CARD_PREF;
import static com.michal_stasinski.tabu.SplashScreen.dataDeliveryTextFieldName;
import static com.michal_stasinski.tabu.SplashScreen.orderList;

public class ShopingCardListView extends SwipeBackActivity {
    private ShopingCardAdapter adapter;
    public static int selected_time = 0;
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
            //TODO komunitkata
        }


        final ShopingCardItem[] items = new ShopingCardItem[40];

        setContentView(R.layout.activity_shoping_card_list_view);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        final BounceListView listView = (BounceListView) findViewById(R.id.shoping_card_listView);

        adapter = new ShopingCardAdapter(this);


        SharedPreferences prefs = getSharedPreferences(DATA_FOR_DELIVERY, MODE_PRIVATE);

        String firstname = prefs.getString(dataDeliveryTextFieldName[1], null);
        String lastname = prefs.getString(dataDeliveryTextFieldName[2], null);
        String email = prefs.getString(dataDeliveryTextFieldName[3], null);
        String phone = prefs.getString(dataDeliveryTextFieldName[4], null);


        ShopingCardItem produkt = new ShopingCardItem();
        if (firstname != null && !firstname.equals("Imię") &&
                lastname != null && !lastname.equals("Nazwisko") &&
                email != null && !email.equals("E-Mail") &&
                phone != null && !phone.equals("Telefon")) {

            produkt.setTitle(firstname + " " + lastname + "\n" + email + "\n" + phone);
            produkt.setDesc("");
        } else {
            produkt.setTitle("DANE UŻYTKOWNIKA.\nUzupełnij.");
            produkt.setDesc("");
        }


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


        ShopingCardItem selectedItem = (ShopingCardItem) adapter.getItem(4);
        if (timeList != null) {
            TimeListItem timeItem = (TimeListItem) timeList.get(selected_time);
            selectedItem.setDesc(timeItem.getTime());
        }


        SharedPreferences prefs0 = getSharedPreferences(SHOPING_CARD_PREF, MODE_PRIVATE);
        String delivery_mode = prefs0.getString("delivery_mode", null);
        String street2 = prefs0.getString("street", null);


        String street = prefs.getString("Ulica", null);


        if (street2 != null && !street.equals("Ulica") && !delivery_mode.equals("ODBIÓR WŁASNY")) {
            ShopingCardItem el = (ShopingCardItem) adapter.getItem(3);
            el.setDesc(street2);
            adapter.notifyDataSetChanged();

        } else {
            ShopingCardItem el0 = (ShopingCardItem) adapter.getItem(2);
            el0.setDesc("ODBIÓR WŁASNY");
            ShopingCardItem el1 = (ShopingCardItem) adapter.getItem(3);
            el1.setDesc(RESTAURANT_ADDRES);
        }

        if (delivery_mode != null && !street.equals("Ulica") && !delivery_mode.equals("ODBIÓR WŁASNY")) {
            ShopingCardItem el = (ShopingCardItem) adapter.getItem(2);
            el.setDesc(delivery_mode);
            adapter.notifyDataSetChanged();

        } else {
            ShopingCardItem el0 = (ShopingCardItem) adapter.getItem(2);
            el0.setDesc("ODBIÓR WŁASNY");
            ShopingCardItem el1 = (ShopingCardItem) adapter.getItem(3);
            el1.setDesc(RESTAURANT_ADDRES);
        }

        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterek, View view, int position, long id) {
                Object listItem = listView.getItemAtPosition(position);
                Intent intent = new Intent();

                if (position == 0) {
                    save_state();
                    intent.setClass(view.getContext(), DataForDeliveryListView.class);
                    startActivity(intent);
                }

                if (position == 2) {
                    ShopingCardItem selectedItem = (ShopingCardItem) adapter.getItem(2);

                    if (selectedItem.getDesc() == "ODBIÓR WŁASNY") {
                        selectedItem.setDesc("DOSTAWA");
                        ShopingCardItem selectedAddres = (ShopingCardItem) adapter.getItem(3);

                        SharedPreferences prefs = getSharedPreferences(DATA_FOR_DELIVERY, MODE_PRIVATE);

                        String street = prefs.getString("Ulica", null);

                        if (street != null && !street.equals("Ulica")) {
                            selectedAddres.setDesc(street);
                        } else {
                            selectedAddres.setDesc("komunikat ffffff");
                        }
                        //TODO Ulica

                    } else {
                        selectedItem.setDesc("ODBIÓR WŁASNY".toUpperCase());
                        ShopingCardItem selectedAddres = (ShopingCardItem) adapter.getItem(3);
                        selectedAddres.setDesc(RESTAURANT_ADDRES);
                    }
                    adapter.notifyDataSetChanged();

                }
                if (position == 3) {
                    save_state();
                    intent.setClass(view.getContext(), DataForDeliveryListView.class);
                    startActivity(intent);
                }

                if (position == 4) {

                    intent.setClass(view.getContext(), TimeOfDeliveryPopUp.class);
                    startActivity(intent);
                }

                if (position > 7 && position < 8 + orderList.size()) {

                    intent.putExtra("position", position);
                    intent.putExtra("quantity", orderList.get(position - 8).getQuantity());
                    intent.putExtra("name", orderList.get(position - 8).getName());
                    intent.putExtra("price", orderList.get(position - 8).getSumOfPrices());

                    intent.setClass(view.getContext(), AddRemoveOrderPopUp.class);
                    startActivity(intent);
                }

            }
        });
        Button closeButton = (Button) findViewById(R.id.closeBtn);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                save_state();
                finish();
                overridePendingTransition(R.anim.from_left, R.anim.to_right);

            }
        });
    }

    protected void save_state() {
        SharedPreferences.Editor editor = getSharedPreferences(SHOPING_CARD_PREF, MODE_PRIVATE).edit();

        ShopingCardItem delivery_mode = (ShopingCardItem) adapter.getItem(2);
        ShopingCardItem street = (ShopingCardItem) adapter.getItem(3);

        editor.putString("delivery_mode", delivery_mode.getDesc().toString());
        editor.putString("street", street.getDesc().toString());

        editor.commit();
    }
}
