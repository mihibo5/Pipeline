package com.company.pipeline;

import static com.company.pipeline.PipelineStatus.STOPPED;

public class Pipe<E> extends Pipeline {
    private PipeExecutable<E> callable;
    protected PipelineStatus status = STOPPED;

    private E result;

    public Pipe(PipeExecutable<E> callable) {
        this.callable = callable;
    }

    protected void startExecution() {
        new Thread(() -> {
            try {
                result = this.callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}
