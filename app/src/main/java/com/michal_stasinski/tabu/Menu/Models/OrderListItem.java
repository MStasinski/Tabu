package com.michal_stasinski.tabu.Menu.Models;

/**
 * Created by win8 on 18.05.2017.
 */

public class OrderListItem {
    private String name;
    private String size;
    private String addon;
    private String sauce;;
    private String note;
    private float sumOfPrices;
    private int quantity;

    public float getSumOfPrices() {
        return sumOfPrices;
    }

    public void setSumOfPrices(float sumOfPrices) {
        this.sumOfPrices = sumOfPrices;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getAddon() {
        return addon;
    }

    public void setAddon(String addon) {
        this.addon = addon;
    }

    public String getSauce() {
        return sauce;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
