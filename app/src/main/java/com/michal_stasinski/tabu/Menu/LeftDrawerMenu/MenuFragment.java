package com.michal_stasinski.tabu.Menu.LeftDrawerMenu;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.LoadFireBaseListViewItem;


public class MenuFragment extends Fragment {
    protected BounceListView mListViewMenu;
    View myView;
    private String fireBaseRef;


    public static MenuFragment newInstance(String fireBaseRef) {
        Bundle bundle = new Bundle();
        bundle.putString("fireBaseRef", fireBaseRef);
        MenuFragment fragment = new MenuFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private void readBundle(Bundle bundle) {
        if (bundle != null) {
            fireBaseRef = bundle.getString("fireBaseRef");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("informacja","MenuFragment________onStart"+fireBaseRef);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        readBundle(getArguments());

        myView = inflater.inflate(R.layout.header_and_bounce_list_view, container, false);

        TextView addonText = (TextView) myView.findViewById(R.id.addonText);
        addonText.setText("Do każdej pizzy jeden sos czosnkowy gratis!");


        mListViewMenu = (BounceListView) myView.findViewById(R.id.mListView_BaseMenu);
        LoadFireBaseListViewItem.loadFireBaseData(fireBaseRef, myView, mListViewMenu);

        setHasOptionsMenu(true);
        return myView;
    }


}