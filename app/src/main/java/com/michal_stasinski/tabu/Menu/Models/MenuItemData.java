package com.michal_stasinski.tabu.Menu.Models;

/**
 * Created by win8 on 27.12.2016.
 */

public class MenuItemData {
    private String name;
    private String cena;
    private String text;
    public MenuItemData() {
        // empty default constructor, necessary for Firebase to be able to deserialize blog posts
    }
    public String getPrice() {

        return cena;
    }
    public String getTitle() {

        return name;
    }
    public String getText() {

        return text;
    }
}
