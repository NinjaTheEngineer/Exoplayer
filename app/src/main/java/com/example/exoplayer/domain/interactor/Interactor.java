package com.example.exoplayer.domain.interactor;

import com.example.exoplayer.domain.executor.PostExecutionThread;
import com.example.exoplayer.domain.executor.ThreadExecutor;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/*
Interactor is used when implementing a clean architecture using Use Cases,
This interface represents a execution unit for different use cases (any use case in the app should implement this contract)
By convention each Interactor implementation will return the result using a Subscriber that will execute its job in a backgroud thread.
and post the result in the UI thread.
*/
public abstract class Interactor {

    private final ThreadExecutor threadExecutor;

    private final PostExecutionThread postExecutionThread;

    private Subscription subscription = Subscriptions.empty();

    protected Interactor(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
    }

    //Builds an Observable which will be used when executing the current Interactor.
    protected abstract Observable buildUseCaseObservable();

    //Executes the current Use Case. useCaseSubscriber will listen to the observable build with buildUseCaseObservable().
    @SuppressWarnings("unchecked")
    public void execute(Subscriber useCaseSubscriber){
        this.subscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.from(threadExecutor))//executes the task in a new Scheduler, in this case from ThreadExecutor.
                .observeOn(postExecutionThread.getScheduler()) //method will provide the result on a specific Scheduler: the postExecutionThread in this case.
                .subscribe(useCaseSubscriber);
    }

    //Unsubscribes from current Subscription
    public void unsubscribe(){
        if(!subscription.isUnsubscribed()){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        subscription.unsubscribe();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }
    }
}
