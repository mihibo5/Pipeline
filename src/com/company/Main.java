package com.company;

import com.company.pipeline.PipeExecutable;
import com.company.pipeline.PipeExecutableParameters;
import com.company.pipeline.Pipeline;
import com.company.pipeline.PipelineInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class Main implements PipelineInterface {

    public static void main(String[] args) {
        Main main = new Main();
        main.usePipeline();
    }

    private void usePipeline() {
        PipeExecutable<String, String>[] callables = new PipeExecutable[] {
                new PipeExecutable<String, String>() {
                    @Override
                    public String call() {
                        return Objects.requireNonNull(httpRequest(this.parameters.getValue().toString())).toString();
                    }
                },

                new PipeExecutable<String, String>() {
                    @Override
                    public String call() {
                        System.out.println(this.parameters.getValue());
                        return "test";
                    }
                }
        };

        Pipeline<String, String> pipeline = new Pipeline<>(this, callables, new PipeExecutableParameters<>("http://www.google.com"));
        pipeline.start();
    }

    private StringBuilder httpRequest(String url) {
        try {
            URL requestURL = new URL(url);
            URLConnection requestConnection = requestURL.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(requestConnection.getInputStream()));

            StringBuilder result = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }
            in.close();

            System.out.println("Finished loading page!");

            return result;


        } catch (IOException e) {
            System.out.println("FAILED!");
            return null;
        }
    }

    /**
     * Pipeline interface usage
     */
    @Override
    public void onExecutionStarted() {
        System.out.println("Pipeline started!");
    }

    @Override
    public void onPipeExecutionStarted(int level) {
        System.out.println("Level " + level + " of pipeline started!");
    }

    @Override
    public void onPipeExecutionFinished(int level) {
        System.out.println("Level " + level + " of pipeline finished!");

    }

    @Override
    public void onPipelineIterationFinished() {
        System.out.println("Pipeline iteration finished!");

    }
}
