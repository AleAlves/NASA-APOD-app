package br.com.aleson.nasa.apod.app.feature.login.domain;

import br.com.aleson.nasa.apod.app.common.response.BaseResponse;

public class AESData extends BaseResponse {

    private String key;
    private String salt;
    private String iv;

    public void setKey(String key) {
        this.key = key;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

}