package com.example.petshopnew.service;


import com.example.petshopnew.entity.Product;


import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
    Product getProduct(Long id);
    List<Product> getProducts();
    List<Product> getProductsByCategory(Long categoryId);

}
