package com.example.eag.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HistoriqueListFragment extends Fragment {

    public static String ATMO_KEY = "DATA_ATMO";

    PointMesure[] pointMesures = null;

    RecyclerView rvHistoriqueATMO;
    AtmoAdaptateur atmoAdaptateur;

    public HistoriqueListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pointMesures = (PointMesure[]) getArguments().getSerializable(ATMO_KEY);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historique_list, container, false);


        // Gestion de l'affichage CardView avec un Recycleur View
        rvHistoriqueATMO = (RecyclerView) view.findViewById(R.id.rvHistorique);
        RecyclerView.LayoutManager  mLayoutManager = new LinearLayoutManager(getContext());
        // sur deux lignes
        rvHistoriqueATMO.setLayoutManager(mLayoutManager);
        rvHistoriqueATMO.setItemAnimator(new DefaultItemAnimator());
        atmoAdaptateur = new AtmoAdaptateur(pointMesures);
        rvHistoriqueATMO.setAdapter(atmoAdaptateur);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
