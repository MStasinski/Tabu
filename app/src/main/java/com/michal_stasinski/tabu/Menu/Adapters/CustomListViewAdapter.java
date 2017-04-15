package com.michal_stasinski.tabu.Menu.Adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by win8 on 27.12.2016.
 */

public class CustomListViewAdapter extends BaseAdapter {


    private static LayoutInflater inflater = null;
    private ArrayList<MenuItemProduct> arr;
    private Context mContext;
    private int color;
    private boolean sortOption;
    private Boolean specialSign;

    public CustomListViewAdapter(Context context, ArrayList<MenuItemProduct> mListArray, int color, Boolean sort) {
        sortOption = sort;
        this.specialSign = specialSign;
        Collections.sort(mListArray, new Comparator() {
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
        });


        this.arr = mListArray;
        this.mContext = context;
        this.color = color;
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

        if (convertView == null) {
            view = View.inflate(mContext, R.layout.left_menu_listview_row, null);
            viewHolder = new ViewHolderItem();
            viewHolder.title = (TextView) view.findViewById(R.id.titleItem);
            viewHolder.textDesc = (TextView) view.findViewById(R.id.txtDesc);
            viewHolder.textPrice = (TextView) view.findViewById(R.id.txtPrice);
            viewHolder.colorShape = (TextView) view.findViewById(R.id.positionInList);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderItem) view.getTag();
        }


        viewHolder.title.setText(arr.get(position).getNameProduct().toUpperCase());
        viewHolder.colorShape.setText(arr.get(position).getRank());


        ((GradientDrawable) viewHolder.colorShape.getBackground()).setColor(mContext.getResources().getColor(this.color));
        viewHolder.textDesc.setText(arr.get(position).getDesc().toLowerCase());
       // viewHolder.textPrice.setText(arr.get(position).getPrice().toString().toUpperCase() + " ZŁ");
        ArrayList <Number> price = arr.get(position).getPriceArr();
        viewHolder.textPrice.setText(price.get(0).toString().toUpperCase() + " ZŁ");




        return view;
    }

    static class ViewHolderItem {

        TextView title;
        TextView textDesc;
        TextView textPrice;
        TextView colorShape;

    }
}
