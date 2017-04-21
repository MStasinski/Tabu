package com.michal_stasinski.tabu.Menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.builders.Actions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.tabu.Menu.Adapters.CustomListViewAdapter;
import com.michal_stasinski.tabu.Menu.Adapters.PizzaSizeAdapter;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.LoadFireBaseListViewItem;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by win8 on 18.04.2017.
 */

public class Pop extends Activity {

    private BounceListView mListViewMenu;
    private static Context contex;

    private int chooseSize = 0;
    private static final String TAG = Pop.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        Log.i("informacja", "onCreate");
        super.onCreate(savedInstanceState);
        contex = this;
        setContentView(R.layout.bounce_list_view);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.widthPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * 0.4));
        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        loadFireBaseData("pizzaSizes", mListViewMenu);


    }

    public static void loadFireBaseData(String databaseReference, final BounceListView mListViewMenu) {
        DatabaseReference myRef;


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference(databaseReference);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<MenuItemProduct> menuItem = new ArrayList<MenuItemProduct>();
                final ArrayList<Integer> markSign = new ArrayList<Integer>();

                for (DataSnapshot item : dataSnapshot.getChildren()) {

                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();
                    String name = (String) map.get("name");
                    String rank = (String) map.get("rank").toString();

                    MenuItemProduct menuItemProduct = new MenuItemProduct();
                    menuItemProduct.setNameProduct(name);
                    markSign.add(0);
                    menuItemProduct.setRank(rank);
                    menuItem.add(menuItemProduct);

                }

                final PizzaSizeAdapter adapterek = new PizzaSizeAdapter(contex, menuItem , markSign);

                mListViewMenu.setAdapter(adapterek);
                mListViewMenu.setScrollingCacheEnabled(false);

                mListViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                    @Override
                    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                        for (int i = 0; i <  markSign.size(); i++) {
                            markSign.set(i,0);
                        }
                        OrderCompositorFragment.setSize(position);
                        markSign.set(position,1);
                        adapterek.notifyDataSetChanged();

                        Object listItem = mListViewMenu.getItemAtPosition(position);
                        Log.i("informacja", " onItemClick________setText"+ markSign);
                        //TextView checkmark = (TextView) view.findViewById(R.id.checkmark);
                        //checkmark.setText("\u2713");
                        // adapterek.notifyDataSetChanged();


                        // v.setText("test");
                        // Bundle bundle = new Bundle();

                        // bundle.putInt("desc",(position).);

                        //   TextView v = (TextView) view.findViewById(R.id.order_row_title);
                        // v.setText("test");
                        // Intent intent = new Intent();
                        //intent.setClass(view.getContext(), Pop.class);
                        //startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("informacja", "Pop________onDestroy");
    }

    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);
        Log.i("informacja", "Pop_______onPanelClosed");

    }

    public int getChooseSize() {
        return chooseSize;
    }

    public void setChooseSize(int chooseSize) {
        this.chooseSize = chooseSize;
    }

}
