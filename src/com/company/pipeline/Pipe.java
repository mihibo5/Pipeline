package com.company.pipeline;

import static com.company.pipeline.PipelineStatus.STOPPED;

public class Pipe<E> {
    private PipeExecutable<E> callable;
    protected PipelineStatus status = STOPPED;

    private PipelineInterface pipelineInterface;

    private int level = -1;
    protected Pipe<?> nextPipe;

    private E result;

    public Pipe(PipeExecutable<E> callable, int level, PipelineInterface pipelineInterface) {
        this.pipelineInterface = pipelineInterface;
        this.callable = callable;
        this.level = level;
    }

    protected void startExecution() {
        new Thread(() -> {
            try {
                this.pipelineInterface.onPipeExecutionStarted(this.level);

                //first we execute the pipeline
                this.result = this.callable.call();

                //then we pass to the next level if there is any
                this.pipelineInterface.onPipeExecutionFinished(this.level);
                if (this.nextPipe != null) {

                }
                else {
                    this.pipelineInterface.onPipelineIterationFinished();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}
