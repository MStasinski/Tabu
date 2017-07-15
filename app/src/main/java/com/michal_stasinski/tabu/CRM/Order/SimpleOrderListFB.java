package com.michal_stasinski.tabu.CRM.Order;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.tabu.CRM.Adapters.SimpleListViewAdapter;
import com.michal_stasinski.tabu.CRM.Model.GetOrderFromFB;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import java.util.ArrayList;
import java.util.Map;

import static com.michal_stasinski.tabu.SplashScreen.DB_ORDER_DATABASE;

public class SimpleOrderListFB extends Activity {

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

    protected LinearLayout news_column;
    protected LinearLayout commit_column;
    protected LinearLayout realization_column;
    protected LinearLayout reception_column;
    protected LinearLayout transport_column;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.crm_simple_order_listview_fb);

        orderFromFB0 = new ArrayList<GetOrderFromFB>();
        orderFromFB1 = new ArrayList<GetOrderFromFB>();
        orderFromFB2 = new ArrayList<GetOrderFromFB>();
        orderFromFB3 = new ArrayList<GetOrderFromFB>();
        orderFromFB4 = new ArrayList<GetOrderFromFB>();

        Button closeButton = (Button) findViewById(R.id.closeBtn);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.from_left, R.anim.to_right);

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

        TextView transportTxt0 = (TextView) findViewById(R.id.news);
        transportTxt0.setTextSize(width / 100);


        TextView transportTxt1 = (TextView) findViewById(R.id.realization);
        transportTxt1.setTextSize(width / 100);

        TextView transportTxt2 = (TextView) findViewById(R.id.accepted);
        transportTxt2.setTextSize(width / 100);
        TextView transportTxt3 = (TextView) findViewById(R.id.receive);
        transportTxt3.setTextSize(width / 100);

        TextView transportTxt4 = (TextView) findViewById(R.id.transport);
        transportTxt4.setTextSize(width / 100);


        bounceListView0 = (BounceListView) findViewById(R.id.simple_order_list_view0);
        bounceListView1 = (BounceListView) findViewById(R.id.simple_order_list_view1);
        bounceListView2 = (BounceListView) findViewById(R.id.simple_order_list_view2);
        bounceListView3 = (BounceListView) findViewById(R.id.simple_order_list_view3);
        bounceListView4 = (BounceListView) findViewById(R.id.simple_order_list_view4);

        loadAllOrders();
    }

    public void loadAllOrders() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //DatabaseReference myRef = database.getReference("TEST_ORDER");
       // String databaseName = "ZamowieniaBierzs";
       // DatabaseReference myRef = database.getReference("OrdersCurrents");
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

                    Log.i("informacja","   status  " +  totalPrice);

                    if(status == null){
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


                SimpleListViewAdapter arrayAdapter0 = new SimpleListViewAdapter(getBaseContext(), orderFromFB0, false, getResources().getColor(R.color.NOWE));
                SimpleListViewAdapter arrayAdapter1 = new SimpleListViewAdapter(getBaseContext(), orderFromFB1, false, getResources().getColor(R.color.PRZYJETE));
                SimpleListViewAdapter arrayAdapter2 = new SimpleListViewAdapter(getBaseContext(), orderFromFB2, false, getResources().getColor(R.color.WREALIZACJI));
                SimpleListViewAdapter arrayAdapter3 = new SimpleListViewAdapter(getBaseContext(), orderFromFB3, false, getResources().getColor(R.color.DOODBIORU));
                SimpleListViewAdapter arrayAdapter4 = new SimpleListViewAdapter(getBaseContext(), orderFromFB4, false, getResources().getColor(R.color.WDOSTAWIE));

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


               /* try {
                    File f = File.createTempFile("file", ".txt", Environment.getExternalStorageDirectory ());
                    FileWriter fw = new FileWriter(f);
                    fw.write("DUPAAAAAAAAAA");
                    fw.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error while saving file", Toast.LENGTH_LONG).show();
                }*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
