package com.example.petshopnew.controller;

import com.example.petshopnew.entity.Cart;
import com.example.petshopnew.entity.CartItem;
import com.example.petshopnew.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Cart> createCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.createCart(userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Cart> getCartByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @GetMapping("/items/{userId}")
    public ResponseEntity<List<CartItem>> getCartItems(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartItems(userId));
    }

    @PostMapping("/add/{userId}/{productId}")
    public ResponseEntity<CartItem> addProductToCart(@PathVariable Long userId, @PathVariable Long productId, @RequestParam int quantity) {
        CartItem newCartItem = cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok(newCartItem);
    }

    @GetMapping("/cart-id/{userId}")
    public ResponseEntity<Long> getCartIdByUserId(@PathVariable Long userId) {
        Long cartId = cartService.getCartIdByUserId(userId);
        return ResponseEntity.ok(cartId);
    }



    @PutMapping("/update/{userId}/{cartItemId}/{quantity}")
    public ResponseEntity<Cart> updateCartItem(@PathVariable Long userId, @PathVariable Long cartItemId, @PathVariable int quantity) {
        return ResponseEntity.ok(cartService.updateCart(userId, cartItemId, quantity));
    }

    @DeleteMapping("/remove/{userId}/{cartItemId}")
    public ResponseEntity<Cart> removeCartItem(@PathVariable Long userId, @PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartService.removeCartItem(userId, cartItemId));
    }

    @DeleteMapping("/clear/{userId}")
    public ResponseEntity<Void> clearCart(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }
}
