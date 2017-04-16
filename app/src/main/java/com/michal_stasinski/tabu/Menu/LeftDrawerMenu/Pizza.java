package com.michal_stasinski.tabu.Menu.LeftDrawerMenu;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;
import com.michal_stasinski.tabu.Menu.BaseMenu;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

public class Pizza extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_menu);
        currentActivity = 1;
        choicetActivity = 1;
        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.left_header_and_bounce_list_view);
        View inflated = stub.inflate();
        TextView addonText = (TextView) findViewById(R.id.addonText);
        addonText.setText("Do każdej pizzy jeden sos czosnkowy gratis!");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        loadFireBaseData("Pizza", true);
    }
}
