package com.salesreporter.output;

/**
 * Writes the report to standard output.
 */
public class ConsoleReportOutput implements ReportOutput {

    @Override
    public void write(String reportText) {
        System.out.println(reportText);
    }
}
