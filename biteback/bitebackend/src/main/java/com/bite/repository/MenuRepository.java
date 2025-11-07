package com.bite.repository;

import com.bite.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByRestaurantId(Long restaurantId);
    List<Menu> findByRestaurantIdAndIsAvailableTrue(Long restaurantId);
}

