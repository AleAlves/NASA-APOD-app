package com.aleson.example.nasaapodapp.feature.apod.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class APOD implements Parcelable {


    @SerializedName("copyright")
    private String copyright;

    @SerializedName("date")
    private String date;

    @SerializedName("explanation")
    private String explanation;

    @SerializedName("hdurl")
    private String hdurl;

    @SerializedName("media_type")
    private String media_type;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String url;

    private boolean like;

    protected APOD(Parcel in) {
        copyright = in.readString();
        date = in.readString();
        explanation = in.readString();
        hdurl = in.readString();
        media_type = in.readString();
        title = in.readString();
        url = in.readString();
        like = in.readByte() != 0;
    }

    public APOD() {

    }

    public static final Creator<APOD> CREATOR = new Creator<APOD>() {
        @Override
        public APOD createFromParcel(Parcel in) {
            return new APOD(in);
        }

        @Override
        public APOD[] newArray(int size) {
            return new APOD[size];
        }
    };

    public boolean isFavorite() {
        return like;
    }

    public void setFavorite(boolean empty) {
        this.like = empty;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getDate() {
        return date;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getHdurl() {
        return hdurl;
    }

    public String getMedia_type() {
        return media_type;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(copyright);
        dest.writeString(date);
        dest.writeString(explanation);
        dest.writeString(hdurl);
        dest.writeString(media_type);
        dest.writeString(title);
        dest.writeString(url);
        dest.writeByte((byte) (like ? 1 : 0));
    }
}