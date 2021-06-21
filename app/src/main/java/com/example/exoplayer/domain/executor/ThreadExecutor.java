package com.example.exoplayer.domain.executor;

import java.util.concurrent.Executor;

/*
Executor implementation can be based on different frameworks or techniques of asunchronous execution, but every implementation
will execute the Interactor out of the UI Thread.
 */
public interface ThreadExecutor extends Executor {

}
