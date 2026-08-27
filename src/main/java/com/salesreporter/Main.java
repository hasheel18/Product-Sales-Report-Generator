package com.salesreporter;

public class Main {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Error: Missing required arguments.");
            System.err.println("Usage: java -jar SalesReporter.jar <csv-file-path> <output-method> [output-file-path]");
            System.exit(1);
        }

        String csvFilePath = args[0];
        String outputMethod = args[1];
        String outputFilePath = args.length >= 3 ? args[2] : null;



    }
}