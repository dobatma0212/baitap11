package com.dado.project24162025.service;

import com.dado.project24162025.model.Product_24162025;
import java.util.List;
import java.util.Map;

public interface IProductService_24162025 {

    Map<Integer, List<Product_24162025>> getProductsGroupedBySeller();
    Product_24162025 getProductDetail(int productId);
}
