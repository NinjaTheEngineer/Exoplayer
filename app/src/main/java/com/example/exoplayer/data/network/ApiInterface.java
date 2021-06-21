package com.example.exoplayer.data.network;

import com.example.exoplayer.data.model.DefaultRequest;
import com.example.exoplayer.data.model.DefaultResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface ApiInterface {
    @POST("startload")
    Call<DefaultResponse> sendVideoStartLoad(@Query("message") DefaultRequest message);

    @POST("showFirstFrame")
    Call<DefaultResponse> sendShowFirstFrame(@Query("message") DefaultRequest message);

    @POST("videoFinished")
    Call<DefaultResponse> sendVideoFinished(@Query("message") DefaultRequest message);
}
