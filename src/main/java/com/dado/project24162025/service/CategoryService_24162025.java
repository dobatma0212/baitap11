package com.dado.project24162025.service;

import com.dado.project24162025.dao.CategoryDAO_24162025;
import com.dado.project24162025.dao.ICategoryDAO_24162025;
import com.dado.project24162025.model.Category_24162025;

import java.util.List;

public class CategoryService_24162025 implements ICategoryService_24162025 {

    private final ICategoryDAO_24162025 categoryDAO = new CategoryDAO_24162025();

    @Override
    public List<Category_24162025> getAllCategories() {
        return categoryDAO.getAll();
    }

    @Override
    public List<Category_24162025> getCategoriesPaged(int page, int pageSize) {
        return categoryDAO.getPaged(page, pageSize);
    }

    @Override
    public int countCategories() {
        return categoryDAO.countAll();
    }

    @Override
    public int totalPages(int pageSize) {
        int total = countCategories();
        return (int) Math.ceil(total / (double) pageSize);
    }

    @Override
    public Category_24162025 getCategoryById(int id) {
        return categoryDAO.getById(id);
    }

    @Override
    public boolean addCategory(Category_24162025 category) {
        return categoryDAO.insert(category);
    }

    @Override
    public boolean updateCategory(Category_24162025 category) {
        return categoryDAO.update(category);
    }

    @Override
    public boolean deleteCategory(int id) {
        return categoryDAO.delete(id);
    }
}
