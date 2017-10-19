package com.example.eag.myapplication;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

class Utilites {


    static String formatDateLong(final int jour){

        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, jour);
        Date date = cal.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);

        return simpleDateFormat.format(date);
    }

    /*
    public static String formatDateCourt(final int jour){

        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, jour);
        Date date = cal.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM", Locale.FRENCH);

        return simpleDateFormat.format(date);
    }
    */

    static String recupNomStation(int numStation){
        switch (numStation)
        {
            case 0:
                return "FDF - Hôtel de Ville";
            case 1:
                return "FDF - Rocade Concorde";
            case 2:
                return "FDF - Renéville";
            case 3:
                return "FDF - Etang Z'Abricot";
            case 4:
                return "FDF - Lycée Bellevue";
            case 5:
                return "Schoelcher";
            case 6:
                return "Sainte-Luce";
            case 7:
                return "Le Robert";
            case 8:
                return "La Lamentin";
            case 9:
                return "Saint-Pierre";
            case 10:
                return "Le Francois";
            default:
                return "Inconnu";
        }

    }

    /*
    static String recupNomCourtPolluant(int numPolluant){
        switch (numPolluant)
        {
            case 0:
                return "SO2";
            case 1:
                return "NO";
            case 2:
                return "NO2";
            case 3:
                return "O3";
            case 4:
                return "NOx";
            case 5:
                return "PM10";
            case 6:
                return "PM2.5";
            default:
                return "Err";
        }
    }
    */

    /*
    public static String recupNomLongPolluant(int numPolluant){
        switch (numPolluant)
        {
            case 0:
                return "Dioxyde de souffre";
            case 1:
                return "Monoxyde d'azote";
            case 2:
                return "Dioxyde d'azote";
            case 3:
                return "Ozone";
            case 4:
                return "Oxyde d'azote";
            case 5:
                return "Particules inférieurs à 10 microns";
            case 6:
                return "Particules inférieurs à 2.5 microns";
            default:
                return "Erreur !";
        }
    }
    */

    public static PointMesure[] parseUrlATMO(URL url) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        CSVReader reader = new CSVReader(in,';');

        List<String[]> contenuCsv = reader.readAll();

        String[] date = contenuCsv.get(0);
        String[] indice = contenuCsv.get(1);

        PointMesure[] PointMesures = new PointMesure[indice.length];

        for (int i = 0; i < PointMesures.length ; i++) {
            PointMesures[i] = new PointMesure(date[i], indice[i]);
        }

        //inverser les éléments du tableau
        for(int i = 0; i < PointMesures.length / 2; i++)
        {
            PointMesure temp = PointMesures[i];
            PointMesures[i] = PointMesures[PointMesures.length - i - 1];
            PointMesures[PointMesures.length - i - 1] = temp;
        }

        return PointMesures;
    }

}