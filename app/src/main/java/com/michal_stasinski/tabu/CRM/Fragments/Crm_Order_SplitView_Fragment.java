package com.michal_stasinski.tabu.CRM.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.tabu.CRM.Adapters.Crm_Order_SplitView_Fragment_Adapter;
import com.michal_stasinski.tabu.CRM.Model.GetOrderFromFB;
import com.michal_stasinski.tabu.CRM.Order.Crm_Order_Main_Activity;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import java.util.ArrayList;
import java.util.Map;

import static com.michal_stasinski.tabu.SplashScreen.DB_ORDER_DATABASE;

public class Crm_Order_SplitView_Fragment extends android.support.v4.app.Fragment implements Crm_Order_SplitView_Fragment_Adapter.SplitViewAdapterSetClick {
    private View myView;
    private Crm_Order_Kanban_Fragment fragment1;
    private ButtonBarLayout all;
    private SplitViewFragmentInteractionListener listener;

    private Handler handler;
    private int AFTER_ONE_MINUTE;
    private ArrayList<GetOrderFromFB> orderFromFB;
    public static ArrayList<GetOrderFromFB> orderFromFB0;
    public static ArrayList<GetOrderFromFB> orderFromFB1;
    public static ArrayList<GetOrderFromFB> orderFromFB2;
    public static ArrayList<GetOrderFromFB> orderFromFB3o;
    public static ArrayList<GetOrderFromFB> orderFromFB3d;
    public static ArrayList<GetOrderFromFB> orderFromFB4;


    private Crm_Order_SplitView_Fragment_Adapter arrayAdapter;


    public Crm_Order_SplitView_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void setClick(int pos) {
        orderFromFB.get(pos).getOrderNo();
        Log.i("informacja", "orderFromFB.get(pos).getOrderNo() "+ orderFromFB.get(pos).getOrderList());
    }

    public interface SplitViewFragmentInteractionListener {
        void messageFromSplitViewFragmentToActivity(String myString);

        void clickPosition(int pos, View arg);
    }

    public static Crm_Order_SplitView_Fragment newInstance(int num) {

        Crm_Order_SplitView_Fragment fragment = new Crm_Order_SplitView_Fragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        listener = (SplitViewFragmentInteractionListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.crm_order_splitview_fragment, container, false);

        all = (ButtonBarLayout) myView.findViewById(R.id.all_btn);

        orderFromFB0 = new ArrayList<GetOrderFromFB>();
        orderFromFB1 = new ArrayList<GetOrderFromFB>();
        orderFromFB2 = new ArrayList<GetOrderFromFB>();
        orderFromFB3o = new ArrayList<GetOrderFromFB>();
        orderFromFB3d = new ArrayList<GetOrderFromFB>();
        orderFromFB4 = new ArrayList<GetOrderFromFB>();

        return myView;

    }

