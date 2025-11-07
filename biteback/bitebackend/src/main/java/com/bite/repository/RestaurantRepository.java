package com.bite.repository;

import com.bite.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByIsActiveTrue();
    List<Restaurant> findByOwnerId(Long ownerId);
}

