package com.bite.controller;

import com.bite.model.Restaurant;
import com.bite.service.RestaurantService;
import com.bite.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurants")
@CrossOrigin(origins = "http://localhost:3000")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserDetailsServiceImpl userService;

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantService.getAllActiveRestaurants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Optional<Restaurant> restaurant = restaurantService.getRestaurantById(id);
        return restaurant.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
        try {
            String username = com.bite.util.JwtUserExtractor.getUsername();
            if (username == null) {
                return ResponseEntity.status(401).build();
            }
            Long userId = userService.getUserIdByUsername(username);
            Restaurant created = restaurantService.createRestaurant(restaurant, userId);
            return ResponseEntity.ok(created);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        try {
            Restaurant updated = restaurantService.updateRestaurant(id, restaurant);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}

