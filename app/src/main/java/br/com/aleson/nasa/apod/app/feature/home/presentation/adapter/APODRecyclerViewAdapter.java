package br.com.aleson.nasa.apod.app.feature.home.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;

public class APODRecyclerViewAdapter extends RecyclerView.Adapter<APODRecyclerViewAdapter.APODViewHolder>  {

    private List<APOD> apodList;

    public APODRecyclerViewAdapter(List<APOD> apodList){
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
    }

    @Override
    public int getItemCount() {
        return apodList.size();
    }

    public class APODViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;

        public APODViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.apod_card_titlle);
        }
    }
}
