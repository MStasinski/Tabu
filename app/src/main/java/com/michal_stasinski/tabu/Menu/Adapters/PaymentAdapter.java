package com.michal_stasinski.tabu.Menu.Adapters;

/**
 * Created by win8 on 22.06.2017.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michal_stasinski.tabu.Menu.Models.PaymentItem;
import com.michal_stasinski.tabu.R;

import java.util.ArrayList;

public class PaymentAdapter extends BaseAdapter {


    private ArrayList<Integer> markSignArr;
    private Context mContext;
    private String mark = "";
    private ArrayList<PaymentItem> paymentmethods;

    public PaymentAdapter(Context context, ArrayList<PaymentItem> payment) {


        paymentmethods = payment;
        this.mContext = context;

    }

    @Override
    public int getCount() {
        return paymentmethods.size();
    }

    @Override
    public Object getItem(int position) {
        return paymentmethods.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolderItem viewHolder;

        if (convertView == null) {
            view = View.inflate(mContext, R.layout.payment_methods_row, null);
            viewHolder = new ViewHolderItem();
            viewHolder.title = (TextView) view.findViewById(R.id.payment_method_text);
            viewHolder.check = (TextView) view.findViewById(R.id.payment_checkmark);
            viewHolder.img = (ImageView) view.findViewById(R.id.payment_image);
            view.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolderItem) view.getTag();
        }


        viewHolder.title.setText(paymentmethods.get(position).getPayment_txt());
        viewHolder.img.setImageResource(paymentmethods.get(position).getImage());
        if (position < 2) {
            viewHolder.img.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary));

        }


        if (paymentmethods.get(position).getMark() == true) {
            viewHolder.check.setText("\u2713");
            viewHolder.title.setTextColor(Color.GRAY);
            viewHolder.check.setTextColor(Color.rgb(255, 126, 0));
        } else {
            viewHolder.check.setText("");
            viewHolder.title.setTextColor(Color.BLACK);
            viewHolder.check.setTextColor(Color.BLACK);
        }
        return view;
    }

    static class ViewHolderItem {
        ImageView img;
        TextView title;
        TextView check;
    }

}