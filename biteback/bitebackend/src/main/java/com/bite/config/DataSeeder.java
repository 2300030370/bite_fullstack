package com.bite.config;

import com.bite.model.*;
import com.bite.repository.MenuRepository;
import com.bite.repository.RestaurantRepository;
import com.bite.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        if (restaurantRepository.count() > 0) {
            System.out.println("Data already exists (" + restaurantRepository.count() + " restaurants). Skipping seed...");
            return;
        }
        
        System.out.println("Starting data seeding...");

        // Create a restaurant owner user
        User restaurantOwner = new User();
        restaurantOwner.setUsername("restowner");
        restaurantOwner.setEmail("owner@bite.com");
        restaurantOwner.setPassword(passwordEncoder.encode("password123"));
        restaurantOwner.setFullName("Restaurant Owner");
        restaurantOwner.setPhone("1234567890");
        restaurantOwner.setAddress("123 Restaurant St");
        restaurantOwner.setRole(UserRole.RESTAURANT_OWNER);
        restaurantOwner = userRepository.save(restaurantOwner);

        // Create Restaurant 1: Pizza Palace
        Restaurant pizzaPalace = new Restaurant();
        pizzaPalace.setName("Pizza Palace");
        pizzaPalace.setDescription("Authentic Italian pizzas with fresh ingredients");
        pizzaPalace.setAddress("123 Main Street, City Center");
        pizzaPalace.setPhone("555-0101");
        pizzaPalace.setImageUrl("https://images.unsplash.com/photo-1513104890138-7c749659a591?w=800&h=600&fit=crop");
        pizzaPalace.setRating(4.5);
        pizzaPalace.setTotalReviews(120);
        pizzaPalace.setIsActive(true);
        pizzaPalace.setOwner(restaurantOwner);
        pizzaPalace = restaurantRepository.save(pizzaPalace);

        // Menu items for Pizza Palace
        Menu margherita = new Menu();
        margherita.setName("Margherita Pizza");
        margherita.setDescription("Classic pizza with tomato, mozzarella, and basil");
        margherita.setPrice(299.0); // ₹299
        margherita.setCategory("Pizza");
        margherita.setImageUrl("https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=800&h=600&fit=crop");
        margherita.setIsAvailable(true);
        margherita.setRestaurant(pizzaPalace);
        menuRepository.save(margherita);

        Menu pepperoni = new Menu();
        pepperoni.setName("Pepperoni Pizza");
        pepperoni.setDescription("Spicy pepperoni with mozzarella cheese");
        pepperoni.setPrice(349.0); // ₹349
        pepperoni.setCategory("Pizza");
        pepperoni.setImageUrl("https://images.unsplash.com/photo-1628840042765-356cda07504e?w=800&h=600&fit=crop");
        pepperoni.setIsAvailable(true);
        pepperoni.setRestaurant(pizzaPalace);
        menuRepository.save(pepperoni);

        Menu veggie = new Menu();
        veggie.setName("Veggie Supreme");
        veggie.setDescription("Loaded with fresh vegetables and cheese");
        veggie.setPrice(329.0); // ₹329
        veggie.setCategory("Pizza");
        veggie.setImageUrl("https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=800&h=600&fit=crop");
        veggie.setIsAvailable(true);
        veggie.setRestaurant(pizzaPalace);
        menuRepository.save(veggie);

        Menu garlicBread = new Menu();
        garlicBread.setName("Garlic Bread");
        garlicBread.setDescription("Freshly baked garlic bread with herbs");
        garlicBread.setPrice(149.0); // ₹149
        garlicBread.setCategory("Sides");
        garlicBread.setImageUrl("https://images.unsplash.com/photo-1572441713132-51c75654db73?w=800&h=600&fit=crop");
        garlicBread.setIsAvailable(true);
        garlicBread.setRestaurant(pizzaPalace);
        menuRepository.save(garlicBread);

        Menu cola = new Menu();
        cola.setName("Soft Drink");
        cola.setDescription("Coca Cola, Pepsi, or Sprite");
        cola.setPrice(50.0); // ₹50
        cola.setCategory("Drinks");
        cola.setImageUrl("https://images.unsplash.com/photo-1554866585-cd94860890b7?w=800&h=600&fit=crop");
        cola.setIsAvailable(true);
        cola.setRestaurant(pizzaPalace);
        menuRepository.save(cola);

        // Create Restaurant 2: Burger Junction
        Restaurant burgerJunction = new Restaurant();
        burgerJunction.setName("Burger Junction");
        burgerJunction.setDescription("Juicy burgers made with premium beef");
        burgerJunction.setAddress("456 Food Avenue, Downtown");
        burgerJunction.setPhone("555-0102");
        burgerJunction.setImageUrl("https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=800&h=600&fit=crop");
        burgerJunction.setRating(4.7);
        burgerJunction.setTotalReviews(95);
        burgerJunction.setIsActive(true);
        burgerJunction.setOwner(restaurantOwner);
        burgerJunction = restaurantRepository.save(burgerJunction);

        // Menu items for Burger Junction
        Menu classicBurger = new Menu();
        classicBurger.setName("Classic Burger");
        classicBurger.setDescription("Beef patty with lettuce, tomato, and special sauce");
        classicBurger.setPrice(199.0); // ₹199
        classicBurger.setCategory("Burgers");
        classicBurger.setImageUrl("https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800&h=600&fit=crop");
        classicBurger.setIsAvailable(true);
        classicBurger.setRestaurant(burgerJunction);
        menuRepository.save(classicBurger);

        Menu cheeseBurger = new Menu();
        cheeseBurger.setName("Cheeseburger");
        cheeseBurger.setDescription("Classic burger with melted cheese");
        cheeseBurger.setPrice(229.0); // ₹229
        cheeseBurger.setCategory("Burgers");
        cheeseBurger.setImageUrl("https://images.unsplash.com/photo-1550547660-d9450f859349?w=800&h=600&fit=crop");
        cheeseBurger.setIsAvailable(true);
        cheeseBurger.setRestaurant(burgerJunction);
        menuRepository.save(cheeseBurger);

        Menu baconBurger = new Menu();
        baconBurger.setName("Bacon Deluxe");
        baconBurger.setDescription("Burger with crispy bacon and BBQ sauce");
        baconBurger.setPrice(299.0); // ₹299
        baconBurger.setCategory("Burgers");
        baconBurger.setImageUrl("https://images.unsplash.com/photo-1550547660-d9450f859349?w=800&h=600&fit=crop");
        baconBurger.setIsAvailable(true);
        baconBurger.setRestaurant(burgerJunction);
        menuRepository.save(baconBurger);

        Menu fries = new Menu();
        fries.setName("French Fries");
        fries.setDescription("Crispy golden fries");
        fries.setPrice(99.0); // ₹99
        fries.setCategory("Sides");
        fries.setImageUrl("https://images.unsplash.com/photo-1573080496219-bb080dd4f877?w=800&h=600&fit=crop");
        fries.setIsAvailable(true);
        fries.setRestaurant(burgerJunction);
        menuRepository.save(fries);

        Menu shake = new Menu();
        shake.setName("Chocolate Shake");
        shake.setDescription("Creamy chocolate milkshake");
        shake.setPrice(149.0); // ₹149
        shake.setCategory("Drinks");
        shake.setImageUrl("https://images.unsplash.com/photo-1572490122747-3968b75cc699?w=800&h=600&fit=crop");
        shake.setIsAvailable(true);
        shake.setRestaurant(burgerJunction);
        menuRepository.save(shake);

        // Create Restaurant 3: Sushi World
        Restaurant sushiWorld = new Restaurant();
        sushiWorld.setName("Sushi World");
        sushiWorld.setDescription("Fresh sushi and Japanese cuisine");
        sushiWorld.setAddress("789 Asian Street, East Side");
        sushiWorld.setPhone("555-0103");
        sushiWorld.setImageUrl("https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=800&h=600&fit=crop");
        sushiWorld.setRating(4.8);
        sushiWorld.setTotalReviews(150);
        sushiWorld.setIsActive(true);
        sushiWorld.setOwner(restaurantOwner);
        sushiWorld = restaurantRepository.save(sushiWorld);

        // Menu items for Sushi World
        Menu salmonRoll = new Menu();
        salmonRoll.setName("Salmon Roll");
        salmonRoll.setDescription("Fresh salmon with rice and nori");
        salmonRoll.setPrice(249.0); // ₹249
        salmonRoll.setCategory("Sushi");
        salmonRoll.setImageUrl("https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=800&h=600&fit=crop");
        salmonRoll.setIsAvailable(true);
        salmonRoll.setRestaurant(sushiWorld);
        menuRepository.save(salmonRoll);

        Menu tunaRoll = new Menu();
        tunaRoll.setName("Tuna Roll");
        tunaRoll.setDescription("Premium tuna with avocado");
        tunaRoll.setPrice(279.0); // ₹279
        tunaRoll.setCategory("Sushi");
        tunaRoll.setImageUrl("https://images.unsplash.com/photo-1611143669185-af224c5e3252?w=800&h=600&fit=crop");
        tunaRoll.setIsAvailable(true);
        tunaRoll.setRestaurant(sushiWorld);
        menuRepository.save(tunaRoll);

        Menu californiaRoll = new Menu();
        californiaRoll.setName("California Roll");
        californiaRoll.setDescription("Crab, avocado, and cucumber");
        californiaRoll.setPrice(229.0); // ₹229
        californiaRoll.setCategory("Sushi");
        californiaRoll.setImageUrl("https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=800&h=600&fit=crop");
        californiaRoll.setIsAvailable(true);
        californiaRoll.setRestaurant(sushiWorld);
        menuRepository.save(californiaRoll);

        Menu misoSoup = new Menu();
        misoSoup.setName("Miso Soup");
        misoSoup.setDescription("Traditional Japanese miso soup");
        misoSoup.setPrice(99.0); // ₹99
        misoSoup.setCategory("Soups");
        misoSoup.setImageUrl("https://images.unsplash.com/photo-1572441713132-51c75654db73?w=800&h=600&fit=crop");
        misoSoup.setIsAvailable(true);
        misoSoup.setRestaurant(sushiWorld);
        menuRepository.save(misoSoup);

        Menu greenTea = new Menu();
        greenTea.setName("Green Tea");
        greenTea.setDescription("Hot Japanese green tea");
        greenTea.setPrice(50.0); // ₹50
        greenTea.setCategory("Drinks");
        greenTea.setImageUrl("https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800&h=600&fit=crop");
        greenTea.setIsAvailable(true);
        greenTea.setRestaurant(sushiWorld);
        menuRepository.save(greenTea);

        // Create Restaurant 4: Taco Fiesta
        Restaurant tacoFiesta = new Restaurant();
        tacoFiesta.setName("Taco Fiesta");
        tacoFiesta.setDescription("Authentic Mexican tacos and burritos");
        tacoFiesta.setAddress("321 Spice Road, West End");
        tacoFiesta.setPhone("555-0104");
        tacoFiesta.setImageUrl("https://images.unsplash.com/photo-1565299585323-38174c03b8e1?w=800&h=600&fit=crop");
        tacoFiesta.setRating(4.6);
        tacoFiesta.setTotalReviews(88);
        tacoFiesta.setIsActive(true);
        tacoFiesta.setOwner(restaurantOwner);
        tacoFiesta = restaurantRepository.save(tacoFiesta);

        // Menu items for Taco Fiesta
        Menu beefTaco = new Menu();
        beefTaco.setName("Beef Taco");
        beefTaco.setDescription("Seasoned beef with lettuce, cheese, and salsa");
        beefTaco.setPrice(179.0); // ₹179
        beefTaco.setCategory("Tacos");
        beefTaco.setImageUrl("https://images.unsplash.com/photo-1565299585323-38174c03b8e1?w=800&h=600&fit=crop");
        beefTaco.setIsAvailable(true);
        beefTaco.setRestaurant(tacoFiesta);
        menuRepository.save(beefTaco);

        Menu chickenTaco = new Menu();
        chickenTaco.setName("Chicken Taco");
        chickenTaco.setDescription("Grilled chicken with fresh veggies");
        chickenTaco.setPrice(179.0); // ₹179
        chickenTaco.setCategory("Tacos");
        chickenTaco.setImageUrl("https://images.unsplash.com/photo-1565299585323-38174c03b8e1?w=800&h=600&fit=crop");
        chickenTaco.setIsAvailable(true);
        chickenTaco.setRestaurant(tacoFiesta);
        menuRepository.save(chickenTaco);

        Menu burrito = new Menu();
        burrito.setName("Beef Burrito");
        burrito.setDescription("Large burrito with rice, beans, and beef");
        burrito.setPrice(299.0); // ₹299
        burrito.setCategory("Burritos");
        burrito.setImageUrl("https://images.unsplash.com/photo-1626700051175-6818013e1d4f?w=800&h=600&fit=crop");
        burrito.setIsAvailable(true);
        burrito.setRestaurant(tacoFiesta);
        menuRepository.save(burrito);

        Menu nachos = new Menu();
        nachos.setName("Nachos");
        nachos.setDescription("Crispy nachos with cheese and jalapeños");
        nachos.setPrice(229.0); // ₹229
        nachos.setCategory("Sides");
        nachos.setImageUrl("https://images.unsplash.com/photo-1513456852971-30c0b8199d4d?w=800&h=600&fit=crop");
        nachos.setIsAvailable(true);
        nachos.setRestaurant(tacoFiesta);
        menuRepository.save(nachos);

        Menu horchata = new Menu();
        horchata.setName("Horchata");
        horchata.setDescription("Traditional Mexican rice drink");
        horchata.setPrice(79.0); // ₹79
        horchata.setCategory("Drinks");
        horchata.setImageUrl("https://images.unsplash.com/photo-1554866585-cd94860890b7?w=800&h=600&fit=crop");
        horchata.setIsAvailable(true);
        horchata.setRestaurant(tacoFiesta);
        menuRepository.save(horchata);

        System.out.println("Sample data seeded successfully!");
        System.out.println("Created 4 restaurants with menu items");
        System.out.println("Restaurant Owner Credentials:");
        System.out.println("Username: restowner");
        System.out.println("Password: password123");
    }
}

