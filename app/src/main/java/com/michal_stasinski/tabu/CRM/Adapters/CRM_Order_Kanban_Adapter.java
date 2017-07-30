package com.michal_stasinski.tabu.CRM.Adapters;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.michal_stasinski.tabu.CRM.Model.GetOrderFromFB;
import com.michal_stasinski.tabu.CRM.OrderZoomPopUp;
import com.michal_stasinski.tabu.User_Side.Models.MenuItemProduct;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.Diffrence_Between_Two_Times;

import java.util.ArrayList;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import me.grantland.widget.AutofitTextView;

import static android.R.attr.data;
import static android.app.Activity.RESULT_OK;
import static com.michal_stasinski.tabu.SplashScreen.DB_ORDER_SERIAL_NUMBER;
import static com.michal_stasinski.tabu.SplashScreen.IS_LOGGED_IN;
import static com.michal_stasinski.tabu.SplashScreen.IS_STAFF_MEMBER;


/**
 * Created by win8 on 27.12.2016.
 */

public class CRM_Order_Kanban_Adapter extends BaseAdapter {


    private static LayoutInflater inflater = null;
    private ArrayList<GetOrderFromFB> arr;
    private Context mContext;
    private Activity activity;
    private boolean sortOption;
    private Boolean specialSign;
    private int color;
    private int height;
    private int width;
    private String[] ordreS;
    private int scale = 140;

    private int counter = 0; //counter to indicate the total second whenever timer fire

    public Boolean getButton_flag_enabled() {
        return button_flag_enabled;
    }

    public void setButton_flag_enabled(Boolean button_flag_enabled) {
        this.button_flag_enabled = button_flag_enabled;
    }

    private Boolean button_flag_enabled = true;

