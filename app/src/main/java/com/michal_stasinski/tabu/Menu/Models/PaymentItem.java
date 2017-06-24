package com.michal_stasinski.tabu.Menu.Models;

import android.widget.ImageView;

/**
 * Created by win8 on 22.06.2017.
 */

public class PaymentItem {
    private String payment_txt;
    private Boolean mark;

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    private Integer image;





    public String getPayment_txt() {
        return payment_txt;
    }

    public void setPayment_txt(String payment_txt) {
        this.payment_txt = payment_txt;
    }

    public Boolean getMark() {
        return mark;
    }

    public void setMark(Boolean mark) {
        this.mark = mark;
    }


}
