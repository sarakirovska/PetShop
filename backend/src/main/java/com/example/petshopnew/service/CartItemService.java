package com.example.petshopnew.service;




import com.example.petshopnew.entity.CartItem;

import java.util.List;

public interface CartItemService {
    CartItem addCartItem(Long userId, Long productId, int quantity);
    CartItem updateCartItem(Long cartItemId, int quantity);
    void deleteCartItem(Long cartItemId);
    CartItem getCartItem(Long cartItemId);
    List<CartItem> getCartItemsByCart(Long cartId);
}
