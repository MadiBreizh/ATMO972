package com.example.eag.myapplication;

/**
 * Created by EAG on 30/08/2017.
 */

public class AtmoElement {

    private String date, indice;

    public AtmoElement(String date, String indice){
        this.date = date;
        if(indice.equals("0"))
            this.indice = "--";
        else
            this.indice = indice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    @Override
    public String toString(){
        return "le : " + date + " - l'indice est de : " + indice;
    }
}
