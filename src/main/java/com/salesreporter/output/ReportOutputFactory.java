package com.salesreporter.output;

import com.salesreporter.exception.InvalidOutputMethodException;

/**
 * Creates the appropriate ReportOutput strategy based on the
 * user-supplied output method argument.
 *
 * This is the one place that needs to change when a brand-new
 * output method (e.g. "email") is introduced — the rest of the
 * application depends only on the ReportOutput interface.
 */
public class ReportOutputFactory {

    public static ReportOutput create(String outputMethod, String outputFilePath)
            throws InvalidOutputMethodException {

        if (outputMethod == null) {
            throw new InvalidOutputMethodException("Output method must not be null.");
        }

        switch (outputMethod.toLowerCase()) {
            case "console":
                return new ConsoleReportOutput();
            case "file":
                if (outputFilePath == null || outputFilePath.isBlank()) {
                    throw new InvalidOutputMethodException(
                            "Output file path is required when output-method is 'file'.");
                }
                return new FileReportOutput(outputFilePath);
            default:
                throw new InvalidOutputMethodException(
                        "Invalid output method: '" + outputMethod
                                + "'. Expected 'console' or 'file'.");
        }
    }
}
