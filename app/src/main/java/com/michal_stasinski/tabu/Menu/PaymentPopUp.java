package com.michal_stasinski.tabu.Menu;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.michal_stasinski.tabu.Menu.Adapters.PaymentAdapter;
import com.michal_stasinski.tabu.Menu.Models.PaymentItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import java.util.ArrayList;

/**
 * Created by win8 on 18.04.2017.
 */

public class PaymentPopUp extends Activity {

    public static ArrayList<PaymentItem> paymentMethodsList;

    private static int size = 0;
    private static Context contex;
    private static PaymentAdapter adapterek;
    private BounceListView mListViewMenu;
    private int choosePaymentMethod = 0;
    private static final String TAG = PizzaSizePopUp.class.getSimpleName();

    protected Integer[] imgPayment = {
            R.mipmap.cash_icon,
            R.mipmap.card_icon,
            R.mipmap.dot_pay_icon,
            R.mipmap.payu_icon

    };

    public static String[] paymentMethods = {
            "GOTÓWKA",
            "Karta",
            "Przelew"
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        contex = this;
        setContentView(R.layout.bounce_list_view);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm. heightPixels;
        //getWindow().setLayout((int) (width * .8), (int) (height * 0.6));
        getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        paymentMethodsList = new ArrayList<PaymentItem>();


        for (int i = 0; i < paymentMethods.length; i++) {

            PaymentItem item = new PaymentItem();
            item.setPayment_txt(paymentMethods[i]);
            item.setImage(imgPayment[i]);
            item.setMark(false);
            if (ShopingCardListView.SELECTED_PAYMENT_METHOD == i) {
                item.setMark(true);
            }

            paymentMethodsList.add(item);
        }

        adapterek = new PaymentAdapter(contex, paymentMethodsList);

        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        mListViewMenu.setAdapter(adapterek);
        mListViewMenu.setScrollingCacheEnabled(false);

        mListViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

                for (int i = 0; i < paymentMethodsList.size(); i++) {
                    PaymentItem obj = (PaymentItem) adapter.getItemAtPosition(i);
                    obj.setMark(false);
                }

                PaymentItem obj = (PaymentItem) adapter.getItemAtPosition(position);
                obj.setMark(true);
                ShopingCardListView.SELECTED_PAYMENT_METHOD = position;
                adapterek.notifyDataSetChanged();


            }
        });

    }


}
