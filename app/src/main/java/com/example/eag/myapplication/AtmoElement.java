package com.example.eag.myapplication;

import java.io.Serializable;

/**
 * Created by EAG on 30/08/2017.
 *
 */

class AtmoElement implements Serializable {

    private String date, indice;

    AtmoElement(String date, String indice){
        this.date = date;
        if(indice.equals("0"))
            this.indice = "--";
        else
            this.indice = indice;
    }

    String getDate() {
        return date;
    }

    String getIndice() {
        return indice;
    }

    @Override
    public String toString(){
        return "le : " + date + " - l'indice est de : " + indice;
    }
}
