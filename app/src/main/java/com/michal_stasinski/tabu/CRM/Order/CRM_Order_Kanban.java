package com.michal_stasinski.tabu.CRM.Order;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.ButtonBarLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.tabu.CRM.Adapters.CRM_Order_Kanban_Adapter;
import com.michal_stasinski.tabu.CRM.Model.GetOrderFromFB;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import java.util.ArrayList;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.michal_stasinski.tabu.SplashScreen.DB_ORDER_DATABASE;
import static com.michal_stasinski.tabu.SplashScreen.DB_ORDER_SERIAL_DATABASE;
import static com.michal_stasinski.tabu.SplashScreen.DB_ORDER_SERIAL_NUMBER;

public class CRM_Order_Kanban extends Activity {

    protected BounceListView bounceListView0;
    protected BounceListView bounceListView1;
    protected BounceListView bounceListView2;
    protected BounceListView bounceListView3;
    protected BounceListView bounceListView4;
    public static ArrayList<GetOrderFromFB> orderFromFB0;
    public static ArrayList<GetOrderFromFB> orderFromFB1;
    public static ArrayList<GetOrderFromFB> orderFromFB2;
    public static ArrayList<GetOrderFromFB> orderFromFB3;
    public static ArrayList<GetOrderFromFB> orderFromFB4;

