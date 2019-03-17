package br.com.aleson.nasa.apod.app.common;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @Expose
    @SerializedName("status")
    private BaseResponseStatus status;

    public BaseResponseStatus getStatus() {
        return status;
    }
}
