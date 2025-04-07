package com.example.petshopnew.service.impl;




import com.example.petshopnew.entity.Cart;
import com.example.petshopnew.entity.CartItem;
import com.example.petshopnew.entity.Product;
import com.example.petshopnew.repository.CartItemRepository;

import com.example.petshopnew.repository.ProductRepository;
import com.example.petshopnew.service.CartItemService;
import com.example.petshopnew.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final CartService cartService;

    public CartItemServiceImpl(CartItemRepository cartItemRepository,
                               ProductRepository productRepository, CartService cartService) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.cartService = cartService;
    }

    @Override
    @Transactional
    public CartItem addCartItem(Long userId, Long productId, int quantity) {

        Cart cart = cartService.getCartByUserId(userId);


        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));


        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .product(product)
                .quantity(quantity)
                .build();

        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));

        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    @Override
    @Transactional
    public void deleteCartItem(Long cartItemId) {
        if (cartItemRepository.existsById(cartItemId)) {
            cartItemRepository.deleteById(cartItemId);
        } else {
            throw new RuntimeException("CartItem not found");
        }
    }


    @Override
    public CartItem getCartItem(Long cartItemId) {
        return cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
    }

    @Override
    public List<CartItem> getCartItemsByCart(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }
}
