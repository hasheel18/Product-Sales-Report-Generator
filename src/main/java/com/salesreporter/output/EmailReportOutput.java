package com.salesreporter.output;

import java.io.IOException;

/**
 * Demonstrates that adding a new output method requires only a new
 * ReportOutput implementation — no changes to existing classes (OCP).
 * Not wired into ReportOutputFactory for this assignment; included as
 * a proof of concept for extensibility.
 */
public class EmailReportOutput implements ReportOutput {
    private final String recipientEmail;

    public EmailReportOutput(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    @Override
    public void write(String reportText) throws IOException {
        System.out.println("[Simulated] Report would be emailed to: " + recipientEmail);
    }
}