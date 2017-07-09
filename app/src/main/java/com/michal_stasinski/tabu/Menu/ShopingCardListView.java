package com.michal_stasinski.tabu.Menu;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.michal_stasinski.tabu.MainActivity;
import com.michal_stasinski.tabu.Menu.Adapters.ShopingCardAdapter;
import com.michal_stasinski.tabu.Menu.DotPay.DotPayActivity;
import com.michal_stasinski.tabu.Menu.Models.PaymentItem;
import com.michal_stasinski.tabu.Menu.Models.ShopData;
import com.michal_stasinski.tabu.Menu.Models.ShopingCardItem;
import com.michal_stasinski.tabu.Menu.Models.TimeListItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.CustomDialogClass;
import com.michal_stasinski.tabu.Utils.MathUtils;
import com.michal_stasinski.tabu.Utils.OrderComposerUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import static com.michal_stasinski.tabu.Menu.PaymentPopUp.paymentMethods;
import static com.michal_stasinski.tabu.Menu.PaymentPopUp.paymentMethodsList;
import static com.michal_stasinski.tabu.Menu.TimeOfDeliveryPopUp.timeList;
import static com.michal_stasinski.tabu.SplashScreen.DATA_FOR_DELIVERY;
import static com.michal_stasinski.tabu.SplashScreen.RESTAURANT_ADDRES;
import static com.michal_stasinski.tabu.SplashScreen.SHOPING_CARD_PREF;
import static com.michal_stasinski.tabu.SplashScreen.dataDeliveryTextFieldName;
import static com.michal_stasinski.tabu.SplashScreen.orderList;


public class ShopingCardListView extends SwipeBackActivity {

    public static int SELECTED_TIME = 0;
    public static int SELECTED_PAYMENT_METHOD = 0;

    private DatabaseReference mDatabase;
    private ShopingCardAdapter adapter;
    private static String comments = "";
    private int deliveryCost = 0;
    private String delivery_mode;

    private DotPayReceiver dotpay_success;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String street;

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

    public static final String DOTPAY_SUCCESS = "dotpay_success";
    public static final String DOTPAY_FAILD = "dotpay_faild";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Check_Time_Open_Close time_open_close = new Check_Time_Open_Close();

        if (!time_open_close.getRestaurantIsOpen()) {
            CustomDialogClass customDialog = new CustomDialogClass(ShopingCardListView.this);
            customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            customDialog.show();
            customDialog.setTitleDialogText("UWAGA");
            customDialog.setDescDialogText("Zamówienia online nieczynne.\nZapraszamy w godzinach otwarcia.");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (dotpay_success != null) {
            unregisterReceiver(dotpay_success);
            dotpay_success = null;
        }

        if (orderList.size() == 0) {
            finish();
        }

        final ShopingCardItem[] items = new ShopingCardItem[40];

        setContentView(R.layout.activity_shoping_card_list_view);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        final BounceListView listView = (BounceListView) findViewById(R.id.shoping_card_listView);

        SharedPreferences prefs = getSharedPreferences(DATA_FOR_DELIVERY, MODE_PRIVATE);

        firstname = prefs.getString(dataDeliveryTextFieldName[1], null);
        lastname = prefs.getString(dataDeliveryTextFieldName[2], null);
        email = prefs.getString(dataDeliveryTextFieldName[3], null);
        phone = prefs.getString(dataDeliveryTextFieldName[4], null);

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
            if (i == 2) {
                Check_Time_Open_Close time_open_close = new Check_Time_Open_Close();
                if (time_open_close.getRestaurantIsOpen()) {
                    produkt0.setDesc("JAK NAJSZYBCIEJ");
                } else {
                    produkt0.setDesc("NIECZYNNE");
                }

            } else if (i == 3) {
                produkt0.setDesc("GOTÓWKA");
            } else {
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

                produkt1.setDesc(String.valueOf(MathUtils.formatDecimal(Float.valueOf(OrderComposerUtils.sum_of_all_the_prices()) + deliveryCost, 2)));
            }
            produkt1.setNr(1);
            produkt1.setType(ShopingCardAdapter.TYPE_SUMMARY);

            adapter.addItem(produkt1);
        }


