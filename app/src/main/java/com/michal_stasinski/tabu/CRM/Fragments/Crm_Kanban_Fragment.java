package com.michal_stasinski.tabu.CRM.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import static com.michal_stasinski.tabu.SplashScreen.DB_ORDER_DATABASE;

/**

 */
public class Crm_Kanban_Fragment extends android.support.v4.app.Fragment {

    private View myView;
    private Handler handler;
    private int AFTER_ONE_MINUTE;
    private Activity activity;
    private CRM_Order_Kanban_Adapter arrayAdapter0;
    private CRM_Order_Kanban_Adapter arrayAdapter1;
    private CRM_Order_Kanban_Adapter arrayAdapter2;
    private CRM_Order_Kanban_Adapter arrayAdapter3;
    private CRM_Order_Kanban_Adapter arrayAdapter4;


    public static ArrayList<GetOrderFromFB> orderFromFB0;
    public static ArrayList<GetOrderFromFB> orderFromFB1;
    public static ArrayList<GetOrderFromFB> orderFromFB2;
    public static ArrayList<GetOrderFromFB> orderFromFB3;
    public static ArrayList<GetOrderFromFB> orderFromFB4;


    public Crm_Kanban_Fragment() {
    }

    public static Crm_Kanban_Fragment newInstance(int num) {

        Crm_Kanban_Fragment fragment = new Crm_Kanban_Fragment();

        Log.i("informacja", "  tu fragment  ");
        return fragment;
    }


    public interface KanbanInteractionListener {
        void messageFromKanbanFragmentToActivity(String myString);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.crm_kanban_fragment, container, false);
        activity = getActivity();
        orderFromFB0 = new ArrayList<GetOrderFromFB>();
        orderFromFB1 = new ArrayList<GetOrderFromFB>();
        orderFromFB2 = new ArrayList<GetOrderFromFB>();
        orderFromFB3 = new ArrayList<GetOrderFromFB>();
        orderFromFB4 = new ArrayList<GetOrderFromFB>();


        loadAllOrders();

        return myView;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("informacja", "  resume  ");


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

                BounceListView bounceListView0 = (BounceListView) myView.findViewById(R.id.kanban_bounce_list_view_0);
                BounceListView bounceListView1 = (BounceListView) myView.findViewById(R.id.kanban_bounce_list_view_1);
                BounceListView bounceListView2 = (BounceListView) myView.findViewById(R.id.kanban_bounce_list_view_2);
                BounceListView bounceListView3 = (BounceListView) myView.findViewById(R.id.kanban_bounce_list_view_3);
                BounceListView bounceListView4 = (BounceListView) myView.findViewById(R.id.kanban_bounce_list_view_4);


                Log.i("informacja", "  getActivity()  " + getActivity());

                if (getActivity() != null) {
                    arrayAdapter0 = new CRM_Order_Kanban_Adapter(getActivity(), myView.getContext(), orderFromFB0, false, getResources().getColor(R.color.NOWE));
                    arrayAdapter1 = new CRM_Order_Kanban_Adapter(getActivity(), myView.getContext(), orderFromFB1, false, getResources().getColor(R.color.PRZYJETE));
                    arrayAdapter2 = new CRM_Order_Kanban_Adapter(getActivity(), myView.getContext(), orderFromFB2, false, getResources().getColor(R.color.WREALIZACJI));
                    arrayAdapter3 = new CRM_Order_Kanban_Adapter(getActivity(), myView.getContext(), orderFromFB3, false, getResources().getColor(R.color.DOODBIORU));
                    arrayAdapter4 = new CRM_Order_Kanban_Adapter(getActivity(), myView.getContext(), orderFromFB4, false, getResources().getColor(R.color.ODBIOR3));


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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
