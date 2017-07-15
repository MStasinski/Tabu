package com.michal_stasinski.tabu.Utils;

import android.util.Log;

import com.michal_stasinski.tabu.Menu.Check_Time_Open_Close;
import com.michal_stasinski.tabu.Menu.Models.TimeListItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static com.michal_stasinski.tabu.Menu.ShopingCardListView.SELECTED_TIME;


/**
 * Created by win8 on 15.07.2017.
 * Klasa wykonująca oliczenie wszystkich mozliwych czasów dostawy i zwracjąjaca ArrayList z obiektami typu TimeListItem
 */

public class CountTimesOfDelivery {


    public static ArrayList<TimeListItem> countAllPossibleTimesOfDelivery() {

        int endHoure = 0;
        int startHoure = 0;
        int startMinute = 0;

        ArrayList<TimeListItem> arrayOfAllPossibleTimeToSelect = new ArrayList<TimeListItem>();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        String strDate = mdformat.format(calendar.getTime());


        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String todayAsString = dateFormat.format(today);
        String tomorrowAsString = dateFormat.format(tomorrow);


        String[] hourArr = strDate.split(":");
        Check_Time_Open_Close time_open_close = new Check_Time_Open_Close();

        String[] closeTime = time_open_close.getCloseTime();

        endHoure = Integer.parseInt(closeTime[0]) + 1;
        startMinute = Integer.parseInt(closeTime[1]);

        Date Start = null;
        Date End = null;


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        try {
            Start = simpleDateFormat.parse(strDate);
            End = simpleDateFormat.parse(endHoure + ":" + startMinute);

        } catch (ParseException e) {

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
        int ilePozycjiczasu = (Integer.parseInt(d[0]) * 60 + Integer.parseInt(d[1])) / realizationTime;


        for (int i = 1; i < ilePozycjiczasu + 1; i++) {
            TimeListItem time = new TimeListItem();

            float h = (float) ((endHoure * 60 + startMinute) - (realizationTime * (i))) / 60;
            int ff = ((endHoure * 60 + startMinute) - (realizationTime * (i))) / 60;
            float g = ((endHoure * 60 + startMinute) - (realizationTime * (i))) / 60;
            String output = MathUtils.formatDecimal(h, 0);
            String r = MathUtils.formatDecimal((h - g) * 60, 0);
            String hh = MathUtils.formatDecimal((h - g) * 60, 2);


            if (Math.ceil((h - g) * 60) < 10) {
                if (Integer.parseInt(r) < 0) {
                    ff -= 1;
                    r = String.valueOf(60 - Math.abs(Integer.parseInt(r)));
                }
            }

            if (Math.ceil(ff) < 0) {
                ff = ff + 24;
                time.setDay("2");
            } else if (Math.ceil(ff) >= 24) {
                ff = ff - 24;
                time.setDay("0");
            } else {
                time.setDay("1");
            }

            if (Integer.parseInt(r) < 10) {
                r = "0" + r;
            }


            if (ff < 10) {
                time.setTextTime("0" + ff + ":" + r);
                time.setTime("0" + ff + ":" + r);
            } else {
                time.setTime(ff + ":" + r);
                time.setTextTime(ff + ":" + r);
            }

            time.setMark(false);
            if (SELECTED_TIME == ilePozycjiczasu - i) {
                time.setMark(true);
            }

            arrayOfAllPossibleTimeToSelect.add(time);
        }

        Collections.reverse(arrayOfAllPossibleTimeToSelect);


        String isDayChange = "";
        int count = 0;
        Boolean isChange = false;
        for (int i = 0; i < arrayOfAllPossibleTimeToSelect.size(); i++) {
            if (!isDayChange.equals(arrayOfAllPossibleTimeToSelect.get(i).getDay())) {

                isDayChange = arrayOfAllPossibleTimeToSelect.get(i).getDay();

                if (count > 0) {
                   // Log.i("informacja", "nastapila zmiana" + arrayOfAllPossibleTimeToSelect.get(i).getTime());
                    isChange = true;
                }
                count++;
            }

            if (isChange == false) {
                arrayOfAllPossibleTimeToSelect.get(i).setOrderData(todayAsString);
            } else {
                arrayOfAllPossibleTimeToSelect.get(i).setOrderData(tomorrowAsString);
            }
        }

        if (SELECTED_TIME == 0) {

            arrayOfAllPossibleTimeToSelect.get(0).setMark(true);
            arrayOfAllPossibleTimeToSelect.get(0).setTextTime("JAK NAJSZYBCIEJ");
        }

        return arrayOfAllPossibleTimeToSelect;
    }
}

