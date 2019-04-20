package br.com.aleson.nasa.apod.app.feature.home.repository.request;

public class APODRateRequest extends APODRequest {

    private String pic;

    private String title;

    public void setPic(String pic) {
        this.pic = pic;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
