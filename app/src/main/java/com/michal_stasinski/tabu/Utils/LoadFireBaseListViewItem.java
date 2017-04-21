package com.michal_stasinski.tabu.Utils;

import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.tabu.Menu.Adapters.CustomListViewAdapter;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by win8 on 18.04.2017.
 */

public class LoadFireBaseListViewItem {
    private static DatabaseReference myRef;
    private static ArrayList<MenuItemProduct> menuItem;
    protected BounceListView mListViewMenu;

    public static void loadFireBaseData(String databaseReference, final View myView, final BounceListView mListViewMenu) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(databaseReference);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                menuItem = new ArrayList<MenuItemProduct>();
                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();
                    String name = (String) map.get("name");
                    String rank = (String) map.get("rank").toString();
                    String desc = (String) map.get("desc");
                    //Number price = (Number) map.get("price");

                    ArrayList<Number> price = (ArrayList) map.get("price");
                    MenuItemProduct menuItemProduct = new MenuItemProduct();
                    menuItemProduct.setNameProduct(name);
                    menuItemProduct.setRank(rank);
                    menuItemProduct.setDesc(desc);
                    menuItemProduct.setPriceArr(price);
                    menuItem.add(menuItemProduct);

                }
                CustomListViewAdapter arrayAdapter = new CustomListViewAdapter(myView.getContext(), menuItem, R.color.color_PIZZA, true);
                mListViewMenu.setAdapter(arrayAdapter);
                mListViewMenu.setScrollingCacheEnabled(false);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}
