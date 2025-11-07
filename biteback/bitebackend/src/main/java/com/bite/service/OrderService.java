package com.bite.service;

import com.bite.dto.CreateOrderRequest;
import com.bite.model.*;
import com.bite.repository.MenuRepository;
import com.bite.repository.OrderRepository;
import com.bite.repository.RestaurantRepository;
import com.bite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Transactional
    public Order createOrder(CreateOrderRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Order order = new Order();
        order.setUser(user);
        order.setRestaurant(restaurant);
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setPhone(request.getPhone());

        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0.0;

        for (CreateOrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            Menu menu = menuRepository.findById(itemRequest.getMenuId())
                    .orElseThrow(() -> new RuntimeException("Menu item not found: " + itemRequest.getMenuId()));

            if (!menu.getIsAvailable()) {
                throw new RuntimeException("Menu item is not available: " + menu.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenu(menu);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(menu.getPrice());
            orderItems.add(orderItem);

            totalAmount += menu.getPrice() * itemRequest.getQuantity();
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getOrdersByRestaurant(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}

