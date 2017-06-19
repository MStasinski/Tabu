package com.michal_stasinski.tabu.Menu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.michal_stasinski.tabu.Menu.Adapters.TimeOfDeliveryAdapter;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.Menu.Models.TimeListItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.SplashScreen;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.MathUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

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
        //omriFunction();

    }

    @Override
    protected void onResume() {
        super.onResume();


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strDate = mdformat.format(calendar.getTime());

        Log.i("informacja", " strDate" + strDate);

        String[] gg = strDate.split(":");

        DateFormat df = new SimpleDateFormat("EEEE");
        String date = df.format(Calendar.getInstance().getTime());

        Log.i("informacja", " date  " + date);
        Log.i("informacja"," SplashScreen.timeWhenRestaurantIsClose.get(1  " +SplashScreen.timeWhenRestaurantIsClose );

        int endHoure = 32;
        int startHoure = 0;
        int startMinute = 00;
        int day = 0;

        if (date.equals("Monday")) {


            
            String[] closeTime = String.valueOf(SplashScreen.timeWhenRestaurantIsClose.get(1)).split(":");
            String[] openTime = String.valueOf(SplashScreen.timeWhenRestaurantIsOpen.get(1)).split(":");

            endHoure = Integer.parseInt(closeTime[0])+1;
            startMinute = Integer.parseInt(closeTime[1]);

            Log.i("informacja", " closeTime  " + closeTime[0] + closeTime[1]);
            // endHoure = (int) SplashScreen.timeWhenRestaurantIsClose.get(1);
        }
        if (date.equals("Tuesday")) {
            String[] closeTime = String.valueOf(SplashScreen.timeWhenRestaurantIsClose.get(2)).split(":");
            String[] openTime = String.valueOf(SplashScreen.timeWhenRestaurantIsOpen.get(2)).split(":");
            endHoure = Integer.parseInt(closeTime[0])+1;
            startMinute = Integer.parseInt(closeTime[1]);
        }

        if (date.equals("Wednesday")) {
            String[] closeTime = String.valueOf(SplashScreen.timeWhenRestaurantIsClose.get(3)).split(":");
            String[] openTime = String.valueOf(SplashScreen.timeWhenRestaurantIsOpen.get(3)).split(":");
            endHoure = Integer.parseInt(closeTime[0])+1;
            startMinute = Integer.parseInt(closeTime[1]);
        }

        if (date.equals("Thursday")) {

            String[] closeTime = String.valueOf(SplashScreen.timeWhenRestaurantIsClose.get(4)).split(":");
            String[] openTime = String.valueOf(SplashScreen.timeWhenRestaurantIsOpen.get(4)).split(":");
            endHoure = Integer.parseInt(closeTime[0])+1;
            startMinute = Integer.parseInt(closeTime[1]);
        }
        if (date.equals("Friday")) {

            String[] closeTime = String.valueOf(SplashScreen.timeWhenRestaurantIsClose.get(5)).split(":");
            String[] openTime = String.valueOf(SplashScreen.timeWhenRestaurantIsOpen.get(5)).split(":");
            endHoure = Integer.parseInt(closeTime[0])+1;
            startMinute = Integer.parseInt(closeTime[1]);
        }
        if (date.equals("Saturday")) {

            String[] closeTime = String.valueOf(SplashScreen.timeWhenRestaurantIsClose.get(6)).split(":");
            String[] openTime = String.valueOf(SplashScreen.timeWhenRestaurantIsOpen.get(6)).split(":");
            endHoure = Integer.parseInt(closeTime[0])+1;
            startMinute = Integer.parseInt(closeTime[1]);
        }

        if (date.equals("Sunday")) {

            String[] closeTime = String.valueOf(SplashScreen.timeWhenRestaurantIsClose.get(0)).split(":");
            String[] openTime = String.valueOf(SplashScreen.timeWhenRestaurantIsOpen.get(0)).split(":");
            endHoure = Integer.parseInt(closeTime[0])+1;
            startMinute = Integer.parseInt(closeTime[1]);
        }


        Date Start = null;
        Date End = null;


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        Log.i("informacja", startHoure + " date  " + endHoure);

        try {

            Start = simpleDateFormat.parse(strDate);

            End = simpleDateFormat.parse(endHoure + ":" + startMinute);

        } catch (ParseException e) {
            //Some thing if its not working
        }

        long difference = End.getTime() - Start.getTime() ;
        Log.i("informacja", "difference" + difference);
        Log.i("informacja", "End.getTime() " + End.getTime());
        Log.i("informacja", "Start.getTime() " + Start.getTime());

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
        Log.i("informacja", "ANSWER  " + c);
        int realizationTime = 30;
        int ilePozycjiczasu = (Integer.parseInt(d[0]) * 60 + Integer.parseInt(d[1])) / realizationTime;

        timeList = new ArrayList<TimeListItem>();
        adapterek = new TimeOfDeliveryAdapter(this);

        for (int i = 1; i < ilePozycjiczasu; i++) {
            TimeListItem time = new TimeListItem();

            float h = (float) ((endHoure * 60 + startMinute) - (realizationTime * (i))) / 60;
            int ff = ((endHoure * 60 + startMinute) - (realizationTime * (i))) / 60;

            float g = ((endHoure * 60 + startMinute) - (realizationTime * (i))) / 60;

            String output = MathUtils.formatDecimal(h, 0);

            String r = MathUtils.formatDecimal((h - g) * 60, 0);

            String hh = MathUtils.formatDecimal((h - g) * 60, 2);


          //  Log.i("informacja", "godzina " + output);
           // Log.i("informacja", "r i hh minut " + r);
          //  Log.i("informacja", "_____________________");

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
        time.setTime("JAK NAJSZYBCIEJ");
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
    }

    public void omriFunction() {
        Date Start = null;
        Date End = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            Start = simpleDateFormat.parse(12 + ":" + 00);
            End = simpleDateFormat.parse(21 + ":" + 45);
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


        timeList = new ArrayList<TimeListItem>();
        adapterek = new TimeOfDeliveryAdapter(this);


        int realizationTime = 30;
        int ilePozycjiczasu = ((hours * 60) + min) / realizationTime;

        Log.i("informacja", "ANSWER  " + c);

        if (hours > 24) {
            Log.i("informacja", "przechodzi na nastepny dzień");
        } else {
            Log.i("informacja", "nie przechodzi na nastepny dzień");
        }

        for (int i = 1; i < ilePozycjiczasu + 1; i++) {
            TimeListItem time = new TimeListItem();

            float h = (float) ((hours * 60 + min) - (realizationTime * (i + 1))) / 60;
            int ff = ((hours * 60 + min) - (realizationTime * (i + 1))) / 60;
            float g = ((hours * 60 + min) - (realizationTime * (i + 1))) / 60;

            String output = MathUtils.formatDecimal(h, 0);
            String r = MathUtils.formatDecimal((h - g) * 60, 0);
            String hh = MathUtils.formatDecimal((h - g) * 60, 2);

            Log.i("informacja", "output " + output);
            Log.i("informacja", "r " + r);
            Log.i("informacja", "hh " + hh);

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
        time.setTime("JAK NAJSZYBCIEJ");
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

    }
}
