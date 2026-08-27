package com.salesreporter.io;

import com.salesreporter.exception.InvalidCsvException;
import com.salesreporter.model.Product;

import java.util.List;

public interface ProductReader {

    List<Product> readProducts(String sourcePath) throws InvalidCsvException;

}
