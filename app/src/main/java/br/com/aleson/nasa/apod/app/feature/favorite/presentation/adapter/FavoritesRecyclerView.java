package br.com.aleson.nasa.apod.app.feature.favorite.presentation.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.util.DateUtil;
import br.com.aleson.nasa.apod.app.feature.favorite.repository.response.FavoriteResponse;
import br.com.aleson.nasa.apod.app.feature.home.presentation.APODsActivity;

public class FavoritesRecyclerView extends RecyclerView.Adapter<FavoritesRecyclerView.FavoritesViewHolder> {

    private Context context;
    private List<FavoriteResponse> favorites;

    public FavoritesRecyclerView(Context context, List<FavoriteResponse> favorites) {
        this.context = context;
        this.favorites = favorites;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoritesRecyclerView.FavoritesViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_favorite_adapter_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, final int position) {

        holder.textViewFavApodDate.setText(DateUtil.parseDateToView(favorites.get(position).getDate()));
        holder.textViewFavApodTitle.setText(favorites.get(position).getTitle());

        Glide.with(context)
                .load(favorites.get(position).getPic())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(holder.imageViewFavApodPic);

        holder.constraintLayoutFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAPODActivity(favorites.get(position).getDate());
            }
        });
    }

    private void startAPODActivity(String date) {
        Intent intent = new Intent(context, APODsActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("date", date);

        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewFavApodPic;
        private TextView textViewFavApodDate;
        private TextView textViewFavApodTitle;
        private ConstraintLayout constraintLayoutFavorite;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewFavApodPic = itemView.findViewById(R.id.apod_adapter_apod_image);
            this.textViewFavApodDate = itemView.findViewById(R.id.favorite_apod_date);
            this.textViewFavApodTitle = itemView.findViewById(R.id.favorite_apod_title);
            this.constraintLayoutFavorite = itemView.findViewById(R.id.constraint_favorite);
        }
    }
}
