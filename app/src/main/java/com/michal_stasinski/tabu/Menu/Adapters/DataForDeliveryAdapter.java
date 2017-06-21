
package com.michal_stasinski.tabu.Menu.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.michal_stasinski.tabu.Menu.Models.ShopingCardItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.CustomTextView;

import java.util.ArrayList;
import java.util.TreeSet;

public class DataForDeliveryAdapter extends BaseAdapter {


    private ArrayList<ShopingCardItem> arr;

    private Integer[] icon;
    private LayoutInflater mInflater;

    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    public DataForDeliveryAdapter(Context context, Integer[] img) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icon = img;
        this.arr = new ArrayList<ShopingCardItem>();
    }

    public void addItem(final ShopingCardItem item) {
        this.arr.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
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
                case 1:
                    convertView = mInflater.inflate(R.layout.custom_drawer_row, null);
                    convertView.setBackgroundResource(R.color.colorWhite);
                    viewHolder.title = (CustomTextView) convertView.findViewById(R.id.txtTitleDrawer);
                    viewHolder.imageView = (ImageView) convertView.findViewById(R.id.icon);
                    break;
                case 0:
                    convertView = mInflater.inflate(R.layout.order_separator_row, null);
                    viewHolder.title = (CustomTextView) convertView.findViewById(R.id.order_separator_title);
                    break;

            }
            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolderItem) convertView.getTag();

        }


        viewHolder.title.setText(arr.get(position).getTitle());

        if (viewHolder.imageView != null) {
            viewHolder.imageView.setImageResource(icon[position]);
        }

        return convertView;
    }

    static class ViewHolderItem {
        CustomTextView title;
        ImageView imageView;
    }


}