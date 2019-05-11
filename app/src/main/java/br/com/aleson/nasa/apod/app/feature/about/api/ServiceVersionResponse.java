package br.com.aleson.nasa.apod.app.feature.about.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import br.com.aleson.nasa.apod.app.common.response.BaseResponse;

public class ServiceVersionResponse extends BaseResponse {

    @Expose
    @SerializedName("version")
    private String version;

    public String getVersion() {
        return version;
    }
}
