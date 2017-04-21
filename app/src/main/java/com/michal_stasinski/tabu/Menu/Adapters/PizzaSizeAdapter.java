package com.michal_stasinski.tabu.Menu.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.R;

import java.util.ArrayList;

public class PizzaSizeAdapter extends BaseAdapter {

    private ArrayList<MenuItemProduct> arr;



    private ArrayList<Integer> markSignArr;
    private Context mContext;
    private String mark = "";

    public PizzaSizeAdapter(Context context, ArrayList<MenuItemProduct> mListArray, ArrayList<Integer> markSign) {


        this.arr = mListArray;
        this.markSignArr = markSign;
        this.mContext = context;
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
            view = View.inflate(mContext, R.layout.pizza_size_row, null);
            viewHolder = new ViewHolderItem();
            viewHolder.title = (TextView) view.findViewById(R.id.pizza_size_text);
            viewHolder.check = (TextView) view.findViewById(R.id.checkmark);


            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderItem) view.getTag();
        }

        viewHolder.title.setText(arr.get(position).getNameProduct());
        if(this.markSignArr.get(position)==1){
            viewHolder.check.setText("\u2713");
            viewHolder.title.setTextColor(Color.GRAY);
            viewHolder.check.setTextColor(Color.rgb(255,126,0));
        }else{
            viewHolder.check.setText("");
            viewHolder.title.setTextColor(Color.BLACK);
            viewHolder.check.setTextColor(Color.BLACK);
        }
        return view;
    }

    static class ViewHolderItem {

        TextView title;
        TextView check;

    }
    public ArrayList<Integer> getMarkSignArr() {
        return markSignArr;
    }

    public void setMarkSignArr(ArrayList<Integer> markSignArr) {
        this.markSignArr = markSignArr;
    }
}