package com.example.petshopnew.controller;

import com.example.petshopnew.entity.OrderItem;
import com.example.petshopnew.service.OrderItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }


    @PostMapping("/{orderId}/{productId}/{quantity}/{price}")
    public ResponseEntity<OrderItem> createOrderItem(@PathVariable Long orderId,
                                                     @PathVariable Long productId,
                                                     @PathVariable int quantity,
                                                     @PathVariable double price) {
        OrderItem orderItem = orderItemService.createOrderItem(orderId, productId, quantity, price);
        return new ResponseEntity<>(orderItem, HttpStatus.CREATED);
    }


    @PutMapping("/{orderItemId}/{quantity}/{price}")
    public ResponseEntity<OrderItem> updateOrderItem(@PathVariable Long orderItemId,
                                                     @PathVariable int quantity,
                                                     @PathVariable double price) {
        try {
            OrderItem updatedOrderItem = orderItemService.updateOrderItem(orderItemId, quantity, price);
            return new ResponseEntity<>(updatedOrderItem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }



    // Добиј OrderItem по ID
    @GetMapping("/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItem(@PathVariable Long orderItemId) {
        try {
            OrderItem orderItem = orderItemService.getOrderItem(orderItemId);
            return new ResponseEntity<>(orderItem, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrder(@PathVariable Long orderId) {
        try {
            List<OrderItem> orderItems = orderItemService.getOrderItemsByOrder(orderId);
            return new ResponseEntity<>(orderItems, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}