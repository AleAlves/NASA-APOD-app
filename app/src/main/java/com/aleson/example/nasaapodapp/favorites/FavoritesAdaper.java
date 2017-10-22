package com.aleson.example.nasaapodapp.favorites;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by GAMER on 15/10/2017.
 */

public class FavoritesAdaper extends RecyclerView.Adapter<FavoritesAdaper.ViewHolder> {

    Context context;
    View view1;
    ViewHolder viewHolder1;

    @Override
    public FavoritesAdaper.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FavoritesAdaper.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
