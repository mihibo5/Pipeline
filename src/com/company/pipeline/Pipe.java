package com.company.pipeline;

import static com.company.pipeline.PipelineStatus.*;

public class Pipe<E, T> {
    private PipeExecutable<E, T> callable;
    protected PipelineStatus status = STOPPED;

    private PipelineInterface pipelineInterface;

    private int level = -1;
    protected Pipe<?, T> previousPipe;
    protected Pipe<?, T> nextPipe;

    protected E result;

    public Pipe(PipeExecutable<E, T> callable, int level, PipelineInterface pipelineInterface) {
        this.pipelineInterface = pipelineInterface;
        this.callable = callable;
        this.level = level;
    }

    protected void startExecution(PipeExecutableParameters<?, T> parameters) {
        new Thread(() -> {
            try {
                this.status = RUNNING;
                this.pipelineInterface.onPipeExecutionStarted(this.level);

                //first we deal with parameters
                if (parameters != null) {
                    this.callable.parameters = (PipeExecutableParameters<E, T>) parameters;
                }

                //first we execute the pipeline
                this.result = this.callable.call();

                this.pipelineInterface.onPipeExecutionFinished(this.level);
                if (this.nextPipe != null) {
                    //then we pass to the next level
                    PipeExecutableParameters<?, T> newParameters = new PipeExecutableParameters(this.result);
                    this.nextPipe.startExecution(newParameters);

                    this.status = FINISHED;
                }
                else {
                    //there is no next level
                    this.status = STOPPED;
                    this.pipelineInterface.onPipelineIterationFinished();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }
}
