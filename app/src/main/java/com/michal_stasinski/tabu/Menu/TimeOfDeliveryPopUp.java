package com.michal_stasinski.tabu.Menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.MathUtils;

import java.sql.Array;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class TimeOfDeliveryPopUp extends AppCompatActivity {
    private BounceListView timeOfDeliveryListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_of_delivery_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.widthPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * 0.8));

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strDate = mdformat.format(calendar.getTime());
        String[] gg = strDate.split(":");
        Log.i("informacja", "time__________" + strDate);


        Date Start = null;
        Date End = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            Start = simpleDateFormat.parse(strDate);
            End = simpleDateFormat.parse(22 + ":" + 15);
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
        int f = (Integer.parseInt(d[0]) * 60 + Integer.parseInt(d[1])) / 30;


        for (int i = 0; i < f; i++) {
            String output = MathUtils.formatDecimal((22 * 60 + 15 - 30 * i) / 60,2);
          //  int rest = (22 * 60 + 15 - 30 * i) / 60  - Integer.parseInt(output);

            Log.i("informacja",  ((22 * 60 + 15) - (30 * i+1)) / 60 +"___ans__________" + output );

        }
       /* long currentTime = System.currentTimeMillis();
        int edtOffset = TimeZone.getTimeZone("EST").getOffset(currentTime);
        int gmtOffset = TimeZone.getTimeZone("GMT").getOffset(currentTime);
        int hourDifference = (gmtOffset - edtOffset) / (1000 * 60 * 60);
        String diff = hourDifference + " hours";
        Log.i("informacja", "time__________" + diff);*/

    }


}
