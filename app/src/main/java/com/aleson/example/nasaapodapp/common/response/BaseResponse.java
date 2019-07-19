package com.aleson.example.nasaapodapp.common.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @Expose
    @SerializedName("status")
    private BaseResponseStatus status;

    public BaseResponseStatus getHttpStatus() {
        return status;
    }
}
