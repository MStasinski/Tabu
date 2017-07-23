package com.michal_stasinski.tabu.User_Side.Models;

import java.util.ArrayList;

/**
 * Created by win8 on 12.07.2017.
 */

public class Post {


    public String deliveryDate;
    public String deliveryPrice;
    public String email;
    public String orderMan;
    public String orderNo;
    public String orderStatus;
    public ArrayList<ArrayList<String>> ordersList;
    public String paymentWay;
    public String phone;
    public String receiptAdres;
    public String receiptWay;
    public String totalPrice;
    public String userId;
    public String orderNumber;

    public Post() {
        // Default constructor required for calls to DataSnapshot.getValue(Post.class)
    }

    public Post(String deliveryDate,
                String deliveryPrice,
                String email,
                String orderMan,
                String orderNo,
                String orderStatus,
                ArrayList<ArrayList<String>> ordersList,
                String paymentWay,
                String phone,
                String receiptAdres,
                String receiptWay,
                String totalPrice,
                String userId,
                String orderNumber
    ) {

        this.deliveryDate = deliveryDate;
        this.deliveryPrice = deliveryPrice;
        this.email = email;
        this.orderMan = orderMan;
        this.orderNo = orderNo;
        this.orderStatus = orderStatus;
        this.ordersList = ordersList;
        this.paymentWay = paymentWay;
        this.phone = phone;
        this.receiptAdres = receiptAdres;
        this.receiptWay = receiptWay;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.orderNumber=orderNumber;
    }

}
