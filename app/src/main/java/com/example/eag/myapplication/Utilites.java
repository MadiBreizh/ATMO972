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

    public static String recupNomStation(int numStationNearly){
        switch (numStationNearly)
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

}