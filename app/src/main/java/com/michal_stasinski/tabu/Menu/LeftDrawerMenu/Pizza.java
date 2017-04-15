package com.michal_stasinski.tabu.Menu.LeftDrawerMenu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.RelativeLayout;

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
        stub.setLayoutResource(R.layout.left_pizza);
        View inflated = stub.inflate();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("info","start____________________________________________"+choicetActivity);
        mListViewMenu = (BounceListView) findViewById(R.id.mListView_BaseMenu);
        loadFireBaseData("Pizza",true);
    }
}
