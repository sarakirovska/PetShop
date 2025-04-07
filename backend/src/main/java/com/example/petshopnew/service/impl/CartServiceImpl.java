package com.example.petshopnew.service.impl;



import com.example.petshopnew.entity.Cart;
import com.example.petshopnew.entity.CartItem;
import com.example.petshopnew.entity.PetShopUser;
import com.example.petshopnew.entity.Product;
import com.example.petshopnew.repository.CartItemRepository;
import com.example.petshopnew.repository.CartRepository;
import com.example.petshopnew.repository.PetShopUserRepository;
import com.example.petshopnew.repository.ProductRepository;
import com.example.petshopnew.service.CartService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final PetShopUserRepository petShopUserRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository,
                           ProductRepository productRepository, PetShopUserRepository petShopUserRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.petShopUserRepository = petShopUserRepository;
    }

    @Override
    public Cart createCart(Long userId) {
        PetShopUser user = petShopUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByPetShopUser_Id(userId)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setPetShopUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByPetShopUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }



    @Override
    public List<CartItem> getCartItems(Long userId) {
        Cart cart = getCartByUserId(userId);
        return cart.getCartItems();
    }

    @Override
    @Transactional
    public CartItem addProductToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            return cartItemRepository.save(cartItem);
        } else {
            CartItem newCartItem = new CartItem();
            newCartItem.setCart(cart);
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            cart.getCartItems().add(newCartItem);
            return cartItemRepository.save(newCartItem);
        }
    }



    @Override
    @Transactional
    public Cart updateCart(Long userId, Long cartItemId, int quantity) {
        Cart cart = getCartByUserId(userId);

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cart.getCartItems().contains(cartItem)) {
            throw new RuntimeException("Cart item does not belong to this cart");
        }

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);

        return cart;
    }

    @Override
    @Transactional
    public Cart removeCartItem(Long userId, Long cartItemId) {
        Cart cart = getCartByUserId(userId);

        cart.getCartItems().removeIf(cartItem -> {
            if (cartItem.getId().equals(cartItemId)) {
                cartItemRepository.delete(cartItem);
                return true;
            }
            return false;
        });

        return cartRepository.save(cart);
    }

    @Override
    public Long getCartIdByUserId(Long userId) {
        Cart cart = cartRepository.findByPetShopUser_Id(userId)
                .orElseThrow(() -> new RuntimeException("Cart not found for user with ID: " + userId));
        return cart.getId();
    }
    @Override
    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}
