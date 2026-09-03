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

            summary.getRevenuePerProduct().put(product, revenue);
            summary.getRevenuePerCategory().merge(product.getCategory(), revenue, Double::sum);

            grandTotal += revenue;

            if (product.getQuantitySold() > bestSeller.getQuantitySold()) {
                bestSeller = product;
            }

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