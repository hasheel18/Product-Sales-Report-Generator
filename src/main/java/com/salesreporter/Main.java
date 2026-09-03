package com.salesreporter;

import com.salesreporter.formatter.ReportFormatter;
import com.salesreporter.io.CsvProductReader;
import com.salesreporter.io.ProductReader;
import com.salesreporter.model.Product;
import com.salesreporter.report.SalesCalculator;
import com.salesreporter.report.SalesSummary;

import java.util.List;

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


        ProductReader reader = new CsvProductReader();
        List<Product> products = null;
        try {
            products = reader.readProducts(csvFilePath);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }

        SalesCalculator calculator = new SalesCalculator();
        SalesSummary summary = calculator.calculate(products);

        ReportFormatter formatter = new ReportFormatter();
        String reportText = formatter.format(summary);
        System.out.println(reportText);
    }
}