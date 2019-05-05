package br.com.aleson.nasa.apod.app.feature.apod.presentation.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import com.github.android.aleson.slogger.SLogger;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import br.com.aleson.nasa.apod.app.R;
import br.com.aleson.nasa.apod.app.common.constants.Constants;
import br.com.aleson.nasa.apod.app.common.file.FileOperationCallback;
import br.com.aleson.nasa.apod.app.common.file.FileUtil;
import br.com.aleson.nasa.apod.app.common.callback.DialogCallback;
import br.com.aleson.nasa.apod.app.common.callback.FavoriteCallback;
import br.com.aleson.nasa.apod.app.common.domain.DialogMessage;
import br.com.aleson.nasa.apod.app.common.permission.PermissionManager;
import br.com.aleson.nasa.apod.app.common.session.Session;
import br.com.aleson.nasa.apod.app.common.util.DateHelper;
import br.com.aleson.nasa.apod.app.feature.apod.domain.APOD;
import br.com.aleson.nasa.apod.app.feature.apod.presentation.APODFullscreenActivity;
import br.com.aleson.nasa.apod.app.feature.apod.presentation.APODView;
import br.com.aleson.nasa.apod.app.feature.apod.repository.request.APODRateRequest;

public class APODRecyclerViewAdapter extends RecyclerView.Adapter<APODRecyclerViewAdapter.APODViewHolder> {

    private Activity activity;
    private APODView apodView;
    private List<APOD> apodList;

    public APODRecyclerViewAdapter(Activity activity, List<APOD> apodList, APODView apodView) {
        this.activity = activity;
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

        updateFavoriteButton(holder, apodList.get(position).isFavorite());

        if (apodList.get(position).getCopyright() == null) {
            holder.textViewCopyrigth.setVisibility(View.GONE);
        } else {
            holder.textViewCopyrigth.setVisibility(View.VISIBLE);
            holder.textViewCopyrigth.setText(apodList.get(position).getCopyright() + " ©");
        }

        holder.textViewTitle.setText(apodList.get(position).getTitle());

        holder.textViewExplanation.setText(apodList.get(position).getExplanation());

        holder.textViewDate.setText(DateHelper.parseDateToView(apodList.get(position).getDate()));

        holder.imageButtonDelete.setVisibility(View.GONE);
        holder.imageButtonDownload.setVisibility(View.VISIBLE);

        if (FileUtil.savedImageExists(apodList.get(position).getDate())) {

            holder.imageButtonDownload.setVisibility(View.GONE);
            holder.imageButtonDelete.setVisibility(View.VISIBLE);
        }

        final ProgressBar progressBar = holder.progressBarImageLoading;

        if (Constants.MEDIA.VIDEO.equals(apodList.get(position).getMedia_type())) {

            holder.imageButtonDownload.setEnabled(false);
            holder.progressBarImageLoading.setVisibility(View.GONE);
            holder.imageViewVideoPlay.setVisibility(View.VISIBLE);
            holder.imageViewAPOD.setImageResource(0);
            holder.imageViewVideoPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.imageViewAPOD.callOnClick();
                }
            });

        } else {

            holder.progressBarImageLoading.setVisibility(View.VISIBLE);
            holder.imageViewVideoPlay.setVisibility(View.GONE);
            Glide.with(activity)
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

        holder.imageViewAPOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constants.MEDIA.VIDEO.equals(apodList.get(position).getMedia_type())) {

                    try {

                        activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(apodList.get(position).getUrl())));
                    } catch (Exception e) {
                        SLogger.e(e);
                    }

                } else {
                    Intent intent = new Intent(activity, APODFullscreenActivity.class);
                    intent.putExtra("url", apodList.get(position).getUrl());
                    activity.startActivity(intent);
                }
            }
        });

        holder.imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Session.getInstance().isLogged()) {

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

                } else {

                    DialogMessage message = new DialogMessage();
                    message.setMessage(activity.getString(R.string.dialog_message_login_warn));
                    message.setPositiveButton(activity.getString(R.string.dialog_button_login_positive));
                    message.setNegativeButton(activity.getString(R.string.dialog_button_login_negative));

                    apodView.showDialog(message, false, new DialogCallback.Buttons() {
                        @Override
                        public void onPositiveAction() {

                            apodView.exit();
                        }

                        @Override
                        public void onNegativeAction() {
                            //TODO analytics tags
                        }

                        @Override
                        public void onDismiss() {
                            //TODO analytics tags
                        }
                    });
                }
            }
        });

        holder.imageButtonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PermissionManager.verfyStoragePermission(activity)) {
                    FileUtil.saveAPOD(activity, apodList.get(position), holder.imageViewAPOD, new FileOperationCallback() {
                        @Override
                        public void onFinish(String message) {

                            holder.imageButtonDelete.setVisibility(View.VISIBLE);
                            holder.imageButtonDownload.setVisibility(View.GONE);

                            apodView.showToast(message);
                        }
                    });
                } else {

                    apodView.askStoragePermission();
                }
            }
        });

        holder.imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtil.delete(apodList.get(position).getDate(), new FileOperationCallback() {
                    @Override
                    public void onFinish(String message) {

                        holder.imageButtonDelete.setVisibility(View.GONE);
                        holder.imageButtonDownload.setVisibility(View.VISIBLE);

                        apodView.showToast(message);
                    }
                });
            }
        });

        holder.imageButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FileUtil.sharing(activity,
                        holder.imageViewAPOD,
                        apodList.get(position).getTitle(),
                        apodList.get(position).getExplanation());
            }
        });
    }


    private void updateFavoriteButton(APODViewHolder holder, boolean favorite) {

        if (favorite) {
            holder.imageButtonFavorite.setImageDrawable(activity.getDrawable(R.drawable.ic_favorite_24dp));
        } else {
            holder.imageButtonFavorite.setImageDrawable(activity.getDrawable(R.drawable.ic_favorite_border_24dp));
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
        private ImageView imageViewVideoPlay;
        private ProgressBar progressBarImageLoading;
        private ImageButton imageButtonFavorite;
        private ImageButton imageButtonShare;
        private ImageButton imageButtonDownload;
        private ImageButton imageButtonDelete;

        public APODViewHolder(@NonNull View itemView) {

            super(itemView);
            this.imageButtonShare = itemView.findViewById(R.id.button_apod_share);
            this.imageButtonDelete = itemView.findViewById(R.id.button_apod_delete);
            this.textViewTitle = itemView.findViewById(R.id.apod_adapter_apod_title);
            this.imageViewAPOD = itemView.findViewById(R.id.apod_adapter_apod_image);
            this.textViewDate = itemView.findViewById(R.id.apod_adapter_textview_date);
            this.imageButtonDownload = itemView.findViewById(R.id.button_apod_download);
            this.imageButtonFavorite = itemView.findViewById(R.id.button_apod_favorite);
            this.imageViewVideoPlay = itemView.findViewById(R.id.imageview_video_player);
            this.textViewCopyrigth = itemView.findViewById(R.id.apod_adapter_apod_copyright);
            this.textViewExplanation = itemView.findViewById(R.id.apod_adapter_apod_explanation);
            this.progressBarImageLoading = itemView.findViewById(R.id.apod_adapter_apod_image_loading);
        }
    }
}
