package com.company.pipeline;

import static com.company.pipeline.PipelineStatus.STOPPED;

public class Pipe<E> extends Pipeline {
    private PipeExecutable<E> callable;
    protected PipelineStatus status = STOPPED;

    protected Pipe<?> nextPipe;

    private E result;

    public Pipe(PipeExecutable<E> callable) {
        this.callable = callable;
    }

    protected void startExecution() {
        new Thread(() -> {
            try {
                //first we execute the pipeline
                this.result = this.callable.call();

                //then we pass to the next level if there is any
                if (this.nextPipe != null) {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}
