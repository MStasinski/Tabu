package com.michal_stasinski.tabu.Menu.Pop_Ups;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.michal_stasinski.tabu.Menu.Adapters.TimeOfDeliveryAdapter;
import com.michal_stasinski.tabu.Menu.Models.TimeListItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import java.util.ArrayList;

import static com.michal_stasinski.tabu.Menu.ShopingCard.SELECTED_TIME;

public class TimeOfDeliveryPopUp extends AppCompatActivity {

    public static ArrayList<TimeListItem> timeList;
    private BounceListView mListViewMenu;
    private String[] hourArr;
    private static TimeOfDeliveryAdapter adapterek;
    private String[] closeTime;
    private String todayAsString;
    private String tomorrowAsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bounce_list_view);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * 0.6));

        if(timeList.size()<10) {
            getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

       // timeList = new ArrayList<TimeListItem>();

        /*oblicz wszystkie mozliwe czasy dostwy i wrzuc do adpapterka*/
        //timeList = countAllPossibleTimesOfDelivery(Integer.parseInt(TIME_OF_REALIZATION_DELIVERY));

         /*TimeOfdeliveryPopUp działą z adapterem TimeOfDeliveryAdapter*/
        adapterek = new TimeOfDeliveryAdapter(this);

        for (int i = 0; i < timeList.size(); i++) {
            adapterek.addItem(timeList.get(i));
        }


        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        mListViewMenu.setAdapter(adapterek);
        mListViewMenu.setScrollingCacheEnabled(false);
        mListViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                for (int i = 0; i < adapterek.getCount(); i++) {
                    TimeListItem item = (TimeListItem) adapterek.getItem(i);
                    item.setMark(false);
                }

                TimeListItem item = (TimeListItem) adapterek.getItem(position);
                item.setMark(true);
                SELECTED_TIME = position;

                adapterek.notifyDataSetChanged();

            }
        });
    }

    public static void reloadTimeOfDeliverPopUp(){
      if(adapterek!=null)
        adapterek.notifyDataSetChanged();
    }
}
