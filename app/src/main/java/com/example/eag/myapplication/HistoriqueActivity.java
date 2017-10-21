package com.example.eag.myapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class HistoriqueActivity extends BaseActivity {

    PointMesure[] pointMesures = null;
    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    public static String ATMO_KEY = "DATA_ATMO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        // Récupération des données
        pointMesures = (PointMesure[]) getIntent().getSerializableExtra(ATMO_KEY);

        // Gestion de l'ActionBar
        toolbar = (Toolbar) findViewById(R.id.tb_historique);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bnv_historique);

        Bundle arguments = new Bundle();
        arguments.putSerializable(HistoriqueActivity.ATMO_KEY, pointMesures);
        HistoriqueGraphFragment historiqueGraphFragment = new HistoriqueGraphFragment();
        historiqueGraphFragment.setArguments(arguments);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content_historique, historiqueGraphFragment).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        Bundle arguments = new Bundle();
                        switch (item.getItemId()){
                            case R.id.action_graphique :
                                arguments.putSerializable(HistoriqueActivity.ATMO_KEY, pointMesures);
                                HistoriqueGraphFragment historiqueGraphFragment = new HistoriqueGraphFragment();
                                historiqueGraphFragment.setArguments(arguments);

                                transaction.replace(R.id.fl_content_historique, historiqueGraphFragment).commit();
                                break;
                            case R.id.action_tab :
                                arguments.putSerializable(HistoriqueActivity.ATMO_KEY, pointMesures);
                                HistoriqueListFragment historiqueListFragment = new HistoriqueListFragment();
                                historiqueListFragment.setArguments(arguments);

                                transaction.replace(R.id.fl_content_historique, historiqueListFragment).commit();
                                break;
                        }
                        return false;
                    }
                }
        );


//

    }




}
