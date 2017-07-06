package com.michal_stasinski.tabu.Menu.Models;

import pl.mobiltek.paymentsmobile.dotpay.model.PaymentCardData;

public class ShopData {

    private static  String name = "";
    private static  String lastName = "";
    private static  String email = "";

    //    private  final String merchantId = "777777";
    private static  String merchantId = "710275";
    private static double productPrice ;

    private static  String description = "";

    private static String currency = "PLN";
    private static String language = "pl";

    private static  PaymentCardData paymentCard = new PaymentCardData("4444 4444 4444 4448", "448", "Jan", "Nowak", "08", "2017");

    public static void setName(String name) {
        ShopData.name = name;
    }

    public static void setLastName(String lastName) {
        ShopData.lastName = lastName;
    }

    public static void setEmail(String email) {
        ShopData.email = email;
    }

    public static void setMerchantId(String merchantId) {
        ShopData.merchantId = merchantId;
    }

    public static void setProductPrice(double productPrice) {
        ShopData.productPrice = productPrice;
    }

    public static void setDescription(String description) {
        ShopData.description = description;
    }

    public static void setCurrency(String currency) {
        ShopData.currency = currency;
    }

    public static void setLanguage(String language) {
        ShopData.language = language;
    }

    public static void setPaymentCard(PaymentCardData paymentCard) {
        ShopData.paymentCard = paymentCard;
    }

    public static String getName() {
        return name;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getEmail() {
        return email;
    }

    public static String getMerchantId() {
        return merchantId;
    }

    public static double getProductPrice() {
        return productPrice;
    }

    public static String getDescription() {
        return description;
    }

    public static String getCurrency() {
        return currency;
    }

    public static String getLanguage() {
        return language;
    }

    public static PaymentCardData getPaymentCard() {
        return paymentCard;
    }
}