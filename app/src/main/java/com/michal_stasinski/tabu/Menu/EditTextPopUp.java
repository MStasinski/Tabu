package com.michal_stasinski.tabu.Menu;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.CustomTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by win8 on 28.05.2017.
 */

public class EditTextPopUp extends Activity {


    private static Context contex;
    private int pos;
    private String title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        contex = this;
        setContentView(R.layout.edit_text_popup);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.widthPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * 0.8));

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            pos = bundle.getInt("position");
            title = bundle.getString("title");
            CustomTextView titleTxt = (CustomTextView) findViewById(R.id.title_edit_popup);
            titleTxt.setText(title.toUpperCase());
        }

        Button saveButton = (Button) findViewById(R.id.btm_save);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText editText = (EditText) findViewById(R.id.edit_text_popup);
                Boolean isValidate = false;

                if (pos == 1) {
                    isValidate = (Boolean) validateFirstName(editText.getText().toString());

                    Log.i("informacja", "isValidate " + isValidate + " " + pos);
                }

                if (pos == 2) {
                    isValidate = (Boolean) validateLastName(editText.getText().toString());
                    Log.i("informacja", "isValidate " + isValidate + " " + pos);
                }
                if (pos == 3) {
                    isValidate = (Boolean) isValidEmail(editText.getText().toString());
                    Log.i("informacja", "isValidate " + isValidate + " " + pos);
                }

                if (pos == 4) {
                    isValidate = (Boolean) isValidPhone(editText.getText().toString());
                    Log.i("informacja", "isValidate " + isValidate + " " + pos);

                }

                if (pos == 6 || pos == 7) {
                    isValidate = (Boolean) validateLastName(editText.getText().toString());
                    Log.i("informacja", "isValidate " + isValidate + " " + pos);
                    if (editText.getText().length() < 2) {
                        isValidate = false;
                    }


                }


                if (pos == 8) {
                    if (editText.getText().length() > 0) {
                        isValidate = true;
                    }

                }

                if (pos == 9) {

                    if (editText.getText().length() > 0) {
                        isValidate = true;
                    }

                }

                if (pos == 10) {

                    if (editText.getText().length() > 0) {
                        isValidate = true;
                    }

                }
                if (pos == 11) {

                    if (editText.getText().length() > 0) {
                        isValidate = true;
                    }

                }

                if (pos == 12) {

                    if (editText.getText().length() > 0) {
                        isValidate = true;
                    }

                }

                if (isValidate == false) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EditTextPopUp.this);
                    alertDialogBuilder.setTitle("UWAGA");
                    alertDialogBuilder
                            .setMessage("Wpisz poprawnie " + title.toLowerCase() + ".")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // if this button is clicked, close
                                    // current activity

                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();


                } else {


                    Intent intent = new Intent();
                    intent.putExtra("edit_text", editText.getText().toString());
                    intent.putExtra("pos", pos);
                    setResult(Activity.RESULT_OK, intent);

                    finish();
                }

            }
        });

    }

    public static boolean validateFirstName(String firstName) {
        return firstName.matches("[A-Z][a-zA-Z]*");
    } // end method validateFirstName

    // validate last name
    public static boolean validateLastName(String lastName) {
        return lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
    } // end method validateLastName

    public static boolean isValidPhone(String phone) {
        String expression = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{3,15}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        return matcher.matches();

    }

    public static boolean validateAddress(String address) {
        return address.matches(
                "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
    } //

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
