package com.aleson.example.nasaapodapp.favorites;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.apod.domain.ApodModel;
import com.aleson.example.nasaapodapp.utils.ApodBD;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by JUNED on 6/10/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<ApodModel> apodList = new ArrayList<>();
    Context context;
    View view1;
    static Activity activity;
    ViewHolder viewHolder1;
    static FavoritesView mFavoritesView;

    public RecyclerViewAdapter(Context context, List<ApodModel> messages, Favorites activity) {
        this.apodList.addAll(messages);
        this.context = context;
        this.activity = activity;
        this.mFavoritesView = (FavoritesView) activity;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewDate;
        public TextView textViewTitle;
        public ImageView imageViewImage;
        public ProgressBar progressBarLoadingFavImage;
        public ImageButton buttonDeleteApod;
        public ImageButton buttonWallpaperApod;

        public ViewHolder(View v) {

            super(v);

            textViewDate = (TextView) v.findViewById(R.id.textview_apod_date);
            textViewTitle = (TextView) v.findViewById(R.id.textview_apod_title);
            imageViewImage = (ImageView) v.findViewById(R.id.imageview_apod_image);
            progressBarLoadingFavImage = (ProgressBar) v.findViewById(R.id.progressbar_loading_fav_image);
            buttonDeleteApod = (ImageButton) v.findViewById(R.id.button_delete_apod);
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
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.image_apod_card, parent, false);

        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE , dd MMM yyyy");
        try {
            holder.textViewDate.setText(outFormat.format(inFormat.parse(apodList.get(position).getDate())));
        }catch (Exception e){

        }
        holder.textViewTitle.setText(apodList.get(position).getTitle());
        loadImage(apodList.get(position).getUrl(), holder, holder.progressBarLoadingFavImage);

        holder.buttonDeleteApod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApodBD apodBD = new ApodBD(context);
                apodBD.delete(apodList.get(position));
                mFavoritesView.reloadFavoritesList();
            }
        });
    }

    @Override
    public int getItemCount() {
        return apodList.size();
    }


    private void loadImage(final String url, final ViewHolder holder, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.getIndeterminateDrawable().setColorFilter(0xF9F9F9F9, android.graphics.PorterDuff.Mode.MULTIPLY);
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
