package com.michal_stasinski.tabu.Menu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.michal_stasinski.tabu.Menu.Models.ShopingCardItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.CustomFont_Avenir_Medium;
import com.michal_stasinski.tabu.Utils.MathUtils;

import java.util.ArrayList;
import java.util.TreeSet;

public class ShopingCardAdapter extends BaseAdapter {
    public static final int TYPE_PURCHASER = 0;
    public static final int TYPE_ORDER_RULE = 1;
    public static final int TYPE_ORDER_ITEM = 2;
    public static final int TYPE_SUMMARY= 3;
    public static final int TYPE_SEPARATOR = 4;


    private ArrayList<ShopingCardItem> arr;

    private LayoutInflater mInflater;
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    public ShopingCardAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        this.arr = new ArrayList<ShopingCardItem>();
    }

    public void addItem(final ShopingCardItem item) {
        this.arr.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        return this.arr.get(position).getType();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolderItem viewHolder;

        int rowType = getItemViewType(position);

        if (convertView == null) {

            viewHolder = new ViewHolderItem();
            switch (rowType) {
                case TYPE_PURCHASER:

                    convertView = mInflater.inflate(R.layout.order_purchaser_row, null);
                    viewHolder.title = (CustomFont_Avenir_Medium) convertView.findViewById(R.id.order_purchaser_name);
                    break;

                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.order_separator_row, null);
                    viewHolder.title = (CustomFont_Avenir_Medium) convertView.findViewById(R.id.order_separator_title);

                    break;


                case TYPE_ORDER_RULE:
                    convertView = mInflater.inflate(R.layout.order_rule_row, null);
                    viewHolder.title = (CustomFont_Avenir_Medium) convertView.findViewById(R.id.order_rule_title);
                    viewHolder.desc = (CustomFont_Avenir_Medium) convertView.findViewById(R.id.order_rule_desc);

                    break;
                case TYPE_ORDER_ITEM:
                    convertView = mInflater.inflate(R.layout.order_item_row, null);
                    viewHolder.title = (CustomFont_Avenir_Medium) convertView.findViewById(R.id.order_item_title);
                    viewHolder.quantity = (CustomFont_Avenir_Medium) convertView.findViewById(R.id.order_item_quantity);
                    viewHolder.desc = (CustomFont_Avenir_Medium) convertView.findViewById(R.id.order_item_desc);
                    viewHolder.price = (CustomFont_Avenir_Medium) convertView.findViewById(R.id.order_item_price);
                    break;

                case TYPE_SUMMARY:
                    convertView = mInflater.inflate(R.layout.order_summary, null);
                    viewHolder.title = (CustomFont_Avenir_Medium) convertView.findViewById(R.id.order_summary_title);
                    viewHolder.desc = (CustomFont_Avenir_Medium) convertView.findViewById(R.id.order_summary_sum_of_all_prices);
                    break;

            }
            convertView.setTag(viewHolder);
        } else {

           viewHolder = (ViewHolderItem) convertView.getTag();
        }



        viewHolder.title.setText(arr.get(position).getTitle().toUpperCase());

        if(viewHolder.quantity != null){
          viewHolder.quantity.setText(String.valueOf(arr.get(position).getNr()));
        }
        if(viewHolder.desc != null){
            viewHolder.desc.setText(String.valueOf(arr.get(position).getDesc()));
        }
        if(viewHolder.price != null){
            String output = MathUtils.formatDecimal(arr.get(position).getPrice(),2);
            viewHolder.price.setText(String.valueOf(output));
        }
        return convertView;
    }

    static class ViewHolderItem {
        CustomFont_Avenir_Medium title;
        CustomFont_Avenir_Medium desc;
        CustomFont_Avenir_Medium quantity;
        CustomFont_Avenir_Medium price;

    }



}