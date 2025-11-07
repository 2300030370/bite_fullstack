package com.bite.controller;

import com.bite.config.DataSeeder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class SeedController {

    @Autowired
    private DataSeeder dataSeeder;

    @PostMapping("/seed-data")
    public ResponseEntity<Map<String, String>> seedData() {
        try {
            // Note: This is a simple approach. In production, you'd want better control
            Map<String, String> response = new HashMap<>();
            response.put("message", "Data seeding should be done on application startup. Please restart the application to seed data.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}

