package com.example.petshopnew.service;




import com.example.petshopnew.entity.OrderItem;

import java.util.List;

public interface OrderItemService {
    OrderItem createOrderItem(Long orderId, Long productId, int quantity, double price);
    OrderItem updateOrderItem(Long orderItemId, int quantity, double price);
    void deleteOrderItem(Long orderItemId);
    OrderItem getOrderItem(Long orderItemId);
    List<OrderItem> getOrderItemsByOrder(Long orderId);
}
