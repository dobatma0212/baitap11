package com.dado.project24162025.dao;

import com.dado.project24162025.model.Category_24162025;
import java.util.List;

public interface ICategoryDAO_24162025 {
    List<Category_24162025> getAll();
    List<Category_24162025> getPaged(int page, int pageSize);
    int countAll();
    Category_24162025 getById(int categoryId);
    boolean insert(Category_24162025 category);
    boolean update(Category_24162025 category);
    boolean delete(int categoryId);
}
