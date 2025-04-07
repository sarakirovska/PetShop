package com.example.petshopnew.controller;


import com.example.petshopnew.entity.CartItem;
import com.example.petshopnew.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@CrossOrigin(origins = "http://localhost:3000")
public class CartItemController {

    private final CartItemService cartItemService;

    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/add/{userId}/{productId}/{quantity}")
    public ResponseEntity<CartItem> addCartItem(@PathVariable Long userId, @PathVariable Long productId, @PathVariable int quantity) {
        return ResponseEntity.ok(cartItemService.addCartItem(userId, productId, quantity));
    }

    @PutMapping("/update/{cartItemId}/{quantity}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long cartItemId, @PathVariable int quantity) {
        return ResponseEntity.ok(cartItemService.updateCartItem(cartItemId, quantity));
    }



    @GetMapping("/{cartItemId}")
    public ResponseEntity<CartItem> getCartItem(@PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartItemService.getCartItem(cartItemId));
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItemsByCart(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartItemService.getCartItemsByCart(cartId));
    }
}
