package com.example.petshopnew.service;





import com.example.petshopnew.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(String name);
    Category updateCategory(Long id, String name);
    void deleteCategory(Long id);
    Category getCategory(Long id);
    List<Category> getCategories();
}

