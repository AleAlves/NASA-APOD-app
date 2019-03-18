package br.com.aleson.nasa.apod.app.login.repository.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import br.com.aleson.nasa.apod.app.common.response.BaseResponse;

public class TicketResponse extends BaseResponse {

    @Expose
    @SerializedName("ticket")
    private String ticket;

    public String getTicket() {
        return ticket;
    }
}
