package com.michal_stasinski.tabu.CRM;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.michal_stasinski.tabu.CRM.Adapters.CRM_MainMenu_ListViewAdapter;
import com.michal_stasinski.tabu.Menu.Models.MenuItemProduct;
import com.michal_stasinski.tabu.Menu.Models.OrderComposerItem;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

import java.util.ArrayList;

public class CRM_MainMenu_ListView extends AppCompatActivity {

    protected BounceListView main_list_view;
    private ArrayList<MenuItemProduct> menuArrayList;
    private String[] menuText = {
            "Powiadomienia",
            "Nowy Post",
            "Zamówienia",
            "Zamówienia zakończone",
            "Czas realizacji",
            "Wyloguj"
    };
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crm_main_menu_manager);

        main_list_view = (BounceListView) findViewById(R.id.crm_listView_menu);
        CRM_MainMenu_ListViewAdapter arrayAdapter = new CRM_MainMenu_ListViewAdapter(this);

        for (int i = 0; i <menuText.length ; i++) {
            OrderComposerItem item = new OrderComposerItem();
            item.setTitle(menuText[i]);
            arrayAdapter.addItem(item);
        }


        main_list_view.setAdapter(arrayAdapter);
        main_list_view.setScrollingCacheEnabled(false);

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
