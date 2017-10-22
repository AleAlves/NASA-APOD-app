package com.aleson.example.nasaapodapp.favorites;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.apod.domain.ApodModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;


/**
 * Created by JUNED on 6/10/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<ApodModel> apodList = new ArrayList<>();
    Context context;
    View view1;
    ViewHolder viewHolder1;

    public RecyclerViewAdapter(Context context, ArrayList<ApodModel> messages) {
        this.apodList.addAll(messages);
        this.context = context;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewDate;
        public TextView textViewTitle;
        public ImageView imageViewImage;


        public ViewHolder(View v) {

            super(v);

            textViewDate = (TextView) v.findViewById(R.id.textview_apod_date);
            textViewTitle = (TextView) v.findViewById(R.id.textview_apod_title);
            imageViewImage = (ImageView) v.findViewById(R.id.imageview_apod_image);
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view1 = LayoutInflater.from(context).inflate(R.layout.image_apod_card, parent, false);

        viewHolder1 = new ViewHolder(view1);

        return viewHolder1;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textViewDate.setText(apodList.get(position).getDate());
        holder.textViewDate.setText(apodList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {

        return apodList.size();
    }


}
