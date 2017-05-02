package com.michal_stasinski.tabu.Menu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.michal_stasinski.tabu.Menu.Adapters.OrderComposerListViewAdapter;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.CustomTextView;

import static com.michal_stasinski.tabu.SplashScreen.pizzaList;
import static com.michal_stasinski.tabu.Menu.AddonsPopUp.addonsPopUpAdapter;
import static com.michal_stasinski.tabu.Menu.SaucePopUp.saucePopUpAdapter;

/**
 * Created by  Michał Stasińskion 27.12.2016.
 * The user composes the order - selects the size of the pizza add-ons and sauces
 */

public class OrderComposerFragment extends Fragment {

    private View myView;
    private int itemPositionInMenuListView;
    private OrderComposerListViewAdapter adapter;
    private static int size = 0;
    private int quantity=1;
    private String[] titleText = {
            "Rozmiar",
            "Dodatki",
            "Dodatkowy sos",
            "Uwagi",
            "addRemoveButton"
    };
    private String[] descText = {
            "30 cm",
            "Wybierz dodatki",
            "Wybierz dodatkowy sos",
            "Dodaj swoje uwagi",
            "addRemoveButton"
    };

    private float sum = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_order_compositor, container, false);

        String name;
        String price;
        String desc;

        CustomTextView title = (CustomTextView) myView.findViewById(R.id.order_composer_positionInList);
        CustomTextView nameTxt = (CustomTextView) myView.findViewById(R.id.order_composer_titleItem);
        CustomTextView descTxt = (CustomTextView) myView.findViewById(R.id.order_composer_desc);
        CustomTextView priceTxt = (CustomTextView) myView.findViewById(R.id.order_composer_price);
        Button addToCartBtn = (Button) myView.findViewById(R.id.order_composer_button);
        addToCartBtn.setText("DODAJ "+quantity + " DO KOSZYKA    "+ sum);
        final BounceListView listView = (BounceListView) myView.findViewById(R.id.order_composer_listView);


        Bundle bundle = this.getArguments();

        if (bundle != null) {
            itemPositionInMenuListView = bundle.getInt("position");
            name = bundle.getString("name");
            desc = bundle.getString("desc");
            price = bundle.getString("price");
            size = bundle.getInt("size");

            title.setText("-" + String.valueOf(itemPositionInMenuListView + 1) + "-");
            nameTxt.setText(name.toUpperCase());
            descTxt.setText(desc);
            priceTxt.setText(pizzaList.get(itemPositionInMenuListView).getPriceArray().get(size).toString());
        }


        descText[0] = String.valueOf(20 + size * 10) + " cm";

        adapter = new OrderComposerListViewAdapter(myView.getContext(), titleText, descText);
        adapter.notifyDataSetChanged();

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Object listItem = listView.getItemAtPosition(position);

                Intent intent = new Intent();

                if (position == 0) {
                    intent.putExtra("size", getSize());
                    intent.putExtra("position", itemPositionInMenuListView);
                    intent.setClass(view.getContext(), PizzaSizePopUp.class);
                    startActivity(intent);
                }

                if (position == 1) {
                    intent.putExtra("size", getSize());
                    intent.setClass(view.getContext(), AddonsPopUp.class);
                    startActivity(intent);
                }

                if (position == 2) {
                    intent.putExtra("size", getSize());
                    intent.setClass(view.getContext(), SaucePopUp.class);
                    startActivity(intent);
                }
            }
        });

        return myView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();


        CustomTextView descTxt = (CustomTextView) myView.findViewById(R.id.order_composer_desc);
        descText[0] = String.valueOf(20 + getSize() * 10) + " cm";
        sum = pizzaList.get(itemPositionInMenuListView).getPriceArray().get(getSize()).floatValue();



        if (addonsPopUpAdapter != null) {
            String txt = "";
            Boolean start = true;
            for (int i = 0; i < addonsPopUpAdapter.getItemArray().size(); i++) {
                if (addonsPopUpAdapter.getItemArray().get(i).getHowManyItemWereSelected() != 0) {

                    if (!start) {

                        txt += ", ";
                    }
                    start = false;
                    if (addonsPopUpAdapter.getItemArray().get(i).getHowManyItemWereSelected() == 1) {
                        txt += addonsPopUpAdapter.getItemArray().get(i).getName();
                        sum += addonsPopUpAdapter.getItemArray().get(i).getPriceArray().get(getSize()).floatValue();

                    } else {
                        txt += addonsPopUpAdapter.getItemArray().get(i).getName() + " x2";
                        sum += 2*addonsPopUpAdapter.getItemArray().get(i).getPriceArray().get(getSize()).floatValue();
                    }
                }
            }
            if (txt != "") {
                descText[1] = txt;
            } else {
                descText[1] = "Wybierz dodatki";
            }

        }
        if (saucePopUpAdapter != null) {
            String txt = "";
            Boolean start = true;
            for (int i = 0; i < saucePopUpAdapter.getItemArray().size(); i++) {
                if (saucePopUpAdapter.getItemArray().get(i).getHowManyItemWereSelected() != 0) {
                    if (!start) {

                        txt += ", ";
                    }
                    start = false;
                    if (saucePopUpAdapter.getItemArray().get(i).getHowManyItemWereSelected() == 1) {
                        txt += saucePopUpAdapter.getItemArray().get(i).getName();
                        sum += saucePopUpAdapter.getItemArray().get(i).getPriceArray().get(getSize()).floatValue();
                    } else {
                        txt += saucePopUpAdapter.getItemArray().get(i).getName() + " x2";
                        sum += 2*saucePopUpAdapter.getItemArray().get(i).getPriceArray().get(getSize()).floatValue();
                    }
                }
            }
            if (txt != "") {
                descText[2] = txt;
            } else {
                descText[2] = "Wybierz dodatkowy sos";
            }

        }
        Button addToCartBtn = (Button) myView.findViewById(R.id.order_composer_button);
        addToCartBtn.setText("DODAJ "+quantity + " DO KOSZYKA    " + sum + " zł");
        CustomTextView priceTxt = (CustomTextView) myView.findViewById(R.id.order_composer_price);
        priceTxt.setText(pizzaList.get(itemPositionInMenuListView).getPriceArray().get(getSize()).toString() + " zł");

        adapter.setDescArr(descText);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("informacja", "Kompozytor_onDestroy");
    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        OrderComposerFragment.size = size;
    }
}
