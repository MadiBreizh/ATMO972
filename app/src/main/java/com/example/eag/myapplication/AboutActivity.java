package com.example.eag.myapplication;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class AboutActivity extends BaseActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //TODO: faire un BottomNavigationView pour afficheur le contenu de A Propos et le texte de la licence
        toolbar = (Toolbar) findViewById(R.id.tb_about);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