    @Override
    public void onResume() {
        super.onResume();
        // btn3o_is_mark = true;
        //btn3d_is_mark = true;

        Log.i("informacja", "onResume splitview");


        ButtonBarLayout odbior_btn = (ButtonBarLayout) myView.findViewById(R.id.odbior_btn);
        ButtonBarLayout dowoz_btn = (ButtonBarLayout) myView.findViewById(R.id.dowoz_btn);
        ButtonBarLayout zaznacz_wszystko = (ButtonBarLayout) myView.findViewById(R.id.selectAll_btn);
        ButtonBarLayout odznacz_wszystko = (ButtonBarLayout) myView.findViewById(R.id.clearAll_btn);

        if (!Crm_Order_Main_Activity.btn3_is_mark) {
            odbior_btn.setVisibility(View.INVISIBLE);
            dowoz_btn.setVisibility(View.INVISIBLE);
        } else {
            odbior_btn.setVisibility(View.VISIBLE);
            dowoz_btn.setVisibility(View.VISIBLE);

            // Crm_Order_Main_Activity.btn3o_is_mark = true;
            // Crm_Order_Main_Activity.btn3d_is_mark = true;
        }

        TextView dowoz_txt = (TextView) myView.findViewById(R.id.dowoz_txt);

        if (Crm_Order_Main_Activity.btn3d_is_mark) {
            dowoz_txt.setTextColor(getResources().getColor(R.color.colorDarkGray));
        } else {
            dowoz_txt.setTextColor(getResources().getColor(R.color.colorSecondGrey));
        }

        TextView odbior_txt = (TextView) myView.findViewById(R.id.odbior_txt);


        if (Crm_Order_Main_Activity.btn3o_is_mark) {
            odbior_txt.setTextColor(getResources().getColor(R.color.colorDarkGray));
        } else {
            odbior_txt.setTextColor(getResources().getColor(R.color.colorSecondGrey));
        }


        odznacz_wszystko.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ButtonBarLayout odbior_btn = (ButtonBarLayout) myView.findViewById(R.id.odbior_btn);
                ButtonBarLayout dowoz_btn = (ButtonBarLayout) myView.findViewById(R.id.dowoz_btn);
                odbior_btn.setVisibility(View.INVISIBLE);
                dowoz_btn.setVisibility(View.INVISIBLE);
                Crm_Order_Main_Activity.btn0_is_mark = false;
                Crm_Order_Main_Activity.btn1_is_mark = false;
                Crm_Order_Main_Activity.btn2_is_mark = false;
                Crm_Order_Main_Activity.btn3_is_mark = false;
                Crm_Order_Main_Activity.btn3o_is_mark = false;
                Crm_Order_Main_Activity.btn3d_is_mark = false;
                Crm_Order_Main_Activity.btn4_is_mark = false;
                listener.messageFromSplitViewFragmentToActivity("reset");
                loadAllOrders();

            }
        });


        zaznacz_wszystko.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ButtonBarLayout odbior_btn = (ButtonBarLayout) myView.findViewById(R.id.odbior_btn);
                ButtonBarLayout dowoz_btn = (ButtonBarLayout) myView.findViewById(R.id.dowoz_btn);
                odbior_btn.setVisibility(View.VISIBLE);
                dowoz_btn.setVisibility(View.VISIBLE);
                TextView odbior_txt = (TextView) myView.findViewById(R.id.odbior_txt);
                TextView dowoz_txt = (TextView) myView.findViewById(R.id.dowoz_txt);
                odbior_txt.setTextColor(getResources().getColor(R.color.colorDarkGray));
                dowoz_txt.setTextColor(getResources().getColor(R.color.colorDarkGray));
                Crm_Order_Main_Activity.btn0_is_mark = true;
                Crm_Order_Main_Activity.btn1_is_mark = true;
                Crm_Order_Main_Activity.btn2_is_mark = true;
                Crm_Order_Main_Activity.btn3_is_mark = true;
                Crm_Order_Main_Activity.btn3o_is_mark = true;
                Crm_Order_Main_Activity.btn3d_is_mark = true;
                Crm_Order_Main_Activity.btn4_is_mark = true;
                listener.messageFromSplitViewFragmentToActivity("selectAll");

                loadAllOrders();

            }
        });

        all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.messageFromSplitViewFragmentToActivity("reset");
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.crm_fragment_contener, new Crm_Order_Kanban_Fragment());
                ft.commit();

            }
        });


        odbior_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView odbior_txt = (TextView) myView.findViewById(R.id.odbior_txt);
                odbior_txt.setTextColor(getResources().getColor(R.color.colorSecondGrey));
                if (!Crm_Order_Main_Activity.btn3o_is_mark) {
                    odbior_txt.setTextColor(getResources().getColor(R.color.colorDarkGray));
                }

                Crm_Order_Main_Activity.btn3o_is_mark = !Crm_Order_Main_Activity.btn3o_is_mark;
                loadAllOrders();

            }
        });

        dowoz_btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                TextView dowoz_txt = (TextView) myView.findViewById(R.id.dowoz_txt);
                dowoz_txt.setTextColor(getResources().getColor(R.color.colorSecondGrey));
                if (!Crm_Order_Main_Activity.btn3d_is_mark) {
                    dowoz_txt.setTextColor(getResources().getColor(R.color.colorDarkGray));
                }

                Crm_Order_Main_Activity.btn3d_is_mark = !Crm_Order_Main_Activity.btn3d_is_mark;
                loadAllOrders();
            }
        });

        loadAllOrders();


        handler = new Handler(new Handler.Callback() {

            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == AFTER_ONE_MINUTE) {

                    if (arrayAdapter != null) arrayAdapter.notifyDataSetChanged();

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
                orderFromFB3o.clear();
                orderFromFB3d.clear();
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
                        if (receiptWay.equals("ODBIÓR WŁASNY")) {
                            orderFromFB3o.add(orderFromFB_Item);
                        } else {
                            orderFromFB3d.add(orderFromFB_Item);
                        }
                    }
                    if (status.equals("4")) {
                        orderFromFB4.add(orderFromFB_Item);
                    }
                }


                orderFromFB = new ArrayList<GetOrderFromFB>();


                if (Crm_Order_Main_Activity.btn0_is_mark) {
                    orderFromFB.addAll(orderFromFB0);
                }
                if (Crm_Order_Main_Activity.btn1_is_mark) {
                    orderFromFB.addAll(orderFromFB1);
                }
                if (Crm_Order_Main_Activity.btn2_is_mark) {
                    orderFromFB.addAll(orderFromFB2);
                }
                if (Crm_Order_Main_Activity.btn3_is_mark) {
                    if (Crm_Order_Main_Activity.btn3o_is_mark) {
                        orderFromFB.addAll(orderFromFB3o);
                    }

                    if (Crm_Order_Main_Activity.btn3d_is_mark) {
                        orderFromFB.addAll(orderFromFB3d);
                    }
                }
                if (Crm_Order_Main_Activity.btn4_is_mark) {
                    orderFromFB.addAll(orderFromFB4);
                }

                refreshAdapter(0);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void refreshAdapter(int pos) {

        if (getActivity() != null) {
            BounceListView bounceListView0 = (BounceListView) myView.findViewById(R.id.left_listView);
            arrayAdapter = new Crm_Order_SplitView_Fragment_Adapter(getActivity(), myView.getContext(), orderFromFB, this);
            arrayAdapter.setSelectItem(pos);
            bounceListView0.setAdapter(arrayAdapter);
            bounceListView0.setScrollingCacheEnabled(false);
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
