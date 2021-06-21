package com.example.exoplayer.data.model;

import com.google.gson.annotations.SerializedName;

import javax.inject.Inject;

public class DefaultRequest {
    public DefaultRequest(String message) {
        this.message = message;
    }

    @SerializedName("message")
    private String message;

}
