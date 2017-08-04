package com.michal_stasinski.tabu.CRM.Fragments;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.ButtonBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.michal_stasinski.tabu.R;

public class Crm_SplitView_Fragment extends android.support.v4.app.Fragment {
    private View myView;
    private Crm_Kanban_Fragment fragment1;
    private ButtonBarLayout all;
    private SplitViewFragmentInteractionListener listener;
    public Crm_SplitView_Fragment() {
        // Required empty public constructor
    }

    public interface SplitViewFragmentInteractionListener {
        void messageFromSplitViewFragmentToActivity(String myString);
    }

    public static Crm_SplitView_Fragment newInstance(int num) {

        Crm_SplitView_Fragment fragment = new Crm_SplitView_Fragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = getActivity();
        listener = (SplitViewFragmentInteractionListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.crm__split_view_fragment, container, false);

        all = (ButtonBarLayout) myView.findViewById(R.id.all_btn);
        //ButtonBarLayout przyjeteButton = (ButtonBarLayout) myView.findViewById(R.id.odbior_btn);
        // ButtonBarLayout realizacjaButton = (ButtonBarLayout) myView.findViewById(R.id.dowoz_btn);
        //ButtonBarLayout gotoweButton = (ButtonBarLayout) myView.findViewById(R.id.paragon_btn);


        return myView;

    }

    @Override
    public void onResume() {
        super.onResume();

        all.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                listener.messageFromSplitViewFragmentToActivity("I am the parent fragment.");
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.crm_fragment_contener, new Crm_Kanban_Fragment());
                ft.commit();

            }
        });
    }
}
