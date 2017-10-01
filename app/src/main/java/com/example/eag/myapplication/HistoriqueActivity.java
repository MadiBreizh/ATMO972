package com.example.eag.myapplication;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class HistoriqueActivity extends BaseActivity {

    AtmoElement[] atmoElements = null;
    RecyclerView rvHistoriqueATMO;
    AtmoAdaptateur atmoAdaptateur;
    Toolbar toolbar;

    public static String ATMO_KEY = "DATA_ATMO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        toolbar = (Toolbar) findViewById(R.id.tb_historique);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rvHistoriqueATMO = (RecyclerView) findViewById(R.id.rvHistorique);

        atmoElements = (AtmoElement[]) getIntent().getSerializableExtra(ATMO_KEY);

        atmoAdaptateur = new AtmoAdaptateur(atmoElements);

        RecyclerView.LayoutManager  mLayoutManager = new LinearLayoutManager(getApplicationContext());
        // sur deux lignes
        //RecyclerView.LayoutManager  mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rvHistoriqueATMO.setLayoutManager(mLayoutManager);
        rvHistoriqueATMO.setItemAnimator(new DefaultItemAnimator());
        rvHistoriqueATMO.setAdapter(atmoAdaptateur);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
