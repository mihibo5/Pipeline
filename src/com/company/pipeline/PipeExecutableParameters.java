package com.company.pipeline;

import java.util.HashMap;

public class PipeExecutableParameters<E, T> {
    //HashMap<T, Object> parameters = new HashMap<>();
    T parameters;

    public PipeExecutableParameters(T value) {
        this.parameters = value;
    }

    public Object getValue() {
        return this.parameters;
    }

    public int getSize() {
        //return parameters.size();
        return parameters.toString().length();
    }
}
