package com.bite.service;

import com.bite.model.Menu;
import com.bite.model.Restaurant;
import com.bite.repository.MenuRepository;
import com.bite.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Menu> getMenusByRestaurant(Long restaurantId) {
        return menuRepository.findByRestaurantIdAndIsAvailableTrue(restaurantId);
    }

    public Optional<Menu> getMenuById(Long id) {
        return menuRepository.findById(id);
    }

    public Menu createMenu(Menu menu, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        menu.setRestaurant(restaurant);
        return menuRepository.save(menu);
    }

    public Menu updateMenu(Long id, Menu menuDetails) {
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Menu item not found"));

        menu.setName(menuDetails.getName());
        menu.setDescription(menuDetails.getDescription());
        menu.setPrice(menuDetails.getPrice());
        menu.setImageUrl(menuDetails.getImageUrl());
        menu.setCategory(menuDetails.getCategory());
        menu.setIsAvailable(menuDetails.getIsAvailable());

        return menuRepository.save(menu);
    }

    public void deleteMenu(Long id) {
        menuRepository.deleteById(id);
    }
}

