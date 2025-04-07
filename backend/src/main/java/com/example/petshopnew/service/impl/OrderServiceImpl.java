package com.example.petshopnew.service.impl;




import com.example.petshopnew.entity.*;
import com.example.petshopnew.entity.enums.OrderStatus;
import com.example.petshopnew.repository.OrderItemRepository;
import com.example.petshopnew.repository.OrderRepository;
import com.example.petshopnew.repository.PetShopUserRepository;
import com.example.petshopnew.repository.ProductRepository;
import com.example.petshopnew.service.CartService;
import com.example.petshopnew.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartService cartService;
    private final PetShopUserRepository petShopUserRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderItemRepository orderItemRepository,
                            CartService cartService,
                            PetShopUserRepository petShopUserRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartService = cartService;
        this.petShopUserRepository = petShopUserRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Order createOrder(Long userId) {

        PetShopUser user = petShopUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));


        Cart cart = cartService.getCartByUserId(userId);
        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty, cannot place order");
        }


        double totalPrice = cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();


        Order order = new Order();
        order.setPetShopUser(user);
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(java.time.LocalDateTime.now());


        order = orderRepository.save(order);


        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : cart.getCartItems()) {

            OrderItem orderItem = new OrderItem(
                    null,
                    order,
                    cartItem.getProduct(),
                    cartItem.getQuantity(),
                    cartItem.getProduct().getPrice() * cartItem.getQuantity()
            );
            orderItems.add(orderItem);
        }


        orderItemRepository.saveAll(orderItems);


        order.setOrderItems(orderItems);


        cartService.clearCart(userId);


        return orderRepository.save(order);
    }


    @Override
    @Transactional
    public Order updateOrder(Long id, OrderStatus status) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));


        if (status == OrderStatus.COMPLETED) {
            for (OrderItem item : existingOrder.getOrderItems()) {
                Product product = item.getProduct();
                int newQuantity = product.getStockQuantity() - item.getQuantity();


                if (newQuantity < 0) {
                    throw new RuntimeException("Not enough stock for product " + product.getName());
                }


                product.setStockQuantity(newQuantity);
                productRepository.save(product);
            }
        }

        existingOrder.setStatus(status);


        return orderRepository.save(existingOrder);
    }


    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findBypetShopUserId(userId);
    }
}
