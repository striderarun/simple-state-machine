package com.arun;

public enum TaskState {
	
    QUEUED(false),
    PLANNING(false),
    STARTING(false),
    RUNNING(false),
    FINISHING(false),
    FINISHED(true),
    FAILED(true);

    private final boolean doneState;

    TaskState(boolean doneState) {
        this.doneState = doneState;
    }
    
    public boolean isDone() {
        return doneState;
    }
}
