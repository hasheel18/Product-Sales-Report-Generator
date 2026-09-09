package com.salesreporter;

import com.salesreporter.model.Product;
import com.salesreporter.report.SalesCalculator;
import com.salesreporter.report.SalesSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SalesCalculatorTest {

    private SalesCalculator calculator;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        calculator = new SalesCalculator();
        products = List.of(
                new Product("P001", "Wireless Mouse", "Electronics", 12, 25.50),
                new Product("P002", "Notebook", "Stationery", 35, 3.75),
                new Product("P003", "USB Hub", "Electronics", 8, 18.00),
                new Product("P004", "Ballpoint Pen", "Stationery", 100, 0.50),
                new Product("P005", "HDMI Cable", "Electronics", 20, 12.00)
        );
    }

    @Test
    void calculatesRevenuePerProductCorrectly() {
        SalesSummary summary = calculator.calculate(products);

        for (Product p : products) {
            double expectedRevenue = p.getQuantitySold() * p.getUnitPrice();
            assertEquals(expectedRevenue, summary.getRevenuePerProduct().get(p), 0.001,
                    "Revenue mismatch for " + p.getProductName());
        }

        // Spot check against assignment example: Wireless Mouse -> $306.00
        Product mouse = products.get(0);
        assertEquals(306.00, summary.getRevenuePerProduct().get(mouse), 0.001);
    }

    @Test
    void calculatesRevenuePerCategoryCorrectly() {
        SalesSummary summary = calculator.calculate(products);

        // Electronics: 306.00 + 144.00 + 240.00 = 690.00
        assertEquals(690.00, summary.getRevenuePerCategory().get("Electronics"), 0.001);

        // Stationery: 131.25 + 50.00 = 181.25
        assertEquals(181.25, summary.getRevenuePerCategory().get("Stationery"), 0.001);
    }

    @Test
    void calculatesGrandTotalRevenueCorrectly() {
        SalesSummary summary = calculator.calculate(products);
        assertEquals(871.25, summary.getGrandTotalRevenue(), 0.001);
    }

    @Test
    void detectsBestSellingProductByQuantity() {
        SalesSummary summary = calculator.calculate(products);
        assertEquals("Ballpoint Pen", summary.getBestSellingProduct().getProductName());
        assertEquals(100, summary.getBestSellingProduct().getQuantitySold());
    }

    @Test
    void detectsHighestRevenueProduct() {
        SalesSummary summary = calculator.calculate(products);
        assertEquals("Wireless Mouse", summary.getHighestRevenueProduct().getProductName());
        assertEquals(306.00, summary.getHighestRevenueProduct().getRevenue(), 0.001);
    }

    @Test
    void singleProductListIsHandledCorrectly() {
        List<Product> single = List.of(new Product("P099", "Solo Item", "Misc", 5, 10.0));
        SalesSummary summary = calculator.calculate(single);

        assertEquals("Solo Item", summary.getBestSellingProduct().getProductName());
        assertEquals("Solo Item", summary.getHighestRevenueProduct().getProductName());
        assertEquals(50.0, summary.getGrandTotalRevenue(), 0.001);
    }

    @Test
    void throwsExceptionForEmptyProductList() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(List.of()));
    }

    @Test
    void throwsExceptionForNullProductList() {
        assertThrows(IllegalArgumentException.class, () -> calculator.calculate(null));
    }
}
