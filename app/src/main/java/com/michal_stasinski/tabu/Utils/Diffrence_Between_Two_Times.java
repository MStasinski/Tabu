package com.michal_stasinski.tabu.Utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by win8 on 27.07.2017.
 */

public class Diffrence_Between_Two_Times {

    public static String getTimeDifferance(String endTime) {
        try {

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            String startTime = simpleDateFormat.format(calendar.getTime());

            Date time1 = new SimpleDateFormat("HH:mm").parse(endTime);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            Date time2 = new SimpleDateFormat("HH:mm").parse(startTime);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);

            Date x = calendar1.getTime();
            Date xy = calendar2.getTime();
            long diff = x.getTime() - xy.getTime();
            int diffMinutes = (int) (diff / (60 * 1000));

            int diffHours = diffMinutes / 60;

            if (diffMinutes > 59) {
                diffMinutes = diffMinutes % 60;
            }
            Log.i("informacja", "-----diffHours+\":\"+diffMinutes " + diffHours + ":" + diffMinutes);

            if (diffHours > 0) {
                String totalDiff = String.valueOf((diffHours * 60) + diffMinutes);//diffHours+":"+diffMinutes;
                return totalDiff;
            } else {
                String totalDiff = String.valueOf(diffMinutes);
                return totalDiff;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }
}
