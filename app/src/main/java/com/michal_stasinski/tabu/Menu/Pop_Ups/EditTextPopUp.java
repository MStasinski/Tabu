package com.michal_stasinski.tabu.Menu.Pop_Ups;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.michal_stasinski.tabu.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by win8 on 28.05.2017.
 */

public class EditTextPopUp extends Activity {


    private static Context contex;
    private int pos;
    private String actualText;
    private String title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        contex = this;
        setContentView(R.layout.edit_text_popup);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * 0.8));

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            pos = bundle.getInt("position");
            title = bundle.getString("title");
            actualText = bundle.getString("actualText");
            TextView titleTxt = (TextView) findViewById(R.id.title_edit_popup);
            titleTxt.setText(title.toUpperCase());
        }

        EditText editText = (EditText) findViewById(R.id.edit_text_popup);
        editText.setText(actualText);
        editText.setSelection(editText.getText().length());

        if (pos == 1) {
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        }
        if (pos == 2) {
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        }

        if (pos == 3) {
            editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }

        if (pos == 4) {
            editText.setInputType(InputType.TYPE_CLASS_PHONE);
        }

        if (pos == 8) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        if (pos == 9) {
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        Button saveButton = (Button) findViewById(R.id.btm_save);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText editText = (EditText) findViewById(R.id.edit_text_popup);
                Boolean isValidate = false;

                if (pos == 1) {

                    isValidate = (Boolean) validateFirstName(editText.getText().toString().trim());
                    if (editText.getText().length() < 2) {
                        isValidate = false;
                    }
                }

                if (pos == 2) {
                    isValidate = (Boolean) validateLastName(editText.getText().toString().trim());
                    if (editText.getText().length() < 2) {
                        isValidate = false;
                    }
                }
                if (pos == 3) {
                    isValidate = (Boolean) isValidEmail(editText.getText().toString().trim());
                }

                if (pos == 4) {
                    isValidate = (Boolean) isValidPhone(editText.getText().toString().trim());
                }

                if (pos == 6) {
                    isValidate = (Boolean) validateLastName(editText.getText().toString().trim());
                    if (editText.getText().length() < 2) {
                        isValidate = false;
                    }


                }
                if (pos == 7) {
                    isValidate = true;
                    if (editText.getText().length() < 2) {
                        isValidate = false;
                    }


                }

                if (pos == 8) {
                    if (editText.getText().length() > 0 && validatNr(editText.getText().toString().trim())) {
                        isValidate = true;
                    }
                }

                if (pos == 9) {

                    if (editText.getText().length() > 0 && validatNr(editText.getText().toString().trim())) {
                        isValidate = true;
                    }
                }

                if (pos == 10) {

                    if (editText.getText().length() > 0 && validatNr(editText.getText().toString().trim())) {
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

                if (pos == 13) {

                    if (editText.getText().length() > 0) {
                        isValidate = true;
                    }

                }

                if (pos == 14) {

                    if (editText.getText().length() > 3) {
                        isValidate = true;
                    }

                }

                if (isValidate == false && editText.getText().length() > 0) {

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
                    Log.i("informacja", " edit editText.getText().toString()  " + editText.getText().toString());
                    intent.putExtra("edit_text", editText.getText().toString().trim());
                    intent.putExtra("pos", pos);
                    setResult(Activity.RESULT_OK, intent);

                    finish();
                }

            }
        });

    }

    public static boolean validateFirstName(String firstName) {
        return firstName.matches("[A-Z][a-z]+( [A-Z][a-z]+)*");
    } // end method validateFirstName

    // validate last name
    public static boolean validateLastName(String lastName) {
        return lastName.matches("[A-Z][a-z]+( [A-Z][a-z]+)*");
        // return lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
    } // end method validateLastName

    public static boolean validateAddress(String lastName) {
        return lastName.matches("[a-zA-Z0-9-]*");
        // return lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
    } // e

    public static boolean validatNr(String lastName) {
        return lastName.matches("^[+]?[0-9]{1,13}$*");
        // return lastName.matches("[a-zA-z]+([ '-][a-zA-Z]+)*");
    } // end method validateLastName

    public static boolean isValidPhone(String phone) {
        String expression = "^([0-9\\+]|\\(\\d{1,3}\\))[0-9\\-\\. ]{3,15}$";
        CharSequence inputString = phone;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputString);
        return matcher.matches();

    }

    /*public static boolean validateAddress(String address) {
        return address.matches(
                "\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
    } */

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
