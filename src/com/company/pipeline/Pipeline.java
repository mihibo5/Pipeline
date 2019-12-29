package com.company.pipeline;

import java.util.LinkedList;

public class Pipeline {
    private PipelineInterface pipelineInterface;

    private int pipelineLevel = 1;
    private LinkedList<Pipe<?>> pipeline;

    public Pipeline(PipelineInterface pipelineInterface) {
        this.pipeline = new LinkedList<>();
        this.pipelineInterface = pipelineInterface;
    }

    public Pipeline(PipelineInterface pipelineInterface, PipeExecutable<?>[] listOfExecutables) {
        this(pipelineInterface);
        this.pipelineLevel = listOfExecutables.length;

        for (PipeExecutable<?> callable: listOfExecutables) {
            Pipe<?> pipe = new Pipe<>(callable);
            this.pipeline.add(pipe);
        }

        for (int i = 0; i < pipeline.size() - 1; i++) {
            this.pipeline.get(i).nextPipe = pipeline.get(i + 1);
        }
    }

    public Pipeline() { }

    public void start() {
        this.pipelineInterface.onExecutionStarted();
        this.pipeline.getFirst().startExecution();
    }
}
