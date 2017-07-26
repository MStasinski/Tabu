package com.michal_stasinski.tabu.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import static com.michal_stasinski.tabu.SplashScreen.IS_LOGGED_IN;
import static com.michal_stasinski.tabu.SplashScreen.IS_STAFF_MEMBER;
import static com.michal_stasinski.tabu.SplashScreen.staffTeamArray;

/**
 * Created by win8 on 21.07.2017.
 */

public class Do_you_belong_to_staff extends Activity {
    private String _email;
    private String _firstname;
    private String _password;
    private String _phone;
    private String _surname;
    private String _userId;


    public Do_you_belong_to_staff(final Activity activity, String firstname, String surname, String email, String phone) {

        _firstname = firstname;
        _surname = surname;
        _email = email;
        _phone = phone;

        for (int i = 0; i < staffTeamArray.size(); i++) {

            String fn = staffTeamArray.get(i).getFirstname().toString();
            String sn = staffTeamArray.get(i).getSurname().toString();
            String em = staffTeamArray.get(i).getEmail().toString();
            String ph = staffTeamArray.get(i).getPhone().toString();
            IS_STAFF_MEMBER = false;
            IS_LOGGED_IN = false;
            if (_firstname.equals(fn) && _surname.equals(sn) && _email.equals(em) && _phone.equals(ph)) {

                Log.i("informacja", "przeszło 2");
                Log.i("informacja", _firstname + " firstname " + fn);
                Log.i("informacja", _surname + " surname " + sn);
                Log.i("informacja", _email + " email " + em);
                Log.i("informacja", _phone + " phone " + ph);

                IS_STAFF_MEMBER = true;
                _password = staffTeamArray.get(i).getPassword().toString();
                Log.i("informacja", " _IS_STAFF_MEMBER inside  " + IS_STAFF_MEMBER);

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle("Witaj " + firstname + " " + surname);
                alertDialog.setMessage("Wpisz hasło");

               // AlertDialog dialog = alertDialog.create();
                //dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


                final EditText input = new EditText(activity);

              //  activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
               // InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.getBaseContext().INPUT_METHOD_SERVICE);
               // imm.showSoftInput(input, InputMethodManager.SHOW_IMPLICIT);



                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                input.setLayoutParams(lp);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                alertDialog.setView(input);

               // alertDialog.show();

                alertDialog.setPositiveButton("TAK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String pass = input.getText().toString();

                                IS_STAFF_MEMBER = true;
                                if (pass.compareTo("") != 0) {
                                    if (pass.equals(_password)) {
                                        Toast.makeText(activity, "Jesteś zalogowany", Toast.LENGTH_SHORT).show();
                                        IS_LOGGED_IN = true;
                                        activity.recreate();


                                    } else {
                                        IS_LOGGED_IN = false;
                                        Toast.makeText(activity, "Nieprawidłowe hasło!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {

                                }
                            }
                        });

                alertDialog.setNegativeButton("NIE",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                IS_STAFF_MEMBER = true;
                                IS_LOGGED_IN = false;
                                dialog.cancel();
                            }
                        });


                AlertDialog alert=alertDialog.create();
                alert.getWindow().clearFlags( WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

                alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                alert.show();



            }


        }

    }
}
