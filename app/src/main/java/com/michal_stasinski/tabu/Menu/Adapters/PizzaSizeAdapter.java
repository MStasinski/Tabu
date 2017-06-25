package com.michal_stasinski.tabu.Menu.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.CustomFont_Avenir_Medium;
import com.michal_stasinski.tabu.Utils.MathUtils;

import java.util.ArrayList;

public class PizzaSizeAdapter extends BaseAdapter {

    private ArrayList<MenuItemProduct> pizzaSizesArr;
    private ArrayList<MenuItemProduct> pizzas;
    private ArrayList<Integer> markSignArr;
    private Context mContext;
    private String mark = "";
    private int positionInMenuListView;

    public PizzaSizeAdapter(Context context, int positioninMenu, ArrayList<MenuItemProduct> pizzas, ArrayList<MenuItemProduct> pizzaSizes, ArrayList<Integer> markSign) {
        this.pizzaSizesArr = pizzaSizes;
        this.markSignArr = markSign;
        this.pizzas = pizzas;
        this.mContext = context;
        this.positionInMenuListView = positioninMenu;
    }

    @Override
    public int getCount() {
        return pizzaSizesArr.size();
    }

    @Override
    public Object getItem(int position) {
        return pizzaSizesArr.get(position);
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
            view = View.inflate(mContext, R.layout.pop_up_row, null);
            viewHolder = new ViewHolderItem();
            viewHolder.title = (TextView) view.findViewById(R.id.pizza_size_text);
            viewHolder.isSold = (CustomFont_Avenir_Medium) view.findViewById(R.id.sold_info);
            viewHolder.check = (TextView) view.findViewById(R.id.checkmark);
            view.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolderItem) view.getTag();
        }
        String output = MathUtils.formatDecimal(this.pizzas.get(this.positionInMenuListView).getPriceArray().get(position),2);
        viewHolder.title.setText(pizzaSizesArr.get(position).getName() +" (" +output+" zł)");

        if(pizzaSizesArr.get(position).getSold()){
            viewHolder.isSold.setVisibility(View.VISIBLE);
        }else{
            viewHolder.isSold.setVisibility(View.INVISIBLE);
        }



        if (this.markSignArr.get(position) == 1) {
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
        CustomFont_Avenir_Medium isSold;
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