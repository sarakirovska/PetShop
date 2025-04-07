package com.example.petshopnew.controller;



import com.example.petshopnew.entity.Product;
import com.example.petshopnew.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        if (product != null) {
            return ResponseEntity.ok().body(product);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
        return productService.getProductsByCategory(categoryId);
    }


    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        if (product == null || product.getName() == null || product.getName().trim().isEmpty() ||
                product.getPrice() == null || product.getCategory() == null || product.getCategory().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Product createdProduct = productService.createProduct(product);
            return ResponseEntity.ok(createdProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PutMapping("/edit/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return ResponseEntity.ok().body(updatedProduct);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        try {
            System.out.println("Attempting to delete product with ID: " + id);
            productService.deleteProduct(id);
            System.out.println("Product deleted successfully.");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.out.println("Error deleting product: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
