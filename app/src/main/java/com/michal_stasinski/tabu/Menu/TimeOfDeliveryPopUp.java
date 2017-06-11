package com.michal_stasinski.tabu.Menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.tabu.Menu.Adapters.TimeOfDeliveryAdapter;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.Menu.Models.TimeListItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.MathUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

import static com.michal_stasinski.tabu.Menu.ShopingCardListView.selected_time;

public class TimeOfDeliveryPopUp extends AppCompatActivity {
    private BounceListView timeOfDeliveryListView;
    public static ArrayList<TimeListItem> timeList;
    private BounceListView mListViewMenu;


    private TimeOfDeliveryAdapter adapterek;
    private ArrayList<MenuItemProduct> timeArr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_time_of_delivery_pop_up);

        setContentView(R.layout.bounce_list_view);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.widthPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * 0.8));



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("SendOrderOnlines");


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();
                    // String end = (String) map.get("end");
                    ArrayList<Number> price = (ArrayList) map.get("end");
                    Log.i("informacja", "timeArr "+price);


                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("informacja", "resummmmmmmmmme");

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strDate = mdformat.format(calendar.getTime());
        String[] gg = strDate.split(":");




        Date Start = null;
        Date End = null;

        int endHoure = 32;
        int startMinute = 15;


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");


        try {

            Start = simpleDateFormat.parse(strDate);

            End = simpleDateFormat.parse(endHoure + ":" + startMinute);

        } catch (ParseException e) {
            //Some thing if its not working
        }

        long difference = End.getTime() - Start.getTime();


        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        if (hours < 0) {
            hours += 24;
        }
        if (min < 0) {
            float newone = (float) min / 60;
            min += 60;
            hours = (int) (hours + newone);
        }
        String c = hours + ":" + min;
        String[] d = c.split(":");
        int realizationTime = 30;
        int f = (Integer.parseInt(d[0]) * 60 + Integer.parseInt(d[1])) / realizationTime;

        timeList = new ArrayList<TimeListItem>();
        adapterek = new TimeOfDeliveryAdapter(this);

        for (int i = 0; i < f; i++) {
            TimeListItem time = new TimeListItem();

            float h = (float) ((endHoure * 60 + startMinute) - (realizationTime * (i + 1))) / (float) 60;
            int ff = ((endHoure * 60 + 15) - (realizationTime * (i + 1))) / 60;
            float g = ((endHoure * 60 + 15) - (realizationTime * (i + 1))) / 60;
            String output = MathUtils.formatDecimal(h, 0);
            String r = MathUtils.formatDecimal((h - g) * 60, 0);
            String hh = MathUtils.formatDecimal((h - g) * 60, 2);
            if (Math.ceil((h - g) * 60) < 10) {
                r = "0" + r;
            }
            if (Math.ceil(ff) >= 24) {
                ff = ff - 24;
            }

            time.setTime(ff + ":" + r);
            time.setMark(false);
            if (selected_time == i) {
                time.setMark(true);
            }
            timeList.add(time);
            //adapterek.addItem(time);
        }



        Collections.reverse(timeList);
        TimeListItem time = new TimeListItem();
        time.setTime("jak najszybciej");
        time.setMark(false);
        if (selected_time == 0) {
            time.setMark(true);
        }

        timeList.add(0, time);

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
                selected_time = position;
                adapterek.notifyDataSetChanged();


            }
        });


       /* long currentTime = System.currentTimeMillis();
        int edtOffset = TimeZone.getTimeZone("EST").getOffset(currentTime);
        int gmtOffset = TimeZone.getTimeZone("GMT").getOffset(currentTime);
        int hourDifference = (gmtOffset - edtOffset) / (1000 * 60 * 60);
        String diff = hourDifference + " hours";
        Log.i("informacja", "time__________" + diff);*/

    }
}
