package com.example.eag.myapplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utilites {

    /* Ne fonctionne pas lorsque l'on passe un argument en paramètre*/
    public static String formatageDate(final String date){
        String pattern = "dd-MM-yyyy";
        Date dateReformat = null;

        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        try {
            dateReformat = formatter.parse(date.toLowerCase());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateReformat.toString();
    }

    public static String recupererDate(final int jour){

        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DAY_OF_MONTH, jour);
        Date date = cal.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.format(date);
    }
}