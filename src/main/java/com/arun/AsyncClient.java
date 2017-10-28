package com.arun;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsyncClient {

	public static final Logger LOGGER = LoggerFactory.getLogger(AsyncClient.class);

	public DeferredResult<TaskState> getResource() {

		Executor executor = Executors.newCachedThreadPool();
		DeferredResult<TaskState> deferredTask = new DeferredResult<>("getResource", executor, TaskState.QUEUED);

		// Some long running task to fetch some resource
		Runnable longRunningTask = () -> {
			try {
				LOGGER.info("Thread: {}", Thread.currentThread().getName());
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			deferredTask.set(TaskState.FINISHED);
		};
		executor.execute(longRunningTask);
		return deferredTask;
	}

	public static void main(String args[]) {
		AsyncClient asyncClient = new AsyncClient();

		// Client submits a request and is given a DeferredResult immediately
		DeferredResult<TaskState> deferredResult = asyncClient.getResource();

		// Client provided callback when the resource becomes available
		deferredResult.addStateChangeListener(newState -> {
			LOGGER.info("Task completed. State change detected. Listening thread: {}", Thread.currentThread().getName());
			LOGGER.info("Task state is {}", newState);
		});

		//Do other things
		LOGGER.info("Doing other things {}", Thread.currentThread().getName());
		LOGGER.info("Done {}", Thread.currentThread().getName());

	}
}
