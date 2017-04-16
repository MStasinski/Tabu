package com.michal_stasinski.tabu.Menu.LeftDrawerMenu;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import com.michal_stasinski.tabu.Menu.BaseMenu;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;

public class Salad extends BaseMenu {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_menu);

        ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.left_bounce_list_view);
        View inflated = stub.inflate();

        currentActivity = 2;
        choicetActivity = 2;
    }

    @Override
    protected void onStart() {
        super.onStart();
        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        loadFireBaseData("Salad", true);
    }
}