        SharedPreferences prefs0 = getSharedPreferences(SHOPING_CARD_PREF, MODE_PRIVATE);
        delivery_mode = prefs0.getString("delivery_mode", null);
        String street2 = prefs0.getString("street", null);


        ShopingCardItem selectedItem_del_cost = (ShopingCardItem) adapter.getItem(adapter.getCount() - 2);
        final ShopingCardItem selectedItem_all_cost = (ShopingCardItem) adapter.getItem(adapter.getCount() - 1);

        String townFromData = prefs.getString("Miasto", null);
        String streetFromData = prefs.getString("Ulica", null);
        String houseNrFromData = prefs.getString("Nr domu", null);
        int deliveryCostFromData = prefs.getInt("deliveryCost", 0);

        street = townFromData + ", " + streetFromData + " " + houseNrFromData;


        if (street != null) {
            if (street2 != null && !streetFromData.equals("Ulica") && !townFromData.equals("Miasto") && !houseNrFromData.equals("Nr domu") && !(delivery_mode.equals("ODBIÓR WŁASNY") && DataForDeliveryListView.deliveryCost > 0)) {
                ShopingCardItem el = (ShopingCardItem) adapter.getItem(3);
                el.setDesc(street);
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
        }
        if (street != null) {
            if (delivery_mode != null && !streetFromData.equals("Ulica") && !townFromData.equals("Miasto") && !houseNrFromData.equals("Nr domu") && !delivery_mode.equals("ODBIÓR WŁASNY") && DataForDeliveryListView.deliveryCost > 0) {
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
                    ShopingCardItem selectedItem_del_cost = (ShopingCardItem) adapter.getItem(adapter.getCount() - 2);
                    ShopingCardItem selectedItem_all_cost = (ShopingCardItem) adapter.getItem(adapter.getCount() - 1);

                    if (selectedItem.getDesc().equals("ODBIÓR WŁASNY")) {
                        selectedItem.setDesc("DOSTAWA");
                        selectedItem_del_cost.setDesc(String.valueOf(MathUtils.formatDecimal(deliveryCost, 2)));
                        selectedItem_all_cost.setDesc(String.valueOf(MathUtils.formatDecimal(Float.valueOf(OrderComposerUtils.sum_of_all_the_prices()) + deliveryCost, 2)));

                        ShopingCardItem selectedAddres = (ShopingCardItem) adapter.getItem(3);

                        SharedPreferences prefs = getSharedPreferences(DATA_FOR_DELIVERY, MODE_PRIVATE);

                        String townFromData = prefs.getString("Miasto", null);
                        String streetFromData = prefs.getString("Ulica", null);
                        String houseNrFromData = prefs.getString("Nr domu", null);

                        String street = townFromData + ", " + streetFromData + " " + houseNrFromData;

                        if (street != null && !streetFromData.equals("Ulica") && !townFromData.equals("Miasto") && !houseNrFromData.equals("Nr domu") && DataForDeliveryListView.deliveryCost > 0) {
                            selectedAddres.setDesc(street);
                        } else {
                            CustomDialogClass customDialog = new CustomDialogClass(ShopingCardListView.this);
                            customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            customDialog.show();
                            customDialog.setTitleDialogText("UWAGA!\nBrak adresu dostawy lub pod podany adres nie dostarczamy zamówień");
                            customDialog.setDescDialogText("Puknij w ZMIEŃ w polu ADRES ODBIORU u uzupełnij adres dostawy ");
                            selectedItem.setDesc("ODBIÓR WŁASNY".toUpperCase());
                            selectedItem_del_cost.setDesc("0.00");
                            selectedItem_all_cost.setDesc(String.valueOf(MathUtils.formatDecimal(Float.valueOf(OrderComposerUtils.sum_of_all_the_prices()), 2)));
                            selectedAddres.setDesc(RESTAURANT_ADDRES);
                        }


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
                    Check_Time_Open_Close time_open_close = new Check_Time_Open_Close();
                    if (time_open_close.getRestaurantIsOpen()) {
                        intent.setClass(view.getContext(), TimeOfDeliveryPopUp.class);
                        startActivity(intent);
                    } else {
                        CustomDialogClass customDialog = new CustomDialogClass(ShopingCardListView.this);
                        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        customDialog.show();
                        customDialog.setTitleDialogText("UWAGA");
                        customDialog.setDescDialogText("Zamówienia online nieczynne.\nZapraszamy w godzinach otwarcia.");
                    }

                }

                if (position == 5) {

                    intent.setClass(view.getContext(), PaymentPopUp.class);
                    startActivity(intent);
                }
                if (position == 6) {

                    intent.putExtra("title", "UWAGI");
                    intent.putExtra("position", 14);
                    ShopingCardItem selectedComments = (ShopingCardItem) adapter.getItem(6);
                    if (!selectedComments.equals("Uwagi")) {

                        intent.putExtra("actualText", selectedComments.getDesc());
                    }
                    intent.setClass(view.getContext(), EditTextPopUp.class);
                    startActivityForResult(intent, 2);


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

        /*      przesyłanie zamówienia do bazy danych */

        mDatabase = FirebaseDatabase.getInstance().getReference();
        ButtonBarLayout sendButton = (ButtonBarLayout) findViewById(R.id.send_order);
        sendButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Check_Time_Open_Close time_open_close = new Check_Time_Open_Close();
                if (time_open_close.getRestaurantIsOpen()) {

                    if (SELECTED_PAYMENT_METHOD == 2) {

                        ShopData.setName(firstname);
                        ShopData.setLastName(lastname);
                        ShopData.setEmail(email);
                        // ShopData.setCurrency("PLN");
                        ShopData.setDescription("Zamowienie z Pizza Tabu");
                        ShopData.setProductPrice(Double.parseDouble(selectedItem_all_cost.getDesc()));

                        dotpay_success = new DotPayReceiver();
                        IntentFilter intentFilter = new IntentFilter(DOTPAY_SUCCESS);
                        registerReceiver(dotpay_success, intentFilter);

                        Intent intent = new Intent();
                        intent.setClass(getBaseContext(), DotPayActivity.class);
                        startActivity(intent);

                    } else {

                        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                                ShopingCardListView.this);

                        alertDialog2.setTitle("ZAMÓWIENIE");

                        alertDialog2.setMessage("Czy chcesz wysłać zamówienie?");

                        alertDialog2.setPositiveButton("TAK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to execute after dialog
                                        sendToFirebase(firstname, lastname, email, phone, street);
                                        Intent intent = new Intent();
                                        intent.setClass(getBaseContext(), MainActivity.class);
                                        startActivity(intent);
                                        dialog.cancel();
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(getApplicationContext(),
                                                "Zamówienie zostało wysłane. Dziękujemy", Toast.LENGTH_SHORT)


                                                .show();
                                    }
                                });

// Setting Negative "NO" Btn
                        alertDialog2.setNegativeButton("NIE",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Write your code here to execute after dialog
                                        Toast.makeText(getApplicationContext(),
                                                "Zamwienie nie zostało wysłane", Toast.LENGTH_SHORT)
                                                .show();
                                        dialog.cancel();
                                    }
                                });
                        // Showing Alert Dialog
                        alertDialog2.show();

                    }
                } else {

                    CustomDialogClass customDialog = new CustomDialogClass(ShopingCardListView.this);
                    customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    customDialog.show();
                    customDialog.setTitleDialogText("UWAGA");
                    customDialog.setDescDialogText("Zamówienia online nieczynne.\nZapraszamy w godzinach otwarcia.");

                }


            }
        });


        adapter.notifyDataSetChanged();
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
        ShopingCardItem selectedComments = (ShopingCardItem) adapter.getItem(6);
        selectedComments.setDesc(comments);

        adapter.notifyDataSetChanged();


        /*----------------------------------------------------------------------*/

    }

    public void sendToFirebase(String firstname, String lastname, String email, String phone, String street) {

        //TODO To na poczate
        String uniqueId = UUID.randomUUID().toString();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("yy-MM-dd_hh:mm:ss");
        SimpleDateFormat mdformat2 = new SimpleDateFormat("yy-MM-dd hh:mm");

        String strDate = mdformat.format(calendar.getTime());

        String strDate2 = mdformat.format(calendar.getTime());


        ShopingCardItem delivery_mode = (ShopingCardItem) adapter.getItem(2);
        ShopingCardItem selectedItem_all_cost = (ShopingCardItem) adapter.getItem(adapter.getCount() - 1);

       // String databaseName = "TEST_ORDER";

        String databaseName ="OrdersCurrents";
        mDatabase.child(databaseName).child(strDate + uniqueId).child("deliveryDate").setValue(strDate2);
        mDatabase.child(databaseName).child(strDate + uniqueId).child("deliveryPrice").setValue(String.valueOf(deliveryCost));
        mDatabase.child(databaseName).child(strDate + uniqueId).child("email").setValue(email);
        mDatabase.child(databaseName).child(strDate + uniqueId).child("orderMan").setValue(firstname + " " + lastname);
        mDatabase.child(databaseName).child(strDate + uniqueId).child("orderStatus").setValue(0);

        ArrayList orderArray = new ArrayList();
        for (int i = 0; i < orderList.size(); i++) {

            ArrayList<String> arr = new ArrayList<String>();
            arr.add(String.valueOf(orderList.get(i).getNr()));
            arr.add(orderList.get(i).getName());
            String sizeTxt = "";
            String addonsTxt = "";
            String sauceTxt = "";
            String noteTxt = "";
            if (orderList.get(i).getSize() != null) {
                sizeTxt = orderList.get(i).getSize() + " ";
            }

            if (orderList.get(i).getAddon() != null) {
                addonsTxt = orderList.get(i).getAddon() + " ";
            }

            if (orderList.get(i).getSauce() != null) {
                sauceTxt = orderList.get(i).getSauce() + " ";
            }

            if (orderList.get(i).getNote() != null) {
                noteTxt = orderList.get(i).getNote();
            }

            arr.add(sizeTxt + addonsTxt + sauceTxt + noteTxt);
            arr.add(String.valueOf(orderList.get(i).getQuantity()));
            arr.add(String.valueOf(orderList.get(i).getPrice()));

            orderArray.add(arr);

        }

        mDatabase.child(databaseName).child(strDate + uniqueId).child("ordersList").setValue(orderArray);

        //GOTÓWKA?
        mDatabase.child(databaseName).child(strDate + uniqueId).child("paymentWay").setValue(paymentMethods[SELECTED_PAYMENT_METHOD]);

        mDatabase.child(databaseName).child(strDate + uniqueId).child("phone").setValue(phone);
        mDatabase.child(databaseName).child(strDate + uniqueId).child("receiptAdres").setValue(street);
        mDatabase.child(databaseName).child(strDate + uniqueId).child("receiptWay").setValue(delivery_mode.getDesc().toString());
        mDatabase.child(databaseName).child(strDate + uniqueId).child("totalPrice").setValue(selectedItem_all_cost.getDesc());
        mDatabase.child(databaseName).child(strDate + uniqueId).child("userId").setValue(uniqueId);
        mDatabase.child(databaseName).child(strDate + uniqueId).child("orderNo").setValue(strDate + uniqueId);
        orderList.clear();

    }


    protected void save_state() {
        SharedPreferences.Editor editor = getSharedPreferences(SHOPING_CARD_PREF, MODE_PRIVATE).edit();

        ShopingCardItem delivery_mode = (ShopingCardItem) adapter.getItem(2);
        ShopingCardItem street = (ShopingCardItem) adapter.getItem(3);

        editor.putString("delivery_mode", delivery_mode.getDesc().toString());
        editor.putString("street", street.getDesc().toString());


        editor.commit();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 2) {

            if (resultCode == Activity.RESULT_OK) {

                String result = data.getStringExtra("edit_text");
                Log.i("informacja", "shopp editText.getText().toString()result  " + result);

                ShopingCardItem selectedComments = (ShopingCardItem) adapter.getItem(6);
                selectedComments.setDesc(result);
                comments = result;

                adapter.notifyDataSetChanged();
            }

            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
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
        FirebaseUserActions.getInstance().start(getIndexApiAction());
    }


    public class DotPayReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(DOTPAY_SUCCESS)) {
                sendToFirebase(firstname, lastname, email, phone, street);
                unregisterReceiver(dotpay_success);
                dotpay_success = null;


            }
        }
    }
}
