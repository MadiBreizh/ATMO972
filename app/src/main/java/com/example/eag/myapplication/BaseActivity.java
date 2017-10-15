package com.example.eag.myapplication;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Erwan on 01/10/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    AtmoElement[] atmoElements = null;


    //action à mener sur le clic d'une option
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                Context context = getApplicationContext();
                Intent intent = new Intent(context, AboutActivity.class);
                context.startActivity(intent);
                return true;
            case R.id.menu_contact:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"eangot.bzh@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "[CONTACT] - Application ATMO972");
                //i.putExtra(Intent.EXTRA_TEXT, "Laissez votre message ici");
                try {
                    startActivity(Intent.createChooser(i, "Envoyer Email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "Pas de client mail installer", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.menu_avis:
                Toast.makeText(this, "Indisponible pour le moment", Toast.LENGTH_SHORT).show();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
