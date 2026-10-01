package com.dado.project24162025.controller;

import com.dado.project24162025.model.Category_24162025;
import com.dado.project24162025.service.ICategoryService_24162025;
import com.dado.project24162025.service.CategoryService_24162025;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryCrudController_24162025 extends HttpServlet {

    private final ICategoryService_24162025 categoryService = new CategoryService_24162025();
    private static final int PAGE_SIZE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "add":
                req.getRequestDispatcher("/views/admin/categoryForm_24162025.jsp").forward(req, resp);
                break;
            case "edit":
                int editId = Integer.parseInt(req.getParameter("id"));
                Category_24162025 c = categoryService.getCategoryById(editId);
                req.setAttribute("category", c);
                req.getRequestDispatcher("/views/admin/categoryForm_24162025.jsp").forward(req, resp);
                break;
            case "delete":
                int delId = Integer.parseInt(req.getParameter("id"));
                categoryService.deleteCategory(delId);
                resp.sendRedirect(req.getContextPath() + "/admin/categories");
                break;
            default:
                listCategories(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("save".equals(action)) {
            String idParam = req.getParameter("categoryId");
            Category_24162025 c = new Category_24162025();
            c.setCategoryName(req.getParameter("categoryName"));
            c.setImages(req.getParameter("images"));
            c.setStatus(Integer.parseInt(req.getParameter("status")));

            if (idParam == null || idParam.trim().isEmpty()) {
                categoryService.addCategory(c);
            } else {
                c.setCategoryId(Integer.parseInt(idParam));
                categoryService.updateCategory(c);
            }
            resp.sendRedirect(req.getContextPath() + "/admin/categories");
            return;
        }
        doGet(req, resp);
    }

    private void listCategories(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = 1;
        try {
            if (req.getParameter("page") != null) page = Integer.parseInt(req.getParameter("page"));
        } catch (NumberFormatException ignored) {}
        if (page < 1) page = 1;

        int totalPages = Math.max(1, categoryService.totalPages(PAGE_SIZE));
        if (page > totalPages) page = totalPages;

        List<Category_24162025> categories = categoryService.getCategoriesPaged(page, PAGE_SIZE);

        req.setAttribute("categories", categories);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.getRequestDispatcher("/views/admin/categoryList_24162025.jsp").forward(req, resp);
    }
}
