package com.salesreporter.service;

import com.salesreporter.formatter.ReportFormatter;
import com.salesreporter.io.ProductReader;
import com.salesreporter.model.Product;
import com.salesreporter.output.ReportOutput;
import com.salesreporter.report.SalesCalculator;
import com.salesreporter.report.SalesSummary;


import java.util.List;

/**
 * High-level service responsible for coordinating the sales reporting process.

 * Dependencies are injected through the constructor rather than being
 * created inside this class. This demonstrates Dependency Inversion
 * and Dependency Injection.
 */
public class SalesReportService {

    private final ProductReader productReader;
    private final SalesCalculator salesCalculator;
    private final ReportFormatter reportFormatter;
    private final ReportOutput reportOutput;

    public SalesReportService(
            ProductReader productReader,
            SalesCalculator salesCalculator,
            ReportFormatter reportFormatter,
            ReportOutput reportOutput) {

        this.productReader = productReader;
        this.salesCalculator = salesCalculator;
        this.reportFormatter = reportFormatter;
        this.reportOutput = reportOutput;
    }

    /**
     * Executes the complete sales reporting process.
     */
    public void generateReport(String csvFilePath)
            throws Exception {

        // 1. Read products
        List<Product> products =
                productReader.readProducts(csvFilePath);

        // 2. Calculate sales
        SalesSummary summary =
                salesCalculator.calculate(products);

        // 3. Format report
        String reportText =
                reportFormatter.format(summary);

        // 4. Output report
        reportOutput.write(reportText);
    }
}

