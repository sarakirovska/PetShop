package com.example.petshopnew.service;




import com.example.petshopnew.entity.Cart;
import com.example.petshopnew.entity.CartItem;

import java.util.List;
public interface CartService {
    Cart createCart(Long userId);
    Cart getCartByUserId(Long userId);
    Long getCartIdByUserId(Long userId);
    List<CartItem> getCartItems(Long userId);
    CartItem addProductToCart(Long userId, Long productId, int quantity);

    Cart updateCart(Long userId, Long cartItemId, int quantity);
    Cart removeCartItem(Long userId, Long cartItemId);
    void clearCart(Long userId);
}
