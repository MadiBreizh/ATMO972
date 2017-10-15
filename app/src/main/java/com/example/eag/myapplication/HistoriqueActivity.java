package com.example.eag.myapplication;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class HistoriqueActivity extends BaseActivity {

    AtmoElement[] atmoElements = null;
    RecyclerView rvHistoriqueATMO;
    AtmoAdaptateur atmoAdaptateur;
    Toolbar toolbar;
    BarChart graphiqueHistorique;
    RelativeLayout rlGraphHistorique;

    public static String ATMO_KEY = "DATA_ATMO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        // Récupération des données
        atmoElements = (AtmoElement[]) getIntent().getSerializableExtra(ATMO_KEY);

        // Gestion de l'ActionBar
        toolbar = (Toolbar) findViewById(R.id.tb_historique);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Gestion de l'affichage graphique
        rlGraphHistorique = (RelativeLayout) findViewById(R.id.rlGraphHistorique);
        graphiqueHistorique = new BarChart(getApplicationContext());
        final RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        rlGraphHistorique.addView(graphiqueHistorique,lp);

        List<BarEntry> entries = getEntries(atmoElements);

        BarDataSet dataSet = new BarDataSet(entries, "Graphique Historique");
        dataSet.setColor(0xff0099cc);
        dataSet.setValueTextColor(0x00000000);


        BarData lineData = new BarData(dataSet);
        graphiqueHistorique.setData(lineData);
        graphiqueHistorique.invalidate(); // refresh

        // Gestion de l'affichage CardView avec un Recycleur View
        rvHistoriqueATMO = (RecyclerView) findViewById(R.id.rvHistorique);
        RecyclerView.LayoutManager  mLayoutManager = new LinearLayoutManager(getApplicationContext());
        // sur deux lignes
        //RecyclerView.LayoutManager  mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rvHistoriqueATMO.setLayoutManager(mLayoutManager);
        rvHistoriqueATMO.setItemAnimator(new DefaultItemAnimator());
        atmoAdaptateur = new AtmoAdaptateur(atmoElements);
        rvHistoriqueATMO.setAdapter(atmoAdaptateur);
    }


    private List<BarEntry> getEntries(AtmoElement[] atmoElements) {

        //inverser les éléments du tableau
        for(int i = 0; i < atmoElements.length / 2; i++)
        {
            AtmoElement temp = atmoElements[i];
            atmoElements[i] = atmoElements[atmoElements.length - i - 1];
            atmoElements[atmoElements.length - i - 1] = temp;
        }

        List<BarEntry> entries = new ArrayList<>();
        int i=0;
        for (AtmoElement atmoElement : atmoElements) {
            float indice = 0;
            if(!atmoElement.getIndice().equals("--")){
                indice = (float) Integer.parseInt(atmoElement.getIndice());
            }
            // turn your data into Entry objects
            entries.add(new BarEntry((float) i, indice));
        i++;
        }
        return entries;
    }

}