    private CRM_Order_Kanban_Adapter arrayAdapter0;
    private CRM_Order_Kanban_Adapter arrayAdapter1;
    private CRM_Order_Kanban_Adapter arrayAdapter2;
    private CRM_Order_Kanban_Adapter arrayAdapter3;
    private CRM_Order_Kanban_Adapter arrayAdapter4;
    protected LinearLayout news_column;
    protected LinearLayout commit_column;
    protected LinearLayout realization_column;
    protected LinearLayout reception_column;
    protected LinearLayout transport_column;
    private Handler handler;
    private int AFTER_ONE_MINUTE;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeStaffLogged);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.crm_order_kanban_main);


        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("AvenirNext-DemiBold.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );

        orderFromFB0 = new ArrayList<GetOrderFromFB>();
        orderFromFB1 = new ArrayList<GetOrderFromFB>();
        orderFromFB2 = new ArrayList<GetOrderFromFB>();
        orderFromFB3 = new ArrayList<GetOrderFromFB>();
        orderFromFB4 = new ArrayList<GetOrderFromFB>();

        ButtonBarLayout closeButton = (ButtonBarLayout) findViewById(R.id.bClose);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.from_left, R.anim.to_right);

            }
        });


        ButtonBarLayout deliveryBtn = (ButtonBarLayout) findViewById(R.id.btn_w_dostawie);

        deliveryBtn.setOnClickListener(new View.OnClickListener() {
            Boolean deliveryBtnState = false;
            @Override
            public void onClick(View v) {
                if(deliveryBtnState==false) {
                    deliveryBtnState=true;
                    ImageView img = (ImageView) findViewById(R.id.img_w_dostawie);
                    img.setBackgroundResource(R.mipmap.lista_zamowien);
                }else{
                    deliveryBtnState=false;
                    ImageView img = (ImageView) findViewById(R.id.img_w_dostawie);
                    img.setBackgroundResource(R.mipmap.lista_w_dostawie);
                }

            }
        });




        news_column = (LinearLayout) findViewById(R.id.news_column);
        commit_column = (LinearLayout) findViewById(R.id.commit_column);
        realization_column = (LinearLayout) findViewById(R.id.realization_column);
        reception_column = (LinearLayout) findViewById(R.id.reception_column);
        transport_column = (LinearLayout) findViewById(R.id.transport_column);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        news_column.setLayoutParams(new LinearLayout.LayoutParams(width / 5, LinearLayout.LayoutParams.WRAP_CONTENT));
        commit_column.setLayoutParams(new LinearLayout.LayoutParams(width / 5, LinearLayout.LayoutParams.WRAP_CONTENT));
        realization_column.setLayoutParams(new LinearLayout.LayoutParams(width / 5, LinearLayout.LayoutParams.WRAP_CONTENT));
        reception_column.setLayoutParams(new LinearLayout.LayoutParams(width / 5, LinearLayout.LayoutParams.WRAP_CONTENT));
        transport_column.setLayoutParams(new LinearLayout.LayoutParams(width / 5, LinearLayout.LayoutParams.WRAP_CONTENT));

        bounceListView0 = (BounceListView) findViewById(R.id.simple_order_list_view0);
        bounceListView1 = (BounceListView) findViewById(R.id.simple_order_list_view1);
        bounceListView2 = (BounceListView) findViewById(R.id.simple_order_list_view2);
        bounceListView3 = (BounceListView) findViewById(R.id.simple_order_list_view3);
        bounceListView4 = (BounceListView) findViewById(R.id.simple_order_list_view4);

        loadAllOrders();
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        load_order_serial_number();
        handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == AFTER_ONE_MINUTE) {

                    if (arrayAdapter0 != null) arrayAdapter0.notifyDataSetChanged();
                    if (arrayAdapter1 != null) arrayAdapter1.notifyDataSetChanged();
                    if (arrayAdapter2 != null) arrayAdapter2.notifyDataSetChanged();
                    if (arrayAdapter3 != null) arrayAdapter3.notifyDataSetChanged();
                    if (arrayAdapter4 != null) arrayAdapter4.notifyDataSetChanged();

                    handler.sendEmptyMessageDelayed(AFTER_ONE_MINUTE, 60000);
                }

                return true;
            }
        });
        handler.sendEmptyMessageDelayed(AFTER_ONE_MINUTE, 60000);


    }
    public void load_order_serial_number() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(DB_ORDER_SERIAL_DATABASE);
        // DatabaseReference myRef = database.getReference("Orders");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();
                    DB_ORDER_SERIAL_NUMBER = (String) map.get("nr").toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void loadAllOrders() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(DB_ORDER_DATABASE);
        // DatabaseReference myRef = database.getReference("Orders");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderFromFB0.clear();
                orderFromFB1.clear();
                orderFromFB2.clear();
                orderFromFB3.clear();
                orderFromFB4.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {


                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();

                    String deliveryDate = (String) map.get("deliveryDate");
                    String deliveryPrice = (String) String.valueOf(map.get("deliveryPrice"));
                    String email = (String) String.valueOf(map.get("email"));
                    ArrayList<ArrayList<String>> orderList = (ArrayList<ArrayList<String>>) map.get("ordersList");
                    String paymentWay = (String) String.valueOf(map.get("paymentWay"));
                    String receiptAdres = (String) String.valueOf(map.get("receiptAdres"));
                    String receiptWay = (String) String.valueOf(map.get("receiptWay"));
                    String totalPrice = (String) String.valueOf(map.get("totalPrice"));
                    String userId = (String) String.valueOf(map.get("userId"));
                    String status = (String) String.valueOf(map.get("orderStatus"));
                    String orderNumber = (String) String.valueOf(map.get("orderNumber"));
                    String orderNo = (String) String.valueOf(map.get("orderNo"));


                    GetOrderFromFB orderFromFB_Item = new GetOrderFromFB();

                    orderFromFB_Item.setDeliveryDate(deliveryDate);
                    orderFromFB_Item.setDeliveryPrice(deliveryPrice);
                    orderFromFB_Item.setEmail(email);
                    orderFromFB_Item.setOrderList(orderList);
                    orderFromFB_Item.setPaymentWay(paymentWay);
                    orderFromFB_Item.setReceiptAdres(receiptAdres);
                    orderFromFB_Item.setReceiptWay(receiptWay);
                    orderFromFB_Item.setTotalPrice(totalPrice);
                    orderFromFB_Item.setUserId(userId);
                    orderFromFB_Item.setOrderNumber(orderNumber);
                    orderFromFB_Item.setStatus(status);
                    orderFromFB_Item.setOrderNo(orderNo);

                    //  orderFromFB_Item.setNumberOFOrderItem(numOfOrderItems);
                    Log.i("informacja", "   status  " + totalPrice);

                    if (status == null) {
                        // status ="0";
                    }
                    if (status.equals("0")) {
                        orderFromFB0.add(orderFromFB_Item);
                    }
                    if (status.equals("1")) {
                        orderFromFB1.add(orderFromFB_Item);
                    }
                    if (status.equals("2")) {
                        orderFromFB2.add(orderFromFB_Item);
                    }
                    if (status.equals("3")) {
                        orderFromFB3.add(orderFromFB_Item);
                    }
                    if (status.equals("4")) {
                        orderFromFB4.add(orderFromFB_Item);
                    }
                }

                arrayAdapter0 = new CRM_Order_Kanban_Adapter(CRM_Order_Kanban.this,getBaseContext(), orderFromFB0, false, getResources().getColor(R.color.NOWE));
                arrayAdapter1 = new CRM_Order_Kanban_Adapter(CRM_Order_Kanban.this,getBaseContext(), orderFromFB1, false, getResources().getColor(R.color.PRZYJETE));
                arrayAdapter2 = new CRM_Order_Kanban_Adapter(CRM_Order_Kanban.this,getBaseContext(),orderFromFB2, false, getResources().getColor(R.color.WREALIZACJI));
                arrayAdapter3 = new CRM_Order_Kanban_Adapter(CRM_Order_Kanban.this,getBaseContext(), orderFromFB3, false, getResources().getColor(R.color.DOODBIORU));
                arrayAdapter4 = new CRM_Order_Kanban_Adapter(CRM_Order_Kanban.this,getBaseContext(), orderFromFB4, false, getResources().getColor(R.color.WDOSTAWIE));

                bounceListView0.setAdapter(arrayAdapter0);
                bounceListView0.setScrollingCacheEnabled(false);
                arrayAdapter0.notifyDataSetChanged();

                bounceListView1.setAdapter(arrayAdapter1);
                bounceListView1.setScrollingCacheEnabled(false);
                arrayAdapter1.notifyDataSetChanged();

                bounceListView2.setAdapter(arrayAdapter2);
                bounceListView2.setScrollingCacheEnabled(false);
                arrayAdapter2.notifyDataSetChanged();

                bounceListView3.setAdapter(arrayAdapter3);
                bounceListView3.setScrollingCacheEnabled(false);
                arrayAdapter3.notifyDataSetChanged();

                bounceListView4.setAdapter(arrayAdapter4);
                bounceListView4.setScrollingCacheEnabled(false);
                arrayAdapter4.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
