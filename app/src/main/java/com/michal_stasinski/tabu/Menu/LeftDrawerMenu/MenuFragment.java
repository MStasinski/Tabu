package com.michal_stasinski.tabu.Menu.LeftDrawerMenu;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michal_stasinski.tabu.MainActivity;
import com.michal_stasinski.tabu.Menu.Adapters.CustomListViewAdapter;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import java.util.ArrayList;

import static com.michal_stasinski.tabu.SplashScreen.pizzaList;
import static com.michal_stasinski.tabu.SplashScreen.pizzaSauces;
import static com.michal_stasinski.tabu.SplashScreen.saladList;


public class MenuFragment extends Fragment {


    protected BounceListView mListViewMenu;
    private View myView;
    private int fireBaseRef;
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

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readBundle(getArguments());


        myView = inflater.inflate(R.layout.header_and_bounce_list_view, container, false);

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

            myView = inflater.inflate(R.layout.bounce_list_view3, container, false);
            menuArrayList = pizzaList;
        }


        mListViewMenu = (BounceListView) myView.findViewById(R.id.mListView_BaseMenu);

        arrayAdapter = new CustomListViewAdapter(myView.getContext(), menuArrayList, R.color.color_PIZZA, true);
        mListViewMenu.setAdapter(arrayAdapter);
        mListViewMenu.setScrollingCacheEnabled(false);

        arrayAdapter.notifyDataSetChanged();
        setHasOptionsMenu(true);
        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
        arrayAdapter.notifyDataSetChanged();
        arrayAdapter.setButton_flag_enabled(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void reloadBase() {

        if (MainActivity.CHOICE_ACTIVITY == 1) {
            menuArrayList = pizzaList;
        }
        if (MainActivity.CHOICE_ACTIVITY == 2) {
            menuArrayList = saladList;
        }
        if (MainActivity.CHOICE_ACTIVITY == 3) {
            menuArrayList = pizzaSauces;
        }
        if (MainActivity.CHOICE_ACTIVITY == 4) {
            menuArrayList = pizzaList;
        }
        arrayAdapter = new CustomListViewAdapter(myView.getContext(), menuArrayList, R.color.color_PIZZA, true);
        mListViewMenu.setAdapter(arrayAdapter);
        mListViewMenu.setScrollingCacheEnabled(false);
        arrayAdapter.notifyDataSetChanged();
    }

}