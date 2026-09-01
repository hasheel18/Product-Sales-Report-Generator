package com.salesreporter.io;

import com.salesreporter.exception.InvalidCsvException;
import com.salesreporter.model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvProductReader implements ProductReader {

    private static final int EXPECTED_COLUMNS = 5;

    @Override
    public List<Product> readProducts(String sourcePath) throws InvalidCsvException {

        Path path = Path.of(sourcePath);

        if (!Files.exists(path)) {
            throw new InvalidCsvException("CSV file not found: " + sourcePath);
        }

        List<Product> products = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(sourcePath))) {

            String line;
            int lineNumber = 0;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {

                lineNumber++;

                if (line.isBlank()) {
                    continue;
                }

                if (firstLine) {
                    firstLine = false;
                    if (isHeaderRow(line)) {
                        continue;
                    }
                }

                products.add(parseLine(line, lineNumber));
            }
        } catch (IOException e) {
            throw new InvalidCsvException("Unable to read CSV file: " + sourcePath, e);
        }

        if (products.isEmpty()) {
            throw new InvalidCsvException("CSV file contains no product data: " + sourcePath);
        }

        return products;
    }

    private boolean isHeaderRow(String line) {
        String firstColumn = line.split(",")[0].trim().toLowerCase();
        return firstColumn.equals("product_id");
    }

    private Product parseLine(String line, int lineNumber) throws InvalidCsvException {
        String[] columns = line.split(",");

        if (columns.length < EXPECTED_COLUMNS) {
            throw new InvalidCsvException(
                    "Row " + lineNumber + " has missing columns (expected " + EXPECTED_COLUMNS + ", found " + columns.length + "): " + line);
        }

        try {
            String productId = columns[0].trim();
            String productName = columns[1].trim();
            String category = columns[2].trim();
            int quantitySold = Integer.parseInt(columns[3].trim());
            double unitPrice = Double.parseDouble(columns[4].trim());

            return new Product(productId, productName, category, quantitySold, unitPrice);
        } catch (NumberFormatException e) {
            throw new InvalidCsvException(
                    "Row " + lineNumber + " contains an invalid numeric value: " + line, e);
        }
    }
}
