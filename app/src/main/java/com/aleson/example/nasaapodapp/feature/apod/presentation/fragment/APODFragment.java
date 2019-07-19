package com.aleson.example.nasaapodapp.feature.apod.presentation.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import com.aleson.example.nasaapodapp.R;
import com.aleson.example.nasaapodapp.common.callback.DialogCallback;
import com.aleson.example.nasaapodapp.common.callback.FavoriteCallback;
import com.aleson.example.nasaapodapp.common.constants.Constants;
import com.aleson.example.nasaapodapp.common.domain.DialogMessage;
import com.aleson.example.nasaapodapp.common.file.FileOperationCallback;
import com.aleson.example.nasaapodapp.common.file.FileUtil;
import com.aleson.example.nasaapodapp.common.permission.PermissionManager;
import com.aleson.example.nasaapodapp.common.session.Session;
import com.aleson.example.nasaapodapp.common.util.DateHelper;
import com.aleson.example.nasaapodapp.feature.apod.domain.APOD;
import com.aleson.example.nasaapodapp.feature.apod.presentation.APODFullscreenActivity;
import com.aleson.example.nasaapodapp.feature.apod.presentation.APODView;
import com.aleson.example.nasaapodapp.feature.apod.repository.request.APODRateRequest;

public class APODFragment extends Fragment {

    private static final String ARG_APOD = "APOD";

    private APOD apod;

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
    private APODView view;

    public APODFragment() {
        // Required empty public constructor
    }

    public static APODFragment newInstance(APOD apod) {
        APODFragment fragment = new APODFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_APOD, apod);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            apod = getArguments().getParcelable(ARG_APOD);
        }
        view = (APODView) getActivity();
    }

    public String tag() {
        return apod.getDate();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_apod, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.imageButtonShare = view.findViewById(R.id.button_apod_share);
        this.imageButtonDelete = view.findViewById(R.id.button_apod_delete);
        this.textViewTitle = view.findViewById(R.id.apod_adapter_apod_title);
        this.imageViewAPOD = view.findViewById(R.id.apod_adapter_apod_image);
        this.textViewDate = view.findViewById(R.id.apod_adapter_textview_date);
        this.imageButtonDownload = view.findViewById(R.id.button_apod_download);
        this.imageButtonFavorite = view.findViewById(R.id.button_apod_favorite);
        this.imageViewVideoPlay = view.findViewById(R.id.imageview_video_player);
        this.textViewCopyrigth = view.findViewById(R.id.apod_adapter_apod_copyright);
        this.textViewExplanation = view.findViewById(R.id.apod_adapter_apod_explanation);
        this.progressBarImageLoading = view.findViewById(R.id.apod_adapter_apod_image_loading);
        onBind();
    }

    public void onBind() {

        updateFavoriteButton(apod.isFavorite());

        if (apod.getCopyright() == null) {
            textViewCopyrigth.setVisibility(View.GONE);
        } else {
            textViewCopyrigth.setVisibility(View.VISIBLE);
            textViewCopyrigth.setText(apod.getCopyright() + " ©");
        }

        textViewTitle.setText(apod.getTitle());

        textViewExplanation.setText(apod.getExplanation());

        textViewDate.setText(DateHelper.parseDateToView(apod.getDate()));

        imageButtonDelete.setVisibility(View.GONE);
        imageButtonDownload.setVisibility(View.VISIBLE);

        if (FileUtil.savedImageExists(apod.getDate())) {

            imageButtonDownload.setVisibility(View.GONE);
            imageButtonDelete.setVisibility(View.VISIBLE);
        }

        final ProgressBar progressBar = progressBarImageLoading;

        if (Constants.MEDIA.VIDEO.equals(apod.getMedia_type())) {

            imageButtonDownload.setVisibility(View.GONE);
            progressBarImageLoading.setVisibility(View.GONE);
            imageViewVideoPlay.setVisibility(View.VISIBLE);
            imageViewAPOD.setImageResource(0);
            imageViewVideoPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    imageViewAPOD.callOnClick();
                }
            });

        } else {

            progressBarImageLoading.setVisibility(View.VISIBLE);
            imageViewVideoPlay.setVisibility(View.GONE);
            Glide.with(getActivity())
                    .load(apod.getUrl())
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
                    .into(imageViewAPOD);
        }

        imageViewAPOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constants.MEDIA.VIDEO.equals(apod.getMedia_type())) {

                    try {

                        getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(apod.getUrl())));
                    } catch (Exception e) {
                        SLogger.e(e);
                    }

                } else {
                    Intent intent = new Intent(getActivity(), APODFullscreenActivity.class);
                    intent.putExtra("url", apod.getUrl());
                    getActivity().startActivity(intent);
                }
            }
        });

        imageButtonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Session.getInstance().isLogged()) {

                    APODRateRequest rateRequest = new APODRateRequest();
                    rateRequest.setDate(apod.getDate());
                    rateRequest.setPic(apod.getHdurl());
                    rateRequest.setTitle(apod.getTitle());

                    view.rate(rateRequest, new FavoriteCallback() {
                        @Override
                        public void status(boolean favorite) {

                            updateFavoriteButton(favorite);
                        }
                    });

                } else {

                    DialogMessage message = new DialogMessage();
                    message.setMessage(getActivity().getString(R.string.dialog_message_login_warn));
                    message.setPositiveButton(getActivity().getString(R.string.dialog_button_login_positive));
                    message.setNegativeButton(getActivity().getString(R.string.dialog_button_login_negative));

                    view.showDialog(message, false, new DialogCallback.Buttons() {
                        @Override
                        public void onPositiveAction() {

                            view.exit();
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

        imageButtonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PermissionManager.verfyStoragePermission(getActivity())) {
                    FileUtil.saveAPOD(getActivity(), apod, imageViewAPOD, new FileOperationCallback() {
                        @Override
                        public void onFinish(boolean success, String message) {

                            if (success) {
                                imageButtonDelete.setVisibility(View.VISIBLE);
                                imageButtonDownload.setVisibility(View.GONE);
                            }

                            view.showToast(message);
                        }
                    });
                } else {

                    view.askStoragePermission();
                }
            }
        });

        imageButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileUtil.delete(getActivity(), apod.getDate(), new FileOperationCallback() {
                    @Override
                    public void onFinish(boolean success, String message) {

                        if (success) {
                            imageButtonDelete.setVisibility(View.GONE);
                            imageButtonDownload.setVisibility(View.VISIBLE);
                        }

                        view.showToast(message);
                    }
                });
            }
        });

        imageButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FileUtil.sharing(getActivity(),
                        imageViewAPOD,
                        apod.getTitle(),
                        apod.getExplanation());
            }
        });
    }


    private void updateFavoriteButton(boolean favorite) {

        if (favorite) {
            imageButtonFavorite.setImageDrawable(getActivity().getDrawable(R.drawable.ic_favorite_24dp));
        } else {
            imageButtonFavorite.setImageDrawable(getActivity().getDrawable(R.drawable.ic_favorite_border_24dp));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
