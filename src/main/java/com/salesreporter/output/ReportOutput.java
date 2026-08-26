package com.salesreporter.output;

import java.io.IOException;

public interface ReportOutput {
    void write(String reportText) throws IOException;
}
