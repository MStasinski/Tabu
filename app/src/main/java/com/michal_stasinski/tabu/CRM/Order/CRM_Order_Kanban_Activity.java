package com.michal_stasinski.tabu.CRM.Order;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michal_stasinski.tabu.CRM.Fragments.Crm_Kanban_Fragment;
import com.michal_stasinski.tabu.CRM.Fragments.Crm_SplitView_Fragment;
import com.michal_stasinski.tabu.R;

import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.michal_stasinski.tabu.SplashScreen.DB_ORDER_SERIAL_DATABASE;
import static com.michal_stasinski.tabu.SplashScreen.DB_ORDER_SERIAL_NUMBER;

public class CRM_Order_Kanban_Activity extends AppCompatActivity implements Crm_SplitView_Fragment.SplitViewFragmentInteractionListener {
    private Crm_Kanban_Fragment fragment;
    private Crm_SplitView_Fragment fragment1;

    public static Boolean btn0_is_mark = false;
    public static Boolean btn1_is_mark = false;
    public static Boolean btn2_is_mark = false;
    public static Boolean btn3_is_mark = false;
    public static Boolean btn4_is_mark = false;
    // private Handler handler;
    //private int AFTER_ONE_MINUTE;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeStaffLogged);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.crm_order_kanban_main_view);

        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("AvenirNext-DemiBold.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );


        ButtonBarLayout closeButton = (ButtonBarLayout) findViewById(R.id.bClose);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.from_left, R.anim.to_right);

            }
        });

        FragmentManager fragmentManager = getFragmentManager();
        fragment = Crm_Kanban_Fragment.newInstance(0);
        getSupportFragmentManager().beginTransaction().replace(R.id.crm_fragment_contener, fragment).commit();


        ButtonBarLayout noweButton = (ButtonBarLayout) findViewById(R.id.news_btn);


        ButtonBarLayout przyjeteButton = (ButtonBarLayout) findViewById(R.id.commit_btn);
        ButtonBarLayout realizacjaButton = (ButtonBarLayout) findViewById(R.id.realization_btn);
        ButtonBarLayout gotoweButton = (ButtonBarLayout) findViewById(R.id.reception_btn);
        ButtonBarLayout dostawaButton = (ButtonBarLayout) findViewById(R.id.transport_btn);

        resetButtons();


        noweButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView nowe_text = (TextView) findViewById(R.id.nowe_txt);
                nowe_text.setTextColor(getResources().getColor(R.color.colorSecondGrey));

                Log.i("informacja", "   click " + btn0_is_mark);
                if (!btn0_is_mark) {
                    nowe_text.setTextColor(getResources().getColor(R.color.colorDarkGray));
                }
                FragmentManager fragmentManager = getFragmentManager();
                fragment1 = Crm_SplitView_Fragment.newInstance(0);
                getSupportFragmentManager().beginTransaction().replace(R.id.crm_fragment_contener, fragment1).commit();

                btn0_is_mark = !btn0_is_mark;
            }
        });


        przyjeteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView przyjete_text = (TextView) findViewById(R.id.przyjete_txt);
                przyjete_text.setTextColor(getResources().getColor(R.color.colorSecondGrey));

                Log.i("informacja", "   click " + btn1_is_mark);
                if (!btn1_is_mark) {

                    przyjete_text.setTextColor(getResources().getColor(R.color.colorDarkGray));
                }

                FragmentManager fragmentManager = getFragmentManager();
                fragment1 = Crm_SplitView_Fragment.newInstance(0);
                getSupportFragmentManager().beginTransaction().replace(R.id.crm_fragment_contener, fragment1).commit();
                btn1_is_mark = !btn1_is_mark;
            }
        });

        realizacjaButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView wrealizacji_text = (TextView) findViewById(R.id.wrealizacji_txt);
                wrealizacji_text.setTextColor(getResources().getColor(R.color.colorSecondGrey));

                Log.i("informacja", "   click " + btn2_is_mark);
                if (!btn2_is_mark) {

                    wrealizacji_text.setTextColor(getResources().getColor(R.color.colorDarkGray));
                }

                FragmentManager fragmentManager = getFragmentManager();
                fragment1 = Crm_SplitView_Fragment.newInstance(0);
                getSupportFragmentManager().beginTransaction().replace(R.id.crm_fragment_contener, fragment1).commit();
                btn2_is_mark = !btn2_is_mark;
            }
        });

        gotoweButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView gotowe_text = (TextView) findViewById(R.id.gotowe_txt);
                gotowe_text.setTextColor(getResources().getColor(R.color.colorSecondGrey));

                Log.i("informacja", "   click " + btn3_is_mark);

                if (!btn3_is_mark) {
                    gotowe_text.setTextColor(getResources().getColor(R.color.colorDarkGray));
                }

                FragmentManager fragmentManager = getFragmentManager();
                fragment1 = Crm_SplitView_Fragment.newInstance(0);
                getSupportFragmentManager().beginTransaction().replace(R.id.crm_fragment_contener, fragment1).commit();
                btn3_is_mark = !btn3_is_mark;
            }
        });

        dostawaButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                TextView wdostawie_text = (TextView) findViewById(R.id.wdostawie_txt);
                wdostawie_text.setTextColor(getResources().getColor(R.color.colorSecondGrey));

                if (!btn4_is_mark) {

                    wdostawie_text.setTextColor(getResources().getColor(R.color.colorDarkGray));
                }

                FragmentManager fragmentManager = getFragmentManager();
                fragment1 = Crm_SplitView_Fragment.newInstance(0);
                getSupportFragmentManager().beginTransaction().replace(R.id.crm_fragment_contener, fragment1).commit();
                btn4_is_mark = !btn4_is_mark;
            }
        });


    }

    public void resetButtons() {

        btn0_is_mark = false;
        btn1_is_mark = false;
        btn2_is_mark = false;
        btn3_is_mark = false;
        btn4_is_mark = false;

        TextView nowe_text = (TextView) findViewById(R.id.nowe_txt);
        nowe_text.setTextColor(getResources().getColor(R.color.colorSecondGrey));

        TextView przyjete_text = (TextView) findViewById(R.id.przyjete_txt);
        przyjete_text.setTextColor(getResources().getColor(R.color.colorSecondGrey));

        TextView wrealizacji_text = (TextView) findViewById(R.id.wrealizacji_txt);
        wrealizacji_text.setTextColor(getResources().getColor(R.color.colorSecondGrey));

        TextView gotowe_text = (TextView) findViewById(R.id.gotowe_txt);
        gotowe_text.setTextColor(getResources().getColor(R.color.colorSecondGrey));

        TextView wdostawie_text = (TextView) findViewById(R.id.wdostawie_txt);
        wdostawie_text.setTextColor(getResources().getColor(R.color.colorSecondGrey));
    }

    @Override
    protected void onDestroy() {
        //  handler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }


    public void load_order_serial_number() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(DB_ORDER_SERIAL_DATABASE);
        // DatabaseReference myRef = database.getReference("Orders");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot item : dataSnapshot.getChildren()) {
                    DataSnapshot dataitem = item;
                    Map<String, Object> map = (Map<String, Object>) dataitem.getValue();
                    DB_ORDER_SERIAL_NUMBER = (String) map.get("nr").toString();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void messageFromSplitViewFragmentToActivity(String myString) {
        resetButtons();
    }
}
