package com.example.eag.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    CardView cardView;

    Toolbar toolbar;

    AtmoElement[] atmoElements = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tb_acceuil);
        setSupportActionBar(toolbar);

        tvDate = (TextView)findViewById(R.id.tvDate);
        tvIndice = (TextView)findViewById(R.id.tvIndice);
        cardView = (CardView)findViewById(R.id.cvATMO);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Intent intent = new Intent(context, HistoriqueActivity.class);
                intent.putExtra(HistoriqueActivity.ATMO_KEY, atmoElements);
                context.startActivity(intent);
            }
        });

        //Si pas encore charger (evite de relancer le parse à chaque execution de onCreate. ex: changement orientation)
        if(atmoElements == null)
            new atmoMadininair().execute();
    }

    //ajout d'un menu personnaliser
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_acceuil, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //action à mener sur le clic d'une option
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_actualiser:
                //TODO : A verifier si cela actualise le Recycler View
                new atmoMadininair().execute();
                return true;
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

    private class atmoMadininair extends AsyncTask<Void, Void, AtmoElement[]>{

        @Override
        protected AtmoElement[] doInBackground(Void... params) {
            int nbrJourAntérieur = -20;

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

            tvDate.setText(atmoElements[0].getDate());
            tvIndice.setText(atmoElements[0].getIndice());

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
