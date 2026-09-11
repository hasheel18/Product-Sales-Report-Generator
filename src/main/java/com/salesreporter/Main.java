package com.salesreporter;

import com.salesreporter.exception.InvalidCsvException;
import com.salesreporter.exception.InvalidOutputMethodException;
import com.salesreporter.formatter.ReportFormatter;
import com.salesreporter.io.CsvProductReader;
import com.salesreporter.io.ProductReader;
import com.salesreporter.output.ReportOutput;
import com.salesreporter.output.ReportOutputFactory;
import com.salesreporter.report.SalesCalculator;
import com.salesreporter.service.SalesReportService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Error: Missing required arguments.");
            System.err.println(
                    "Usage: java -jar SalesReporter.jar " +
                            "<csv-file-path> <output-method> [output-file-path]"
            );
            System.exit(1);
        }

        String csvFilePath = args[0];
        String outputMethod = args[1];
        String outputFilePath =
                args.length >= 3 ? args[2] : null;

        try {

            /*
             * Composition root:
             * Dependencies are created here and injected into
             * the high-level SalesReportService.
             */

            ProductReader productReader =
                    new CsvProductReader();

            SalesCalculator salesCalculator =
                    new SalesCalculator();

            ReportFormatter reportFormatter =
                    new ReportFormatter();

            ReportOutput reportOutput =
                    ReportOutputFactory.create(
                            outputMethod,
                            outputFilePath
                    );

            SalesReportService service =
                    new SalesReportService(
                            productReader,
                            salesCalculator,
                            reportFormatter,
                            reportOutput
                    );

            service.generateReport(csvFilePath);

        } catch (InvalidCsvException e) {

            System.err.println(
                    "Error reading CSV file: "
                            + e.getMessage()
            );
            System.exit(1);

        } catch (InvalidOutputMethodException e) {

            System.err.println(
                    "Error: "
                            + e.getMessage()
            );
            System.exit(1);

        } catch (IOException e) {

            System.err.println(
                    "Error writing report: "
                            + e.getMessage()
            );
            System.exit(1);

        } catch (IllegalArgumentException e) {

            System.err.println(
                    "Error: "
                            + e.getMessage()
            );
            System.exit(1);

        } catch (Exception e) {

            System.err.println(
                    "Unexpected error: "
                            + e.getMessage()
            );
            System.exit(1);
        }
    }
}
