package br.com.aleson.nasa.apod.app.login.domain;

public class AESData {

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