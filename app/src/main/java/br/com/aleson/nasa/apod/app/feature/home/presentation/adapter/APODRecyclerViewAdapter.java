package br.com.aleson.nasa.apod.app.feature.home.presentation.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.callback.FavoriteCallback;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.home.presentation.APODFullscreenActivity;
import br.com.aleson.nasa.apod.app.feature.home.presentation.APODView;
import br.com.aleson.nasa.apod.app.feature.home.repository.request.APODRateRequest;

public class APODRecyclerViewAdapter extends RecyclerView.Adapter<APODRecyclerViewAdapter.APODViewHolder> {

    private Context context;
    private APODView apodView;
    private List<APOD> apodList;

    public APODRecyclerViewAdapter(Context context, List<APOD> apodList, APODView apodView) {
        this.context = context;
        this.apodList = apodList;
        this.apodView = apodView;
    }

    @NonNull
    @Override
    public APODViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new APODRecyclerViewAdapter.APODViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_apod_apdater_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final APODViewHolder holder, final int position) {

        if (Session.getInstance().getToken() == null) {
            holder.imageButtonFavorite.setVisibility(View.GONE);
        }

        updateFavoriteButton(holder, apodList.get(position).isFavorite());

        if (apodList.get(position).getCopyright() == null) {
            holder.textViewCopyrigth.setVisibility(View.GONE);
        } else {
            holder.textViewCopyrigth.setVisibility(View.VISIBLE);
            holder.textViewCopyrigth.setText(apodList.get(position).getCopyright() + " ©");
        }

        holder.textViewTitle.setText(apodList.get(position).getTitle());
        holder.textViewExplanation.setText(apodList.get(position).getExplanation());
        holder.textViewDate.setText(apodList.get(position).getDate());
        holder.progressBarImageLoading.setVisibility(View.VISIBLE);
        final ProgressBar progressBar = holder.progressBarImageLoading;

        Glide.with(context)
                .load(apodList.get(position).getUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageViewAPOD);

        holder.imageViewAPOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, APODFullscreenActivity.class);
                intent.putExtra("url", apodList.get(position).getUrl());
                context.startActivity(intent);
            }
        });

        holder.imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                APODRateRequest rateRequest = new APODRateRequest();
                rateRequest.setDate(apodList.get(position).getDate());
                rateRequest.setPic(apodList.get(position).getHdurl());
                rateRequest.setTitle(apodList.get(position).getTitle());

                apodView.rate(rateRequest, new FavoriteCallback() {
                    @Override
                    public void status(boolean favorite) {
                        updateFavoriteButton(holder, favorite);
                    }
                });
            }
        });
    }

    private void updateFavoriteButton(APODViewHolder holder, boolean favorite) {
        if (favorite) {
            holder.imageButtonFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_24dp));
        } else {
            holder.imageButtonFavorite.setImageDrawable(context.getDrawable(R.drawable.ic_favorite_border_24dp));
        }
    }

    @Override
    public int getItemCount() {
        return apodList.size();
    }

    public class APODViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewDate;
        private TextView textViewTitle;
        private TextView textViewCopyrigth;
        private TextView textViewExplanation;
        private ImageView imageViewAPOD;
        private ProgressBar progressBarImageLoading;
        private ImageButton imageButtonFavorite;

        public APODViewHolder(@NonNull View itemView) {

            super(itemView);
            this.imageButtonFavorite = itemView.findViewById(R.id.button_apod_share);
            this.textViewTitle = itemView.findViewById(R.id.apod_adapter_apod_title);
            this.imageViewAPOD = itemView.findViewById(R.id.apod_adapter_apod_image);
            this.textViewDate = itemView.findViewById(R.id.apod_adapter_textview_date);
            this.textViewCopyrigth = itemView.findViewById(R.id.apod_adapter_apod_copyright);
            this.textViewExplanation = itemView.findViewById(R.id.apod_adapter_apod_explanation);
            this.progressBarImageLoading = itemView.findViewById(R.id.apod_adapter_apod_image_loading);
        }
    }
}
