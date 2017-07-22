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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.michal_stasinski.tabu.Menu.Adapters.DataForDeliveryAdapter;
import com.michal_stasinski.tabu.Menu.Models.ShopingCardItem;
import com.michal_stasinski.tabu.Menu.Pop_Ups.EditTextPopUp;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.Do_you_belong_to_staff;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.michal_stasinski.tabu.SplashScreen.DATA_FOR_DELIVERY;
import static com.michal_stasinski.tabu.SplashScreen.IS_LOGGED_IN;
import static com.michal_stasinski.tabu.SplashScreen.RESTAURANT_ADDRES;
import static com.michal_stasinski.tabu.SplashScreen.dataDeliveryTextFieldName;
import static com.michal_stasinski.tabu.SplashScreen.deliveryCostArray;

public class DataForDelivery extends SwipeBackActivity {
    private DataForDeliveryAdapter adapter;
    private BounceListView listView;
    private Boolean isClick = false;

    public static int deliveryCost = 0;
    public static Boolean adressExist = false;

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
            R.mipmap.firma_icon,
            R.mipmap.ic_launcher,
            R.mipmap.uwagi_icon,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(IS_LOGGED_IN) {
            setTheme(R.style.AppThemeStaffLogged);
        }else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_data_for_delivery_list_view);
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);

        listView = (BounceListView) findViewById(R.id.data_delivery_listView);
        adapter = new DataForDeliveryAdapter(this, imgid);

        for (int i = 0; i < dataDeliveryTextFieldName.length; i++) {

            ShopingCardItem produkt = new ShopingCardItem();

            if (i == 0 || i == 5 || i == 12) {
                produkt.setType(0);
            } else {
                produkt.setType(1);
            }
            produkt.setTitle(dataDeliveryTextFieldName[i]);
            adapter.addItem(produkt);
        }

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        final Button closeButton = (Button) findViewById(R.id.closeBtn);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closeButton.setOnClickListener(null);
                saveState();
                finish();
                overridePendingTransition(R.anim.from_left, R.anim.to_right);
            }
        });

        loadState();


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


        if (addresses != null) {
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                return address;
            } else {
                return null;
            }
        } else {

            return null;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if (resultCode == Activity.RESULT_OK) {

                String result = data.getStringExtra("edit_text");
                Integer pos = data.getIntExtra("pos", 1);
                ShopingCardItem item = (ShopingCardItem) adapter.getItem(pos);

                if (result.equals("")) {
                    item.setTitle(dataDeliveryTextFieldName[pos]);

                } else {
                    item.setTitle(result);
                }

                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }

        ShopingCardItem firstname = (ShopingCardItem) adapter.getItem(1);
        ShopingCardItem surname = (ShopingCardItem) adapter.getItem(2);
        ShopingCardItem email = (ShopingCardItem) adapter.getItem(3);
        ShopingCardItem phone = (ShopingCardItem) adapter.getItem(4);


        String fn = firstname.getTitle().toString();
        String sn = surname.getTitle().toString();
        String em = email.getTitle().toString();
        String ph = phone.getTitle().toString();

        //TODO Sprawdzam czy należy do staffu i czy jest zalogowany
        if (!IS_LOGGED_IN) {
            if (!firstname.getTitle().equals("Imię") && !surname.getTitle().equals("Nazwisko") && !email.getTitle().equals("E-Mail") && !phone.getTitle().equals("Telefon")) {

                Do_you_belong_to_staff areYouStaffMember = new Do_you_belong_to_staff(DataForDelivery.this, fn, sn, em, ph);

            }
        }

    }


    @Override
    protected void onStop() {
        super.onStop();
        saveState();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Address address0 = (Address) getCoordinatesFromAddresse(RESTAURANT_ADDRES);


        ShopingCardItem townEdit = (ShopingCardItem) adapter.getItem(6);
        ShopingCardItem streetEdit = (ShopingCardItem) adapter.getItem(7);
        ShopingCardItem nrEdit = (ShopingCardItem) adapter.getItem(8);

        String town = townEdit.getTitle();
        String street = streetEdit.getTitle();
        String nr = nrEdit.getTitle();

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

        TextView text_cost_delivery = (TextView) findViewById(R.id.data_cost_of_delivery_text);

        if (address1 != null) {

            Location locationA = new Location("point A");

            locationA.setLatitude(address0.getLatitude());
            locationA.setLongitude(address0.getLongitude());

            Location locationB = new Location("point B");

            locationB.setLatitude(address1.getLatitude());
            locationB.setLongitude(address1.getLongitude());

            float distance = locationA.distanceTo(locationB);


            if (townEdit.getTitle().equals(address1.getLocality()) && streetEdit.getTitle().equals(address1.getThoroughfare())) {

                if (distance > Integer.parseInt(String.valueOf(deliveryCostArray.get(2).getDistacne())) * 1000) {
                    text_cost_delivery.setText("NIE DOWOZIMY POD WSKAZANY ADRES");
                    deliveryCost = 0;

                } else if (distance > Integer.parseInt(String.valueOf(deliveryCostArray.get(1).getDistacne())) * 1000) {
                    text_cost_delivery.setText("KOSZT DOSTAWY " + deliveryCostArray.get(2).getPrice() + " zł");
                    deliveryCost = Integer.valueOf(deliveryCostArray.get(2).getPrice());

                } else if (distance > Integer.parseInt(String.valueOf(deliveryCostArray.get(0).getDistacne())) * 1000) {

                    text_cost_delivery.setText("KOSZT DOSTAWY " + deliveryCostArray.get(1).getPrice() + " zł");
                    deliveryCost = Integer.valueOf(deliveryCostArray.get(1).getPrice());
                } else if (distance <= Integer.parseInt(String.valueOf(deliveryCostArray.get(0).getDistacne())) * 1000) {

                    text_cost_delivery.setText("KOSZT DOSTAWY " + deliveryCostArray.get(0).getPrice() + " zł");
                    deliveryCost = Integer.valueOf(deliveryCostArray.get(0).getPrice());
                }
            } else {
                text_cost_delivery.setText("PODANY ADRES NIE ISTNIEJE");
                deliveryCost = 0;
            }


            if (address1.getLocality() == null || address1.getThoroughfare() == null || address1.getPostalCode() == null) {

                if (townEdit.getTitle().equals(address1.getLocality()) && streetEdit.getTitle().equals(address1.getThoroughfare())) {

                } else {
                    text_cost_delivery.setText("PODANY ADRES NIE ISTNIEJE");
                    deliveryCost = 0;
                }
            }

            adapter.notifyDataSetChanged();
        }
        if (address1 == null) {
            text_cost_delivery.setText("BRAK ADRESU DOSTAWY");
            deliveryCost = 0;
        }
        if (townEdit.getTitle().equals("") || streetEdit.getTitle().equals("")) {
            text_cost_delivery.setText("BRAK ADRESU DOSTAWY");
            deliveryCost = 0;
        }
        isClick = false;

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterek, View view, int position, long id) {
                if (isClick == false) {

                    Object listItem = listView.getItemAtPosition(position);


                    if (position == 0 || position == 5 || position == 12) {
                    } else {
                        isClick = true;
                        listView.setOnItemClickListener(null);
                        Intent intent = new Intent(view.getContext(), EditTextPopUp.class);
                        intent.putExtra("title", dataDeliveryTextFieldName[position]);
                        intent.putExtra("position", position);
                        ShopingCardItem item = (ShopingCardItem) adapter.getItem(position);

                        if (item.getTitle().toUpperCase().equals(dataDeliveryTextFieldName[position].toUpperCase())) {
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

    private void saveState() {
        SharedPreferences.Editor editor = getSharedPreferences(DATA_FOR_DELIVERY, MODE_PRIVATE).edit();

        for (int i = 0; i <= 13; i++) {
            ShopingCardItem item = (ShopingCardItem) adapter.getItem(i);

            editor.putString(dataDeliveryTextFieldName[i], item.getTitle().toString());
        }

        editor.putInt("deliveryCost", deliveryCost);
        editor.commit();
        // editor.clear().commit();
    }

    private void loadState() {
        SharedPreferences prefs = getSharedPreferences(DATA_FOR_DELIVERY, MODE_PRIVATE);

        for (int i = 0; i <= 13; i++) {
            String item = prefs.getString(dataDeliveryTextFieldName[i], null);
            if (item != null & i != 12) {
                ShopingCardItem el = (ShopingCardItem) adapter.getItem(i);
                el.setTitle(item);
                adapter.notifyDataSetChanged();
            }
        }
    }

}
