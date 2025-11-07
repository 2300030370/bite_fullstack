package com.bite.controller;

import com.bite.dto.CreateOrderRequest;
import com.bite.model.Order;
import com.bite.model.OrderStatus;
import com.bite.service.OrderService;
import com.bite.service.UserDetailsServiceImpl;
import com.bite.util.JwtUserExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserDetailsServiceImpl userService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            String username = JwtUserExtractor.getUsername();
            if (username == null) {
                return ResponseEntity.status(401).build();
            }
            Long userId = userService.getUserIdByUsername(username);
            Order order = orderService.createOrder(request, userId);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Order>> getMyOrders() {
        try {
            String username = JwtUserExtractor.getUsername();
            if (username == null) {
                return ResponseEntity.status(401).build();
            }
            Long userId = userService.getUserIdByUsername(username);
            return ResponseEntity.ok(orderService.getOrdersByUser(userId));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody OrderStatus status) {
        try {
            Order order = orderService.updateOrderStatus(id, status);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

