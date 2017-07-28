package com.michal_stasinski.tabu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.tabu.User_Side.Models.DeliveryCostItem;
import com.michal_stasinski.tabu.User_Side.Models.MenuItemProduct;
import com.michal_stasinski.tabu.User_Side.Models.NewsItem;
import com.michal_stasinski.tabu.User_Side.Models.OrderListItem;
import com.michal_stasinski.tabu.User_Side.Models.StaffMember;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import pl.mobiltek.paymentsmobile.dotpay.AppSDK;

public class SplashScreen extends Activity {

    public static ArrayList<MenuItemProduct> pizzaSizesList;
    public static ArrayList<MenuItemProduct> pizzaList;
    public static ArrayList<MenuItemProduct> saladList;
    public static ArrayList<MenuItemProduct> pizzaSpices;
    public static ArrayList<MenuItemProduct> pizzaCheeseList;
    public static ArrayList<MenuItemProduct> pizzaColdCutsMeatList;
    public static ArrayList<MenuItemProduct> pizzaWegetables;
    public static ArrayList<MenuItemProduct> pizzaSauces;
    public static ArrayList<NewsItem> newsArrayList;
    public static ArrayList<StaffMember> staffTeamArray;
    public static ArrayList<Number> timeWhenRestaurantIsOpen;
    public static ArrayList<Number> timeWhenRestaurantIsClose;


    public static ArrayList<Integer> pizzaSizes_CheckMark;
    public static ArrayList<Integer> pizzaAddons_CheckMark;
    public static ArrayList<DeliveryCostItem> deliveryCostArray;
    public static ArrayList<OrderListItem> orderList;


    public static final String DATA_FOR_DELIVERY = "DataForDelivery";

    public static final String SHOPING_CARD_PREF = "ShopingCardPref";

    /*unikalne ID zamawiającego */
    public static String USER_UNIQUE_ID_PREF;

    /*TODO adres restauracji klienta - kązdorazwoaod zmienić*/
    public static final String RESTAURANT_ADDRES = "Gdynia, Jaskółcza 20";


    public static String MINIMAL_PRICE_OF_ORDER = "";

    /*interwał dostaw w timeOfDelivery Popup ListView*/
    public static String TIME_OF_DELIVERY_INTERVAL = "20";

    /*czas realizacj zamówienia z dostawą*/
    public static String TIME_OF_REALIZATION_DELIVERY = "30";

    /*czas realizacji zamówienia przy odbiorze na miejscu*/
    public static String TIME_OF_REALIZATION_TAKEAWAY = "20";
    public static String DB_ORDER_DATABASE = "TEST_ORDER";
    public static String DB_SETTINGS_DATABASE = "Ustawienias";
    public static String DB_ORDER_SERIAL_DATABASE = "Numeros";
    /* nazwa wezła bazy danych na które wysyłane jest zagiowienie*/;
    public static String DB_ORDER_SERIAL_NUMBER = "0";


    public static Boolean IS_STAFF_MEMBER = false;
    public static Boolean IS_LOGGED_IN = false;
    public static Boolean ACTULAL_STATE_OF_LOGGED = false;


    /*nazwa wezłą bazy danych -czas kiedy możliwe jest skałdadanie zamowien online*/
    public static String DB_TIMES_OPEN_END_OF_RESTAURANT = "SendOrderOnlines";
    public static String DB_NEWS = "News";
    public static String DB_STAFFS = "Staffs";


