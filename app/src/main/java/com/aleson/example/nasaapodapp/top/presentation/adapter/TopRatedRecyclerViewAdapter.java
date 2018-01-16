// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.top.presentation.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.apod.presentation.MainActivity;
import com.aleson.example.nasaapodapp.top.presentation.TopRatedActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.vision.text.Line;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TopRatedRecyclerViewAdapter extends RecyclerView.Adapter<TopRatedRecyclerViewAdapter.TopRatedViewHolder> {

    private ArrayList<Apod> apodList = new ArrayList<>();
    private Context context;
    private Activity activity;

    public TopRatedRecyclerViewAdapter(Context context, List<Apod> apods, TopRatedActivity activity) {
        this.apodList.addAll(apods);
        this.context = context;
        this.activity = activity;
    }

    @Override
    public TopRatedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_top_rated_card, parent, false);

        return new TopRatedViewHolder(itemView);
    }


    protected static class TopRatedViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDate;
        private TextView textViewTitle;
        private TextView textViewIndex;
        private TextView textViewRates;
        private ImageView imageViewImage;
        private ProgressBar progressBarLoadingTopRatedImage;
        private LinearLayout linearLayoutTopRated;
        private RatingBar ratingBarStars;

        private TextView teste;

        public TopRatedViewHolder(View v) {

            super(v);

            ratingBarStars = (RatingBar) v.findViewById(R.id.rating_stars);
            textViewDate = (TextView) v.findViewById(R.id.textview_top_rated_apod_date);
            textViewTitle = (TextView) v.findViewById(R.id.textview_top_rated_apod_title);
            textViewIndex = (TextView) v.findViewById(R.id.textview_apod_index);
            textViewRates = (TextView) v.findViewById(R.id.textview_apod_rates_count);
            imageViewImage = (ImageView) v.findViewById(R.id.imageview_top_rated_apod_image);
            progressBarLoadingTopRatedImage = (ProgressBar) v.findViewById(R.id.progressbar_loading_top_rated_image);
            linearLayoutTopRated = (LinearLayout) v.findViewById(R.id.linear_layout_top_rated);
        }
    }

    @Override
    public void onBindViewHolder(final TopRatedViewHolder holder, final int position) {

        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE , dd MMM yyyy");
        try {
            holder.textViewDate.setText(outFormat.format(inFormat.parse(apodList.get(position).getDate())));
        } catch (Exception e) {
            Log.e("LOG", e.getMessage());
        }
        holder.textViewIndex.setText(String.valueOf(position + 1));
        holder.textViewRates.setText(String.valueOf(apodList.get(position).getVotes()));
        holder.textViewTitle.setText(apodList.get(position).getTitle());
        loadImage(apodList.get(position).getUrl(), holder, holder.progressBarLoadingTopRatedImage);
        holder.ratingBarStars.setRating((float) apodList.get(position).getAverageRate());
        holder.linearLayoutTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(activity, MainActivity.class);
                mainIntent.putExtra("Apod", apodList.get(position));
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(mainIntent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return apodList.size();
    }


    private void loadImage(final String url, final TopRatedViewHolder holder, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(0xFF000022, android.graphics.PorterDuff.Mode.MULTIPLY);
        Glide.with(context)
                .load(url)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        loadImage(url, holder, progressBar);
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageViewImage);
    }

}
