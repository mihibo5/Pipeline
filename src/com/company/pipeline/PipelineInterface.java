package com.company.pipeline;

public interface PipelineInterface {
    public void onExecutionStarted();
    public void onPipeExecutionStarted(int level);
    public void onPipeExecutionFinished(int level);
    public void onPipelineIterationFinished();
}
