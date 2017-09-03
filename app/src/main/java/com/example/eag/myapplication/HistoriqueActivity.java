package com.example.eag.myapplication;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class HistoriqueActivity extends AppCompatActivity {

    AtmoElement[] atmoElements = null;
    RecyclerView rvHistoriqueATMO;
    AtmoAdaptateur atmoAdaptateur;

    public static String ATMO_KEY = "DATA_ATMO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        rvHistoriqueATMO = (RecyclerView) findViewById(R.id.rvHistorique);

        atmoElements = (AtmoElement[]) getIntent().getSerializableExtra(ATMO_KEY);

        atmoAdaptateur = new AtmoAdaptateur(atmoElements);

        RecyclerView.LayoutManager  mLayoutManager = new LinearLayoutManager(getApplicationContext());
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
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
