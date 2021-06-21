package com.example.exoplayer.domain.repository;

import com.example.exoplayer.data.model.DefaultRequest;
import com.example.exoplayer.data.model.DefaultResponse;

import rx.Observable;

public interface VideoEventsRepository {

    Observable<DefaultResponse> sendVideoStartLoad(DefaultRequest request);

    Observable<DefaultResponse> sendShowFirstFrame(DefaultRequest request);

    Observable<DefaultResponse> sendVideoFinished(DefaultRequest request);
}
