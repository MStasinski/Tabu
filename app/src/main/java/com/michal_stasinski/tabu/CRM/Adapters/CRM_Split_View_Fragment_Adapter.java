package com.michal_stasinski.tabu.CRM.Adapters;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michal_stasinski.tabu.CRM.Model.GetOrderFromFB;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.User_Side.Models.MenuItemProduct;
import com.michal_stasinski.tabu.Utils.Diffrence_Between_Two_Times;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import static com.michal_stasinski.tabu.SplashScreen.DB_ORDER_DATABASE;
import static com.michal_stasinski.tabu.SplashScreen.DB_ORDER_SERIAL_DATABASE;
import static com.michal_stasinski.tabu.SplashScreen.DB_ORDER_SERIAL_NUMBER;
import static com.michal_stasinski.tabu.SplashScreen.USER_UNIQUE_ID_PREF;


/**
 * Created by win8 on 27.12.2016.
 */

public class CRM_Split_View_Fragment_Adapter extends BaseAdapter {


    private static LayoutInflater inflater = null;
    private ArrayList<GetOrderFromFB> arr;
    private Context mContext;
    private Activity activity;
    private boolean sortOption;
    private Boolean specialSign;
    private int height;
    private int width;
    private String[] ordreS;
    private int scale = 140;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private int counter = 0; //counter to indicate the total second whenever timer fire

    public Boolean getButton_flag_enabled() {
        return button_flag_enabled;
    }

    public void setButton_flag_enabled(Boolean button_flag_enabled) {
        this.button_flag_enabled = button_flag_enabled;
    }

    private Boolean button_flag_enabled = true;

    public CRM_Split_View_Fragment_Adapter(final Activity activity, Context context, ArrayList<GetOrderFromFB> mListArray) {


        this.activity = activity;
        this.specialSign = specialSign;
        Collections.sort(mListArray, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {


                if (sortOption) {
                    int id1 = Integer.parseInt(((GetOrderFromFB) o1).getDeliveryDate());
                    int id2 = Integer.parseInt(((GetOrderFromFB) o2).getDeliveryDate());

                    if (id1 > id2) {
                        return 1;
                    }
                    if (id1 < id2) {
                        return -1;
                    } else {
                        return 0;
                    }

                } else {
                    String s1 = (((GetOrderFromFB) o1).getDeliveryDate());
                    String s2 = (((GetOrderFromFB) o2).getDeliveryDate());
                    return s1.compareToIgnoreCase(s2);
                }
            }
        });


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


        Log.i("informacja", " adapter getView ");

        View view = convertView;
        ViewHolderItem viewHolder;

        final int clikPos = position;

