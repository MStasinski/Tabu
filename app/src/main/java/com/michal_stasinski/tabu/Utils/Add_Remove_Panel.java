package com.michal_stasinski.tabu.Utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.michal_stasinski.tabu.Menu.OrderComposer;
import com.michal_stasinski.tabu.R;

/**
 * Created by win8 on 05.06.2017.
 */

public class Add_Remove_Panel extends LinearLayout {
    private LayoutInflater inflater;
    private int num_value = 1;
    public int getNum_value() {
        return num_value;
    }

    public void setNum_value(int num_value) {
        this.num_value = num_value;
    }

    public Add_Remove_Panel(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
        init();
    }

    public Add_Remove_Panel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflater = LayoutInflater.from(context);
        init();
    }

    public Add_Remove_Panel(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflater = LayoutInflater.from(context);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Add_Remove_Panel(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        inflater.inflate(R.layout.fragment_order_compositor_add_remove_panel, this, true);

        Button add_btn = (Button) findViewById(R.id.order_compositor_addItem);
        Button remove_btn = (Button) findViewById(R.id.order_compositor_removeItem);
        final TextView num = (TextView) findViewById(R.id.order_compositor_quantity_num);

         num.setText(String.valueOf(num_value));

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num_value++;
                num.setText(String.valueOf(num_value));
                Intent inte = new Intent();
            }
        });

        remove_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num_value>1) {
                    num_value--;
                }
                num.setText(String.valueOf(num_value));
            }
        });

    }


}
