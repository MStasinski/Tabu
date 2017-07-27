package com.michal_stasinski.tabu.Utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.michal_stasinski.tabu.CRM.Adapters.CRM_Order_Kanban_Adapter;
import com.michal_stasinski.tabu.R;

import java.util.TimerTask;

/**
 * Created by mstasinski on 27.07.2017.
 */

public class CountDownTask extends TimerTask {

    private  int counter;

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }



    Handler handler = new Handler();



        @Override
    public void run() {


        handler.post(new Runnable() {
            @Override
            public void run() {
                counter+=1;
                if(counter >= 10){ //check wether it is 90 second (1 and half minutes)
                    counter=0;

                    final Message msg = new Message();
                    final Bundle b = new Bundle();
                    b.putInt("KEY",12);
                    msg.setData(b);
                    handler.sendMessage(msg);

                }
            }
        });
    }
}