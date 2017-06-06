package com.michal_stasinski.tabu.Utils;

import android.util.Log;

import static com.michal_stasinski.tabu.SplashScreen.orderList;

/**
 * Created by win8 on 06.06.2017.
 */

public class OrderComposerUtils {

    public static  int sum_of_all_quantities() {
        int sumOfQuantity = 0;

        for (int i = 0; i < orderList.size(); i++) {
            sumOfQuantity += orderList.get(i).getQuantity();
        }
       return  sumOfQuantity;
    }

    public static String sum_of_all_the_prices() {
        float sumOfAllPrices = 0;

        for (int i = 0; i < orderList.size(); i++) {
            sumOfAllPrices += (orderList.get(i).getSumOfPrices()*orderList.get(i).getQuantity()) ;
        }

       return MathUtils.formatDecimal(sumOfAllPrices,2);
    }
}
