package com.michal_stasinski.tabu.Utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by mstasinski on 25.05.2017.
 */

public class MathUtils {

    public static String formatDecimal(Number num , int decimal) {

        NumberFormat formatter = new DecimalFormat("0.00");
        formatter.setMinimumFractionDigits(decimal);
        String output = formatter.format(num);
        return output.replace(',','.');
    }
}
