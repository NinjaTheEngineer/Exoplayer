package com.example.exoplayer.data.repository;

import com.example.exoplayer.data.model.DefaultRequest;
import com.example.exoplayer.data.model.DefaultResponse;
import com.example.exoplayer.domain.repository.VideoEventsRepository;

import javax.inject.Inject;

import retrofit2.Call;
import rx.Observable;
public class VideoEventsDataRepository {}/*implements VideoEventsRepository {

    private final VideoEventsDataSource dataSource;

    @Inject
    public VideoEventsDataRepository(VideoEventsDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Call<DefaultResponse> sendVideoStartLoad(DefaultRequest request) {
        return dataSource.sendVideoStartLoad(request);
    }

    @Override
    public Call<DefaultResponse> sendShowFirstFrame(DefaultRequest request) {
        return dataSource.sendShowFirstFrame(request);
    }

    @Override
    public Call<DefaultResponse> sendVideoFinished(DefaultRequest request) {
        return dataSource.sendVideoFinished(request);
    }*/
