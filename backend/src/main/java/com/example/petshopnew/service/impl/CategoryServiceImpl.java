package com.example.petshopnew.service.impl;

import com.example.petshopnew.entity.Category;
import com.example.petshopnew.repository.CategoryRepository;
import com.example.petshopnew.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(String name) {

        if (categoryRepository.existsByName(name)) {
            throw new RuntimeException("Category with this name already exists");
        }

        Category category = new Category();
        category.setName(name);
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, String name) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        category.setName(name);
        return categoryRepository.save(category);
    }


    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }
}
