package com.michal_stasinski.tabu.Menu;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.michal_stasinski.tabu.Menu.Adapters.OrderCompositorListViewAdapter;
import com.michal_stasinski.tabu.R;
import com.michal_stasinski.tabu.Utils.BounceListView;
import com.michal_stasinski.tabu.Utils.CustomTextView;


public class OrderCompositorFragment extends Fragment {


    private View myView;
    private OrderCompositorListViewAdapter adapter;
    private static int size = 0;
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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("informacja", "OrderCompositorFragment_________onCreate");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("informacja", "OrderCompositorFragment________ onCreateView");
        myView = inflater.inflate(R.layout.fragment_order_compositor, container, false);

        int position;
        String name;
        String price;
        String desc;


        CustomTextView title = (CustomTextView) myView.findViewById(R.id.order_compozytor_positionInList);
        CustomTextView nameTxt = (CustomTextView) myView.findViewById(R.id.order_compozytor_titleItem);
        CustomTextView descTxt = (CustomTextView) myView.findViewById(R.id.order_compozytor_desc);
        CustomTextView priceTxt = (CustomTextView) myView.findViewById(R.id.order_compozytor_price);
        final BounceListView listView = (BounceListView) myView.findViewById(R.id.order_compozytor_listview);


        Bundle bundle = this.getArguments();

        if (bundle != null) {
            position = bundle.getInt("position");
            name = bundle.getString("name");
            desc = bundle.getString("desc");
            price = bundle.getString("price");
            size = bundle.getInt("size");

            title.setText("-" + String.valueOf(position + 1) + "-");
            nameTxt.setText(name.toUpperCase());
            descTxt.setText(desc);
            priceTxt.setText(price.toUpperCase());
        }


        descText[0] = String.valueOf(20 + size * 10) + " cm";

        adapter = new OrderCompositorListViewAdapter(myView.getContext(), titleText, descText);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
                Object listItem = listView.getItemAtPosition(position);
                Log.i("informacja", "OrderCompositorFragment________click: " + position);
                //   TextView v = (TextView) view.findViewById(R.id.order_row_title);
                // v.setText("test");


                //tu przekaz  size

                Intent intent = new Intent();
                intent.setClass(view.getContext(), Pop.class);
                startActivity(intent);
            }
        });


        return myView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("informacja", "OrderCompositorFragment_________onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        CustomTextView descTxt = (CustomTextView) myView.findViewById(R.id.order_compozytor_desc);
        descText[0] = String.valueOf(20 + getSize() * 10) + " cm";
        Log.i("informacja", "OrderCompositorFragment_________onResume" + getSize());
        adapter.setDescArr(descText);
        adapter.notifyDataSetChanged();

    }

    public static int getSize() {
        return size;
    }

    public static void setSize(int size) {
        OrderCompositorFragment.size = size;
    }
}
