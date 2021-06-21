package com.example.exoplayer.domain.features.video_events;

import com.example.exoplayer.data.model.DefaultRequest;
import com.example.exoplayer.domain.executor.PostExecutionThread;
import com.example.exoplayer.domain.executor.ThreadExecutor;
import com.example.exoplayer.domain.interactor.Interactor;
import com.example.exoplayer.domain.repository.VideoEventsRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class SendVideoFinishedUseCase extends Interactor {

    VideoEventsRepository repository;

    DefaultRequest request;

    @Inject
    public SendVideoFinishedUseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread, VideoEventsRepository repository) {
        super(threadExecutor, postExecutionThread);
        this.repository = repository;
    }

    public void execute(DefaultRequest request, Subscriber useCaseSubscriber) {
        super.execute(useCaseSubscriber);
        this.request = request;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return repository.sendVideoFinished(request);
    }
}