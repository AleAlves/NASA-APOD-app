package br.com.aleson.nasa.apod.app.feature.home.repository.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import br.com.aleson.nasa.apod.app.common.response.BaseResponse;
import br.com.aleson.nasa.apod.app.feature.home.domain.APOD;

public class APODResponse  extends BaseResponse {

    @Expose
    @SerializedName("apod")
    private APOD apod;

    public APOD getApod() {
        return apod;
    }

    public void setApod(APOD apod) {
        this.apod = apod;
    }
}
