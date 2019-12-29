package com.company;

import com.company.pipeline.PipeExecutable;
import com.company.pipeline.Pipeline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        PipeExecutable<?>[] callables = {
            new PipeExecutable<String>() {
                @Override
                public String call() {
                    return Objects.requireNonNull(httpRequest("http://www.google.com")).toString();
                }
            },

            new PipeExecutable<String>() {
                @Override
                public String call() {
                    return "test";
                }
            }
        };

        Pipeline pipeline = new Pipeline(callables);
        pipeline.start();

        System.out.println("testing");
    }

    private static StringBuilder httpRequest(String url) {
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
}
