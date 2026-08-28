package com.salesreporter.formatter;

import com.salesreporter.model.Product;
import com.salesreporter.report.SalesSummary;

import java.util.Map;


public class ReportFormatter {

    private static final String DIVIDER = "============================================";

    public String format(SalesSummary summary) {
        StringBuilder sb = new StringBuilder();

        sb.append(DIVIDER).append("\n");
        sb.append(" PRODUCT SALES SUMMARY REPORT\n");
        sb.append(DIVIDER).append("\n\n");

        sb.append("--- Revenue Per Product ---\n");
        for (Map.Entry<Product, Double> entry : summary.getRevenuePerProduct().entrySet()) {
            Product p = entry.getKey();
            sb.append(String.format("%-6s %-18s %-14s $%8.2f%n",
                    p.getProductId(), p.getProductName(), p.getCategory(), entry.getValue()));
        }

        sb.append("\n--- Revenue Per Category ---\n");
        for (Map.Entry<String, Double> entry : summary.getRevenuePerCategory().entrySet()) {
            sb.append(String.format("%-14s : $%.2f%n", entry.getKey(), entry.getValue()));
        }

        sb.append("\n--- Highlights ---\n");
        sb.append(String.format("Best-Selling Product : %s (%d units)%n",
                summary.getBestSellingProduct().getProductName(),
                summary.getBestSellingProduct().getQuantitySold()));
        sb.append(String.format("Highest Revenue      : %s ($%.2f)%n",
                summary.getHighestRevenueProduct().getProductName(),
                summary.getHighestRevenueProduct().getRevenue()));
        sb.append(String.format("Grand Total Revenue  : $%.2f%n", summary.getGrandTotalRevenue()));

        sb.append(DIVIDER).append("\n");

        return sb.toString();
    }
}
