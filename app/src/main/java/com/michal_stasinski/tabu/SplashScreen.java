package com.michal_stasinski.tabu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.tabu.Menu.Models.DeliveryCostItem;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.Menu.Models.OrderListItem;

import java.util.ArrayList;
import java.util.Map;

public class SplashScreen extends Activity {

    public static ArrayList<MenuItemProduct> pizzaSizesList;
    public static ArrayList<MenuItemProduct> pizzaList;
    public static ArrayList<MenuItemProduct> saladList;
    public static ArrayList<MenuItemProduct> pizzaSpices;
    public static ArrayList<MenuItemProduct> pizzaCheeseList;
    public static ArrayList<MenuItemProduct> pizzaColdCutsMeatList;
    public static ArrayList<MenuItemProduct> pizzaWegetables;
    public static ArrayList<MenuItemProduct> pizzaSauces;
    public static ArrayList<Integer> pizzaSizes_CheckMark;
    public static ArrayList<Integer> pizzaAddons_CheckMark;
    public static ArrayList<DeliveryCostItem> deliveryCostArray;
    public static ArrayList<OrderListItem> orderList;
    public static final String DATA_FOR_DELIVERY = "DataForDelivery";
    public static final String SHOPING_CARD_PREF = "ShopingCardPref";
    public static final String RESTAURANT_ADDRES = "Gdynia, Jaskółcza 20";

    public static String[] dataDeliveryTextFieldName = {
            "Zamawiający",
            "Imię",
            "Nazwisko",
            "E-Mail",
            "Telefon",
            "Adres dostawy",
            "Miasto",
            "Ulica",
            "Nr domu",
            "Nr mieszkania",
            "piętro",
            "firma",
            "Dodatkowe wskazówki",
            "Dodatkowe wskazówki"
    };
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH =0;

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            Log.i("informacja", "ładuje bazy w tle...czekaj");

            pizzaList = new ArrayList<MenuItemProduct>();
            saladList = new ArrayList<MenuItemProduct>();
            pizzaCheeseList = new ArrayList<MenuItemProduct>();
            pizzaColdCutsMeatList = new ArrayList<MenuItemProduct>();
            pizzaWegetables = new ArrayList<MenuItemProduct>();
            pizzaSpices = new ArrayList<MenuItemProduct>();
            pizzaSauces = new ArrayList<MenuItemProduct>();
            orderList = new ArrayList<OrderListItem>();


            Task<?>[] tasks = new Task[]{
                    loadPizzaSizes("PizzaSizes", 0),
                    loadFireBaseData("Pizzas", pizzaList),
                    loadFireBaseData("Salads", saladList),
                    loadFireBaseData("PizzaCheeses", pizzaCheeseList),
                    loadFireBaseData("PizzaColdCutsMeats", pizzaColdCutsMeatList),
                    loadFireBaseData("PizzaWegetables", pizzaWegetables),
                    loadFireBaseData("PizzaSauces", pizzaSauces),
                    loadFireBaseData("PizzaSpices", pizzaSpices),
                    loadFireBaseDeliverCost("DeliveryCosts", deliveryCostArray),
                    StartApp()

            };
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            Log.i("informacja", "gotowe...bazy załadowane");
        }

        @Override
        protected void onPreExecute() {
            Log.i("informacja", "musze załadować bazy danych");

        }

        @Override
        protected void onProgressUpdate(Void... values) {

            Log.i("informacja", "progres");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences prefs = getSharedPreferences(DATA_FOR_DELIVERY, MODE_PRIVATE);

        String itemo = prefs.getString(dataDeliveryTextFieldName[0], null);

    }

    @Override
    protected void onResume() {
        super.onResume();
        new LongOperation().execute("");
    }

    private Task<String> StartApp() {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();

                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
        return tcs.getTask();
    }

    public Task<String> loadPizzaSizes(String databaseReference, int count) {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        myRef = database.getReference(databaseReference);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                pizzaSizesList = new ArrayList<MenuItemProduct>();
                pizzaSizes_CheckMark = new ArrayList<Integer>();

                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();
                    String name = (String) map.get("name");
                    String rank = (String) map.get("rank").toString();

                    MenuItemProduct menuItemProduct = new MenuItemProduct();
                    menuItemProduct.setName(name);
                    pizzaSizes_CheckMark.add(0);
                    menuItemProduct.setRank(rank);
                    pizzaSizesList.add(menuItemProduct);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        Log.i("informacja", " t0 zaladowana baza "+ databaseReference);
        return tcs.getTask();
    }


    public static Task<String> loadFireBaseData(String databaseReference, final ArrayList<MenuItemProduct> nameArrayList) {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;

        myRef = database.getReference(databaseReference);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();
                    String name = (String) map.get("name");
                    String rank = (String) map.get("rank").toString();
                    String desc = (String) map.get("desc");

                    ArrayList<Number> price = (ArrayList) map.get("price");

                    MenuItemProduct menuItemProduct = new MenuItemProduct();
                    menuItemProduct.setName(name);
                    menuItemProduct.setRank(rank);
                    menuItemProduct.setDescription(desc);
                    menuItemProduct.setPriceArray(price);
                    menuItemProduct.setHowManyItemSelected(0);
                    nameArrayList.add(menuItemProduct);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        Log.i("informacja", "t1 zaladowana baza "+ databaseReference);
        return tcs.getTask();
    }

    public static Task<String> loadFireBaseDeliverCost(String databaseReference, final ArrayList<DeliveryCostItem> nameArrayList) {


        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        myRef = database.getReference(databaseReference);

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                deliveryCostArray = new ArrayList<DeliveryCostItem>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();

                    String price = (String) map.get("price").toString();
                    String dist = (String) map.get("dist").toString();

                    // MenuItemProduct menuItemProduct = new MenuItemProduct();
                    DeliveryCostItem deliveryCostItem = new DeliveryCostItem();
                    deliveryCostItem.setPrice(price);
                    deliveryCostItem.setDistacne(dist);

                    deliveryCostArray.add(deliveryCostItem);
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }

        });
        Log.i("informacja", "t2 zaladowana baza "+ databaseReference);
        return tcs.getTask();

    }



}

