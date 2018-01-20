// Copyright (c) 2018 aleson.a.s@gmail.com, All Rights Reserved.

package com.aleson.example.nasaapodapp.favorites.presentation.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.apod.presentation.MainActivity;
import com.aleson.example.nasaapodapp.favorites.domain.Device;
import com.aleson.example.nasaapodapp.favorites.presentation.FavoritesActivity;
import com.aleson.example.nasaapodapp.favorites.presentation.FavoritesView;
import com.aleson.example.nasaapodapp.favorites.presenter.FavoritesPresenter;
import com.aleson.example.nasaapodapp.utils.LocalDataBase;
import com.aleson.example.nasaapodapp.utils.Wallpaper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class FavoritesRecyclerViewAdapter extends RecyclerView.Adapter<FavoritesRecyclerViewAdapter.FavoritesViewHolder> {

    private ArrayList<Apod> apodList = new ArrayList<>();
    private Context context;
    private static Activity activity;
    private static FavoritesView mFavoritesView;
    private FavoritesPresenter favoritesPresenter;


    public FavoritesRecyclerViewAdapter(Context context, List<Apod> apods, FavoritesActivity activity, FavoritesPresenter favoritesPresenter) {
        this.apodList.addAll(apods);
        this.context = context;
        this.activity = activity;
        this.mFavoritesView = (FavoritesView) activity;
        this.favoritesPresenter = favoritesPresenter;
    }


    protected static class FavoritesViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDate;
        private TextView textViewTitle;
        private ImageView imageViewImage;
        private ProgressBar progressBarLoadingFavImage;
        private ImageButton buttonDeleteApod;
        private ImageButton buttonWallpaperApod;
        private ImageButton buttonSeeApod;
        private RatingBar ratingBarStars;

        public FavoritesViewHolder(View v) {

            super(v);

            ratingBarStars = (RatingBar) v.findViewById(R.id.favorites_rating_stars);
            textViewDate = (TextView) v.findViewById(R.id.textview_apod_date);
            textViewTitle = (TextView) v.findViewById(R.id.textview_apod_title);
            imageViewImage = (ImageView) v.findViewById(R.id.imageview_apod_image);
            progressBarLoadingFavImage = (ProgressBar) v.findViewById(R.id.progressbar_loading_image);
            buttonDeleteApod = (ImageButton) v.findViewById(R.id.button_delete_apod);
            buttonSeeApod = (ImageButton) v.findViewById(R.id.button_see_apod);
            buttonWallpaperApod = (ImageButton) v.findViewById(R.id.button_wallpaper_apod);
            buttonWallpaperApod.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFavoritesView.openWallpaperManager();
                }
            });
        }
    }

    @Override
    public FavoritesRecyclerViewAdapter.FavoritesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavoritesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.image_favorite_card, parent, false));
    }

    @Override
    public void onBindViewHolder(final FavoritesViewHolder holder, final int position) {

        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE , dd MMM yyyy");
        try {
            holder.textViewDate.setText(outFormat.format(inFormat.parse(apodList.get(position).getDate())));
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        holder.textViewTitle.setText(apodList.get(position).getTitle());
        holder.imageViewImage.setVisibility(View.VISIBLE);
        loadImage(apodList.get(position).getUrl(), holder, holder.progressBarLoadingFavImage);
        holder.ratingBarStars.setRating((float) apodList.get(position).getRate());
        holder.buttonDeleteApod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDialog(position);
            }
        });

        holder.buttonSeeApod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(activity, MainActivity.class);
                mainIntent.putExtra("Apod", apodList.get(position));
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(mainIntent);
                activity.finish();
            }
        });


        holder.ratingBarStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                holder.ratingBarStars.setIsIndicator(true);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        holder.ratingBarStars.setIsIndicator(false);
                    }
                }, 5000);
                saveRate(apodList.get(position), (int) v, holder.ratingBarStars);
            }
        });

        holder.imageViewImage.setOnClickListener(new View.OnClickListener() {
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

    private void saveRate(Apod model, int rate, final RatingBar ratingBar) {
        LocalDataBase apodBD = new LocalDataBase(context);
        model.setRate(rate);
        Device deviceModel = apodBD.getDeviceInfo();
        deviceModel.setRateValue(rate);
        apodBD.saveDeviceInfo(deviceModel);
        apodBD.saveApod(model);
        favoritesPresenter.sendRate(model, deviceModel, rate);
    }

    @Override
    public int getItemCount() {
        return apodList.size();
    }


    private void loadImage(final String url, final FavoritesViewHolder holder, final ProgressBar progressBar) {
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

    private void deleteDialog(final int position) {
        if (apodList.get(position).getRate() > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage("Do you want to remove this APOD?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    LocalDataBase apodBD = new LocalDataBase(context);
                    apodBD.delete(apodList.get(position));
                    Wallpaper wallpaper = new Wallpaper(activity);
                    wallpaper.deleteFile(apodList.get(position).getFileLocation());
                    mFavoritesView.reloadFavoritesList();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setMessage("Help us, rate it before delete.");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}
