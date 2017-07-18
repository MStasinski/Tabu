package com.michal_stasinski.tabu.Menu.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.michal_stasinski.tabu.Menu.Models.OrderComposerItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.MathUtils;

import java.util.ArrayList;


public class OrderComposerListViewAdapter extends BaseAdapter {
    public static final int TYPE_HEADER = 0;
    public static final int TYPE_PIZZA_SIZE = 1;
    public static final int TYPE_PIZZA_ADDONS = 2;
    public static final int TYPE_PIZZA_SOUCE = 3;
    public static final int TYPE_COMMENTS = 4;
    public static final int TYPE_ADD_REMOVE_PANEL = 5;

    private String[] titleArr;
    private String[] descArr;
    private Context mContext;
    private int num_value = 1;
    private LayoutInflater mInflater;
    private ArrayList<OrderComposerItem> arr;

    public int getNum_value() {
        return num_value;
    }


    public OrderComposerListViewAdapter(Context context) {

        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arr = new ArrayList<OrderComposerItem>();
    }

    public void addItem(final OrderComposerItem item) {
        this.arr.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 6;
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
        ViewHolderItem viewHolder = null;
        int rowType = getItemViewType(position);


        if (convertView == null) {
            viewHolder = new ViewHolderItem();

            switch (rowType) {
                case TYPE_HEADER:

                    view = mInflater.inflate(R.layout.fragment_order_composer_header, null);
                    viewHolder.title = (TextView) view.findViewById(R.id.order_composer_titleItem);
                    viewHolder.desc = (TextView) view.findViewById(R.id.order_composer_desc);
                    viewHolder.price = (TextView) view.findViewById(R.id.order_composer_price);
                    break;

                case TYPE_PIZZA_SIZE:
                    view = mInflater.inflate(R.layout.fragment_order_compositor_row, null);
                    viewHolder.title = (TextView) view.findViewById(R.id.order_row_title);
                    viewHolder.desc = (TextView) view.findViewById(R.id.order_row_desc);

                    break;

                case TYPE_PIZZA_ADDONS:
                    view = mInflater.inflate(R.layout.fragment_order_compositor_row, null);
                    viewHolder.title = (TextView) view.findViewById(R.id.order_row_title);
                    viewHolder.desc = (TextView) view.findViewById(R.id.order_row_desc);

                    break;
                case TYPE_PIZZA_SOUCE:
                    view = mInflater.inflate(R.layout.fragment_order_compositor_row, null);
                    viewHolder.title = (TextView) view.findViewById(R.id.order_row_title);
                    viewHolder.desc = (TextView) view.findViewById(R.id.order_row_desc);
                    break;

                case TYPE_COMMENTS:
                    view = mInflater.inflate(R.layout.fragment_order_compositor_row, null);
                    viewHolder.title = (TextView) view.findViewById(R.id.order_row_title);
                    viewHolder.desc = (TextView) view.findViewById(R.id.order_row_desc);
                    break;

                case TYPE_ADD_REMOVE_PANEL:
                    view = mInflater.inflate(R.layout.fragment_order_compositor_add_remove_panel, null);
                    Button add_btn = (Button) view.findViewById(R.id.order_compositor_addItem);
                    Button remove_btn = (Button) view.findViewById(R.id.order_compositor_removeItem);
                    final TextView num = (TextView) view.findViewById(R.id.order_compositor_quantity_num);

                    num.setText(String.valueOf(num_value));

                    add_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            num_value++;
                            num.setText(String.valueOf(num_value));
                            notifyDataSetChanged();
                        }


                    });

                    remove_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (num_value > 1) {
                                num_value--;
                            }
                            num.setText(String.valueOf(num_value));
                            notifyDataSetChanged();
                        }
                    });
                    break;
            }


            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) view.getTag();
        }

        if (viewHolder.title != null) {
            viewHolder.title.setText(arr.get(position).getTitle().toUpperCase());
        }

        if (viewHolder.desc != null) {
            viewHolder.desc.setText(arr.get(position).getDesc());
        }

        if (viewHolder.price != null) {
            viewHolder.price.setText(arr.get(position).getPrice()+ " zł");
        }

        return view;
    }

    static class ViewHolderItem {

        TextView title;
        TextView desc;
        TextView price;

    }


}