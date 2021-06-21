package com.example.exoplayer.data.model;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("errorCode")
    private int errorCode;
}
