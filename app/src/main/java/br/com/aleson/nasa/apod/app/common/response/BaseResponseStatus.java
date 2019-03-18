package br.com.aleson.nasa.apod.app.common.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponseStatus {

    @Expose
    @SerializedName("status")
    private String status;

    @Expose
    @SerializedName("code")
    private String code;
}
