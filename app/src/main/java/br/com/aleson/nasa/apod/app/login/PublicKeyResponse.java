package br.com.aleson.nasa.apod.app.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import br.com.aleson.nasa.apod.app.common.BaseResponse;

public class PublicKeyResponse extends BaseResponse {

    @Expose
    @SerializedName("publicKey")
    private String publicKey;


    public String getPublicKey() {
        return publicKey;
    }
}
