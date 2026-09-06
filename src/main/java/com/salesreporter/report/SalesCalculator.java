package com.salesreporter.report;

import com.salesreporter.model.Product;
import java.util.List;

public class SalesCalculator {

    public SalesSummary calculate(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Product list must not be null or empty");
        }

        SalesSummary summary = new SalesSummary();
        double grandTotal = 0.0;
        Product bestSeller = products.get(0);
        Product highestRevenue = products.get(0);

        for (Product product : products) {
            double revenue = product.getRevenue();

            // Revenue per product
            summary.getRevenuePerProduct().put(product, revenue);

            // Revenue per category (accumulate)
            summary.getRevenuePerCategory().merge(product.getCategory(), revenue, Double::sum);

            // Grand total
            grandTotal += revenue;

            // Best-selling product (highest quantity_sold).
            // Tie-breaking: the first product encountered with the maximum
            // quantity is kept, since we only replace on strictly greater
            // than (not >=). This gives predictable, stable behavior when
            // two products have identical quantity_sold values.
            if (product.getQuantitySold() > bestSeller.getQuantitySold()) {
                bestSeller = product;
            }

            // Highest revenue product — same tie-breaking rule applies here:
            // first product with the max revenue wins in the event of a tie.
            if (revenue > highestRevenue.getRevenue()) {
                highestRevenue = product;
            }
        }

        summary.setBestSellingProduct(bestSeller);
        summary.setHighestRevenueProduct(highestRevenue);
        summary.setGrandTotalRevenue(grandTotal);

        return summary;
    }
}