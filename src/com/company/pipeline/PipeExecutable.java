package com.company.pipeline;

import java.util.concurrent.Callable;

public class PipeExecutable<E> implements Callable<E> {

    private PipeExecutableParameters<?> parameters;

    public PipeExecutable() {}

    public PipeExecutable(PipeExecutableParameters<?> parameters) {
        this();
        this.parameters = parameters;
    }

    @Override
    public E call() throws Exception {
        return null;
    }
}
