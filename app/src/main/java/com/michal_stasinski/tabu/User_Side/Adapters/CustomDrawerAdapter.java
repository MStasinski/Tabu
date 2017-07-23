package com.michal_stasinski.tabu.User_Side.Adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.michal_stasinski.tabu.R;

public class CustomDrawerAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] largeTxtArr;
    private final Integer[] imgid;

    public CustomDrawerAdapter(Activity context, String[] largeTextItem, Integer[] imgid) {
        super(context, R.layout.drawer_row, largeTextItem);

        this.context = context;
        this.largeTxtArr = largeTextItem;
        this.imgid = imgid;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View view = convertView;
        ViewHolderDrawer viewHolder;

        if (convertView == null) {

            view = View.inflate(context, R.layout.drawer_row, null);
            viewHolder = new ViewHolderDrawer();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.icon);
            viewHolder.txtTitle = (TextView) view.findViewById(R.id.txtTitleDrawer);
            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolderDrawer) view.getTag();
        }
        viewHolder.txtTitle.setText(largeTxtArr[position]);
        viewHolder.imageView.setImageResource(imgid[position]);

        return view;

    }

    static class ViewHolderDrawer {
        ImageView imageView;
        TextView txtTitle;
    }
}