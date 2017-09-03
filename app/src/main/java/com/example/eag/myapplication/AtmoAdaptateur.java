package com.example.eag.myapplication;

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

        holder.date.setText(atmoElements[position].getDate());
        holder.indice.setText(atmoElements[position].getIndice());

    }

    //combien de position existe-t-il
    @Override
    public int getItemCount() {
        return atmoElements.length;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView indice;

        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.tvDate);
            indice = (TextView) itemView.findViewById(R.id.tvIndice);

        }
    }
}
