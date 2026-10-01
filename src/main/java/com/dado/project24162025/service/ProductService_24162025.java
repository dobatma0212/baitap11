package com.dado.project24162025.service;

import com.dado.project24162025.dao.IProductDAO_24162025;
import com.dado.project24162025.dao.ProductDAO_24162025;
import com.dado.project24162025.model.Product_24162025;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductService_24162025 implements IProductService_24162025 {

    private final IProductDAO_24162025 productDAO = new ProductDAO_24162025();

    @Override
    public Map<Integer, List<Product_24162025>> getProductsGroupedBySeller() {
        List<Product_24162025> all = productDAO.getAllGroupedBySeller();
        Map<Integer, List<Product_24162025>> grouped = new LinkedHashMap<>();
        for (Product_24162025 p : all) {
            grouped.computeIfAbsent(p.getSellerId(), k -> new java.util.ArrayList<>()).add(p);
        }
        return grouped;
    }

    @Override
    public Product_24162025 getProductDetail(int productId) {
        return productDAO.getById(productId);
    }
}
