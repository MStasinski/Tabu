package com.michal_stasinski.tabu.Menu.Models;

import java.util.ArrayList;

/**
 * Created by Michal Stasiński on 27.12.2016.
 */

public class MenuItemProduct {
    /**
     * @param rank  - position in firebase
     * @param HowManyItemSelected  - state [0- not selected ,1,2 ]in Order Composer when user choose "Extras"  in AddonsPopUp
     * @param priceArray - load from firebase  -price depends on pizzasize
     */
    private String rank;
    private String name;
    private String description;
    private ArrayList<Number> priceArray;
    private int HowManyItemSelected;





    public int getHowManyItemSelected() {
        return HowManyItemSelected;
    }

    public void setHowManyItemSelected(int howManyItemSelected) {
        this.HowManyItemSelected = howManyItemSelected;
    }

    public ArrayList<Number> getPriceArray() {
        return priceArray;
    }

    public void setPriceArray(
            ArrayList<Number> priceArray) {
        this.priceArray = priceArray;
    }

    public String getDescription() {
        return description;
    }


    public void setDescription(String desc) {
        this.description = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

}
