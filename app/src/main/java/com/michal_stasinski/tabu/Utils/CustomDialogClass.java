package com.michal_stasinski.tabu.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.michal_stasinski.tabu.R;

/**
 * Created by win8 on 25.06.2017.
 */

public class CustomDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes;

    public String getTitleDialogText() {
        return titleDialogText;
    }

    public void setTitleDialogText(String titleDialogText) {
        this.titleDialogText = titleDialogText;
        TextView titleText = (TextView) findViewById(R.id.title_text_dialog);
        titleText.setText( this.titleDialogText);
    }

    public String getDescDialogText() {
        return descDialogText;
    }

    public void setDescDialogText(String descDialogText) {
        this.descDialogText = descDialogText;

        TextView desctitleText = (TextView) findViewById(R.id.desc_text_dialog);
        desctitleText.setText(this.descDialogText);
    }

    private String titleDialogText;
    private String descDialogText;
    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.alert_dialog_ok);
        yes = (Button) findViewById(R.id.btn_yes);
        yes.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                dismiss();
                break;
            default:
                break;
        }

    }
}