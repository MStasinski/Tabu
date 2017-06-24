package com.michal_stasinski.tabu.Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.michal_stasinski.tabu.Menu.Adapters.ShopingCardAdapter;
import com.michal_stasinski.tabu.Menu.Models.PaymentItem;
import com.michal_stasinski.tabu.Menu.Models.ShopingCardItem;
import com.michal_stasinski.tabu.Menu.Models.TimeListItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.MathUtils;
import com.michal_stasinski.tabu.Utils.OrderComposerUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import static com.michal_stasinski.tabu.Menu.TimeOfDeliveryPopUp.timeList;
import static com.michal_stasinski.tabu.Menu.PaymentPopUp.paymentMethodsList;
import static com.michal_stasinski.tabu.SplashScreen.DATA_FOR_DELIVERY;
import static com.michal_stasinski.tabu.SplashScreen.RESTAURANT_ADDRES;
import static com.michal_stasinski.tabu.SplashScreen.SHOPING_CARD_PREF;
import static com.michal_stasinski.tabu.SplashScreen.dataDeliveryTextFieldName;
import static com.michal_stasinski.tabu.SplashScreen.orderList;


public class ShopingCardListView extends SwipeBackActivity {
    private DatabaseReference mDatabase;

    private ShopingCardAdapter adapter;
    public static int SELECTED_TIME = 0;
    public static int SELECTED_PAYMENT_METHOD = 0;
    private int deliveryCost = 0;
    private String delivery_mode;


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

        SharedPreferences prefs = getSharedPreferences(DATA_FOR_DELIVERY, MODE_PRIVATE);

        final String firstname = prefs.getString(dataDeliveryTextFieldName[1], null);
        final String lastname = prefs.getString(dataDeliveryTextFieldName[2], null);
        final String email = prefs.getString(dataDeliveryTextFieldName[3], null);
        final String phone = prefs.getString(dataDeliveryTextFieldName[4], null);
        deliveryCost = prefs.getInt("deliveryCost", 0);

        adapter = new ShopingCardAdapter(this);
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
            if(i==2) {
                produkt0.setDesc("JAK NAJSZYBCIEJ");
            }else if(i==3) {
                produkt0.setDesc("GOTÓWKA");
            }else{
                produkt0.setDesc("");
            }
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
            produkt2.setPrice(Float.valueOf(MathUtils.formatDecimal(orderList.get(i).getQuantity() * orderList.get(i).getPrice(), 2)));
            String txtDesc = "";
            produkt2.setTitle(orderList.get(i).getName());
            if (orderList.get(i).getSize() != null) {
                txtDesc = orderList.get(i).getSize();
            }


            if (orderList.get(i).getAddon() != null) {
                if (!orderList.get(i).getAddon().equals("")) {
                    txtDesc += ", " + orderList.get(i).getAddon();
                }
            }
            if (orderList.get(i).getSauce() != null) {
                if (!orderList.get(i).getSauce().equals("")) {
                    txtDesc += ", " + orderList.get(i).getSauce();
                }
            }
            if (orderList.get(i).getNote() != null) {
                if (!orderList.get(i).getNote().equals("")) {
                    if (!txtDesc.equals("")) {
                        txtDesc += ", " + orderList.get(i).getNote();
                    } else {
                        txtDesc = orderList.get(i).getNote();
                    }
                }
            }
            if (txtDesc != null) {
                produkt2.setDesc(txtDesc);
            } else {
                produkt2.setDesc("");
            }
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
            if (i == 0) {
                produkt1.setDesc(OrderComposerUtils.sum_of_all_the_prices());
            }
            if (i == 1) {
                produkt1.setDesc(String.valueOf(MathUtils.formatDecimal(deliveryCost, 2)));
            }
            if (i == 2) {


                //TODO tu był bład na przeciku
                produkt1.setDesc(String.valueOf(MathUtils.formatDecimal(Float.valueOf(OrderComposerUtils.sum_of_all_the_prices()) + deliveryCost, 2)));
            }
            produkt1.setNr(1);
            produkt1.setType(ShopingCardAdapter.TYPE_SUMMARY);

