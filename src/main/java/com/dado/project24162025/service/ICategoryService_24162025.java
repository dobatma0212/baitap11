package com.dado.project24162025.service;

import com.dado.project24162025.model.Category_24162025;
import java.util.List;

public interface ICategoryService_24162025 {
    List<Category_24162025> getAllCategories();
    List<Category_24162025> getCategoriesPaged(int page, int pageSize);
    int countCategories();
    int totalPages(int pageSize);
    Category_24162025 getCategoryById(int id);
    boolean addCategory(Category_24162025 category);
    boolean updateCategory(Category_24162025 category);
    boolean deleteCategory(int id);
}
