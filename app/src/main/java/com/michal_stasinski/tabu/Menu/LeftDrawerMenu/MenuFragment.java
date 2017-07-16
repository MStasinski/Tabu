package com.michal_stasinski.tabu.Menu.LeftDrawerMenu;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.michal_stasinski.tabu.MainActivity;
import com.michal_stasinski.tabu.Menu.Adapters.CustomListViewAdapter;
import com.michal_stasinski.tabu.Menu.Adapters.CustomNewsListViewAdapter;
import com.michal_stasinski.tabu.Menu.Check_Time_Open_Close;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.Menu.ShopingCardListView;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.CustomDialogClass;

import java.util.ArrayList;

import static com.michal_stasinski.tabu.MainActivity.CHOICE_ACTIVITY;
import static com.michal_stasinski.tabu.SplashScreen.MINIMAL_PRICE_OF_ORDER;
import static com.michal_stasinski.tabu.SplashScreen.newsArrayList;
import static com.michal_stasinski.tabu.SplashScreen.pizzaList;
import static com.michal_stasinski.tabu.SplashScreen.pizzaSauces;
import static com.michal_stasinski.tabu.SplashScreen.saladList;


public class MenuFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {


    protected BounceListView mListViewMenu;
    private View myView;
    private int fireBaseRef;
    GoogleMap mGoogleMap;
    MapView mMapView;
    private CustomListViewAdapter arrayAdapter;
    private ArrayList<MenuItemProduct> menuArrayList;


    public static MenuFragment newInstance(int fireBaseRef) {
        Bundle bundle = new Bundle();
        bundle.putInt("fireBaseRef", fireBaseRef);
        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            fireBaseRef = bundle.getInt("fireBaseRef");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTheme(R.style.AppThemeStaffLogged);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readBundle(getArguments());


        myView = inflater.inflate(R.layout.header_and_bounce_list_view, container, false);


        if (fireBaseRef == 0) {

            myView = inflater.inflate(R.layout.fragment_start, container, false);
            mListViewMenu = (BounceListView) myView.findViewById(R.id.mListView_BaseMenu);
            CustomNewsListViewAdapter arrayAdapter = new CustomNewsListViewAdapter(myView.getContext(), newsArrayList, R.color.color_PIZZA);

            mListViewMenu.setAdapter(arrayAdapter);
            mListViewMenu.setScrollingCacheEnabled(false);

            arrayAdapter.notifyDataSetChanged();
            setHasOptionsMenu(true);

        }
        if (fireBaseRef == 1) {
            myView = inflater.inflate(R.layout.header_and_bounce_list_view, container, false);
            TextView addonText = (TextView) myView.findViewById(R.id.addonText);
            addonText.setText("Do każdej pizzy jeden sos czosnkowy gratis!");
            menuArrayList = pizzaList;
        }

        if (fireBaseRef == 2) {

            myView = inflater.inflate(R.layout.bounce_list_view3, container, false);
            menuArrayList = saladList;
        }
        if (fireBaseRef == 3) {

            myView = inflater.inflate(R.layout.bounce_list_view3, container, false);
            menuArrayList = pizzaSauces;
        }
        if (fireBaseRef == 4) {

            myView = inflater.inflate(R.layout.fragment_contact, container, false);

        }


        if (fireBaseRef != 4 && fireBaseRef != 0) {
            mListViewMenu = (BounceListView) myView.findViewById(R.id.mListView_BaseMenu);

            arrayAdapter = new CustomListViewAdapter(myView.getContext(), menuArrayList, R.color.color_PIZZA, true);
            mListViewMenu.setAdapter(arrayAdapter);
            mListViewMenu.setScrollingCacheEnabled(false);

            arrayAdapter.notifyDataSetChanged();
            setHasOptionsMenu(true);
        } else {

            if (fireBaseRef == 4) {
                ImageButton button_route = (ImageButton) myView.findViewById(R.id.route_button);
                button_route.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        Uri gmmIntentUri = Uri.parse("geo:54.5258318,18.5149058?q=diStrada");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });

                ImageButton button_call = (ImageButton) myView.findViewById(R.id.call_button);

                button_call.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:48586603395"));

                        if (ActivityCompat.checkSelfPermission(myView.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(callIntent);

                    }
                });
                mMapView = (MapView) myView.findViewById(R.id.map);
                if (mMapView != null) {
                    mMapView.onCreate(null);
                    mMapView.onResume();
                    mMapView.getMapAsync(this);
                }
            }
        }
        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();


        TextView toolBarTitle = (TextView) getActivity().findViewById(R.id.toolBarTitle);
        toolBarTitle.setText((MainActivity.largeTextArr[CHOICE_ACTIVITY]).toString());
        if (CHOICE_ACTIVITY != 4 && CHOICE_ACTIVITY != 0) {
            arrayAdapter.notifyDataSetChanged();
            arrayAdapter.setButton_flag_enabled(true);
        }
        if (MainActivity.CHOICE_ACTIVITY == 0) {
            Check_Time_Open_Close time_open_close = new Check_Time_Open_Close();

            TextView txt = (TextView) myView.findViewById(R.id.order_times);

            txt.setText("Zamówienia online: " + time_open_close.getOpenTime()[0] + ":" + time_open_close.getOpenTime()[1] + " - " + time_open_close.getCloseTime()[0] + ":" + time_open_close.getCloseTime()[1] + "\nDostawa przy zamówieniach od " + MINIMAL_PRICE_OF_ORDER + " zł");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void reloadBase() {

        if (CHOICE_ACTIVITY != 4 && CHOICE_ACTIVITY != 0) {


            if (CHOICE_ACTIVITY == 1) {
                menuArrayList = pizzaList;
            }
            if (CHOICE_ACTIVITY == 2) {
                menuArrayList = saladList;
            }
            if (CHOICE_ACTIVITY == 3) {
                menuArrayList = pizzaSauces;
            }

            arrayAdapter = new CustomListViewAdapter(myView.getContext(), menuArrayList, R.color.color_PIZZA, true);
            mListViewMenu.setAdapter(arrayAdapter);
            mListViewMenu.setScrollingCacheEnabled(false);
            arrayAdapter.notifyDataSetChanged();
        }

        if (CHOICE_ACTIVITY == 0) {

            mListViewMenu = (BounceListView) myView.findViewById(R.id.mListView_BaseMenu);

            Check_Time_Open_Close time_open_close = new Check_Time_Open_Close();
            TextView txt = (TextView) myView.findViewById(R.id.order_times);
            txt.setText("Zamówienia online: " + time_open_close.getOpenTime()[0] + ":" + time_open_close.getOpenTime()[1] + " - " + time_open_close.getCloseTime()[0] + ":" + time_open_close.getCloseTime()[1] + "\nDostawa przy zamówieniach od " + MINIMAL_PRICE_OF_ORDER + " zł");

            CustomNewsListViewAdapter arrayAdapter = new CustomNewsListViewAdapter(myView.getContext(), newsArrayList, R.color.color_PIZZA);


            mListViewMenu.setAdapter(arrayAdapter);
            mListViewMenu.setScrollingCacheEnabled(false);

            arrayAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(mMapView.getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(54.5258318, 18.5149058)).title("Di Strada").snippet("zapraszamy na pyszną pizzę"));
        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(54.5258318, 18.5149058)).zoom(14).bearing(0).tilt(45).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));
    }
}