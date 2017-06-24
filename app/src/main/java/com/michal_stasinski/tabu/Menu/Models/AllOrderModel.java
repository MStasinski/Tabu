package com.michal_stasinski.tabu.Menu.Models;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by win8 on 14.06.2017.
 */

public class AllOrderModel  {

    private Date deliveryDate ;
    private String deliveryPrice;
    private String text;
    private String orderMan;
    ArrayList<OrderListItem>  order = new ArrayList<OrderListItem>();

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOrderMan() {
        return orderMan;
    }

    public void setOrderMan(String orderMan) {
        this.orderMan = orderMan;
    }

    public ArrayList<OrderListItem> getOrder() {
        return order;
    }

    public void setOrder(ArrayList<OrderListItem> order) {
        this.order = order;
    }







}
