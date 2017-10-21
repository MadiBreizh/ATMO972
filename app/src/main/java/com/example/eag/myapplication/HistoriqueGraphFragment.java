package com.example.eag.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;


public class HistoriqueGraphFragment extends Fragment {

    public static String ATMO_KEY = "DATA_ATMO";

    PointMesure[] pointMesures = null;

    BarChart graphiqueHistorique;
    RelativeLayout rlGraphHistorique;

    public HistoriqueGraphFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pointMesures = (PointMesure[])getArguments().getSerializable(ATMO_KEY);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historique_graph, container, false);

        // Gestion de l'affichage graphique
        rlGraphHistorique = (RelativeLayout) view.findViewById(R.id.rlGraphHistorique);
        graphiqueHistorique = new BarChart(getContext());
        final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        rlGraphHistorique.addView(graphiqueHistorique,lp);

        List<BarEntry> entries = getEntries(pointMesures);

        BarDataSet dataSet = new BarDataSet(entries, "Graphique Historique");
        //TODO : ne fonctionne pas avec le fichier de ressource colors
        dataSet.setColor(R.color.barColorGraph);
        dataSet.setValueTextColor(R.color.textColorGraph);


        BarData lineData = new BarData(dataSet);
        graphiqueHistorique.setData(lineData);
        graphiqueHistorique.getLegend().setEnabled(false);
        graphiqueHistorique.getDescription().setEnabled(false);
        graphiqueHistorique.invalidate(); // refresh

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


    private List<BarEntry> getEntries(PointMesure[] PointMesures) {

        //Vérifier si la fonction clone est la meilleur methode
        PointMesure[] tabElements = PointMesures.clone();
        //inverser les éléments du tableau
        for(int i = 0; i < tabElements.length / 2; i++)
        {
            PointMesure temp = tabElements[i];
            tabElements[i] = tabElements[tabElements.length - i - 1];
            tabElements[tabElements.length - i - 1] = temp;
        }

        List<BarEntry> entries = new ArrayList<>();
        int i=0;
        for (PointMesure element : tabElements) {
            float indice = 0;
            if(!element.getPoint().equals("--")){
                indice = (float) Integer.parseInt(element.getPoint());
            }
            // turn your data into Entry objects
            entries.add(new BarEntry((float) i, indice));
            i++;
        }
        return entries;
    }
}
