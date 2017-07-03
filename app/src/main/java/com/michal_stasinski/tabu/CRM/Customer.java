package com.michal_stasinski.tabu.CRM;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.michal_stasinski.tabu.R;

public class Customer extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
    }
}
