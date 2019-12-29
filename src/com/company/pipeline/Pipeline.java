package com.company.pipeline;

import java.util.LinkedList;

public class Pipeline {
    protected PipelineInterface pipelineInterface;

    private int pipelineLevel = 1;
    private LinkedList<Pipe<?>> pipeline;
    private PipeExecutableParameters<?> initialParameters;

    public Pipeline(PipelineInterface pipelineInterface) {
        this.pipeline = new LinkedList<>();
        this.pipelineInterface = pipelineInterface;
    }

    public Pipeline(PipelineInterface pipelineInterface, PipeExecutable<?>[] listOfExecutables) {
        this(pipelineInterface);
        this.pipelineLevel = listOfExecutables.length;

        int index = 0;
        for (PipeExecutable<?> callable: listOfExecutables) {
            Pipe<?> pipe = new Pipe<>(callable, index, this.pipelineInterface);
            this.pipeline.add(pipe);
            index++;
        }

        for (int i = 0; i < pipeline.size() - 1; i++) {
            this.pipeline.get(i).nextPipe = pipeline.get(i + 1);
        }
    }

    public Pipeline(PipelineInterface pipelineInterface, PipeExecutable<?>[] listOfExecutables, PipeExecutableParameters<?> parameters) {
        this(pipelineInterface, listOfExecutables);
        this.initialParameters = parameters;
    }

    public void start() {
        this.pipelineInterface.onExecutionStarted();
        this.pipeline.getFirst().startExecution(this.initialParameters);
    }
}
