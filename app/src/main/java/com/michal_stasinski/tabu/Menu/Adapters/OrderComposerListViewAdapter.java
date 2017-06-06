package com.michal_stasinski.tabu.Menu.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.michal_stasinski.tabu.R;


public class OrderComposerListViewAdapter extends BaseAdapter {

    private String[] titleArr;
    private String[] descArr;
    private Context mContext;
    private int num_value = 1;


    public int getNum_value() {
        return num_value;
    }


    public OrderComposerListViewAdapter(Context context, String[] titleArr, String[] descArr) {

        this.titleArr = titleArr;
        this.descArr = descArr;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return titleArr.length;
    }

    @Override
    public Object getItem(int position) {
        return titleArr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolderItem viewHolder = null;

        if (convertView == null) {
            if (position == titleArr.length - 1) {
                view = View.inflate(mContext, R.layout.fragment_order_compositor_add_remove_panel, null);
                //view = View.inflate(mContext, R.layout.test, null);

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

                        if(num_value>1) {
                            num_value--;
                        }
                        num.setText(String.valueOf(num_value));
                        notifyDataSetChanged();
                    }
                });

            } else {
                viewHolder = new ViewHolderItem();
                view = View.inflate(mContext, R.layout.fragment_order_compositor_row, null);
                viewHolder.title = (TextView) view.findViewById(R.id.order_row_title);
                viewHolder.desc = (TextView) view.findViewById(R.id.order_row_desc);
                view.setTag(viewHolder);
            }

        } else {
            viewHolder = (ViewHolderItem) view.getTag();
        }

        if (viewHolder != null) {
            viewHolder.title.setText(titleArr[position].toUpperCase());
            viewHolder.desc.setText(descArr[position]);
        }

        return view;
    }

    static class ViewHolderItem {

        TextView title;
        TextView desc;
    }
    public String[] getDescArr() {
        return descArr;
    }

    public void setDescArr(String[] descArr) {
        this.descArr = descArr;
    }
}