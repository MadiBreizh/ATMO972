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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.Speedometer;
import com.github.anastr.speedviewlib.components.Indicators.Indicator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
        initSpeedView();
        InitSwipeToRefresh();

        findStationNearly();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PointMesures == null){
                    Toast.makeText(MainActivity.this, R.string.historique_data_empty, Toast.LENGTH_SHORT).show();
                }
                else {
                    Context context = getApplicationContext();
                    Intent intent = new Intent(context, HistoriqueActivity.class);
                    intent.putExtra(HistoriqueActivity.ATMO_KEY, PointMesures);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

        //TODO : Implementer uns sauvegarde des donnée sur un changelebt d'orientation
        new AtmoMadininair().execute();
    }

    //fonction ajout rafraichissement lors d'un swipe sur l'écran
    private void InitSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new AtmoMadininair().execute();
                        findStationNearly();
                        //Supression de la l'icone rafraichissement
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
        );
    }

    private void initSpeedView() {
        svATMO.setTrembleDegree(0.1F);
        svATMO.setMinMaxSpeed(1, 10);
        svATMO.setStartEndDegree(180, 360);
        svATMO.setIndicator(Indicator.Indicators.SpindleIndicator);
        svATMO.setLowSpeedPercent(30);
        svATMO.setMediumSpeedPercent(70);
        svATMO.setSpeedometerTextRightToLeft(true);
        svATMO.setUnit("");
        svATMO.setLowSpeedColor(0xFF0099CC);
        svATMO.setMediumSpeedColor(0xFFFF8800);
        svATMO.setHighSpeedColor(0xFFFF1744);
        svATMO.setTickNumber(10);
        svATMO.setSpeedTextSize(0);
        svATMO.setSpeedTextPosition(Speedometer.Position.TOP_CENTER);
    }

    private void findStationNearly() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // Sur Défaut, l'on force la localisation de "FDF - Lycée Bellevue"
            stationMadininair= new StationsMadininair(14.602902, 61.077537);
            tvStationNearly.setText(R.string.find_station_error);
        } else
        {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //TODO: a controler si cela fonctionne avec d'autres périphériques
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            stationMadininair= new StationsMadininair(loc.getLatitude(), loc.getLongitude());
            tvStationNearly.setText(String.format("%s : %s",
                    getString(R.string.display_station_nearly),
                    Utilites.recupNomStation(stationMadininair.getNumStationNearly())));
        }
    }

    //Gestion de la fonction retour sur l'écran principal
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

    private class AtmoMadininair extends AsyncTask<Void, Void, PointMesure[]> {

        @Override
        protected PointMesure[] doInBackground(Void... params) {
            int nbrJourAntérieur = -15;

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
                PointMesures = Utilites.parseUrlATMO(url);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return PointMesures;
        }

        @Override
        protected void onPostExecute(PointMesure[] PointMesures) {
            super.onPostExecute(PointMesures);

            if(!PointMesures[0].getPoint().equals("--")){
                svATMO.speedTo((float) Integer.parseInt(PointMesures[0].getPoint()), 2000);
            }
        }
    }

}
