package com.company.pipeline;

import java.util.LinkedList;

public class Pipeline {
    private int pipelineLevel = 1;
    private LinkedList<Pipe<?>> pipeline;

    public Pipeline() {
        this.pipeline = new LinkedList<>();
    }

    public Pipeline(PipeExecutable<?>[] listOfExecutables) {
        this();
        this.pipelineLevel = listOfExecutables.length;

        for (PipeExecutable<?> callable: listOfExecutables) {
            this.pipeline.add(new Pipe(callable));
        }
    }

    public void start() {
        pipeline.getFirst().startExecution();
    }
}
