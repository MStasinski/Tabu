package com.michal_stasinski.tabu.CRM.Order;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;
import com.michal_stasinski.tabu.CRM.Adapters.SimpleListViewAdapter;
import com.michal_stasinski.tabu.CRM.Model.GetOrderFromFB;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import java.util.ArrayList;
import java.util.Map;

public class SimpleOrderListFB extends Activity {

    protected BounceListView bounceListView0;
    protected BounceListView bounceListView1;
    protected BounceListView bounceListView2;
    protected BounceListView bounceListView3;
    protected BounceListView bounceListView4;
    private ArrayList<GetOrderFromFB> orderFromFB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.crm_simple_order_listview_fb);

        orderFromFB = new ArrayList<GetOrderFromFB>();

        Button closeButton = (Button) findViewById(R.id.closeBtn);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.from_left, R.anim.to_right);

            }
        });
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
        DatabaseReference myRef = database.getReference("Orders");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderFromFB.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {


                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();

                    String deliveryDate = (String) map.get("deliveryDate");
                    String deliveryPrice = (String) String.valueOf(map.get("deliveryPrice"));
                    String email = (String) String.valueOf(map.get("email"));
                    ArrayList<String> orderList = (ArrayList<String>) map.get("ordersList");
                    String paymentWay = (String) String.valueOf(map.get("paymentWay"));
                    String receiptAdres = (String) String.valueOf(map.get("receiptAdres"));
                    String receiptWay = (String) String.valueOf(map.get("receiptWay"));
                    String totalPrice = (String) String.valueOf(map.get("totalPrice"));
                    String userId = (String) String.valueOf(map.get("userId"));

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

                    orderFromFB.add(orderFromFB_Item);
                }

                SimpleListViewAdapter arrayAdapter0 = new SimpleListViewAdapter(getBaseContext(), orderFromFB, false, getResources().getColor(R.color.NOWE));
                SimpleListViewAdapter arrayAdapter1 = new SimpleListViewAdapter(getBaseContext(), orderFromFB, false,getResources().getColor(R.color.PRZYJETE));
                SimpleListViewAdapter arrayAdapter2 = new SimpleListViewAdapter(getBaseContext(), orderFromFB, false,getResources().getColor(R.color.WREALIZACJI));
                SimpleListViewAdapter arrayAdapter3 = new SimpleListViewAdapter(getBaseContext(), orderFromFB, false,getResources().getColor(R.color.DOODBIORU));
                SimpleListViewAdapter arrayAdapter4 = new SimpleListViewAdapter(getBaseContext(), orderFromFB, false,getResources().getColor(R.color.WDOSTAWIE));

                bounceListView0.setAdapter(arrayAdapter0);
                bounceListView0.setScrollingCacheEnabled(false);
                bounceListView1.setAdapter(arrayAdapter1);
                bounceListView1.setScrollingCacheEnabled(false);
                bounceListView2.setAdapter(arrayAdapter2);
                bounceListView2.setScrollingCacheEnabled(false);
                bounceListView3.setAdapter(arrayAdapter3);
                bounceListView3.setScrollingCacheEnabled(false);
                bounceListView4.setAdapter(arrayAdapter4);
                bounceListView4.setScrollingCacheEnabled(false);

                Log.i("informacja", " orderFromFB " + orderFromFB);
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

   /* public void loadOrdersFromDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("TEST_ORDER");

        ValueEventListener valueEventListener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // allOrderFromDBList.clear();
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();

                    ArrayList orderListForOneIndividualOrder = (ArrayList) map.get("orderList");


                    if (orderListForOneIndividualOrder != null) {
                        String arr = orderListForOneIndividualOrder.get(1).toString();

                        arr = arr.replace("{quantity", "|");
                        arr = arr.replace("price", "|");
                        arr = arr.replace("sauce", "|");
                        arr = arr.replace("size", "|");
                        arr = arr.replace("addon", "|");
                        arr = arr.replace("name", "|");
                        arr = arr.replace("note", "|");


                        String[] ord = arr.toString().split("\\|=");

                        Log.i("informacja", "  ______________  " + removeLastChar(ord[1].toString()));
                        Log.i("informacja", "  ______________  " + removeLastChar(ord[2].toString()));
                        Log.i("informacja", "  ______________  " + removeLastChar(ord[3].toString()));
                        Log.i("informacja", "  ______________  " + removeLastChar(ord[4].toString()));
                        Log.i("informacja", "  ______________  " + removeLastChar(ord[5].toString()));
                        Log.i("informacja", "  +++++++++++++++++++++++++++++++++++++++++++++++++++ ");
                    }
                }
            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }

        });


    }*/

}
