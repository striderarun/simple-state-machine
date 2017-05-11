package com.arun;

public enum QueryState {
	
    QUEUED(false),
    PLANNING(false),
    STARTING(false),
    RUNNING(false),
    FINISHING(false),
    FINISHED(true),
    FAILED(true);

    private final boolean doneState;

    QueryState(boolean doneState) {
        this.doneState = doneState;
    }
    
    public boolean isDone() {
        return doneState;
    }
}