    private Boolean APPSTART = true;

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
    private final int SPLASH_DISPLAY_LENGTH = 0;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //  FirebaseUserActions.getInstance().start(getIndexApiAction());
    }

    @Override
    public void onStop() {

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        super.onStop();
    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {


            staffTeamArray = new ArrayList<StaffMember>();
            newsArrayList = new ArrayList<NewsItem>();
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
                    loadStaffTeam(),
                    loadFireOneOValue(DB_SETTINGS_DATABASE),
                    loadTimesOfRestaurant(),
                    loadNews()


            };
            return "Executed";
        }


        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppSDK.initialize(this);

        setContentView(R.layout.activity_splash_screen);
        SharedPreferences prefs = getSharedPreferences(DATA_FOR_DELIVERY, MODE_PRIVATE);


        /* przydzielam ID urzytkownikowi*/

        SharedPreferences uniqueIdPref = getApplicationContext().getSharedPreferences("uniqueIdPref", MODE_PRIVATE);
        USER_UNIQUE_ID_PREF = uniqueIdPref.getString("ID", null);
        if (USER_UNIQUE_ID_PREF == null) {
            SharedPreferences.Editor editor = uniqueIdPref.edit();
            USER_UNIQUE_ID_PREF = UUID.randomUUID().toString().toUpperCase();
            editor.putString("ID", USER_UNIQUE_ID_PREF);
            editor.commit();
        }

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
                    menuItemProduct.setSold(false);
                    pizzaSizesList.add(menuItemProduct);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return tcs.getTask();
    }

    public Task<String> loadFireOneOValue(String databaseReference) {
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
                    TIME_OF_REALIZATION_DELIVERY = (String) map.get("czas_realizacji_dostawa").toString();
                    TIME_OF_REALIZATION_TAKEAWAY = (String) map.get("czas_realizacji_odbior").toString();
                    TIME_OF_DELIVERY_INTERVAL = (String) map.get("interwal_czasu_dostaw").toString();
                    MINIMAL_PRICE_OF_ORDER = (String) map.get("minimalna_wartosc_zamowienia").toString();
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

        return tcs.getTask();
    }


    public Task<String> loadFireBaseData(String databaseReference, final ArrayList<MenuItemProduct> nameArrayList) {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef;
        myRef = database.getReference(databaseReference);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameArrayList.clear();

                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();
                    String name = (String) map.get("name");
                    String rank = (String) map.get("rank").toString();
                    String desc = (String) map.get("desc");
                    Boolean isSold = (Boolean) map.get("sold");

                    ArrayList<Number> price = (ArrayList) map.get("price");

                    MenuItemProduct menuItemProduct = new MenuItemProduct();
                    menuItemProduct.setName(name);
                    menuItemProduct.setRank(rank);
                    menuItemProduct.setDescription(desc);
                    menuItemProduct.setPriceArray(price);
                    menuItemProduct.setSold(isSold);
                    menuItemProduct.setHowManyItemSelected(0);
                    nameArrayList.add(menuItemProduct);
                }

                Intent intent = new Intent();
                intent.setAction(MainActivity.FIREBASE_CHANGED);
                sendBroadcast(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });

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
        return tcs.getTask();

    }


    public Task<String> loadTimesOfRestaurant() {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(DB_TIMES_OPEN_END_OF_RESTAURANT);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                timeWhenRestaurantIsClose = new ArrayList<Number>();
                timeWhenRestaurantIsOpen = new ArrayList<Number>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();
                    timeWhenRestaurantIsClose = (ArrayList) map.get("end");
                    timeWhenRestaurantIsOpen = (ArrayList) map.get("start");
                }
                if (APPSTART) {
                    APPSTART = false;
                    StartApp();
                }

                Intent intent = new Intent();
                intent.setAction(MainActivity.FIREBASE_CHANGED);
                sendBroadcast(intent);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }

        });

        return tcs.getTask();
    }


    public Task<String> loadNews() {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(DB_NEWS);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newsArrayList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();
                    String date = (String) map.get("date");
                    String news = (String) map.get("news");
                    String title = (String) map.get("title");
                    String rank = (String) map.get("rank");
                    String url = (String) map.get("imageUrl");

                    NewsItem newsItem = new NewsItem();

                    newsItem.setDate(date);
                    newsItem.setNews(news);
                    newsItem.setTitle(title);
                    newsItem.setRank(rank);
                    newsItem.setUrl(url);

                    newsArrayList.add(newsItem);
                }
                Intent intent = new Intent();
                intent.setAction(MainActivity.FIREBASE_CHANGED);
                sendBroadcast(intent);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return tcs.getTask();
    }

    public Task<String> loadStaffTeam() {
        final TaskCompletionSource<String> tcs = new TaskCompletionSource<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(DB_STAFFS);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                staffTeamArray.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {


                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();
                    String email = (String) map.get("email");
                    String firstname = (String) map.get("firstname");
                    String surname = (String) map.get("surname");
                    String password = (String) map.get("password");
                    String phone = (String) map.get("phone").toString();
                    String userId = (String) map.get("userId");

                    StaffMember staffMember = new StaffMember();

                    staffMember.setEmail(email);
                    staffMember.setFirstname(firstname);
                    staffMember.setSurname(surname);
                    staffMember.setPassword(password);
                    staffMember.setPhone(phone);
                    staffMember.setUserId(userId);

                    staffTeamArray.add(staffMember);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return tcs.getTask();
    }

    private static String removeLastChar(String str) {
        return str.substring(0, str.length() - 2);
    }
}

