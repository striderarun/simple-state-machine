package com.arun;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class QueryStateMachine {

	public static void main(String args[]) {
		Executor executor = Executors.newCachedThreadPool();
		StateMachine<QueryState> queryState = new StateMachine<QueryState>("test", executor, QueryState.QUEUED);
		queryState.addStateChangeListener(newState -> {
			System.out.println(String.format("State change detected. Listening thread: %s", Thread.currentThread().getName()));
			System.out.println(String.format("Query state is %s", newState));
		});
		queryState.set(QueryState.PLANNING);
		try {
			Thread.sleep(2000);
			System.out.println("Do other things " + Thread.currentThread().getName());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
}
