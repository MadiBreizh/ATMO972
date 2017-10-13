package com.example.eag.myapplication;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by EAG on 02/09/2017.
 */

public class AtmoAdaptateur extends RecyclerView.Adapter<AtmoAdaptateur.ViewHolder> {

    AtmoElement[] atmoElements = null;

    public AtmoAdaptateur(AtmoElement[] atmoElements) {
        this.atmoElements = atmoElements;
    }

    //dire à l'adaptateur à quoi ressemble le layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // la layout item_indice_atmo sera le template
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_indice_atmo, parent, false);
        return new ViewHolder(itemView);
    }

    //information de notre position
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int couleur1_2 = 0xff0099cc;
        int couleur3_4 = 0xff33b5e5;
        int couleur5 = 0xffffbb33;
        int couleur6_7 = 0xffff8800;
        int couleur8_9 = 0xffff4444;
        int couleur10 = 0xffaa66cc;
        int couleurdefault = 0xffffffff;

        holder.date.setText(atmoElements[position].getDate());
        holder.indice.setText(atmoElements[position].getIndice());
        switch (atmoElements[position].getIndice()){
            case "1":
            case "2":
                holder.cvIndiceAtmo.setCardBackgroundColor(couleur1_2);
                break;
            case "3":
            case "4":
                holder.cvIndiceAtmo.setCardBackgroundColor(couleur3_4);
                break;
            case "5":
                holder.cvIndiceAtmo.setCardBackgroundColor(couleur5);
                break;
            case "6":
            case "7":
                holder.cvIndiceAtmo.setCardBackgroundColor(couleur6_7);
                break;
            case "8":
            case "9":
                holder.cvIndiceAtmo.setCardBackgroundColor(couleur8_9);
                break;
            case "10":
                holder.cvIndiceAtmo.setCardBackgroundColor(couleur10);
            default:
                holder.cvIndiceAtmo.setCardBackgroundColor(couleurdefault);
        }

    }

    //combien de position existe-t-il
    @Override
    public int getItemCount() {
        return atmoElements.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView indice;
        CardView cvIndiceAtmo;

        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.tvDate);
            indice = (TextView) itemView.findViewById(R.id.tvIndice);
            cvIndiceAtmo = (CardView) itemView.findViewById(R.id.cv_indice_atmo);

        }
    }
}
