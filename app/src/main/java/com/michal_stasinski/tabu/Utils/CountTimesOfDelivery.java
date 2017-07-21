package com.michal_stasinski.tabu.Utils;

import com.michal_stasinski.tabu.Menu.Models.TimeListItem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static com.michal_stasinski.tabu.Menu.ShopingCard.SELECTED_TIME;
import static com.michal_stasinski.tabu.SplashScreen.TIME_OF_DELIVERY_INTERVAL;


/**
 * Created by win8 on 15.07.2017.
 * Klasa wykonująca oliczenie wszystkich mozliwych czasów dostawy
 */

public class CountTimesOfDelivery {


    /*funkcja z wszystkimy czasami w lisćie z TimeOdDeliveryPopUp zwracjąjaca ArrayList z obiektami typu TimeListItem*/

    public static ArrayList<TimeListItem> countAllPossibleTimesOfDelivery(int time_Of_realization) {

        /*godzina i minuta zakonczenia*/
        int endHoure = 0;
        int endMinute = 0;
        int strDate_with_realization_Houre;
        int strDate_with_realization_Minute;
        int interval_Time_in_ListView = Integer.parseInt(TIME_OF_DELIVERY_INTERVAL);

        int realizationTime = time_Of_realization;


        ArrayList<TimeListItem> arrayOfAllPossibleTimeToSelect = new ArrayList<TimeListItem>();

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        String startTime = simpleDateFormat.format(calendar.getTime());


        Date today = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String todayAsString = dateFormat.format(today);
        String tomorrowAsString = dateFormat.format(tomorrow);


        Check_if_the_restaurant_is_open time_open_close = new Check_if_the_restaurant_is_open();

        String[] closeTime = time_open_close.getCloseTime();


        int eh = Integer.parseInt(closeTime[0]);
        int em = Integer.parseInt(closeTime[1]);
        Date eTimeData = null;
        try {

            eTimeData = simpleDateFormat.parse(eh + ":" + em);
        } catch (ParseException e) {

        }

        Date startTimeData = null;
        Date endTimeData = null;


        String[] startHourArr = startTime.split(":");
        /*czas rozpoczecia realizacji*/
        strDate_with_realization_Houre = Integer.parseInt(startHourArr[0]) * 60 + Integer.parseInt(startHourArr[1]) + realizationTime;
        strDate_with_realization_Minute = strDate_with_realization_Houre - (strDate_with_realization_Houre / 60) * 60;
        strDate_with_realization_Minute = (strDate_with_realization_Minute / 5) * 5;

        int houre = strDate_with_realization_Houre / 60;
        if (houre >= 24) {
            houre = houre - 24;
        }

        String startDateNew = String.valueOf(houre) + ":" + String.valueOf(strDate_with_realization_Minute);


        int endH = eh * 60 + em + realizationTime;
        int endM = endH - (endH / 60) * 60;


        String endDateNew = String.valueOf(endH / 60) + ":" + String.valueOf(endM);

        endHoure = endH / 60;
        endMinute = endM;

        try {

            startTimeData = simpleDateFormat.parse(startDateNew);
            endTimeData = simpleDateFormat.parse(endDateNew);

        } catch (ParseException e) {

        }

        long difference = endTimeData.getTime() - startTimeData.getTime();
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
        String ileGodzinRoznicy = hours + ":" + min;
        String[] d = ileGodzinRoznicy.split(":");


        int ilePozycjiczasu = (Integer.parseInt(d[0]) * 60 + Integer.parseInt(d[1])) / interval_Time_in_ListView;

        for (int i = 0; i < ilePozycjiczasu+1 ; i++) {

            TimeListItem time = new TimeListItem();
            float h = (float) ((endHoure * 60 + endMinute) - (interval_Time_in_ListView * (i))) / 60;
            int ff = ((endHoure * 60 + endMinute) - (interval_Time_in_ListView * (i))) / 60;

            float g = ((endHoure * 60 + endMinute) - (interval_Time_in_ListView * (i))) / 60;
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
            } else if (Math.ceil(ff) >= 24) {
                ff = ff - 24;
            } else {
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

            int change = strDate_with_realization_Houre / 60;
            if (ff < change) {
                time.setOrderData(tomorrowAsString);
            } else {
                time.setOrderData(todayAsString);
            }

            time.setMark(false);
            if (SELECTED_TIME == ilePozycjiczasu - i) {
                time.setMark(true);
            }

            arrayOfAllPossibleTimeToSelect.add(time);
        }

        Collections.reverse(arrayOfAllPossibleTimeToSelect);
        arrayOfAllPossibleTimeToSelect.get(0).setTextTime("JAK NAJSZYBCIEJ");
        if (SELECTED_TIME == 0) {

            arrayOfAllPossibleTimeToSelect.get(0).setMark(true);

        }

        return arrayOfAllPossibleTimeToSelect;
    }


   /* obliczenie czasu dla dostawy  "Jak Najszybciej... w moencie wysłania zamowienia

    public static String countTimeOfDelivery_type_asFastYouCan(int time_Of_realization) {
        int realizationTime = time_Of_realization;

        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        String strDate = mdformat.format(calendar.getTime());

        String[] hourArr = strDate.split(":");

        int newhourValue = Integer.parseInt(hourArr[0]) * 60 + Integer.parseInt(hourArr[1]) + realizationTime;

        int min = newhourValue - (newhourValue / 60) * 60;
        //float newhourValue2 = Integer.parseInt(hourArr[0]) * 60 + Integer.parseInt(hourArr[1]) + reslization;

        //String timeAFYC = String.valueOf(newhourValue / 60);
        String timeAFYC = String.valueOf(newhourValue / 60) + ":" + String.valueOf(min);
        return timeAFYC;
    }*/

}

