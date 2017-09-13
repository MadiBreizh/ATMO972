package com.example.eag.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class HistoriqueActivity extends AppCompatActivity {

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

        //RecyclerView.LayoutManager  mLayoutManager = new LinearLayoutManager(getApplicationContext());
        // sur deux lignes
        RecyclerView.LayoutManager  mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        rvHistoriqueATMO.setLayoutManager(mLayoutManager);
        rvHistoriqueATMO.setItemAnimator(new DefaultItemAnimator());
        rvHistoriqueATMO.setAdapter(atmoAdaptateur);
    }

    //ajout d'un menu personnaliser
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_historique, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //action à mener sur le clic d'une option
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_a_propos:
                return true;
            case R.id.menu_contact:
                return true;
            case android.R.id.home:
                // on ferme l'activité en cours et l'on revient à la précédente.
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
