package com.example.eag.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public abstract class BaseActivity extends AppCompatActivity {

    AtmoElement[] atmoElements = null;


    //action à mener sur le clic d'une option
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about:
                Context context = getApplicationContext();
                Intent intent = new Intent(context, AboutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
