# Bite - Online Food Delivery System

A full-stack online food delivery application built with React (Frontend), Spring Boot (Backend), and MySQL (Database).

## Project Structure

```
bite/
├── bitefront/           # React Frontend Application
├── biteback/            
│   └── bitebackend/     # Spring Boot Backend Application
└── README.md            # This file
```

## Features

### Customer Features
- User registration and authentication
- Browse restaurants
- View restaurant menus with categories
- Add items to shopping cart
- Place orders
- View order history and track order status

### Restaurant Owner Features
- Register as restaurant owner
- Create and manage restaurants
- Add and manage menu items
- View and manage orders

### Admin Features
- User management
- Restaurant management
- Order management

## Technology Stack

### Frontend
- **React** 18.2.0
- **React Router** 6.16.0
- **Axios** 1.5.0
- **CSS3** for styling

### Backend
- **Spring Boot** 3.1.5
- **Spring Security** for authentication
- **Spring Data JPA** for database operations
- **JWT** for token-based authentication
- **MySQL** 8.0+ as database

### Database
- **MySQL** 8.0+
- Database name: `bite_data`

## Prerequisites

### Backend
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

### Frontend
- Node.js 16+ and npm

## Installation & Setup

### 1. Database Setup

1. Start MySQL server
2. Create the database:
```sql
CREATE DATABASE bite_data;
```

3. Update database credentials in `biteback/bitebackend/src/main/resources/application.properties`:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 2. Backend Setup

1. Navigate to backend directory:
```bash
cd biteback/bitebackend
```

2. Build and run the application:
```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

### 3. Frontend Setup

1. Navigate to frontend directory:
```bash
cd bitefront
```

2. Install dependencies:
```bash
npm install
```

3. Start the development server:
```bash
npm start
```

The frontend will start on `http://localhost:3000`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login user

### Restaurants
- `GET /api/restaurants` - Get all active restaurants
- `GET /api/restaurants/{id}` - Get restaurant by ID
- `POST /api/restaurants` - Create restaurant (authenticated)
- `PUT /api/restaurants/{id}` - Update restaurant (authenticated)
- `DELETE /api/restaurants/{id}` - Delete restaurant (authenticated)

### Menu
- `GET /api/restaurants/{restaurantId}/menus` - Get menus by restaurant
- `GET /api/restaurants/{restaurantId}/menus/{id}` - Get menu by ID
- `POST /api/restaurants/{restaurantId}/menus` - Create menu (authenticated)
- `PUT /api/restaurants/{restaurantId}/menus/{id}` - Update menu (authenticated)
- `DELETE /api/restaurants/{restaurantId}/menus/{id}` - Delete menu (authenticated)

### Orders
- `POST /api/orders` - Create order (authenticated)
- `GET /api/orders` - Get user's orders (authenticated)
- `GET /api/orders/{id}` - Get order by ID (authenticated)
- `PUT /api/orders/{id}/status` - Update order status (authenticated)

## Database Schema

The application uses the following main entities:
- **User** - Stores user information (customers, restaurant owners, admins)
- **Restaurant** - Stores restaurant information
- **Menu** - Stores menu items for restaurants
- **Order** - Stores order information
- **OrderItem** - Stores individual items in an order

## Authentication

The application uses JWT (JSON Web Tokens) for authentication. After successful login/registration, a token is returned which should be included in subsequent API requests:

```
Authorization: Bearer <token>
```

## User Roles

- **CUSTOMER** - Can browse restaurants, place orders
- **RESTAURANT_OWNER** - Can manage restaurants and menus
- **ADMIN** - Full system access

## Development

### Backend Development
- The backend uses Spring Boot with Maven
- Project location: `biteback/bitebackend/`
- Main application class: `com.bite.BiteApplication`
- API controllers are in `com.bite.controller`
- Services are in `com.bite.service`
- Models/Entities are in `com.bite.model`

### Frontend Development
- The frontend uses Create React App
- Main entry point: `src/index.js`
- Components are in `src/components`
- Pages are in `src/pages`
- API service: `src/services/api.js`
- Contexts: `src/context` (Auth, Cart)

## License

This project is open source and available for educational purposes.

