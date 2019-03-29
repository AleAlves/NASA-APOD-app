package br.com.aleson.nasa.apod.app.feature.home.presentation.adapter;

import android.content.Context;
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
        holder.textViewTitle.setText(apodList.get(position).getTitle());
        holder.textViewCopyrigth.setText(apodList.get(position).getCopyright()+"©");
        holder.textViewExplanation.setText(apodList.get(position).getExplanation());
        Glide.with(context)
                .load(apodList.get(position).getUrl())
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
                .into(holder.imageViewAPOD);
    }

    @Override
    public int getItemCount() {
        return apodList.size();
    }

    public class APODViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;
        private TextView textViewCopyrigth;
        private TextView textViewExplanation;
        private ImageView imageViewAPOD;

        public APODViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewTitle = itemView.findViewById(R.id.apod_adapter_apod_title);
            this.imageViewAPOD = itemView.findViewById(R.id.apod_adapter_apod_image);
            this.textViewCopyrigth = itemView.findViewById(R.id.apod_adapter_apod_copyright);
            this.textViewExplanation = itemView.findViewById(R.id.apod_adapter_apod_explanation);
        }
    }
}
