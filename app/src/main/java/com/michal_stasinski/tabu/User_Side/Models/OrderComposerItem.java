package com.michal_stasinski.tabu.User_Side.Models;

/**
 * Created by win8 on 17.07.2017.
 */

public class OrderComposerItem {
    private String title;
    private String desc;
    private String price;
    private int type;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
