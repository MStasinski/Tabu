package com.michal_stasinski.tabu.Menu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.michal_stasinski.tabu.Menu.Adapters.DataForDeliveryAdapter;
import com.michal_stasinski.tabu.Menu.Models.DeliveryCostItem;
import com.michal_stasinski.tabu.Menu.Models.ShopingCardItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.CustomTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DataForDeliveryListView extends SwipeBackActivity {
    private DataForDeliveryAdapter adapter;
    private BounceListView listView;
    private Boolean isClick = false;

    private ArrayList<DeliveryCostItem> deliveryCostArray;
    public static final String MY_PREFS_NAME = "MyPrefsFile";


    private String[] titleText = {
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
    protected Integer[] imgid = {
            R.mipmap.ic_launcher,
            R.mipmap.person_icon,
            R.mipmap.person_icon,
            R.mipmap.email_icon,
            R.mipmap.tel_icon,
            R.mipmap.ic_launcher,
            R.mipmap.miasto_icon,
            R.mipmap.ulica_icon,
            R.mipmap.nr_domu_icon,
            R.mipmap.nr_mieszkania_icon,
            R.mipmap.pietro_icon,
            R.mipmap.pietro_icon,
            R.mipmap.ic_launcher,
            R.mipmap.uwagi_icon,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_for_delivery_list_view);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        listView = (BounceListView) findViewById(R.id.data_delivery_listView);
        adapter = new DataForDeliveryAdapter(this, imgid);

        for (int i = 0; i < titleText.length; i++) {

            ShopingCardItem produkt = new ShopingCardItem();

            if (i == 0 || i == 5 || i == 12) {
                produkt.setType(0);
            } else {
                produkt.setType(1);
            }
            produkt.setTitle(titleText[i]);
            adapter.addItem(produkt);
        }

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        Button closeButton = (Button) findViewById(R.id.closeBtn);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.from_left, R.anim.to_right);
            }
        });

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        for (int i = 0; i < 12; i++) {
            String item = prefs.getString(titleText[i], null);
            Log.i("informacja", "create  " + item);
            if (item != null) {
                ShopingCardItem el = (ShopingCardItem) adapter.getItem(i);
                el.setTitle(item);
                adapter.notifyDataSetChanged();

            }
        }
    }

    /* public double CalculationByDistance(LatLng StartP, LatLng EndP) {
         int Radius = 6371;// radius of earth in Km
         double lat1 = StartP.latitude;
         double lat2 = EndP.latitude;
         double lon1 = StartP.longitude;
         double lon2 = EndP.longitude;
         double dLat = Math.toRadians(lat2 - lat1);
         double dLon = Math.toRadians(lon2 - lon1);
         double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                 + Math.cos(Math.toRadians(lat1))
                 * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                 * Math.sin(dLon / 2);
         double c = 2 * Math.asin(Math.sqrt(a));
         double valueResult = Radius * c;
         double km = valueResult / 1;
         DecimalFormat newFormat = new DecimalFormat("####");
         int kmInDec = Integer.valueOf(newFormat.format(km));
         double meter = valueResult % 1000;
         int meterInDec = Integer.valueOf(newFormat.format(meter));
         Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                 + " Meter   " + meterInDec);

         return Radius * c;
     }*/

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("DeliveryCosts");

        deliveryCostArray = new ArrayList<DeliveryCostItem>();

        myRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();

                    Number price = (Number) map.get("price");
                    Number dist = (Number) map.get("dist");

                   // MenuItemProduct menuItemProduct = new MenuItemProduct();
                    DeliveryCostItem deliveryCostItem = new DeliveryCostItem();
                    deliveryCostItem.setPrice(price);
                    deliveryCostItem.setDistacne(dist);

                    deliveryCostArray.add(deliveryCostItem);
                    Log.i("informacja", "  deliveryCostArray start " +   deliveryCostArray.get(0).getPrice());

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }

        });


    }


    private Address getCoordinatesFromAddresse(String txt) {

        double latitude = 0;
        double longitude = 0;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        String textToSearch = txt;
        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocationName(textToSearch, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (addresses.size() > 0) {
            Address address = addresses.get(0);
            return address;
        } else {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("informacja", "onActivityResult  ");

        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                String result = data.getStringExtra("edit_text");
                Integer pos = data.getIntExtra("pos", 1);
                ShopingCardItem item = (ShopingCardItem) adapter.getItem(pos);

                item.setTitle(result);

                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("informacja", "onResume");
        Address address0 = (Address) getCoordinatesFromAddresse("Gdynia Jaskółcza 20");

        ShopingCardItem item0 = (ShopingCardItem) adapter.getItem(6);
        ShopingCardItem item1 = (ShopingCardItem) adapter.getItem(7);
        ShopingCardItem item2 = (ShopingCardItem) adapter.getItem(8);

        String town = item0.getTitle();
        String street = item1.getTitle();
        String nr = item2.getTitle();

        Address address1;


        if (town.toUpperCase().equals("MIASTO")) {
            address1 = null;
        } else if (street.toUpperCase().equals("ULICA")) {
            address1 = null;
        } else if (nr.toUpperCase().equals("NR DOMU")) {
            address1 = (Address) getCoordinatesFromAddresse(town + " " + street);
        } else {
            address1 = (Address) getCoordinatesFromAddresse(town + " " + street + " " + nr);
        }


        CustomTextView text_cost_delivery = (CustomTextView) findViewById(R.id.data_cost_of_delivery_text);
        if (address1 != null) {

            Log.i("informacja", "distance address1" + address1);
            Location locationA = new Location("point A");

            locationA.setLatitude(address0.getLatitude());
            locationA.setLongitude(address0.getLongitude());

            Location locationB = new Location("point B");

            locationB.setLatitude(address1.getLatitude());
            locationB.setLongitude(address1.getLongitude());

            float distance = locationA.distanceTo(locationB);


            Log.i("informacja", "deliveryCostArray____ " + deliveryCostArray);

            if (distance > 7000) {
                text_cost_delivery.setText("Nie dowozimy pod wskazany adres");
            } else if (distance > 5000) {
                text_cost_delivery.setText("Koszt dostawy 5zł");
            } else if (distance > 3000) {
                text_cost_delivery.setText("Koszt dostawy 4zł");
            } else if (distance <= 3000) {
                text_cost_delivery.setText("Koszt dostawy 3zł");
            }


            Log.i("informacja", "miasto" + address1.getLocality());
            Log.i("informacja", "ulica" + address1.getThoroughfare());
            Log.i("informacja", "numer" + address1.getFeatureName());

            ShopingCardItem townEdit = (ShopingCardItem) adapter.getItem(6);
            ShopingCardItem streetEdit = (ShopingCardItem) adapter.getItem(7);
            ShopingCardItem nrEdit = (ShopingCardItem) adapter.getItem(8);

            townEdit.setTitle(address1.getLocality());
            streetEdit.setTitle(address1.getThoroughfare());
            nrEdit.setTitle(address1.getFeatureName());
            adapter.notifyDataSetChanged();
        }
        if (address1 == null) {
            text_cost_delivery.setText("Nie ma takiego adresu");
        }

        isClick = false;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterek, View view, int position, long id) {
                if (isClick == false) {
                    isClick = true;
                    Object listItem = listView.getItemAtPosition(position);

                    if (position != 0 || position != 5 || position != 12) {
                        // view.setOnClickListener(null);

                        Intent intent = new Intent(view.getContext(), EditTextPopUp.class);
                        intent.putExtra("title", titleText[position]);
                        intent.putExtra("position", position);
                        ShopingCardItem item = (ShopingCardItem) adapter.getItem(position);

                        if (item.getTitle().toUpperCase().equals(titleText[position].toUpperCase())) {
                            intent.putExtra("actualText", "");
                        } else {
                            intent.putExtra("actualText", item.getTitle());
                        }

                        intent.setClass(view.getContext(), EditTextPopUp.class);
                        startActivityForResult(intent, 1);
                    }
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();

        for (int i = 0; i < 12; i++) {
            ShopingCardItem item = (ShopingCardItem) adapter.getItem(i);
            editor.putString(titleText[i], item.getTitle().toString());
        }
        editor.commit();


    }
}
