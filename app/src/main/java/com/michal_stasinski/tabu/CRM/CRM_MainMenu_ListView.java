package com.michal_stasinski.tabu.CRM;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.michal_stasinski.tabu.CRM.Adapters.CRM_MainMenu_ListViewAdapter;
import com.michal_stasinski.tabu.CRM.Order.CRM_Order_Kanban;
import com.michal_stasinski.tabu.CRM.Order.CRM_Order_Kanban_MainView;
import com.michal_stasinski.tabu.MainActivity;
import com.michal_stasinski.tabu.User_Side.LeftDrawerMenu.MenuFragment;
import com.michal_stasinski.tabu.User_Side.Models.MenuItemProduct;
import com.michal_stasinski.tabu.User_Side.Models.OrderComposerItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import java.util.ArrayList;

import static com.michal_stasinski.tabu.SplashScreen.DATA_FOR_DELIVERY;
import static com.michal_stasinski.tabu.SplashScreen.IS_LOGGED_IN;

public class CRM_MainMenu_ListView extends AppCompatActivity {

    protected BounceListView main_list_view;
    private ArrayList<MenuItemProduct> menuArrayList;
    private String[] menuText;

    @Override


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.crm_main_menu_manager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ButtonBarLayout closeButton = (ButtonBarLayout) findViewById(R.id.bClose);
        TextView textTitle = (TextView) findViewById(R.id.crm_title_menu);
        textTitle.setText("ZARZĄDZANIE");
        if (IS_LOGGED_IN) {
            closeButton.setVisibility(View.INVISIBLE);

            menuText = new String[]{"Powiadomienia",
                    "Nowy Post",
                    "Zamówienia",
                    "Zamówienia zakończone",
                    "Czas realizacji",
                    "Wyloguj",

            };
        } else {
            closeButton.setVisibility(View.VISIBLE);
            menuText = new String[]{
                    "Zaloguj"
            };
        }


        main_list_view = (BounceListView) findViewById(R.id.crm_listView_menu);
        CRM_MainMenu_ListViewAdapter arrayAdapter = new CRM_MainMenu_ListViewAdapter(this);

        for (int i = 0; i < menuText.length; i++) {
            OrderComposerItem item = new OrderComposerItem();
            item.setTitle(menuText[i]);
            arrayAdapter.addItem(item);
        }


        main_list_view.setAdapter(arrayAdapter);
        main_list_view.setScrollingCacheEnabled(false);

        main_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Object listItem = main_list_view.getItemAtPosition(position);

                Intent intent = new Intent();
                //main_list_view.setOnItemClickListener(null);

                if (!IS_LOGGED_IN) {
                    if (position == 0) {
                        SharedPreferences prefs = getSharedPreferences(DATA_FOR_DELIVERY, MODE_PRIVATE);
                        MenuFragment.isTeamMember(prefs, CRM_MainMenu_ListView.this);
                    }
                }

                if (IS_LOGGED_IN) {

                    if (position == 2) {
                        main_list_view.setOnItemClickListener(null);
                        intent.setClass(view.getContext(), CRM_Order_Kanban_MainView.class);
                        startActivity(intent);
                    }
                    if (position == 5) {
                        main_list_view.setOnItemClickListener(null);
                        IS_LOGGED_IN = false;
                        Toast.makeText(CRM_MainMenu_ListView.this, "Zostałeś wylogowany", Toast.LENGTH_SHORT).show();
                        intent.setClass(view.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }

            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.from_left, R.anim.to_right);

            }
        });

    }
}
