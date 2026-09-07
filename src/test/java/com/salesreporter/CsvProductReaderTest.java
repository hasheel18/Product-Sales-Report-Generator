package com.salesreporter;

import com.salesreporter.exception.InvalidCsvException;
import com.salesreporter.io.CsvProductReader;
import com.salesreporter.model.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for CsvProductReader, covering successful parsing
 * and graceful handling of missing files / malformed rows.
 */
class CsvProductReaderTest {

    private final CsvProductReader reader = new CsvProductReader();

    @Test
    void readsValidCsvFileCorrectly(@TempDir Path tempDir) throws IOException, InvalidCsvException {
        Path csvFile = tempDir.resolve("valid.csv");
        Files.writeString(csvFile,
                "product_id, product_name, category, quantity_sold, unit_price\n" +
                        "P001, Wireless Mouse, Electronics, 12, 25.50\n" +
                        "P002, Notebook, Stationery, 35, 3.75\n");

        List<Product> products = reader.readProducts(csvFile.toString());

        assertEquals(2, products.size());
        assertEquals("P001", products.get(0).getProductId());
        assertEquals("Wireless Mouse", products.get(0).getProductName());
        assertEquals(12, products.get(0).getQuantitySold());
        assertEquals(25.50, products.get(0).getUnitPrice(), 0.001);
    }

    @Test
    void skipsHeaderRowAutomatically(@TempDir Path tempDir) throws IOException, InvalidCsvException {
        Path csvFile = tempDir.resolve("header.csv");
        Files.writeString(csvFile,
                "product_id, product_name, category, quantity_sold, unit_price\n" +
                        "P001, Wireless Mouse, Electronics, 12, 25.50\n");

        List<Product> products = reader.readProducts(csvFile.toString());

        assertEquals(1, products.size());
        assertNotEquals("product_id", products.get(0).getProductId());
    }

    @Test
    void throwsExceptionWhenFileDoesNotExist() {
        InvalidCsvException exception = assertThrows(InvalidCsvException.class,
                () -> reader.readProducts("nonexistent_file.csv"));

        assertTrue(exception.getMessage().contains("not found"));
    }

    @Test
    void throwsExceptionForRowWithMissingColumns(@TempDir Path tempDir) throws IOException {
        Path csvFile = tempDir.resolve("malformed.csv");
        Files.writeString(csvFile,
                "product_id, product_name, category, quantity_sold, unit_price\n" +
                        "P001, Wireless Mouse, Electronics, 12\n"); // missing unit_price

        assertThrows(InvalidCsvException.class, () -> reader.readProducts(csvFile.toString()));
    }

    @Test
    void throwsExceptionForEmptyFile(@TempDir Path tempDir) throws IOException {
        Path csvFile = tempDir.resolve("empty.csv");
        Files.writeString(csvFile, "");

        assertThrows(InvalidCsvException.class, () -> reader.readProducts(csvFile.toString()));
    }

    @Test
    void throwsExceptionForInvalidNumericValue(@TempDir Path tempDir) throws IOException {
        Path csvFile = tempDir.resolve("badnumber.csv");
        Files.writeString(csvFile,
                "product_id, product_name, category, quantity_sold, unit_price\n" +
                        "P001, Wireless Mouse, Electronics, not_a_number, 25.50\n");

        assertThrows(InvalidCsvException.class, () -> reader.readProducts(csvFile.toString()));
    }
}
