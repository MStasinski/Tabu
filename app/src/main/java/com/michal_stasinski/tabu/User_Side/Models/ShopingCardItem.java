package com.michal_stasinski.tabu.User_Side.Models;

/**
 * Created by win8 on 14.05.2017.
 */

public class ShopingCardItem {

    private String title;
    private String desc;
    private int type;
    private int nr;
    private float price;


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public int getNr() {
        return nr;
    }

    public void setNr(int nr) {
        this.nr = nr;
    }


    public ShopingCardItem() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
