# Bite Backend - Spring Boot Application

## Project Structure
The backend project is located in the `bitebackend/` folder.

## Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- MySQL server running on localhost:3306

## Database Setup
1. Create MySQL database:
```sql
CREATE DATABASE bite_data;
```

2. Update `bitebackend/src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

## Running the Application
1. Navigate to the backend directory:
```bash
cd bitebackend
```

2. Build and run the application:
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

## API Endpoints

### Authentication
- POST `/api/auth/register` - Register a new user
- POST `/api/auth/login` - Login user

### Restaurants
- GET `/api/restaurants` - Get all active restaurants
- GET `/api/restaurants/{id}` - Get restaurant by ID
- POST `/api/restaurants` - Create restaurant (requires authentication)
- PUT `/api/restaurants/{id}` - Update restaurant (requires authentication)
- DELETE `/api/restaurants/{id}` - Delete restaurant (requires authentication)

### Menu
- GET `/api/restaurants/{restaurantId}/menus` - Get menus by restaurant
- GET `/api/restaurants/{restaurantId}/menus/{id}` - Get menu by ID
- POST `/api/restaurants/{restaurantId}/menus` - Create menu (requires authentication)
- PUT `/api/restaurants/{restaurantId}/menus/{id}` - Update menu (requires authentication)
- DELETE `/api/restaurants/{restaurantId}/menus/{id}` - Delete menu (requires authentication)

### Orders
- POST `/api/orders` - Create order (requires authentication)
- GET `/api/orders` - Get user's orders (requires authentication)
- GET `/api/orders/{id}` - Get order by ID (requires authentication)
- PUT `/api/orders/{id}/status` - Update order status (requires authentication)

## JWT Authentication
The API uses JWT tokens for authentication. Include the token in the Authorization header:
```
Authorization: Bearer <token>
```
