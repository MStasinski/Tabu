package com.michal_stasinski.tabu.Utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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


    public static String twoDatesBetweenTime(String oldtime) {
        // TODO Auto-generated method stub
        int day = 0;
        int hh = 0;
        int mm = 0;
        try {
            //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date oldDate = dateFormat.parse(oldtime);
            Date cDate = new Date();
            Long timeDiff = oldDate.getTime() - cDate.getTime();
            day = (int) TimeUnit.MILLISECONDS.toDays(timeDiff);
            hh = (int) (TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.DAYS.toHours(day));
            mm = (int) (TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (day == 0) {
            return String.valueOf((60 * hh) + mm);
            //return hh + " hour " + mm + " min";
        } else if (hh == 0) {
            return String.valueOf(mm);
            //return mm + " min";
        } else {
            return String.valueOf((24 * 60) + (60 * hh) + mm);
            //  return day + " days " + hh + " hour " + mm + " min";
        }
    }


}
