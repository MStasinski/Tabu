package com.michal_stasinski.tabu.CRM.Model;

import java.util.ArrayList;

/**
 * Created by win8 on 04.07.2017.
 */

public class GetOrderFromFB {
    String deliveryDate;
    String deliveryPrice;
    String email;
    ArrayList<ArrayList<String>> orderList;
    String orderMan;
    String orderNumber;
    String paymentWay;
    String phone;
    String receiptAdres;
    String receiptWay;
    String totalPrice;
    String userId;
    String orderNo;
    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }



    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public ArrayList<ArrayList<String>> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<ArrayList<String>> orderList) {
        this.orderList = orderList;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getOrderMan() {
        return orderMan;
    }

    public void setOrderMan(String orderMan) {
        this.orderMan = orderMan;
    }

    public String getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(String paymentWay) {
        this.paymentWay = paymentWay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReceiptAdres() {
        return receiptAdres;
    }

    public void setReceiptAdres(String receiptAdres) {
        this.receiptAdres = receiptAdres;
    }

    public String getReceiptWay() {
        return receiptWay;
    }

    public void setReceiptWay(String receiptWay) {
        this.receiptWay = receiptWay;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