        if (convertView == null) {
            // ArrayList<Number> price = arr.get(position).getPriceArray();
            view = View.inflate(mContext, R.layout.crm_order_split_view_block, null);
            viewHolder = new ViewHolderItem();
            viewHolder.order_fb_payment_method = (TextView) view.findViewById(R.id.order_fb_payment_method);
            viewHolder.list = (LinearLayout) view.findViewById(R.id.list_of_order_for_one_item);
            viewHolder.order_number = (TextView) view.findViewById(R.id.order_nr);
            viewHolder.hour_of_deliver = (TextView) view.findViewById(R.id.hour_of_deliver);
            viewHolder.time_to_finish = (TextView) view.findViewById(R.id.time_to_finish);
            viewHolder.delivety_method = (TextView) view.findViewById(R.id.delivety_method);
            viewHolder.time_to_finish_min = (TextView) view.findViewById(R.id.time_to_finish_min);
            viewHolder.restLayout = (LinearLayout) view.findViewById(R.id.rest_layout);
            // viewHolder.address_txt = (TextView) view.findViewById(R.id.address_txt);


            viewHolder.tableArray = new ArrayList<TextView>();
            ArrayList<ArrayList<String>> getOrder = arr.get(position).getOrderList();


           /* viewHolder.getOrderHold = getOrder;

            for (int i = 0; i < arr.get(position).getOrderList().size(); i++) {

                TableRow row = new TableRow(mContext);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                row.setLayoutParams(lp);
                lp.setMargins(2, 2, 2, 2);
                //TextView txt_order = new TextView(mContext);
                AutofitTextView txt_order = new AutofitTextView(mContext);
                //txt_order.setHeight(40);

                // ArrayList<String> it = (ArrayList<String>) getOrder.get(i);

                //txt_order.setTypeface(null, Typeface.BOLD);
                txt_order.setTextAlignment(view.TEXT_ALIGNMENT_TEXT_END);
                txt_order.setTextSize(8);
                //txt_order.setTextSize(width / 160);
                // txt_order.setText(it.get(1) + " szt." + it.get(3));

                row.addView(txt_order);

                viewHolder.tableArray.add(txt_order);
                viewHolder.list.addView(row, i);
            }*/


            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolderItem) view.getTag();
        }


        //viewHolder.order_number.setBackgroundColor(color);

        //TODO tu kolorrrrrrrrrrrrrrrrrrrrrrrr

        if (arr.get(position).getStatus().equals("0")) {
            viewHolder.order_number.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.NOWE)));
            viewHolder.delivety_method.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.NOWE)));
        }

        if (arr.get(position).getStatus().equals("1")) {
            viewHolder.order_number.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.PRZYJETE)));
            viewHolder.delivety_method.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.PRZYJETE)));
        }

        if (arr.get(position).getStatus().equals("2")) {
            viewHolder.order_number.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.WREALIZACJI)));
            viewHolder.delivety_method.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.WREALIZACJI)));
        }

        if (arr.get(position).getStatus().equals("3")) {
            if (arr.get(position).getReceiptWay().equals("DOWÓZ")) {
                viewHolder.order_number.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.ODBIOR2)));
                viewHolder.delivety_method.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.ODBIOR2)));
            } else {
                viewHolder.order_number.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.ODBIOR1)));
                viewHolder.delivety_method.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.ODBIOR1)));
            }
        }

        if (arr.get(position).getStatus().equals("4")) {
            viewHolder.order_number.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.ODBIOR3)));
            viewHolder.delivety_method.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.ODBIOR3)));
        }


        viewHolder.order_number.setText(arr.get(position).getOrderNumber());

        viewHolder.order_number.setText(arr.get(position).getOrderNumber());


        viewHolder.delivety_method.setText(arr.get(position).getReceiptWay().replace("WŁASNY", ""));

        String[] time = arr.get(position).getDeliveryDate().split(" ");
        viewHolder.hour_of_deliver.setText(time[1].trim());


        // viewHolder.time_to_finish.setTextSize(width / scale);


        // String count = Diffrence_Between_Two_Times.getTimeDifferance(time[1].trim());
        viewHolder.count = Diffrence_Between_Two_Times.twoDatesBetweenTime(arr.get(position).getDeliveryDate());

        viewHolder.time_to_finish.setText(String.valueOf(viewHolder.count));
        if (Integer.parseInt(viewHolder.count) > 0) {
            int timeColor = viewHolder.time_to_finish.getResources().getColor(R.color.PRZYJETE);
            viewHolder.time_to_finish_min.setTextColor(timeColor);
            viewHolder.time_to_finish.setTextColor(timeColor);
        } else {
            viewHolder.time_to_finish.setText(String.valueOf(Math.abs(Integer.parseInt(viewHolder.count))));
        }



       /* viewHolder.tableArray.clear();
        viewHolder.list.removeAllViews();

        ArrayList<ArrayList<String>> getOrder = arr.get(position).getOrderList();


        for (int i = 0; i < getOrder.size(); i++) {

            TableRow row = new TableRow(mContext);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            lp.setMargins(2, 2, 2, 2);
            TextView txt_order = new TextView(mContext);

            txt_order.setTextColor(Color.rgb(85, 85, 85));
            //checkBox.setText("hello");

            ArrayList<String> it = (ArrayList<String>) getOrder.get(i);
            txt_order.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/AvenirNext-DemiBold.ttf"));
            // txt_order.setTypeface(null, Typeface.BOLD);
            txt_order.setTextAlignment(view.TEXT_ALIGNMENT_TEXT_END);

            txt_order.setTextSize(TypedValue.COMPLEX_UNIT_PX, activity.getResources().getDimension(R.dimen.LISTA));

            txt_order.setText(it.get(1) + " szt." + it.get(3));
            row.addView(txt_order);
            viewHolder.tableArray.add(txt_order);
            viewHolder.list.addView(row, i);


        }*/


        viewHolder.restLayout.setOnClickListener(new View.OnClickListener() {
                                                     @Override
                                                     public void onClick(View arg0) {
                                                         Log.i("informacja", "kliker");

                                                         arg0.setBackgroundTintList(ColorStateList.valueOf(activity.getResources().getColor(R.color.ODBIOR2)));
                                                     }
                                                 });

        viewHolder.order_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if (arr.get(clikPos).getStatus().equals("0")) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                    alertDialog.setTitle("Potwierdzenie zamówienia");
                    ListView modeList = new ListView(activity);
                    String txt = "";
                    ArrayList<ArrayList<String>> getOrder = arr.get(clikPos).getOrderList();
                    ordreS = new String[arr.get(clikPos).getOrderList().size()];
                    for (int i = 0; i < getOrder.size(); i++) {

                        ArrayList<String> it = (ArrayList<String>) getOrder.get(i);
                        ordreS[i] = it.get(1) + " szt." + it.get(3);
                        txt += ordreS[i] + "\n";

                    }

                    String[] stringArray = ordreS;
                    ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(activity, R.layout.payment_methods_row, R.id.payment_method_text, stringArray);
                    modeList.setAdapter(modeAdapter);
                    // alertDialog.setView(modeList);
                    alertDialog.setMessage(txt);

                    alertDialog.setPositiveButton("POTWIERDŹ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

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

                                    String to = "michal.stasinski80@gmail.com";//change accordingly

                                    // Sender's email ID needs to be mentioned

                                    //String from = "michal.stasinski80@gmail.com";//change accordingly
                                    // final String email = "michal.stasinski80@gmail.com";//change accordingly
                                    //  final String password = "Monte#Video1";//change accordingly


                                    String from = "michal.stasinski@poczta.fm";//change accordingly
                                    final String email = "michal.stasinski@poczta.fm";//change accordingly
                                    final String password = "bosfor1234";//change accordingly

                                    // Assuming you are sending email through relay.jangosmtp.net
                                    //String host = "smtp.gmail.com";
                                    String host = "smtp.poczta.fm";


                                    Properties props = new Properties();
                                    props.put("mail.smtp.auth", "true");
                                    props.put("mail.smtp.starttls.enable", "true");
                                    props.put("mail.smtp.host", host);
                                    props.put("mail.smtp.port", "465");
                                    props.put("mail.smtp.socketFactory.port", "465");
                                    props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                                    props.put("mail.smtp.starttls.enable", "true");

                                    // Get the Session object.
                                    Session session = Session.getInstance(props,
                                            new javax.mail.Authenticator() {
                                                protected PasswordAuthentication getPasswordAuthentication() {
                                                    return new PasswordAuthentication(email, password);
                                                }
                                            });

                                    try {
                                        DatabaseReference mDatabase;
                                        String databaseName = DB_ORDER_DATABASE;
                                        String databaseName1 = DB_ORDER_SERIAL_DATABASE;
                                        String uniqueId = USER_UNIQUE_ID_PREF;
                                        mDatabase = FirebaseDatabase.getInstance().getReference();

                                        mDatabase.child(databaseName).child(arr.get(clikPos).getOrderNo()).child("orderStatus").setValue("1");
                                        int orNum = Integer.parseInt(DB_ORDER_SERIAL_NUMBER) + 1;
                                        mDatabase.child(databaseName).child(arr.get(clikPos).getOrderNo()).child("orderNumber").setValue(String.valueOf(orNum));
                                        mDatabase.child(databaseName1).child("numer").child("nr").setValue(orNum);
                                        final ProgressDialog pd = new ProgressDialog(activity);

                                        // Set progress dialog style spinner
                                        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                                        // Set the progress dialog title and message
                                        pd.setTitle("POTWIERDZENIE ZAMÓWIENIA.");
                                        pd.setMessage("Wysyłam.........");

                                        // Set the progress dialog background color
                                        pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));

                                        pd.setIndeterminate(false);

                                        // Finally, show the progress dialog
                                        pd.show();
                                        // Set the progress status zero on each button click
                                        progressStatus = 0;

                                        // Start the lengthy operation in a background thread
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                while (progressStatus < 100) {
                                                    // Update the progress status
                                                    progressStatus += 1;

                                                    // Try to sleep the thread for 20 milliseconds
                                                    try {
                                                        Thread.sleep(20);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }

                                                    // Update the progress bar
                                                    handler.post(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            // Update the progress status
                                                            pd.setProgress(progressStatus);
                                                            // If task execution completed
                                                            if (progressStatus == 100) {
                                                                Toast.makeText(activity, "Zamówienie zostało PRZYJĘTE.", Toast.LENGTH_LONG).show();
                                                                pd.dismiss();
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        }).start(); // Start the operation

                                        // Create a default MimeMessage object.
                                        Message message = new MimeMessage(session);

                                        // Set From: header field of the header.
                                        message.setFrom(new InternetAddress(from));

                                        // Set To: header field of the header.
                                        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

                                        // Set Subject: header field
                                        message.setSubject("Wiadomośc Tabu");

                                        // Now set the actual message
                                        message.setText("Potierdzenie zamówienia " + orNum);

                                        // Send message
                                        Transport.send(message);


                                    } catch (MessagingException e) {
                                        throw new RuntimeException(e);
                                    }

                                }
                            });

                    alertDialog.setNegativeButton("ODRZUĆ",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.i("informacja", "klik" + DB_ORDER_SERIAL_NUMBER);
                                    Toast.makeText(activity, "Zamówienie zostało ODRZUCONE.", Toast.LENGTH_LONG).show();
                                }
                            });

                    alertDialog.setNeutralButton("ZA CHWILE",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.i("informacja", "klik" + DB_ORDER_SERIAL_NUMBER);
                                    Toast.makeText(activity, "Zamówienie czeka na potwierdzenie przyjęcia", Toast.LENGTH_LONG).show();
                                }
                            });

                    alertDialog.create();
                    alertDialog.show();
                }
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
        LinearLayout restLayout;
    }


}
