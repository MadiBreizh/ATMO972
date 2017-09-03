package com.example.eag.myapplication;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    TextView tvDate;
    TextView tvIndice;

    RecyclerView rvHistoriqueATMO;

    AtmoAdaptateur atmoAdaptateur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_historique);

        tvDate = (TextView)findViewById(R.id.tvDate);
        tvIndice = (TextView)findViewById(R.id.tvIndice);
        rvHistoriqueATMO = (RecyclerView) findViewById(R.id.rvHistorique);

        new atmoMadininair().execute();
    }

    private class atmoMadininair extends AsyncTask<Void, Void, AtmoElement[]>{

        @Override
        protected AtmoElement[] doInBackground(Void... params) {
            int nbrJourAntérieur = -30;

            Uri.Builder uriBuilder = new Uri.Builder();

//            String urlMadininairCSV = "http://www.madininair.fr/indice_atmo.php?dd="+Utilites.recupererDate(nbrJourAntérieur)+"&df="+Utilites.recupererDate(0);
            uriBuilder.scheme("http")
                    .authority("www.madininair.fr")
                    .appendPath("indice_atmo.php")
                    .appendQueryParameter("dd", Utilites.recupererDate(nbrJourAntérieur))
                    .appendQueryParameter("df", Utilites.recupererDate(0))
                    .build();

            URL url = null;
            try {
                url = new URL(uriBuilder.toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }


            AtmoElement[] atmoElements = null;

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

            if(atmoElements != null)
                Toast.makeText(MainActivity.this, "Mise à jour réussi", Toast.LENGTH_SHORT).show();

            atmoAdaptateur = new AtmoAdaptateur(atmoElements);

            RecyclerView.LayoutManager  mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvHistoriqueATMO.setLayoutManager(mLayoutManager);
            rvHistoriqueATMO.setItemAnimator(new DefaultItemAnimator());
            rvHistoriqueATMO.setAdapter(atmoAdaptateur);
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

        //inversion des éléments du tableau pour avoir en position 0 l'élément le plus rescent

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
