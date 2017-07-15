package com.michal_stasinski.tabu.Menu.Models;

/**
 * Created by win8 on 23.05.2017.
 */

public class TimeListItem {
    private String time;
    private Boolean mark;
    private String day;
    private String orderData;

    public String getTextTime() {
        return textTime;
    }

    public void setTextTime(String textTime) {
        this.textTime = textTime;
    }

    private String textTime;

    public String getOrderData() {
        return orderData;
    }

    public void setOrderData(String orderData) {
        this.orderData = orderData;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Boolean getMark() {
        return mark;
    }

    public void setMark(Boolean mark) {
        this.mark = mark;
    }


}
