package com.bite.service;

import com.bite.model.Restaurant;
import com.bite.model.User;
import com.bite.repository.RestaurantRepository;
import com.bite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Restaurant> getAllActiveRestaurants() {
        return restaurantRepository.findByIsActiveTrue();
    }

    public List<Restaurant> getRestaurantsByOwner(Long ownerId) {
        return restaurantRepository.findByOwnerId(ownerId);
    }

    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    public Restaurant createRestaurant(Restaurant restaurant, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
        restaurant.setOwner(owner);
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Long id, Restaurant restaurantDetails) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        restaurant.setName(restaurantDetails.getName());
        restaurant.setDescription(restaurantDetails.getDescription());
        restaurant.setAddress(restaurantDetails.getAddress());
        restaurant.setPhone(restaurantDetails.getPhone());
        restaurant.setImageUrl(restaurantDetails.getImageUrl());
        restaurant.setIsActive(restaurantDetails.getIsActive());

        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }
}

