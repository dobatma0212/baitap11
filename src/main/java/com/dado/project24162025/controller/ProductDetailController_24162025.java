package com.dado.project24162025.controller;

import com.dado.project24162025.model.Product_24162025;
import com.dado.project24162025.service.IProductService_24162025;
import com.dado.project24162025.service.ProductService_24162025;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailController_24162025 extends HttpServlet {

    private final IProductService_24162025 productService = new ProductService_24162025();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }
        Product_24162025 product = productService.getProductDetail(id);
        if (product == null) {
            resp.sendRedirect(req.getContextPath() + "/products");
            return;
        }
        req.setAttribute("product", product);
        req.getRequestDispatcher("/views/productDetail_24162025.jsp").forward(req, resp);
    }
}
