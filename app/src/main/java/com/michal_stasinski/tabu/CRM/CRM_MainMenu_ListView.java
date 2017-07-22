package com.michal_stasinski.tabu.CRM;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.michal_stasinski.tabu.CRM.Adapters.CRM_MainMenu_ListViewAdapter;
import com.michal_stasinski.tabu.MainActivity;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.Menu.Models.OrderComposerItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import java.util.ArrayList;

import static com.michal_stasinski.tabu.SplashScreen.IS_LOGGED_IN;

public class CRM_MainMenu_ListView extends AppCompatActivity {

    protected BounceListView main_list_view;
    private ArrayList<MenuItemProduct> menuArrayList;
    private String[] menuText;

    @Override


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        if (IS_LOGGED_IN) {
            menuText = new String[]{"Powiadomienia",
                    "Nowy Post",
                    "Zamówienia",
                    "Zamówienia zakończone",
                    "Czas realizacji",
                    "Wyloguj",

            };
        } else {
            menuText = new String[]{
                    "Informacje",
                    "Regulamin",
                    "Coś tam cośtam",


            };
        }
        setContentView(R.layout.crm_main_menu_manager);

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
                main_list_view.setOnItemClickListener(null);
                if (position == 5) {

                    IS_LOGGED_IN = false;
                    Toast.makeText(CRM_MainMenu_ListView.this, "Zostałeś wylogowany", Toast.LENGTH_SHORT).show();
                    intent.setClass(view.getContext(), MainActivity.class);
                    startActivity(intent);
                }

            }
        });


        Button closeButton = (Button) findViewById(R.id.closeBtn);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.from_left, R.anim.to_right);

            }
        });

    }

}
