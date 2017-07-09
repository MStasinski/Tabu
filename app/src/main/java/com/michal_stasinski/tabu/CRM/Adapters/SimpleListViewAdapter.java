package com.michal_stasinski.tabu.CRM.Adapters;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.michal_stasinski.tabu.CRM.Model.GetOrderFromFB;
import com.michal_stasinski.tabu.CRM.OrderZoomPopUp;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.R;

import java.util.ArrayList;

/**
 * Created by win8 on 27.12.2016.
 */

public class SimpleListViewAdapter extends BaseAdapter {


    private static LayoutInflater inflater = null;
    private ArrayList<GetOrderFromFB> arr;
    private Context mContext;

    private boolean sortOption;
    private Boolean specialSign;
    private int color;
    private int height;
    private int width;

    public Boolean getButton_flag_enabled() {
        return button_flag_enabled;
    }

    public void setButton_flag_enabled(Boolean button_flag_enabled) {
        this.button_flag_enabled = button_flag_enabled;
    }

    private Boolean button_flag_enabled = true;

    public SimpleListViewAdapter(Context context, ArrayList<GetOrderFromFB> mListArray, Boolean sort, int col) {
        sortOption = sort;
        color = col;
        this.specialSign = specialSign;
       /* Collections.sort(mListArray, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {


                if (sortOption) {
                    int id1 = Integer.parseInt(((MenuItemProduct) o1).getRank());
                    int id2 = Integer.parseInt(((MenuItemProduct) o2).getRank());

                    if (id1 > id2) {
                        return 1;
                    }
                    if (id1 < id2) {
                        return -1;
                    } else {
                        return 0;
                    }

                } else {
                    String s1 = (((MenuItemProduct) o1).getRank());
                    String s2 = (((MenuItemProduct) o2).getRank());
                    return s1.compareToIgnoreCase(s2);
                }
            }
        });*/


        this.arr = mListArray;
        this.mContext = context;
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();


        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public Object getItem(int position) {
        return arr.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolderItem viewHolder;

        final int clikPos = position;

        if (convertView == null) {
            // ArrayList<Number> price = arr.get(position).getPriceArray();
            view = View.inflate(mContext, R.layout.crm_simple_list_row, null);
            viewHolder = new ViewHolderItem();
            viewHolder.order_fb_payment_method = (TextView) view.findViewById(R.id.order_fb_payment_method);

            viewHolder.list = (LinearLayout) view.findViewById(R.id.list_of_order_for_one_item);
            viewHolder.price = (TextView) view.findViewById(R.id.order_fb_item_price);
            viewHolder.div0 = (View) view.findViewById(R.id.div0);
            viewHolder.order_number = (TextView) view.findViewById(R.id.order_nr);
            viewHolder.hour_of_deliver = (TextView) view.findViewById(R.id.hour_of_deliver);
            viewHolder.time_to_finish = (TextView) view.findViewById(R.id.time_to_finish);
            viewHolder.delivety_method = (TextView) view.findViewById(R.id.delivety_method);
            viewHolder.address_txt = (TextView) view.findViewById(R.id.address_txt);


            viewHolder.tableArray = new ArrayList<TextView>();
            ArrayList<ArrayList<String>> getOrder = arr.get(position).getOrderList();


            viewHolder.getOrderHold = getOrder;


            for (int i = 0; i < arr.get(position).getOrderList().size(); i++) {

                TableRow row = new TableRow(mContext);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                TextView txt_order = new TextView(mContext);
                //txt_order.setHeight(40);

                // ArrayList<String> it = (ArrayList<String>) getOrder.get(i);

                txt_order.setTypeface(null, Typeface.BOLD);
                txt_order.setTextAlignment(view.TEXT_ALIGNMENT_TEXT_END);
                txt_order.setTextSize(width / 160);
                // txt_order.setText(it.get(1) + " szt." + it.get(3));

                row.addView(txt_order);
                viewHolder.tableArray.add(txt_order);
                viewHolder.list.addView(row, i);
            }


            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderItem) view.getTag();
        }
        viewHolder.order_fb_payment_method.setText(arr.get(position).getPaymentWay());
        viewHolder.price.setText(arr.get(position).getTotalPrice());
        //viewHolder.order_number.setBackgroundColor(color);
        viewHolder.order_number.setBackgroundTintList(ColorStateList.valueOf(color));

        viewHolder.div0.setBackgroundTintList(ColorStateList.valueOf(color));
        viewHolder.order_number.setTextSize(width / 120);
        viewHolder.delivety_method.setTextSize(width / 120);
        viewHolder.hour_of_deliver.setTextSize(width / 120);
        viewHolder.order_fb_payment_method.setTextSize(width / 120);
        viewHolder.price.setTextSize(width / 120);
        //  viewHolder.order_number.setWidth(width / 100);
        viewHolder.order_number.setText(arr.get(position).getOrderNumber());
        if (arr.get(position).getOrderNumber().equals("0")) {
            viewHolder.order_number.setText("NOWE");
        }
        viewHolder.delivety_method.setBackgroundTintList(ColorStateList.valueOf(color));
        viewHolder.delivety_method.setText(arr.get(position).getReceiptWay().replace("WŁASNY", ""));
        viewHolder.hour_of_deliver.setText("11:00");

        viewHolder.time_to_finish.setTextSize(width / 120);
        viewHolder.tableArray.clear();
        viewHolder.list.removeAllViews();

        ArrayList<ArrayList<String>> getOrder = arr.get(position).getOrderList();
        for (int i = 0; i < getOrder.size(); i++) {

            TableRow row = new TableRow(mContext);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView txt_order = new TextView(mContext);
            // txt_order.setHeight(40);
            //checkBox.setText("hello");


            ArrayList<String> it = (ArrayList<String>) getOrder.get(i);

            txt_order.setTypeface(null, Typeface.BOLD);
            txt_order.setTextAlignment(view.TEXT_ALIGNMENT_TEXT_END);
            txt_order.setTextSize(width / 160);
            txt_order.setText(it.get(1) + " szt." + it.get(3));

            row.addView(txt_order);
            viewHolder.tableArray.add(txt_order);
            viewHolder.list.addView(row, i);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Log.i("informacja", "klik" + clikPos);


                    Intent intent = new Intent();

                    Bundle bundle = new Bundle();

                    bundle.putString("receiptWay", arr.get(clikPos).getReceiptWay().replace("WŁASNY", ""));
                    bundle.putString("orderNumber", arr.get(clikPos).getOrderNumber());
                    bundle.putString("price", arr.get(clikPos).getTotalPrice());
                    bundle.putString("status", arr.get(clikPos).getStatus());
                    ArrayList<ArrayList<String>> getOrder = arr.get(clikPos).getOrderList();

                    ArrayList<String> iteme = (ArrayList<String>) getOrder.get(0);




                   // intent.putParcelableArrayListExtra("getOrder" , iteme);
                    for (int i = 0; i < getOrder.size(); i++) {

                        bundle.putSerializable("getOrder"+i , iteme);
                    }

                    intent.setClass(mContext, OrderZoomPopUp.class);

                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    // Activity activity = (Activity) mContext;
                    //activity.startActivity(intent);
                    //  activity.overridePendingTransition(R.anim.from_right, R.anim.to_left);


                }
            });

        }

        return view;
    }

    static class ViewHolderItem {
        ArrayList<ArrayList<String>> getOrderHold;
        TextView txt_order;
        TextView order_fb_payment_method;
        TextView price;
        View div0;
        TextView time_to_finish;
        TextView address_txt;
        TextView order_number;
        TextView delivety_method;
        TextView hour_of_deliver;
        ArrayList<MenuItemProduct> orderList;
        ArrayList<TextView> tableArray;
        ArrayList<String> it;
        LinearLayout list;
    }
}
