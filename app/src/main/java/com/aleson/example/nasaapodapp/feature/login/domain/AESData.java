package com.aleson.example.nasaapodapp.feature.login.domain;

import com.aleson.example.nasaapodapp.common.response.BaseResponse;

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