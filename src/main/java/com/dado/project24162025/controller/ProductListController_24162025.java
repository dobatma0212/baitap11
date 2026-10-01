package com.dado.project24162025.controller;

import com.dado.project24162025.model.Product_24162025;
import com.dado.project24162025.service.IProductService_24162025;
import com.dado.project24162025.service.ProductService_24162025;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ProductListController_24162025 extends HttpServlet {

    private final IProductService_24162025 productService = new ProductService_24162025();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<Integer, List<Product_24162025>> grouped = productService.getProductsGroupedBySeller();
        req.setAttribute("groupedProducts", grouped);
        req.getRequestDispatcher("/views/products_24162025.jsp").forward(req, resp);
    }
}
