package com.arun;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * Simple state machine which holds a single state.  Callers can register for
 * state change events, and can wait for the state to change.
 */
public class DeferredResult<T> {

	private String name;
    private Executor executor;
    private T state;
	private List<StateChangeListener<T>> stateChangeListeners = new ArrayList<>();
	
	public interface StateChangeListener<T> {
		void stateChanged(T state);
	}

	public DeferredResult(String name, Executor executor, T initialState) {
		this.name = name;
		this.executor = executor;
		this.state = initialState;
	}
	
	public T get() {
        return state;
    }
	
	/**
     * Sets the state.
     * If the new state does not {@code .equals()} the current state, listeners and waiters will be notified.
     *
     * @return the old state
     */
	public T set(T newState) {
		if (state.equals(newState)) {
            return state;
        }
		T oldState = state;
        state = newState;
        fireStateChanged(newState, stateChangeListeners);
        return oldState;
	}
	
	/**
	 * executor for firing state change events; must not be a same thread executor
	 * 
	 * @param newState
	 * @param stateChangeListeners
	 */
	private void fireStateChanged(T newState, List<StateChangeListener<T>> stateChangeListeners) {
        safeExecute(() -> {
            for (StateChangeListener<T> stateChangeListener : stateChangeListeners) {
                try {
                    stateChangeListener.stateChanged(newState);
                } catch (Throwable e) {
                    System.out.println("Error notifying state change listener for " + name);
                }
            }
        });
    }
	
	/**
     * Adds a listener to be notified when the state instance changes according to {@code .equals()}.
     */
    public void addStateChangeListener(StateChangeListener<T> stateChangeListener) {
    	stateChangeListeners.add(stateChangeListener);
    }
    
	private void safeExecute(Runnable command) {
        try {
            executor.execute(command);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
