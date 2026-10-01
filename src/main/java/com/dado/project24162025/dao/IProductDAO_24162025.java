package com.dado.project24162025.dao;

import com.dado.project24162025.model.Product_24162025;
import java.util.List;

public interface IProductDAO_24162025 {

    List<Product_24162025> getAllGroupedBySeller();
    Product_24162025 getById(int productId);
}
