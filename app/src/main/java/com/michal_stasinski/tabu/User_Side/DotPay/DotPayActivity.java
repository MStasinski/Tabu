package com.michal_stasinski.tabu.User_Side.DotPay;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.michal_stasinski.tabu.MainActivity;
import com.michal_stasinski.tabu.User_Side.Models.ShopData;
import com.michal_stasinski.tabu.User_Side.ShopingCard;
import com.michal_stasinski.tabu.R;

import java.util.HashMap;
import java.util.Map;


import pl.mobiltek.paymentsmobile.dotpay.Configuration;
import pl.mobiltek.paymentsmobile.dotpay.enums.StateType;
import pl.mobiltek.paymentsmobile.dotpay.events.PaymentEndedEventArgs;
import pl.mobiltek.paymentsmobile.dotpay.events.PaymentManagerCallback;
import pl.mobiltek.paymentsmobile.dotpay.exeptions.NotFoundDefaultPaymentCardException;
import pl.mobiltek.paymentsmobile.dotpay.exeptions.NotFoundPaymentCardException;
import pl.mobiltek.paymentsmobile.dotpay.exeptions.OneClickUnableException;
import pl.mobiltek.paymentsmobile.dotpay.managers.PaymentManager;
import pl.mobiltek.paymentsmobile.dotpay.model.PaymentInformation;
import pl.mobiltek.paymentsmobile.dotpay.utils.Settings;

public class DotPayActivity extends AppCompatActivity implements View.OnClickListener {

    private final String NAME = "firstname";
    private final String LAST_NAME = "lastname";
    private final String EMAIL = "email";


    private TextView versionSDK;
    private Button mPayBtn, mOneClickBtn;
    private ImageButton mCardBtn, mHistoryBtn;

    private PaymentManagerCallback paymentManagerCallback = new PaymentManagerCallback() {
        @Override
        public void onPaymentSuccess(PaymentEndedEventArgs paymentEndedEventArgs) {
            Log.i("informacja", "paymentEndedEventArgs.getPaymentResult().getStateType()  ______________" +paymentEndedEventArgs.getPaymentResult().getStateType());

            if (paymentEndedEventArgs.getPaymentResult().getStateType() == StateType.COMPLETED) {

                Intent intent = new Intent();
                intent.setAction(ShopingCard.DOTPAY_SUCCESS);
                sendBroadcast(intent);
                Intent intent2 = new Intent();
                intent2.setClass(getBaseContext(), MainActivity.class);
                startActivity(intent2);
            } else if (paymentEndedEventArgs.getPaymentResult().getStateType() == StateType.REJECTED) {
                Intent intent = new Intent();
                intent.setAction(ShopingCard.DOTPAY_FAILD);
                sendBroadcast(intent);
                Toast.makeText(DotPayActivity.this, "płatność zakończona odmową", Toast.LENGTH_LONG).show();

            } else {
                //płatność w trakcie realizacji
            }
        }


        @Override
        public void onPaymentFailure(PaymentEndedEventArgs paymentEndedEventArgs) {
            Intent intent = new Intent();
            intent.setAction(ShopingCard.DOTPAY_FAILD);
            sendBroadcast(intent);
            Toast.makeText(DotPayActivity.this, "Płatność nie powiodła się", Toast.LENGTH_LONG).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        initView();
        setupSDK();


    }


    private void initView() {
        versionSDK = (TextView) findViewById(R.id.versionSDK);
        versionSDK.setText("SDK " + Settings.getSDKVersion());

        mOneClickBtn = (Button) findViewById(R.id.oneClickBtn);
        mPayBtn = (Button) findViewById(R.id.formPaymentBtn);
        mHistoryBtn = (ImageButton) findViewById(R.id.historyBtn);
        mCardBtn = (ImageButton) findViewById(R.id.cardManagerBtn);

        mPayBtn.setOnClickListener(this);
        mOneClickBtn.setOnClickListener(this);
        mHistoryBtn.setOnClickListener(this);
        mCardBtn.setOnClickListener(this);


    }

    private void setupSDK() {
        PaymentManager.getInstance().setPaymentManagerCallback(paymentManagerCallback);

        //TODO zmienić na produkcje!!!
        PaymentManager.getInstance().setApplicationVersion(Configuration.TEST_VERSION);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.oneClickBtn:
                payOneClick();
                break;
            case R.id.formPaymentBtn:
                payFormPayment();
                break;
            case R.id.historyBtn:
                showPaymentHistory();
                break;
            case R.id.cardManagerBtn:
                showPaymentCartMenager();
                break;
        }
    }

    private void showPaymentCartMenager() {
        Intent intent = new Intent(this, PaymentCardManagerActivity.class);
        startActivity(intent);
    }

    private void payFormPayment() {
       /* String description = "zamówienie 12345";
        double amount = 123.45;
        PaymentInformation paymentInformation = new PaymentInformation("555", amount, description, "zł");

        Map<String, String> sender = new HashMap<>();
        Map<String, String> additional = new HashMap<>();



        sender.put("firstname", "Jan");
        sender.put("lastname", "Kowalski");
        sender.put("email", "jan.kowalski@test.pl");

        additional.put("id1", "12345");
        additional.put("amount1", "100");
        additional.put("id2", "67890");
        additional.put("amount2", "23.45");




        paymentInformation.setSenderInformation(sender);
        paymentInformation.setAdditionalInformation(additional);*/


        PaymentManager.getInstance().initialize(this, getPaymentInformation());
    }

    private void payOneClick() {
        try {
            PaymentManager.getInstance().oneClickPayment(this, getPaymentInformation());
        } catch (NotFoundPaymentCardException e) {
            Toast.makeText(DotPayActivity.this, "Not found payment card", Toast.LENGTH_LONG).show();
        } catch (NotFoundDefaultPaymentCardException e) {
            Toast.makeText(DotPayActivity.this, "Not found default payment card", Toast.LENGTH_LONG).show();
        } catch (OneClickUnableException e) {
            Toast.makeText(DotPayActivity.this, "1Click unable", Toast.LENGTH_LONG).show();
        }
    }

    @NonNull
    private PaymentInformation getPaymentInformation() {
        PaymentInformation paymentInformation = new PaymentInformation(ShopData.getMerchantId(), ShopData.getProductPrice(), ShopData.getDescription(), ShopData.getCurrency());

        Map<String, String> sender = new HashMap<String, String>();
        sender.put(NAME, ShopData.getName());
        sender.put(LAST_NAME, ShopData.getLastName());
        sender.put(EMAIL, ShopData.getEmail());

        paymentInformation.setSenderInformation(sender);
        return paymentInformation;
    }

    private void showPaymentHistory() {
        Intent intent = new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
}