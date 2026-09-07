package com.salesreporter;

import com.salesreporter.exception.InvalidCsvException;
import com.salesreporter.exception.InvalidOutputMethodException;
import com.salesreporter.formatter.ReportFormatter;
import com.salesreporter.io.CsvProductReader;
import com.salesreporter.io.ProductReader;
import com.salesreporter.model.Product;
import com.salesreporter.output.ReportOutput;
import com.salesreporter.output.ReportOutputFactory;
import com.salesreporter.report.SalesCalculator;
import com.salesreporter.report.SalesSummary;

import java.io.IOException;
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

        try {
            // 1. Read
            ProductReader reader = new CsvProductReader();
            List<Product> products = reader.readProducts(csvFilePath);

            // 2. Calculate
            SalesCalculator calculator = new SalesCalculator();
            SalesSummary summary = calculator.calculate(products);

            // 3. Format
            ReportFormatter formatter = new ReportFormatter();
            String reportText = formatter.format(summary);

            // 4. Output (strategy chosen based on CLI argument)
            ReportOutput output = ReportOutputFactory.create(outputMethod, outputFilePath);
            output.write(reportText);

        } catch (InvalidCsvException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
            System.exit(1);
        } catch (InvalidOutputMethodException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error writing report: " + e.getMessage());
            System.exit(1);
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
