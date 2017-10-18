package com.example.eag.myapplication;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utilites {


    public static String formatDateLong(final int jour){

        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, jour);
        Date date = cal.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }

    public static String formatDateCourt(final int jour){

        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, jour);
        Date date = cal.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");

        return sdf.format(date);
    }

    public static String recupNomStation(int numStation){
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

    public static String recupNomCourtPolluant(int numPolluant){
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

}