package com.michal_stasinski.tabu.Menu.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.R;

import java.util.ArrayList;
import java.util.TreeSet;

public class AddonsAdapter extends BaseAdapter {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_SEPARATOR = 1;


    private ArrayList<MenuItemProduct> arr;
    private int chooseSize = 1;
    private LayoutInflater mInflater;
    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();

    public AddonsAdapter(Context context) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arr = new ArrayList<MenuItemProduct>();
    }

    public void addItem(final MenuItemProduct item) {
        this.arr.add(item);
        notifyDataSetChanged();
    }

    public void addSectionHeaderItem(final MenuItemProduct item) {
        this.arr.add(item);
        sectionHeader.add(this.arr.size() - 1);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
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
                case TYPE_ITEM:
                    convertView = mInflater.inflate(R.layout.pop_up_row, null);
                    viewHolder.title = (TextView) convertView.findViewById(R.id.pizza_size_text);
                    viewHolder.check = (TextView) convertView.findViewById(R.id.checkmark);
                    break;
                case TYPE_SEPARATOR:
                    convertView = mInflater.inflate(R.layout.pop_up_header, null);
                    convertView.setOnClickListener(null);
                    viewHolder.title = (TextView) convertView.findViewById(R.id.addonsName);
                    break;
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderItem) convertView.getTag();
        }

        if (viewHolder.check != null) {
            if (arr.get(position).getHowManyItemWereSelected() != 0) {
                viewHolder.check.setText("x" + arr.get(position).getHowManyItemWereSelected());

            } else {
                viewHolder.check.setText("");
            }
        }
        if (arr.get(position).getPriceArray() != null) {
            viewHolder.title.setText(arr.get(position).getName() + " (" + arr.get(position).getPriceArray().get(chooseSize) + " zł)");
        } else {
            viewHolder.title.setText(arr.get(position).getName());
        }
        return convertView;
    }

    static class ViewHolderItem {
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

        if (arr.get(position).getHowManyItemWereSelected() == 0) {

            arr.get(position).setHowManyItemWereSelected(1);

        } else if (arr.get(position).getHowManyItemWereSelected() == 1) {

            arr.get(position).setHowManyItemWereSelected(2);

        } else if (arr.get(position).getHowManyItemWereSelected() == 2) {

            arr.get(position).setHowManyItemWereSelected(0);
        }
    }

    public ArrayList<MenuItemProduct> getItemArray() {
        return arr;
    }

    public void setItemArray(ArrayList<MenuItemProduct> arr) {
        this.arr = arr;
    }

}