    public CRM_Order_Kanban_Adapter(final Activity activity, Context context, ArrayList<GetOrderFromFB> mListArray, Boolean sort, int col) {
        sortOption = sort;
        color = col;
        this.activity = activity;
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
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();


        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;

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
            // ArrayList<Number> price = arr.get(position).getPriceArray();
            view = View.inflate(mContext, R.layout.crm_order_kanban_block, null);
            viewHolder = new ViewHolderItem();
            viewHolder.order_fb_payment_method = (TextView) view.findViewById(R.id.order_fb_payment_method);

            viewHolder.list = (LinearLayout) view.findViewById(R.id.list_of_order_for_one_item);
            viewHolder.price = (TextView) view.findViewById(R.id.order_fb_item_price);
            viewHolder.div0 = (View) view.findViewById(R.id.div0);
            viewHolder.order_number = (TextView) view.findViewById(R.id.order_nr);
            viewHolder.hour_of_deliver = (TextView) view.findViewById(R.id.hour_of_deliver);
            viewHolder.time_to_finish = (TextView) view.findViewById(R.id.time_to_finish);
            viewHolder.delivety_method = (TextView) view.findViewById(R.id.delivety_method);

            viewHolder.time_to_finish_min = (TextView) view.findViewById(R.id.time_to_finish_min);
            // viewHolder.address_txt = (TextView) view.findViewById(R.id.address_txt);


            viewHolder.tableArray = new ArrayList<TextView>();
            ArrayList<ArrayList<String>> getOrder = arr.get(position).getOrderList();


            viewHolder.getOrderHold = getOrder;

            for (int i = 0; i < arr.get(position).getOrderList().size(); i++) {

                TableRow row = new TableRow(mContext);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                lp.setMargins(2,2,2,2);
                //TextView txt_order = new TextView(mContext);
                AutofitTextView txt_order = new AutofitTextView(mContext);
                //txt_order.setHeight(40);

                // ArrayList<String> it = (ArrayList<String>) getOrder.get(i);

                txt_order.setTypeface(null, Typeface.BOLD);
                txt_order.setTextAlignment(view.TEXT_ALIGNMENT_TEXT_END);
                txt_order.setTextSize(8);
                //txt_order.setTextSize(width / 160);
                // txt_order.setText(it.get(1) + " szt." + it.get(3));

                row.addView(txt_order);

                viewHolder.tableArray.add(txt_order);
                viewHolder.list.addView(row, i);
            }


            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderItem) view.getTag();
        }

        //Log.i("informacja", "arr.get(position).getPaymentWay()" +arr.get(position).getPaymentWay().toString());
        if (arr.get(position).getPaymentWay().toString().equals("GOTÓWKA")) {
            viewHolder.order_fb_payment_method.setText("GOT.");
        }

        if (arr.get(position).getPaymentWay().toString().equals("Karta")) {
            viewHolder.order_fb_payment_method.setText("KART.");
        }
        if (arr.get(position).getPaymentWay().toString().equals("Przelew")) {
            viewHolder.order_fb_payment_method.setText("ZAPł.");
        }
        viewHolder.price.setText(arr.get(position).getTotalPrice() + " zł");
        //viewHolder.order_number.setBackgroundColor(color);
        viewHolder.order_number.setBackgroundTintList(ColorStateList.valueOf(color));

        viewHolder.div0.setBackgroundTintList(ColorStateList.valueOf(color));

       /* viewHolder.order_number.setTextSize(width / scale);
        viewHolder.delivety_method.setTextSize(width / scale);
        viewHolder.hour_of_deliver.setTextSize(width / scale);
        viewHolder.order_fb_payment_method.setTextSize(width / scale);
        viewHolder.price.setTextSize(width / scale);*/


        //viewHolder.order_number.setWidth(width / scale);
        viewHolder.order_number.setText(arr.get(position).getOrderNumber());
        if (arr.get(position).getOrderNumber().equals("0")) {
            viewHolder.order_number.setText(("143").trim());
        }
        viewHolder.delivety_method.setBackgroundTintList(ColorStateList.valueOf(color));
        viewHolder.delivety_method.setText(arr.get(position).getReceiptWay().replace("WŁASNY", ""));

        String[] time = arr.get(position).getDeliveryDate().split(" ");
        viewHolder.hour_of_deliver.setText(time[1].trim());


       // viewHolder.time_to_finish.setTextSize(width / scale);


        // String count = Diffrence_Between_Two_Times.getTimeDifferance(time[1].trim());
        viewHolder.count = Diffrence_Between_Two_Times.twoDatesBetweenTime(arr.get(position).getDeliveryDate());

        viewHolder.time_to_finish.setText(String.valueOf( viewHolder.count));
       if (Integer.parseInt( viewHolder.count) > 0) {
            int timeColor = viewHolder.time_to_finish.getResources().getColor(R.color.color_SALATKI);
            viewHolder.time_to_finish_min.setTextColor(timeColor);
            viewHolder.time_to_finish.setTextColor(timeColor);
        }else{
           viewHolder.time_to_finish.setText(String.valueOf( Math.abs(Integer.parseInt( viewHolder.count))));
       }


        viewHolder.tableArray.clear();
        viewHolder.list.removeAllViews();

        ArrayList<ArrayList<String>> getOrder = arr.get(position).getOrderList();


        for (int i = 0; i < getOrder.size(); i++) {

            TableRow row = new TableRow(mContext);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            lp.setMargins(2,2,2,2);
            AutofitTextView txt_order = new AutofitTextView(mContext);
            // txt_order.setHeight(40);
            //checkBox.setText("hello");

            ArrayList<String> it = (ArrayList<String>) getOrder.get(i);

            //txt_order.setTypeface(null, Typeface.BOLD);
            txt_order.setTextAlignment(view.TEXT_ALIGNMENT_TEXT_END);
            //txt_order.setTextSize(width / (scale + 40));

            txt_order.setTextSize(8);
            txt_order.setText(it.get(1) + " szt." + it.get(3));
            row.addView(txt_order);
            viewHolder.tableArray.add(txt_order);
            viewHolder.list.addView(row, i);


        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle("Potwierdzenie zamówienia");
                // alertDialog.setMessage("Wpisz hasło");
                ListView modeList = new ListView(activity);
                //String[] stringArray = new String[] { "Bright Mode", "Normal Mode" };
                String txt ="";
                ArrayList<ArrayList<String>> getOrder = arr.get(clikPos).getOrderList();
                ordreS = new String[arr.get(clikPos).getOrderList().size()];
                for (int i = 0; i < getOrder.size(); i++) {

                    ArrayList<String> it = (ArrayList<String>) getOrder.get(i);
                    ordreS[i] = it.get(1) + " szt." + it.get(3);

                    txt +=  ordreS[i]+ "\n";

                }

                 String[] stringArray =  ordreS;
                ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(activity, R.layout.payment_methods_row, R.id.payment_method_text, stringArray);
                modeList.setAdapter(modeAdapter);
               // alertDialog.setView(modeList);
                alertDialog.setMessage(txt);

                alertDialog.setPositiveButton("POTWIERDŹ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("informacja", "klik" + DB_ORDER_SERIAL_NUMBER);
                                Intent intent = new Intent(Intent.ACTION_SEND); // it's not ACTION_SEND
                              //  intent.setType("text/plain");
                              //  intent.putExtra(Intent.EXTRA_SUBJECT, "TABU PRZYJECIE ZAMOWIENIA");
                              //  intent.putExtra(Intent.EXTRA_TEXT, "Przyjeliśmy do realizacji zamowienie nr"+arr.get(clikPos).getOrderNumber());
                              //  intent.setData(Uri.parse("mailto:michal.stasinski80@gmail.com")); // or just "mailto:" for blank
                                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                               /* intent.setType("message/rfc822");

                                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"michal.stasinski80@gmail.com"});

                                intent.putExtra(Intent.EXTRA_SUBJECT, "Zamówienie");

                                intent.putExtra(Intent.EXTRA_TEXT, "Przyjeliśmy do realizacji zamowienie nr"+arr.get(clikPos).getOrderNumber());

                                activity.startActivityForResult(Intent.createChooser(intent, "Send Email"),1);*/

                                String to = "ktomasz@me.com";//change accordingly

                                // Sender's email ID needs to be mentioned
                                String from = "michal.stasinski80@gmail.com";//change accordingly
                                final String email = "michal.stasinski80@gmail.com";//change accordingly
                                final String password = "Monte#Video1";//change accordingly

                                // Assuming you are sending email through relay.jangosmtp.net
                                String host = "smtp.gmail.com";

                                Properties props = new Properties();
                                props.put("mail.smtp.auth", "true");
                                props.put("mail.smtp.starttls.enable", "true");
                                props.put("mail.smtp.host", host);
                                props.put("mail.smtp.port", "465");
                                props.put("mail.smtp.socketFactory.port", "465");
                                props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");


                                props.put("mail.smtp.starttls.enable", "true");

                                // Get the Session object.
                                Session session = Session.getInstance(props,
                                        new javax.mail.Authenticator() {
                                            protected PasswordAuthentication getPasswordAuthentication() {
                                                return new PasswordAuthentication(email, password);
                                            }
                                        });

                                try {
                                    // Create a default MimeMessage object.
                                    Message message = new MimeMessage(session);

                                    // Set From: header field of the header.
                                    message.setFrom(new InternetAddress(from));

                                    // Set To: header field of the header.
                                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

                                    // Set Subject: header field
                                    message.setSubject("Wiadomośc Tabu");
                                    Log.i("informacja", "tu zmienie status");
                                    // Now set the actual message
                                    message.setText("Własnie potwierdziłeś zamowienie testow");

                                    // Send message
                                    Transport.send(message);

                                    System.out.println("Próbuje wysłac zamowienie po SMTP bezposrednio z apki bez poczty ....jesli coś dostałeś toz znaczy ze działa");

                                } catch (MessagingException e) {
                                    throw new RuntimeException(e);
                                }


                                //  activity.startActivity(Intent.createChooser(intent , "Choose an Email client :"));

                                // activity.startActivity(intent);
                              //  activity.startActivityForResult(intent,0);
                            }
                        });

                alertDialog.setNegativeButton("ODRZUĆ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("informacja", "klik" + DB_ORDER_SERIAL_NUMBER);
                            }
                        });

                alertDialog.setNeutralButton("ZA CHWILE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("informacja", "klik" + DB_ORDER_SERIAL_NUMBER);
                            }
                        });


                alertDialog.create();
                alertDialog.show();

                 /*   Intent intent = new Intent();

                    Bundle bundle = new Bundle();

                    bundle.putString("receiptWay", arr.get(clikPos).getReceiptWay().replace("WŁASNY", ""));
                    bundle.putString("orderNumber", arr.get(clikPos).getOrderNumber());
                    bundle.putString("price", arr.get(clikPos).getTotalPrice());
                    bundle.putString("status", arr.get(clikPos).getStatus());
                    bundle.putString("orderNo", arr.get(clikPos).getOrderNo());
                    bundle.putInt("position", clikPos);
                    bundle.putInt("color", color);
                    ArrayList<ArrayList<String>> getOrder = arr.get(clikPos).getOrderList();

                    ArrayList<String> iteme = (ArrayList<String>) getOrder.get(0);


                    // intent.putParcelableArrayListExtra("getOrder" , iteme);
                    for (int i = 0; i < getOrder.size(); i++) {

                        bundle.putSerializable("getOrder" + i, iteme);
                    }

                  //  intent.setClass(mContext, OrderZoomPopUp.class);

                    intent.putExtras(bundle);
                    mContext.startActivity(intent);*/


                    /*Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "TABU PRZYJECIE ZAMOWIENIA");
                    intent.putExtra(Intent.EXTRA_TEXT, "Przyjeliśmy do realizacji zamowienie nr");
                    intent.setData(Uri.parse("mailto:michal.stasinski80@gmail.com")); // or just "mailto:" for blank
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                    mContext.startActivity(intent);*/


                // Activity activity = (Activity) mContext;
                //activity.startActivity(intent);
                //  activity.overridePendingTransition(R.anim.from_right, R.anim.to_left);


            }
        });

        return view;
    }

    static class ViewHolderItem {
        //String[] ordreS;
        String count;
        ArrayList<ArrayList<String>> getOrderHold;
        TextView txt_order;
        TextView order_fb_payment_method;
        TextView price;
        TextView time_to_finish_min;
        View div0;
        TextView time_to_finish;
        TextView address_txt;
        TextView order_number;
        TextView delivety_method;
        TextView hour_of_deliver;
        ArrayList<MenuItemProduct> orderList;
        ArrayList<TextView> tableArray;
        ArrayList<String> it;
        LinearLayout list;
    }



}
