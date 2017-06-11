package com.michal_stasinski.tabu.Menu.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.michal_stasinski.tabu.MainActivity;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.Menu.OrderComposerOthers;
import com.michal_stasinski.tabu.Menu.OrderComposerPizza;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.CustomTextView;
import com.michal_stasinski.tabu.Utils.MathUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by win8 on 27.12.2016.
 */

public class CustomListViewAdapter extends BaseAdapter {


    private static LayoutInflater inflater = null;
    private ArrayList<MenuItemProduct> arr;
    private Context mContext;
    private int color;
    private boolean sortOption;
    private Boolean specialSign;

    public Boolean getButton_flag_enabled() {
        return button_flag_enabled;
    }

    public void setButton_flag_enabled(Boolean button_flag_enabled) {
        this.button_flag_enabled = button_flag_enabled;
    }

    private Boolean button_flag_enabled = true;

    public CustomListViewAdapter(Context context, ArrayList<MenuItemProduct> mListArray, int color, Boolean sort) {
        sortOption = sort;
        this.specialSign = specialSign;
        Collections.sort(mListArray, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {


                if (sortOption) {
                    int id1 = Integer.parseInt(((MenuItemProduct) o1).getRank());
                    int id2 = Integer.parseInt(((MenuItemProduct) o2).getRank());

                    if (id1 > id2) {
                        return 1;
                    }
                    if (id1 < id2) {
                        return -1;
                    } else {
                        return 0;
                    }

                } else {
                    String s1 = (((MenuItemProduct) o1).getRank());
                    String s2 = (((MenuItemProduct) o2).getRank());
                    return s1.compareToIgnoreCase(s2);
                }
            }
        });


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

        final int clikPos = position;

        if (convertView == null) {
            ArrayList<Number> price = arr.get(position).getPriceArray();
            view = View.inflate(mContext, R.layout.left_menu_listview_row, null);
            viewHolder = new ViewHolderItem();
            viewHolder.title = (TextView) view.findViewById(R.id.titleItem);
            viewHolder.textDesc = (TextView) view.findViewById(R.id.txtDesc);
            viewHolder.colorShape = (TextView) view.findViewById(R.id.positionInList);
            viewHolder.price = price;
            viewHolder.buttonArray = new ArrayList<Button>();
            viewHolder.soldLabel = (CustomTextView) view.findViewById(R.id.sold_info);

            for (int i = 0; i < price.size(); i++) {
                LinearLayout list = (LinearLayout) view.findViewById(R.id.buttonlayout);
                Button priceBtn = new Button(mContext);

                final int priceId = i;

                int priceBtn_height = mContext.getResources().getDimensionPixelSize(R.dimen.menu_list_view_priceButton_height);
                int priceBtn_width = mContext.getResources().getDimensionPixelSize(R.dimen.menu_list_view_priceButton_width);

                android.widget.LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(priceBtn_width, priceBtn_height);
                lp.setMargins(30, 0, 30, 0); // left, top, right, bottom
                priceBtn.setBackgroundResource(R.drawable.price_shape);
                priceBtn.setTextColor(Color.BLACK);
                priceBtn.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/0248EU27.ttf"));
                priceBtn.setTextSize(14);
                //  priceBtn.setPaintFlags(priceBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                viewHolder.buttonArray.add(priceBtn);

                list.addView(priceBtn, lp);
            }
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderItem) view.getTag();
        }
        if (arr.get(position).getSold()) {
            viewHolder.soldLabel.setVisibility(View.VISIBLE);
        } else {
            viewHolder.soldLabel.setVisibility(View.INVISIBLE);
        }
        viewHolder.title.setText(arr.get(position).getName().toUpperCase());
        viewHolder.colorShape.setText("- " + arr.get(position).getRank() + " -");
        viewHolder.textDesc.setText(arr.get(position).getDescription().toLowerCase());

        for (int i = 0; i < viewHolder.price.size(); i++) {
            String output = MathUtils.formatDecimal(arr.get(position).getPriceArray().get(i), 2);
            viewHolder.buttonArray.get(i).setText(output.toString() + (" zł").toLowerCase());

            final int butId = i;

            viewHolder.buttonArray.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (!arr.get(clikPos).getSold()) {
                        if (button_flag_enabled) {
                            button_flag_enabled = false;
                            Intent intent = new Intent();

                            if (MainActivity.CHOICE_ACTIVITY == 1) {
                                intent.setClass(mContext, OrderComposerPizza.class);
                            } else {
                                intent.setClass(mContext, OrderComposerOthers.class);
                            }

                            Bundle bundle = new Bundle();
                            bundle.putString("name", arr.get(clikPos).getName());
                            bundle.putInt("position", clikPos);
                            bundle.putString("desc", arr.get(clikPos).getDescription());
                            bundle.putInt("size", butId);
                            bundle.putString("price", arr.get(clikPos).getPriceArray().get(butId).toString());

                            Log.i("informacja", "___________________________" + clikPos + "" + butId);
                            Log.i("informacja", "name " + arr.get(clikPos).getName());
                            Log.i("informacja", arr.get(clikPos).getPriceArray() + "price " + arr.get(clikPos).getPriceArray().get(butId).toString());
                            intent.putExtras(bundle);
                            Activity activity = (Activity) mContext;
                            activity.startActivity(intent);
                            activity.overridePendingTransition(R.anim.from_left, R.anim.to_right);


                        }
                    }
                }

            });
        }

        return view;
    }

    static class ViewHolderItem {
        TextView title;
        TextView textDesc;
        TextView colorShape;
        ArrayList<Button> buttonArray;
        ArrayList<Number> price;
        CustomTextView soldLabel;
    }
}
