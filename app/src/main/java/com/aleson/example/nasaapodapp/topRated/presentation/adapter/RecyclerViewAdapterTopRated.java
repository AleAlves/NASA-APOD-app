package com.aleson.example.nasaapodapp.topRated.presentation.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.apod.domain.Apod;
import com.aleson.example.nasaapodapp.apod.presentation.MainActivity;
import com.aleson.example.nasaapodapp.topRated.presentation.TopRatedActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class RecyclerViewAdapterTopRated extends RecyclerView.Adapter<RecyclerViewAdapterTopRated.ViewHolder> {

    ArrayList<Apod> apodList = new ArrayList<>();
    Context context;
    View view1;
    static Activity activity;
    ViewHolder viewHolder1;

    public RecyclerViewAdapterTopRated(Context context, List<Apod> apods, TopRatedActivity activity) {
        this.apodList.addAll(apods);
        this.context = context;
        this.activity = activity;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewDate;
        public TextView textViewTitle;
        public TextView textViewIndex;
        public TextView textViewRates;
        public ImageView imageViewImage;
        public ProgressBar progressBarLoadingTopRatedImage;
        public ImageButton buttonStar1;
        public ImageButton buttonStar2;
        public ImageButton buttonStar3;
        public ImageButton buttonStar4;
        public ImageButton buttonStar5;
        public GridLayout gridLayoutTopRated;

        public ViewHolder(View v) {

            super(v);

            buttonStar1 = (ImageButton) v.findViewById(R.id.image_button_top_rated_start_1);
            buttonStar2 = (ImageButton) v.findViewById(R.id.image_button_top_rated_start_2);
            buttonStar3 = (ImageButton) v.findViewById(R.id.image_button_top_rated_start_3);
            buttonStar4 = (ImageButton) v.findViewById(R.id.image_button_top_rated_start_4);
            buttonStar5 = (ImageButton) v.findViewById(R.id.image_button_top_rated_start_5);


            textViewDate = (TextView) v.findViewById(R.id.textview_top_rated_apod_date);
            textViewTitle = (TextView) v.findViewById(R.id.textview_top_rated_apod_title);
            textViewIndex = (TextView) v.findViewById(R.id.textview_apod_index);
            textViewRates = (TextView) v.findViewById(R.id.textview_apod_rates_count);
            imageViewImage = (ImageView) v.findViewById(R.id.imageview_top_rated_apod_image);
            progressBarLoadingTopRatedImage = (ProgressBar) v.findViewById(R.id.progressbar_loading_top_rated_image);
            gridLayoutTopRated = (GridLayout) v.findViewById(R.id.gridlayout_top_rated);
        }
    }

    @Override
    public RecyclerViewAdapterTopRated.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.image_top_rated_card, parent, false);

        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE , dd MMM yyyy");
        try {
            holder.textViewDate.setText(outFormat.format(inFormat.parse(apodList.get(position).getDate())));
        } catch (Exception e) {

        }
        holder.textViewIndex.setText(String.valueOf(position+1));
        holder.textViewRates.setText(String.valueOf(apodList.get(position).getRates()));
        holder.textViewTitle.setText(apodList.get(position).getTitle());
        loadImage(apodList.get(position).getUrl(), holder, holder.progressBarLoadingTopRatedImage);
        loadRate(apodList.get(position).getAverageRate(), holder);
        holder.gridLayoutTopRated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(activity, MainActivity.class);
                mainIntent.putExtra("Apod",apodList.get(position));
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(mainIntent);
                activity.finish();
            }
        });
    }

    private void loadRate( int rate, ViewHolder holder){
        switch (rate) {
            case 0:
                break;
            case 1:
                holder.buttonStar1.setImageResource(R.drawable.ic_star_black_24dp);
                break;
            case 2:
                holder.buttonStar1.setImageResource(R.drawable.ic_star_black_24dp);
                holder.buttonStar2.setImageResource(R.drawable.ic_star_black_24dp);
                break;
            case 3:
                holder.buttonStar1.setImageResource(R.drawable.ic_star_black_24dp);
                holder.buttonStar2.setImageResource(R.drawable.ic_star_black_24dp);
                holder.buttonStar3.setImageResource(R.drawable.ic_star_black_24dp);
                break;
            case 4:
                holder.buttonStar1.setImageResource(R.drawable.ic_star_black_24dp);
                holder.buttonStar2.setImageResource(R.drawable.ic_star_black_24dp);
                holder.buttonStar3.setImageResource(R.drawable.ic_star_black_24dp);
                holder.buttonStar4.setImageResource(R.drawable.ic_star_black_24dp);
                break;
            case 5:
                holder.buttonStar1.setImageResource(R.drawable.ic_star_black_24dp);
                holder.buttonStar2.setImageResource(R.drawable.ic_star_black_24dp);
                holder.buttonStar3.setImageResource(R.drawable.ic_star_black_24dp);
                holder.buttonStar4.setImageResource(R.drawable.ic_star_black_24dp);
                holder.buttonStar5.setImageResource(R.drawable.ic_star_black_24dp);
                break;
        }
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
