package com.michal_stasinski.tabu.CRM.Adapters;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.michal_stasinski.tabu.CRM.Model.GetOrderFromFB;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.R;

import java.util.ArrayList;

/**
 * Created by win8 on 27.12.2016.
 */

public class SimpleListViewAdapter extends BaseAdapter {


    private static LayoutInflater inflater = null;
    private ArrayList<GetOrderFromFB> arr;
    private Context mContext;

    private boolean sortOption;
    private Boolean specialSign;
    private int color;

    public Boolean getButton_flag_enabled() {
        return button_flag_enabled;
    }

    public void setButton_flag_enabled(Boolean button_flag_enabled) {
        this.button_flag_enabled = button_flag_enabled;
    }

    private Boolean button_flag_enabled = true;

    public SimpleListViewAdapter(Context context, ArrayList<GetOrderFromFB> mListArray, Boolean sort, int col) {
        sortOption = sort;
        color = col;
        this.specialSign = specialSign;
       /* Collections.sort(mListArray, new Comparator() {
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
        });*/


        this.arr = mListArray;
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolderItem viewHolder;

        final int clikPos = position;

        if (convertView == null) {
            // ArrayList<Number> price = arr.get(position).getPriceArray();
            view = View.inflate(mContext, R.layout.crm_simple_list_row, null);
            viewHolder = new ViewHolderItem();
            viewHolder.title = (TextView) view.findViewById(R.id.order_fb_item_title);

            LinearLayout ll = (LinearLayout) view.findViewById(R.id.list_of_order_for_one_item);
            viewHolder.price = (TextView) view.findViewById(R.id.order_fb_item_price);

            viewHolder.order_number = (TextView) view.findViewById(R.id.order_nr);
            viewHolder.delivety_method = (TextView) view.findViewById(R.id.delivety_method);
            viewHolder.hour_of_deliver = (TextView) view.findViewById(R.id.hour_of_deliver );
            viewHolder.address_txt = (TextView) view.findViewById(R.id.address_txt);
            /*
            viewHolder.textDesc = (TextView) view.findViewById(R.id.txtDesc);
            viewHolder.colorShape = (TextView) view.findViewById(R.id.positionInList);
            viewHolder.price = price;
            viewHolder.buttonArray = new ArrayList<Button>();
            viewHolder.soldLabel = (TextView) view.findViewById(R.id.sold_info);*/

            ArrayList myArr = new ArrayList();

            myArr = arr.get(position).getOrderList();

            for (int i = 0; i < myArr.size(); i++) {

                TableRow row = new TableRow(mContext);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                TextView txt_order = new TextView(mContext);
                //checkBox.setText("hello");


                ArrayList<String> it = (ArrayList<String>) myArr.get(i);
                Log.i("informacja", "myArr.get(i)_______________ " + it.get(1));
                txt_order.setTextSize(11);
                txt_order.setTypeface(null, Typeface.BOLD);
                txt_order.setTextAlignment(view.TEXT_ALIGNMENT_TEXT_END);
                txt_order.setText(it.get(1) + " szt." + it.get(3));

                // row.addView(checkBox);
                row.addView(txt_order);
                //row.addView(addBtn);
                ll.addView(row, i);
            }


          /*  for (int i = 0; i < price.size(); i++) {
                LinearLayout list = (LinearLayout) view.findViewById(R.id.buttonlayout);

                Button priceBtn = new Button(mContext);
                priceBtn.setTransformationMethod(null);

                final int priceId = i;

                int priceBtn_height = mContext.getResources().getDimensionPixelSize(R.dimen.menu_list_view_priceButton_height);
                int priceBtn_width = mContext.getResources().getDimensionPixelSize(R.dimen.menu_list_view_priceButton_width);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(priceBtn_width, priceBtn_height);



                int screenWidth = mContext.getResources().getDisplayMetrics().widthPixels;

                //int margin = (int) (mContext.getResources().getDimension(R.dimen.price_margin) / mContext.getResources().getDisplayMetrics().density);
                int margin = (int) ((screenWidth-(price.size()*priceBtn_width)) /(price.size()+price.size()+2));

                Log.i("informacja",margin+ "  screenWidth  " +  screenWidth);

                lp.setMargins(margin, 0, margin, 0); // left, top, right, bottom
                priceBtn.setBackgroundResource(R.drawable.price_shape);

                priceBtn.setTextColor(Color.rgb(85, 85, 85));

                priceBtn.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/AvenirNextCondensed-Medium.ttf"));
                //priceBtn.setTypeface(Typeface.DEFAULT, 4);
                priceBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(price_font));


                priceBtn.setPadding(0, 0, 0, 0);
                priceBtn.setMinHeight(0);
                priceBtn.setMinimumHeight(0);
                priceBtn.setMinimumWidth(0);
                priceBtn.setMinWidth(0);
                //  priceBtn.setPaintFlags(priceBtn.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                viewHolder.buttonArray.add(priceBtn);

                list.addView(priceBtn, lp);
            }*/
            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderItem) view.getTag();
        }
        viewHolder.title.setText(arr.get(position).getPaymentWay());
        viewHolder.price.setText(arr.get(position).getTotalPrice());
        //viewHolder.order_number.setBackgroundColor(color);
        viewHolder.order_number.setBackgroundTintList(ColorStateList.valueOf(color));
        viewHolder.delivety_method.setBackgroundTintList(ColorStateList.valueOf(color));
        viewHolder.delivety_method.setText(arr.get(position).getReceiptWay());
        viewHolder.hour_of_deliver.setText("11:00");
       // viewHolder.hour_of_deliver.setText(arr.get(position).getDeliveryDate());
       // viewHolder.address_txt.setText(arr.get(position).getReceiptAdres());
        /*if (arr.get(position).getSold()) {
            viewHolder.soldLabel.setVisibility(View.VISIBLE);
        } else {
            viewHolder.soldLabel.setVisibility(View.INVISIBLE);
        }

        viewHolder.colorShape.setText("- " + arr.get(position).getRank() + " -");

        viewHolder.textDesc.setText(arr.get(position).getDescription());
        if (arr.get(position).getDescription().equals("")) {
            viewHolder.textDesc.setHeight(0);
        }

        for (int i = 0; i < viewHolder.price.size(); i++) {
            String output = MathUtils.formatDecimal(arr.get(position).getPriceArray().get(i), 2);
            viewHolder.buttonArray.get(i).setText(output.toString() + " zł");

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
                            bundle.putString("rank", arr.get(clikPos).getRank());
                            bundle.putString("price", arr.get(clikPos).getPriceArray().get(butId).toString());

                            intent.putExtras(bundle);
                            Activity activity = (Activity) mContext;
                            activity.startActivity(intent);
                            activity.overridePendingTransition(R.anim.from_right, R.anim.to_left);


                        }
                    }
                }

            });
        }*/

        return view;
    }

    static class ViewHolderItem {
        TextView title;
        TextView price;
        TextView address_txt;
        TextView order_number;
        TextView delivety_method;
        TextView hour_of_deliver;
        ArrayList<MenuItemProduct> orderList;

        /*TextView textDesc;
        TextView colorShape;
        ArrayList<Button> buttonArray;
        ArrayList<Number> price;
        TextView soldLabel;*/
    }
}
