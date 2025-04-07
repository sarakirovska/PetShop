package com.example.petshopnew.controller;


import com.example.petshopnew.entity.Category;
import com.example.petshopnew.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Category category = categoryService.getCategory(id);
        if (category != null) {
            return ResponseEntity.ok().body(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category.getName());
        return ResponseEntity.ok().body(createdCategory);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }

        Category updatedCategory = categoryService.updateCategory(id, category.getName());
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
