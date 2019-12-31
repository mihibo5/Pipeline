package com.company.pipeline;

import java.util.concurrent.Callable;

public class PipeExecutable<E, T> implements Callable<E> {

    public PipeExecutableParameters<E, T> parameters;

    public PipeExecutable() {}

    public PipeExecutable(PipeExecutableParameters<E, T> parameters) {
        this();
        this.parameters = parameters;
    }

    @Override
    public E call() throws Exception {
        return null;
    }


}
