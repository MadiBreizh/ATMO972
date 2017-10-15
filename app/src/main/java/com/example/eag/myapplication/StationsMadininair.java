package com.example.eag.myapplication;


import android.location.Location;
import android.util.Log;

/**
 * Created by Erwan on 14/10/2017.
 */

public class StationsMadininair {

    Location positionUtilisateur;
    int stationNearly;

    boolean[][] etatAnalyseSite =
            {
                    {false, false, false, false, false, false, true}, // Fort-de-France, Hôtel de Ville
                    {false, true, true, false, true, false, false}, // Fort-de-France, rocade Concorde
                    {false, true, true, false, true, true, false}, // Fort-de-France, Renéville
                    {true, false, false, false, false, false, false}, // Fort-de-France, Etang Z’abricot
                    {false, true, true, true, true, false, false}, // Fort-de-France, lycée Bellevue
                    {false, false, false, false, false, true, false}, // Schoelcher, Bourg
                    {false, true, true, true, true, true, true}, // Ste Luce, Morne Pavillon
                    {true, true, true, true, true, true, false}, // Robert, Bourg
                    {false, true, true, true, true, true, false}, // Lamentin, Bas Mission
                    {false, false, false, true, false, true, false}, // Saint-Pierre, CDST
                    {false, false, false, false, false, true, false}, // François, Pointe Couchée
            };

    public StationsMadininair(double latitudePhone, double longitudePhone) {
        this.positionUtilisateur.setLatitude(latitudePhone);
        this.positionUtilisateur.setLongitude(longitudePhone);
        stationNearly = getStationNearly();
    }

    int getStationNearly( ){
        // On initialise à une valeur incohérente pour réaliser un controle
        int numStationNearly = 100;
        double distMinStation = 9999.0;

        double[][] positionsStations = {
                {14.606037, -61.065276}, // 0 - FDF - Hotel de Ville
                {14.613585, -61.063941}, // 1 - FDF - Rocade Concorde
                {14.614324, -61.053169}, // 2 - FDF - Renéville
                {14.603671, -61.041253}, // 3 - FDF - Etang Z'Abricot
                {14.602902, -61.077537}, // 4 - FDF - Lycée Bellevue
                {14.616503, -61.100818}, // 5 - Schoelcher - Bourg
                {14.468902, -60.927383}, // 6 - Sainte Luce
                {14.675522, -60.945784}, // 7 - Le Robert - Bourg
                {14.610664, -61.002608}, // 8 - Lamentin - Bas Mission
                {14.755501, -61.179013}, // 9 - Saint-Pierre - CDST
                {14.633220, -60.899796}, // 10 - Le François - Pointe Couchée
        };

        for (int i = 0; i < positionsStations.length ; i++) {
            double earthRadius = 6371;

            double dLat = Math.toRadians(positionsStations[i][0]-positionUtilisateur.getLatitude());
            double dLng = Math.toRadians(positionsStations[i][1]-positionUtilisateur.getLongitude());

            double sindLat = Math.sin(dLat / 2);
            double sindLng = Math.sin(dLng / 2);

            double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2) * Math.cos(Math.toRadians(positionUtilisateur.getLatitude())) * Math.cos(Math.toRadians(positionsStations[i][0]));

            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

            double dist = earthRadius * c;
            Log.i("GPS", i + " : La distance est de " + dist + " km");

            //mets à jour la position la plus proche si distance plus courte trouvé
            if(dist < distMinStation){
                numStationNearly = i;
                distMinStation = dist;
            }
        }

        return numStationNearly;
    }

}