            adapter.addItem(produkt1);
        }

        /*pobiera row w shopingcard  i dane z TimeOfDEliver timeList */


        ShopingCardItem selectedTimeItem = (ShopingCardItem) adapter.getItem(4);
        if (timeList != null) {
            TimeListItem timeItem = (TimeListItem) timeList.get(SELECTED_TIME);
            selectedTimeItem.setDesc(timeItem.getTime());
        }

        ShopingCardItem selectedPaymentItem = (ShopingCardItem) adapter.getItem(5);
        if (paymentMethodsList != null) {
            PaymentItem paymentItem = (PaymentItem) paymentMethodsList.get(SELECTED_PAYMENT_METHOD);
            selectedPaymentItem.setDesc(paymentItem.getPayment_txt());
        }

        /*----------------------------------------------------------------------*/


        SharedPreferences prefs0 = getSharedPreferences(SHOPING_CARD_PREF, MODE_PRIVATE);
        delivery_mode = prefs0.getString("delivery_mode", null);
        String street2 = prefs0.getString("street", null);


        final String street = prefs.getString("Ulica", null);

        ShopingCardItem selectedItem_del_cost = (ShopingCardItem) adapter.getItem(adapter.getCount() - 2);
        ShopingCardItem selectedItem_all_cost = (ShopingCardItem) adapter.getItem(adapter.getCount() - 1);

        if (street2 != null && !street.equals("Ulica") && !delivery_mode.equals("ODBIÓR WŁASNY")) {
            ShopingCardItem el = (ShopingCardItem) adapter.getItem(3);
            el.setDesc(street2);
            //deliveryCost = DataForDeliveryListView.deliveryCost;
            deliveryCost = prefs.getInt("deliveryCost", 0);
            selectedItem_del_cost.setDesc(String.valueOf(MathUtils.formatDecimal(deliveryCost, 2)));
            selectedItem_all_cost.setDesc(String.valueOf(MathUtils.formatDecimal(Float.valueOf(OrderComposerUtils.sum_of_all_the_prices()) + deliveryCost, 2)));
            adapter.notifyDataSetChanged();

        } else {
            ShopingCardItem el0 = (ShopingCardItem) adapter.getItem(2);
            el0.setDesc("ODBIÓR WŁASNY");
            // deliveryCost = 0;
            selectedItem_del_cost.setDesc("0.00");
            selectedItem_all_cost.setDesc(String.valueOf(MathUtils.formatDecimal(Float.valueOf(OrderComposerUtils.sum_of_all_the_prices()), 2)));
            ShopingCardItem el1 = (ShopingCardItem) adapter.getItem(3);
            el1.setDesc(RESTAURANT_ADDRES);
        }


        if (delivery_mode != null && !street.equals("Ulica") && !delivery_mode.equals("ODBIÓR WŁASNY")) {
            ShopingCardItem el = (ShopingCardItem) adapter.getItem(2);
            el.setDesc(delivery_mode);
            //deliveryCost = DataForDeliveryListView.deliveryCost;
            deliveryCost = prefs.getInt("deliveryCost", 0);
            selectedItem_del_cost.setDesc(String.valueOf(MathUtils.formatDecimal(deliveryCost, 2)));
            adapter.notifyDataSetChanged();

        } else {
            ShopingCardItem el0 = (ShopingCardItem) adapter.getItem(2);
            el0.setDesc("ODBIÓR WŁASNY");
            // deliveryCost = 0;
            ShopingCardItem el1 = (ShopingCardItem) adapter.getItem(3);
            el1.setDesc(RESTAURANT_ADDRES);
        }



        ShopingCardItem el4= (ShopingCardItem) adapter.getItem(4);
        //el4.setDesc("JAK NAJSZYBCIEJ");

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
                    ShopingCardItem selectedItem_del_cost = (ShopingCardItem) adapter.getItem(adapter.getCount() - 2);
                    ShopingCardItem selectedItem_all_cost = (ShopingCardItem) adapter.getItem(adapter.getCount() - 1);

                    if (selectedItem.getDesc() == "ODBIÓR WŁASNY") {
                        selectedItem.setDesc("DOSTAWA");
                        selectedItem_del_cost.setDesc(String.valueOf(MathUtils.formatDecimal(deliveryCost, 2)));
                        selectedItem_all_cost.setDesc(String.valueOf(MathUtils.formatDecimal(Float.valueOf(OrderComposerUtils.sum_of_all_the_prices()) + deliveryCost, 2)));

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
                        selectedItem_del_cost.setDesc("0.00");
                        selectedItem_all_cost.setDesc(String.valueOf(MathUtils.formatDecimal(Float.valueOf(OrderComposerUtils.sum_of_all_the_prices()), 2)));
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

                if (position == 5) {

                    intent.setClass(view.getContext(),PaymentPopUp.class);
                    startActivity(intent);
                }

                if (position > 7 && position < 8 + orderList.size()) {

                    intent.putExtra("position", position);
                    intent.putExtra("quantity", orderList.get(position - 8).getQuantity());
                    intent.putExtra("name", orderList.get(position - 8).getName());
                    intent.putExtra("price", orderList.get(position - 8).getPrice());

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

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Button sendButton = (Button) findViewById(R.id.send_order);
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("yy-MM-dd_hh:mm:ss");
                SimpleDateFormat mdformat2 = new SimpleDateFormat("yy-MM-dd hh:mm");

                String strDate = mdformat.format(calendar.getTime());

                String strDate2 = mdformat.format(calendar.getTime());

                String uniqueId = UUID.randomUUID().toString();

                ShopingCardItem delivery_mode = (ShopingCardItem) adapter.getItem(2);
                ShopingCardItem selectedItem_all_cost = (ShopingCardItem) adapter.getItem(adapter.getCount() - 1);

                mDatabase.child("TEST_ORDER").child(strDate + uniqueId).child("deliveryDate").setValue(strDate2);
                mDatabase.child("TEST_ORDER").child(strDate + uniqueId).child("deliveryPrice").setValue(deliveryCost);
                mDatabase.child("TEST_ORDER").child(strDate + uniqueId).child("email").setValue(email);
                mDatabase.child("TEST_ORDER").child(strDate + uniqueId).child("orderMan").setValue(firstname + " " + lastname);


                ArrayList orderArray = new ArrayList();
                for (int i = 0; i < orderList.size(); i++) {

                    Log.i("informacja", orderList.get(i).getSize());

                    ArrayList<String> arr = new ArrayList<String>();
                    arr.add(String.valueOf(orderList.get(i).getNr()));
                    arr.add(orderList.get(i).getName());
                    arr.add(orderList.get(i).getSize() +" "+orderList.get(i).getAddon() +" "+ orderList.get(i).getAddon() +" "+ orderList.get(i).getSauce());
                    arr.add(String.valueOf(orderList.get(i).getQuantity()));
                    arr.add(String.valueOf(orderList.get(i).getPrice()));

                    orderArray.add(arr);

                }

                mDatabase.child("TEST_ORDER").child(strDate + uniqueId).child("orderList").setValue(orderArray);
                mDatabase.child("TEST_ORDER").child(strDate + uniqueId).child("paymentWay").setValue("GOTÓWKA");
                mDatabase.child("TEST_ORDER").child(strDate + uniqueId).child("phone").setValue(phone);
                mDatabase.child("TEST_ORDER").child(strDate + uniqueId).child("receiptAdres").setValue(street);
                mDatabase.child("TEST_ORDER").child(strDate + uniqueId).child("receiptWay").setValue(delivery_mode.getDesc().toString());
                mDatabase.child("TEST_ORDER").child(strDate + uniqueId).child("totalPrice").setValue(selectedItem_all_cost.getDesc());
                mDatabase.child("TEST_ORDER").child(strDate + uniqueId).child("userId").setValue(uniqueId);

                Log.i("informacja", "wysyłam do bazy");

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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        return Actions.newView("ShopingCardListView", "http://[ENTER-YOUR-URL-HERE]");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().start(getIndexApiAction());
    }

    @Override
    public void onStop() {

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        FirebaseUserActions.getInstance().end(getIndexApiAction());
        super.onStop();
    }
}
