package br.com.aleson.nasa.apod.app.feature.home.presentation.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;

public class APODRecyclerViewAdapter extends RecyclerView.Adapter<APODRecyclerViewAdapter.APODViewHolder> {

    private Context context;
    private List<APOD> apodList;

    public APODRecyclerViewAdapter(Context context, List<APOD> apodList) {
        this.context = context;
        this.apodList = apodList;
    }

    @NonNull
    @Override
    public APODViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new APODRecyclerViewAdapter.APODViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.view_apod_apdater_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull APODViewHolder holder, int position) {
        if (apodList.get(position).isEmpty()) {
            holder.placeHolderView.setVisibility(View.VISIBLE);
        } else {
            holder.placeHolderView.setVisibility(View.GONE);
        }
        holder.textViewTitle.setText(apodList.get(position).getTitle());

        if (apodList.get(position).getCopyright() == null) {
            holder.textViewCopyrigth.setVisibility(View.GONE);
        } else {
            holder.textViewCopyrigth.setVisibility(View.VISIBLE);
            holder.textViewCopyrigth.setText(apodList.get(position).getCopyright() + "©");
        }
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
        private View placeHolderView;

        public APODViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewTitle = itemView.findViewById(R.id.apod_adapter_apod_title);
            this.imageViewAPOD = itemView.findViewById(R.id.apod_adapter_apod_image);
            this.textViewDate = itemView.findViewById(R.id.apod_adapter_textview_date);
            this.textViewCopyrigth = itemView.findViewById(R.id.apod_adapter_apod_copyright);
            this.textViewExplanation = itemView.findViewById(R.id.apod_adapter_apod_explanation);
            this.progressBarImageLoading = itemView.findViewById(R.id.apod_adapter_apod_image_loading);
            this.placeHolderView = itemView.findViewById(R.id.apod_imasgeview_placeholder);
        }
    }
}
