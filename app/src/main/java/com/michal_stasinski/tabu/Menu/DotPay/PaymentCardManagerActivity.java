package com.michal_stasinski.tabu.Menu.DotPay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.michal_stasinski.tabu.Menu.Models.ShopData;
import com.michal_stasinski.tabu.R;


import pl.mobiltek.paymentsmobile.dotpay.fragment.PaymentCardManagerFragment;

public class PaymentCardManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manager);
        initPaymentCardListFragment();
    }

    private void initPaymentCardListFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();


        Fragment fragment = PaymentCardManagerFragment.newInstance(ShopData.getMerchantId(), ShopData.getCurrency(), ShopData.getLanguage());
        ft.replace(R.id.fragmentContainer, fragment, null).commit();
    }

}