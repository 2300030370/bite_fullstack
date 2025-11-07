package com.bite.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public class CreateOrderRequest {
    @NotNull
    private Long restaurantId;

    @NotBlank
    private String deliveryAddress;

    @NotBlank
    private String phone;

    @NotNull
    private List<OrderItemRequest> items;

    public CreateOrderRequest() {}

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }

    public static class OrderItemRequest {
        @NotNull
        private Long menuId;

        @NotNull
        @Positive
        private Integer quantity;

        public OrderItemRequest() {}

        public Long getMenuId() {
            return menuId;
        }

        public void setMenuId(Long menuId) {
            this.menuId = menuId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}

