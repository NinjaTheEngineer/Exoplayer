package com.example.exoplayer.data.datasource;

import com.example.exoplayer.data.model.DefaultRequest;
import com.example.exoplayer.data.model.DefaultResponse;
import com.example.exoplayer.data.network.ApiClient;
import com.example.exoplayer.data.network.ApiInterface;

import javax.inject.Inject;

import retrofit2.Call;
import rx.Observable;
/*
public class VideoEventsDataSource {

    private final ApiClient apiClient;

    @Inject
    public VideoEventsDataSource(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public Call<DefaultResponse> sendVideoStartLoad(DefaultRequest message){
        ApiInterface apiInterface = apiClient.createApiInterface();
        return apiInterface.sendVideoStartLoad(message);
    }

    public Call<DefaultResponse> sendShowFirstFrame(DefaultRequest message){
        ApiInterface apiInterface = apiClient.createApiInterface();
        return apiInterface.sendShowFirstFrame(message);
    }

    public Call<DefaultResponse> sendVideoFinished(DefaultRequest message){
        ApiInterface apiInterface = apiClient.createApiInterface();
        return apiInterface.sendVideoFinished(message);
    }
}
*/

