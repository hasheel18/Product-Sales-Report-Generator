package com.salesreporter.output;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileReportOutput implements ReportOutput {

    private final String outputFilePath;

    public FileReportOutput(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void write(String reportText) throws IOException {
        Path path = Path.of(outputFilePath);
        Files.writeString(path, reportText, StandardCharsets.UTF_8);
        System.out.println("Report successfully written to: " + path.toAbsolutePath());
    }
}
