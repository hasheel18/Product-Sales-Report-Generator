package com.salesreporter;

import com.salesreporter.exception.InvalidOutputMethodException;
import com.salesreporter.output.ReportOutput;
import com.salesreporter.output.ReportOutputFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for ReportOutputFactory, covering correct selection
 * of output strategy and graceful handling of invalid input.
 */
class ReportOutputFactoryTest {

    @Test
    void createsConsoleOutputForConsoleMethod() throws InvalidOutputMethodException {
        ReportOutput output = ReportOutputFactory.create("console", null);
        assertNotNull(output);
    }

    @Test
    void createsFileOutputForFileMethod() throws InvalidOutputMethodException {
        ReportOutput output = ReportOutputFactory.create("file", "report.txt");
        assertNotNull(output);
    }

    @Test
    void throwsExceptionForInvalidMethod() {
        assertThrows(InvalidOutputMethodException.class,
                () -> ReportOutputFactory.create("email", null));
    }

    @Test
    void throwsExceptionWhenFilePathMissing() {
        assertThrows(InvalidOutputMethodException.class,
                () -> ReportOutputFactory.create("file", null));
    }

    @Test
    void throwsExceptionWhenOutputMethodIsNull() {
        assertThrows(InvalidOutputMethodException.class,
                () -> ReportOutputFactory.create(null, null));
    }
}