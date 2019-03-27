package br.com.aleson.nasa.apod.app.feature.login.repository.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import br.com.aleson.nasa.apod.app.common.response.BaseResponse;

public class TokenResponse extends BaseResponse {

    @Expose
    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }
}
