package com.michal_stasinski.tabu.User_Side.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.michal_stasinski.tabu.User_Side.Models.NewsItem;
import com.michal_stasinski.tabu.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by win8 on 27.12.2016.
 */

public class CustomNewsListViewAdapter extends BaseAdapter {


    private static LayoutInflater inflater = null;
    private ArrayList<NewsItem> arr;
    private Context mContext;
    private int color;
    private boolean sortOption;
    private Boolean specialSign;

    public CustomNewsListViewAdapter(Context context, ArrayList<NewsItem> mListArray, int color) {
        Collections.sort(mListArray, Collections.reverseOrder(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {


                String s1 = (((NewsItem) o1).getRank());
                String s2 = (((NewsItem) o2).getRank());
                if (s1 != null && s2 != null) {
                    return s1.compareToIgnoreCase(s2);
                } else {
                    return 0;
                }


            }
        }));


        this.arr = mListArray;
        this.mContext = context;
        this.color = color;
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
            view = View.inflate(mContext, R.layout.left_menu_news_listview_row, null);
            viewHolder = new ViewHolderItem();
            viewHolder.date = (TextView) view.findViewById(R.id.dataText);
            viewHolder.news = (TextView) view.findViewById(R.id.blog_post_desc);
            viewHolder.title = (TextView) view.findViewById(R.id.blog_post_title);
            viewHolder.url = (TextView) view.findViewById(R.id.positionInList);
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderItem) view.getTag();
        }

        viewHolder.title.setText(arr.get(position).getTitle().toUpperCase());
        viewHolder.date.setText(arr.get(position).getDate());
        viewHolder.news.setText(arr.get(position).getNews());
        ImageView post_image = (ImageView) view.findViewById(R.id.blog_post_image);
        Picasso.with(this.mContext).load(arr.get(position).getUrl()).into(post_image);
        return view;
    }

    static class ViewHolderItem {
        TextView date;
        TextView news;
        TextView title;
        TextView url;

    }
}
