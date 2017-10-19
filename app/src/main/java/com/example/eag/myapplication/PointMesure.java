package com.example.eag.myapplication;

import java.io.Serializable;

class PointMesure implements Serializable {

    private String date, point;

    PointMesure(String date, String point){
        this.date = date;
        //s'il n'y a pas de point
        if(point.equals("0"))
            this.point = "--";
        else
            this.point = point;
    }

    String getDate() {
        return date;
    }

    String getPoint() {
        return point;
    }

    @Override
    public String toString(){
        return "le : " + date + " - le point est de : " + point;
    }
}
