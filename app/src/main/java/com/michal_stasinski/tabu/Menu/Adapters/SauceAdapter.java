package com.michal_stasinski.tabu.Menu.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.CustomFont_Avenir_Medium;

import java.util.ArrayList;

public class SauceAdapter extends BaseAdapter {


    private ArrayList<MenuItemProduct> souceArray;
    private int chooseSize = 0;
    private Context mContext;

    public SauceAdapter(Context context, ArrayList<MenuItemProduct> souceArray) {
        this.souceArray = souceArray;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return souceArray.size();
    }

    @Override
    public Object getItem(int position) {
        return souceArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        SauceAdapter.ViewHolderItem viewHolder;

        if (convertView == null) {
            view = View.inflate(mContext, R.layout.pop_up_row, null);
            viewHolder = new SauceAdapter.ViewHolderItem();
            viewHolder.title = (TextView) view.findViewById(R.id.pizza_size_text);
            viewHolder.check = (TextView) view.findViewById(R.id.checkmark);
            viewHolder.isSold = (CustomFont_Avenir_Medium) view.findViewById(R.id.sold_info);
            view.setTag(viewHolder);

        } else {

            viewHolder = (SauceAdapter.ViewHolderItem) view.getTag();
        }

        if (viewHolder.isSold != null) {
            if(this.souceArray.get(position).getSold()){
                viewHolder.isSold.setVisibility(View.VISIBLE);
            }else{
                viewHolder.isSold.setVisibility(View.INVISIBLE);
            }
        }

        viewHolder.title.setText(this.souceArray.get(position).getName() + " (" + this.souceArray.get(position).getPriceArray().get(chooseSize) + " zł)");
        if (viewHolder.check != null) {
            if (this.souceArray.get(position).getHowManyItemSelected() != 0) {
                viewHolder.check.setText("x" + this.souceArray.get(position).getHowManyItemSelected());

            } else {
                viewHolder.check.setText("");
            }
        }

        return view;
    }

    static class ViewHolderItem {
        CustomFont_Avenir_Medium isSold;
        TextView title;
        TextView check;
    }

    public int getChooseSize() {
        return chooseSize;
    }

    public void setChooseSize(int chooseSize) {
        this.chooseSize = chooseSize;
    }

    public void setChoooseHowManyItemYouOrder(int position) {

        if (this.souceArray.get(position).getHowManyItemSelected() == 0) {

            this.souceArray.get(position).setHowManyItemSelected(1);

        } else if (this.souceArray.get(position).getHowManyItemSelected() == 1) {

            this.souceArray.get(position).setHowManyItemSelected(2);

        } else if (this.souceArray.get(position).getHowManyItemSelected() == 2) {

            this.souceArray.get(position).setHowManyItemSelected(0);
        }
    }

    public ArrayList<MenuItemProduct> getItemArray() {
        return this.souceArray;
    }

    public void setItemArray(ArrayList<MenuItemProduct> arr) {
        this.souceArray = arr;
    }

}