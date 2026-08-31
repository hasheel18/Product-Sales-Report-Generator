package com.salesreporter.report;

import com.salesreporter.model.Product;
import java.util.LinkedHashMap;
import java.util.Map;

public class SalesSummary {

    private final Map<Product, Double> revenuePerProduct = new LinkedHashMap<>();
    private final Map<String, Double> revenuePerCategory = new LinkedHashMap<>();
    private Product bestSellingProduct;
    private Product highestRevenueProduct;
    private double grandTotalRevenue;

    public Map<Product, Double> getRevenuePerProduct() {
        return revenuePerProduct;
    }

    public Map<String, Double> getRevenuePerCategory() {
        return revenuePerCategory;
    }

    public Product getBestSellingProduct() {
        return bestSellingProduct;
    }

    public void setBestSellingProduct(Product bestSellingProduct) {
        this.bestSellingProduct = bestSellingProduct;
    }

    public Product getHighestRevenueProduct() {
        return highestRevenueProduct;
    }

    public void setHighestRevenueProduct(Product highestRevenueProduct) {
        this.highestRevenueProduct = highestRevenueProduct;
    }

    public double getGrandTotalRevenue() {
        return grandTotalRevenue;
    }

    public void setGrandTotalRevenue(double grandTotalRevenue) {
        this.grandTotalRevenue = grandTotalRevenue;
    }
}
