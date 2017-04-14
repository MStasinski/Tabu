package com.michal_stasinski.tabu.Menu.Adapters;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.CustomTextView;

public class CustomDrawerAdapter extends ArrayAdapter<String> {

    private final Activity context;
   //private final String[] smallTxtArr;
    private final String[] largeTxtArr;
    private final Integer[] imgid;

    public CustomDrawerAdapter(Activity context, String[] largeTextItem, Integer[] imgid) {
        super(context, R.layout.custom_drawer_row, largeTextItem);

        this.context = context;
        this.largeTxtArr = largeTextItem;
        //this.smallTxtArr = smallTextItem;
        this.imgid = imgid;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View view = convertView;
        ViewHolderDrawer viewHolder;

       if (convertView == null) {

            view = View.inflate(context,R.layout.custom_drawer_row, null);
            viewHolder = new ViewHolderDrawer();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.icon);
            viewHolder.txtTitle = (CustomTextView) view.findViewById(R.id.txtTitleDrawer);
            //viewHolder.smallTxt = (CustomTextView) view.findViewById(R.id.txtDescDrawer);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderDrawer) view.getTag();
        }
       // largeTxtArr[position]
        viewHolder.txtTitle.setText(largeTxtArr[position]);
        viewHolder.imageView.setImageResource(imgid[position]);
        //viewHolder.smallTxt.setText(smallTxtArr[position]);

        return view;

    }

    static class ViewHolderDrawer {
        ImageView imageView;
        CustomTextView txtTitle;
        CustomTextView smallTxt;
    }
}