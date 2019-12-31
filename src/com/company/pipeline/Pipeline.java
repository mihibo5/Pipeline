package com.company.pipeline;

import java.util.LinkedList;

public class Pipeline<E, T> {
    protected PipelineInterface pipelineInterface;

    private int pipelineLevel = 1;
    private LinkedList<Pipe<?, E>> pipeline;
    private PipeExecutableParameters<E, T> initialParameters;

    public Pipeline(PipelineInterface pipelineInterface) {
        this.pipeline = new LinkedList<>();
        this.pipelineInterface = pipelineInterface;
    }

    public Pipeline(PipelineInterface pipelineInterface, PipeExecutable<?, E>[] listOfExecutables) {
        this(pipelineInterface);
        this.pipelineLevel = listOfExecutables.length;

        int index = 0;
        for (PipeExecutable<?, E> callable: listOfExecutables) {
            Pipe<?, E> pipe = new Pipe<>(callable, index, this.pipelineInterface);
            this.pipeline.add(pipe);
            index++;
        }

        for (int i = 0; i < pipeline.size(); i++) {
            if (i != 0 && i < pipeline.size() - 1) {
                this.pipeline.get(i).previousPipe = pipeline.get(i - 1);
                this.pipeline.get(i).nextPipe = pipeline.get(i + 1);
            }
            else if (i == pipeline.size() - 1) {
                this.pipeline.get(i).previousPipe = pipeline.get(i - 1);
            }
            else {
                this.pipeline.get(i).nextPipe = pipeline.get(i + 1);
            }
        }
    }

    public Pipeline(PipelineInterface pipelineInterface, PipeExecutable<?, E>[] listOfExecutables, PipeExecutableParameters<E, T> parameters) {
        this(pipelineInterface, listOfExecutables);
        this.initialParameters = parameters;
    }

    public void start() {
        this.pipelineInterface.onExecutionStarted();
        this.pipeline.getFirst().startExecution((PipeExecutableParameters<?, E>) this.initialParameters);
    }
}
