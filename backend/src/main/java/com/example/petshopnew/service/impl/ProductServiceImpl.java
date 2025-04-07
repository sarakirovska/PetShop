package com.example.petshopnew.service.impl;




import com.example.petshopnew.entity.Category;
import com.example.petshopnew.entity.Product;
import com.example.petshopnew.repository.CategoryRepository;
import com.example.petshopnew.repository.ProductRepository;
import com.example.petshopnew.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private  final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(Product product) {
        if (product.getCategory() != null && product.getCategory().getId() != null) {
            Category category = categoryRepository.findById(product.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategory(category);
        }
        return productRepository.save(product);
    }
    @Override
    public Product updateProduct(Long id, Product product) {

        Product existingProduct=productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setImageUrl(product.getImageUrl());
        existingProduct.setStockQuantity(product.getStockQuantity());
        existingProduct.setCategory(product.getCategory());
        return productRepository.save(existingProduct);

    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }
    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }
}
