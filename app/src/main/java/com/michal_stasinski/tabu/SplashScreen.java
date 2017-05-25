package com.michal_stasinski.tabu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    public static ArrayList<OrderListItem> orderList;

    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
    }

    @Override
    protected void onStart() {
        super.onStart();

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
                StartApp()

        };
    }

    private Task<String> StartApp() {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

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

        return tcs.getTask();
    }

}

