package com.michal_stasinski.tabu.Menu;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.michal_stasinski.tabu.Menu.Adapters.DataForDeliveryAdapter;
import com.michal_stasinski.tabu.Menu.Models.ShopingCardItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DataForDeliveryListView extends SwipeBackActivity {
    private DataForDeliveryAdapter adapter;

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


        Address address0 = (Address) getCoordinatesFromAddresse("Gdynia Jaskółcza 20");

        Address address1 = (Address) getCoordinatesFromAddresse("Gdynia Morska 2");


        Location locationA = new Location("point A");

        locationA.setLatitude(address0.getLatitude());
        locationA.setLongitude(address0.getLongitude());

        Location locationB = new Location("point B");

        locationB.setLatitude(address1.getLatitude());
        locationB.setLongitude(address1.getLongitude());

        float distance = locationA.distanceTo(locationB);


        // Log.i("informacja", "Delivery " + getCoordinatesFromAddresse("Gdynia Jana Brzechwy"));


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


        Address address = addresses.get(0);
        return address;
    }

    @Override
    protected void onResume() {
        super.onResume();

        setContentView(R.layout.activity_data_for_delivery_list_view);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        final BounceListView listView = (BounceListView) findViewById(R.id.data_delivery_listView);

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterek, View view, int position, long id) {
                Object listItem = listView.getItemAtPosition(position);
                Log.i("informacja", "________ " + position);
                Intent intent = new Intent();
                //intent.putExtra("size", getSize());
                //  intent.putExtra("position", itemPositionInMenuListView);
                intent.setClass(view.getContext(), EditTextPopUp.class);
                startActivity(intent);

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
