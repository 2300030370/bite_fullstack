package com.bite.controller;

import com.bite.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "http://localhost:3000")
public class TestController {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @GetMapping("/restaurants-count")
    public ResponseEntity<Map<String, Object>> getRestaurantCount() {
        Map<String, Object> response = new HashMap<>();
        long count = restaurantRepository.count();
        response.put("totalRestaurants", count);
        response.put("activeRestaurants", restaurantRepository.findByIsActiveTrue().size());
        return ResponseEntity.ok(response);
    }
}

