import React, { createContext, useState, useContext, useEffect } from 'react';

const CartContext = createContext();

export const useCart = () => {
  const context = useContext(CartContext);
  if (!context) {
    throw new Error('useCart must be used within a CartProvider');
  }
  return context;
};

export const CartProvider = ({ children }) => {
  const [cart, setCart] = useState([]);

  useEffect(() => {
    const savedCart = localStorage.getItem('cart');
    if (savedCart) {
      setCart(JSON.parse(savedCart));
    }
  }, []);

  useEffect(() => {
    localStorage.setItem('cart', JSON.stringify(cart));
  }, [cart]);

  const addToCart = (item) => {
    setCart((prevCart) => {
      const existingItem = prevCart.find(
        (cartItem) => cartItem.id === item.id && cartItem.restaurantId === item.restaurantId
      );
      if (existingItem) {
        return prevCart.map((cartItem) =>
          cartItem.id === item.id && cartItem.restaurantId === item.restaurantId
            ? { ...cartItem, quantity: cartItem.quantity + 1 }
            : cartItem
        );
      }
      return [...prevCart, { ...item, quantity: 1 }];
    });
  };

  const removeFromCart = (itemId, restaurantId) => {
    setCart((prevCart) =>
      prevCart.filter(
        (item) => !(item.id === itemId && item.restaurantId === restaurantId)
      )
    );
  };

  const updateQuantity = (itemId, restaurantId, quantity) => {
    if (quantity <= 0) {
      removeFromCart(itemId, restaurantId);
      return;
    }
    setCart((prevCart) =>
      prevCart.map((item) =>
        item.id === itemId && item.restaurantId === restaurantId
          ? { ...item, quantity }
          : item
      )
    );
  };

  const clearCart = () => {
    setCart([]);
    localStorage.removeItem('cart');
  };

  const getTotalPrice = () => {
    return cart.reduce((total, item) => total + item.price * item.quantity, 0);
  };

  const getTotalItems = () => {
    return cart.reduce((total, item) => total + item.quantity, 0);
  };

  return (
    <CartContext.Provider
      value={{
        cart,
        addToCart,
        removeFromCart,
        updateQuantity,
        clearCart,
        getTotalPrice,
        getTotalItems,
      }}
    >
      {children}
    </CartContext.Provider>
  );
};

