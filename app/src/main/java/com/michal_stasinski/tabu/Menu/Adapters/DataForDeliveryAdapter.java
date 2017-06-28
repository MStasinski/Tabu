
package com.michal_stasinski.tabu.Menu.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michal_stasinski.tabu.Menu.Models.ShopingCardItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.CustomFont_Avenir_Medium;

import java.util.ArrayList;
import java.util.TreeSet;

import static com.michal_stasinski.tabu.SplashScreen.dataDeliveryTextFieldName;

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
                    convertView = mInflater.inflate(R.layout.data_for_deliver_row, null);
                    convertView.setBackgroundResource(R.color.colorWhite);
                    viewHolder.title = (TextView) convertView.findViewById(R.id.data_row_title);
                    viewHolder.imageView = (ImageView) convertView.findViewById(R.id.data_icon);
                    break;
                case 0:
                    convertView = mInflater.inflate(R.layout.data_for_delivery_separator_row, null);
                    viewHolder.title = (TextView) convertView.findViewById(R.id.data_separator_title);
                    break;

            }
            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolderItem) convertView.getTag();

        }

        boolean isEmpty = false;
        for (int i = 0; i < dataDeliveryTextFieldName.length; i++) {

                if (arr.get(position).getTitle().toUpperCase().equals(dataDeliveryTextFieldName[i].toUpperCase())) {
                    isEmpty = true;

            }
        }
        if (isEmpty) {
            viewHolder.title.setText(arr.get(position).getTitle().toUpperCase());
            viewHolder.title.setTextColor(Color.GRAY);
        }else{
            viewHolder.title.setText(arr.get(position).getTitle());
            viewHolder.title.setTextColor(Color.DKGRAY);
        }


        if (viewHolder.imageView != null) {
            viewHolder.imageView.setImageResource(icon[position]);
        }

        return convertView;
    }

    static class ViewHolderItem {
        TextView title;
        ImageView imageView;
    }


}