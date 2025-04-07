package com.example.petshopnew.service;





import com.example.petshopnew.entity.Order;
import com.example.petshopnew.entity.enums.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(Long userId);
    Order updateOrder(Long id, OrderStatus status);
    void deleteOrder(Long id);
    Order getOrder(Long id);
    List<Order> getOrders();
    List<Order> getOrdersByUser(Long userId);


}
