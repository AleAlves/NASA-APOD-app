package com.aleson.example.nasaapodapp.feature.login.repository.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.aleson.example.nasaapodapp.common.response.BaseResponse;

public class TicketResponse extends BaseResponse {

    @Expose
    @SerializedName("ticket")
    private String ticket;

    public String getTicket() {
        return ticket;
    }
}
