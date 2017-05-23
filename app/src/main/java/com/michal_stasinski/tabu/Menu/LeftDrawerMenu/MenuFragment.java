package com.michal_stasinski.tabu.Menu.LeftDrawerMenu;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michal_stasinski.tabu.Menu.Adapters.CustomListViewAdapter;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import java.util.ArrayList;

import static com.michal_stasinski.tabu.SplashScreen.pizzaList;
import static com.michal_stasinski.tabu.SplashScreen.saladList;
import static com.michal_stasinski.tabu.SplashScreen.pizzaCheeseList;
import static com.michal_stasinski.tabu.SplashScreen.pizzaColdCutsMeatList;


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

        TextView addonText = (TextView) myView.findViewById(R.id.addonText);
        addonText.setText("Do każdej pizzy jeden sos czosnkowy gratis!");


        mListViewMenu = (BounceListView) myView.findViewById(R.id.mListView_BaseMenu);
        if (fireBaseRef == 1) {
            menuArrayList = pizzaList;
        }

        if (fireBaseRef == 2) {
            menuArrayList = saladList;
        }
        if (fireBaseRef == 3) {
            menuArrayList = pizzaCheeseList;
        }
        if (fireBaseRef == 4) {
            menuArrayList = pizzaColdCutsMeatList;
        }




        arrayAdapter = new CustomListViewAdapter(myView.getContext(), menuArrayList, R.color.color_PIZZA, true);

        mListViewMenu.setAdapter(arrayAdapter);
        mListViewMenu.setScrollingCacheEnabled(false);
        setHasOptionsMenu(true);
        return myView;
    }

    @Override
    public void onResume() {
        super.onResume();
        arrayAdapter.setButton_flag_enabled(true);
        Log.i("informacja","onResume w MenuFragement");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("informacja","onDestroyw MenuFragement");
    }
}