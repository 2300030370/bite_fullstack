package com.bite.controller;

import com.bite.model.Menu;
import com.bite.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/menus")
@CrossOrigin(origins = "http://localhost:3000")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<List<Menu>> getMenusByRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(menuService.getMenusByRestaurant(restaurantId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getMenuById(@PathVariable Long id) {
        Optional<Menu> menu = menuService.getMenuById(id);
        return menu.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Menu> createMenu(@PathVariable Long restaurantId, @RequestBody Menu menu) {
        Menu created = menuService.createMenu(menu, restaurantId);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Menu> updateMenu(@PathVariable Long id, @RequestBody Menu menu) {
        try {
            Menu updated = menuService.updateMenu(id, menu);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long id) {
        menuService.deleteMenu(id);
        return ResponseEntity.noContent().build();
    }
}

