package com.example.eag.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.github.anastr.speedviewlib.Gauge;
import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.Speedometer;
import com.github.anastr.speedviewlib.components.Indicators.Indicator;
import com.github.anastr.speedviewlib.components.Indicators.TriangleIndicator;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final int TIME_INTERVAL = 2000; // # milliseconds, desired time passed between two back presses.
    private long mBackPressed;

    CardView cardView;
    TextView tvStationNearly;
    Toolbar toolbar;
    StationsMadininair stationMadininair;
    SwipeRefreshLayout swipeRefreshLayout;
    SpeedView svATMO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tb_acceuil);
        setSupportActionBar(toolbar);

        tvStationNearly = (TextView)findViewById(R.id.tvStationNearly);
        cardView = (CardView)findViewById(R.id.cvATMO);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.srlAcceuil);

        svATMO = (SpeedView) findViewById(R.id.svATMO);

        svATMO.setTrembleDegree(0.1F);
        svATMO.setMinMaxSpeed(1, 10);
        svATMO.setStartEndDegree(180, 360);
        svATMO.setIndicator(Indicator.Indicators.SpindleIndicator);
        svATMO.setLowSpeedPercent(30);
        svATMO.setMediumSpeedPercent(70);
        svATMO.setSpeedometerTextRightToLeft(true);
        svATMO.setUnit("");
        svATMO.setLowSpeedColor(0xff0099cc);
        svATMO.setMediumSpeedColor(0xffff8800);
        svATMO.setHighSpeedColor(0xffaa66cc);
        svATMO.setTickNumber(10);
        svATMO.setSpeedTextSize(0);
        svATMO.setSpeedTextPosition(Speedometer.Position.TOP_CENTER);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // Sur Défaut, l'on force la localisation de "FDF - Lycée Bellevue"
            stationMadininair= new StationsMadininair(14.602902, 61.077537);
            tvStationNearly.setText("Recherche d'une station à proximité impossible");
        } else
        {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //TODO: a controler si cela fonctionne avec d'autres périphériques
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            stationMadininair= new StationsMadininair(loc.getLatitude(), loc.getLongitude());
            tvStationNearly.setText("Station proche de votre position : " + Utilites.recupNomStation(stationMadininair.getNumStationNearly()));
        }

        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        new AtmoMadininair().execute();

                        //Supression de la l'icone rafraichissement
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(atmoElements == null)
                    Toast.makeText(MainActivity.this, "Pas de données pour l'historique", Toast.LENGTH_SHORT).show();
                else {
                    Context context = getApplicationContext();
                    Intent intent = new Intent(context, HistoriqueActivity.class);
                    intent.putExtra(HistoriqueActivity.ATMO_KEY, atmoElements);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

        //TODO : Implementer uns sauvegarde des donnée sur un changelebt d'orientation
        if(atmoElements == null)
            new AtmoMadininair().execute();
    }

    @Override
    public void onBackPressed() {

        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            super.onBackPressed();
            return;
        }
        else { Toast.makeText(getBaseContext(), "Appuyer encore pour Quitter", Toast.LENGTH_SHORT).show(); }

        mBackPressed = System.currentTimeMillis();

    }

    private class AtmoMadininair extends AsyncTask<Void, Void, AtmoElement[]> {

        @Override
        protected AtmoElement[] doInBackground(Void... params) {
            int nbrJourAntérieur = -55;

            Uri.Builder uriBuilder = new Uri.Builder();

//          http://www.madininair.fr/indice_atmo.php?dd="+Utilites.recupererDate(nbrJourAntérieur)+"&df="+Utilites.recupererDate(0)
            uriBuilder.scheme("http")
                    .authority("www.madininair.fr")
                    .appendPath("indice_atmo.php")
                    .appendQueryParameter("dd", Utilites.formatDateLong(nbrJourAntérieur))
                    .appendQueryParameter("df", Utilites.formatDateLong(0))
                    .build();

            URL url = null;
            try {
                url = new URL(uriBuilder.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            try{
                atmoElements = parseUrlMadininair(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return atmoElements;
        }

        @Override
        protected void onPostExecute(AtmoElement[] atmoElements) {
            super.onPostExecute(atmoElements);

            if(!atmoElements[0].getIndice().equals("--"))
                svATMO.speedTo((float) Integer.parseInt(atmoElements[0].getIndice()), 2000);


        }
    }

    private AtmoElement[] parseUrlMadininair(URL url) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        CSVReader reader = new CSVReader(in,';');

        List<String[]> contenuCsv = reader.readAll();

        String[] date = contenuCsv.get(0);
        String[] indice = contenuCsv.get(1);

        AtmoElement[] atmoElements = new AtmoElement[indice.length];

        for (int i = 0; i < atmoElements.length ; i++) {
            atmoElements[i] = new AtmoElement(date[i], indice[i]);
        }

        //inverser les éléments du tableau
        for(int i = 0; i < atmoElements.length / 2; i++)
        {
            AtmoElement temp = atmoElements[i];
            atmoElements[i] = atmoElements[atmoElements.length - i - 1];
            atmoElements[atmoElements.length - i - 1] = temp;
        }

        return atmoElements;
    }

}
