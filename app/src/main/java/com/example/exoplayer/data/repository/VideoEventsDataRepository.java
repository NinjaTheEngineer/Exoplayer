package com.example.exoplayer.data.repository;

import com.example.exoplayer.data.datasource.VideoEventsDataSource;
import com.example.exoplayer.data.model.DefaultRequest;
import com.example.exoplayer.data.model.DefaultResponse;
import com.example.exoplayer.domain.repository.VideoEventsRepository;

import javax.inject.Inject;

import rx.Observable;

public class VideoEventsDataRepository implements VideoEventsRepository {

    private final VideoEventsDataSource dataSource;

    @Inject
    public VideoEventsDataRepository(VideoEventsDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Observable<DefaultResponse> sendVideoStartLoad(DefaultRequest request) {
        return dataSource.sendVideoStartLoad(request);
    }

    @Override
    public Observable<DefaultResponse> sendShowFirstFrame(DefaultRequest request) {
        return dataSource.sendShowFirstFrame(request);
    }

    @Override
    public Observable<DefaultResponse> sendVideoFinished(DefaultRequest request) {
        return dataSource.sendVideoFinished(request);
    }
}
