import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { useCart } from '../context/CartContext';
import { AuthContext } from '../context/AuthContext';
import api from '../services/api';
import './Cart.css';

const Cart = () => {
  const { cart, removeFromCart, updateQuantity, clearCart, getTotalPrice } = useCart();
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [deliveryAddress, setDeliveryAddress] = useState(user?.address || '');
  const [phone, setPhone] = useState(user?.phone || '');

  const handleCheckout = async () => {
    if (cart.length === 0) {
      setError('Cart is empty');
      return;
    }

    if (!deliveryAddress || !phone) {
      setError('Please provide delivery address and phone number');
      return;
    }

    // Group items by restaurant
    const restaurantIds = [...new Set(cart.map(item => item.restaurantId))];
    
    if (restaurantIds.length > 1) {
      setError('Please order from one restaurant at a time');
      return;
    }

    setLoading(true);
    setError('');

    try {
      const restaurantId = restaurantIds[0];
      const orderRequest = {
        restaurantId,
        deliveryAddress,
        phone,
        items: cart.map(item => ({
          menuId: item.id,
          quantity: item.quantity,
        })),
      };
      
      // Calculate total with delivery fee (₹50)
      const deliveryFee = 50.0;
      const orderTotal = getTotalPrice() + deliveryFee;

      const response = await api.post('/orders', orderRequest);
      clearCart();
      navigate(`/orders`);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to place order');
    } finally {
      setLoading(false);
    }
  };

  if (cart.length === 0) {
    return (
      <div className="container">
        <div className="cart-empty">
          <h2>Your cart is empty</h2>
          <p>Add some delicious food from our restaurants!</p>
          <button onClick={() => navigate('/restaurants')} className="btn btn-primary">
            Browse Restaurants
          </button>
        </div>
      </div>
    );
  }

  const restaurantName = cart[0]?.restaurantName || 'Restaurant';

  return (
    <div className="container">
      <h1>Shopping Cart</h1>
      {error && <div className="error-message">{error}</div>}
      
      <div className="cart-content">
        <div className="cart-items">
          <h2>Items from {restaurantName}</h2>
          {cart.map((item) => (
            <div key={`${item.id}-${item.restaurantId}`} className="cart-item">
              <div className="cart-item-info">
                <h3>{item.name}</h3>
                <p className="cart-item-price">₹{item.price.toFixed(2)} each</p>
              </div>
              <div className="cart-item-controls">
                <div className="quantity-controls">
                  <button
                    onClick={() => updateQuantity(item.id, item.restaurantId, item.quantity - 1)}
                    className="quantity-btn"
                  >
                    -
                  </button>
                  <span className="quantity">{item.quantity}</span>
                  <button
                    onClick={() => updateQuantity(item.id, item.restaurantId, item.quantity + 1)}
                    className="quantity-btn"
                  >
                    +
                  </button>
                </div>
                <p className="cart-item-total">
                  ₹{(item.price * item.quantity).toFixed(2)}
                </p>
                <button
                  onClick={() => removeFromCart(item.id, item.restaurantId)}
                  className="btn btn-danger btn-small"
                >
                  Remove
                </button>
              </div>
            </div>
          ))}
        </div>

        <div className="cart-summary">
          <h2>Order Summary</h2>
          <div className="form-group">
            <label>Delivery Address</label>
            <input
              type="text"
              value={deliveryAddress}
              onChange={(e) => setDeliveryAddress(e.target.value)}
              placeholder="Enter delivery address"
            />
          </div>
          <div className="form-group">
            <label>Phone Number</label>
            <input
              type="tel"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
              placeholder="Enter phone number"
            />
          </div>
          <div className="summary-row">
            <span>Subtotal:</span>
            <span>₹{getTotalPrice().toFixed(2)}</span>
          </div>
          <div className="summary-row">
            <span>Delivery Fee:</span>
            <span>₹50.00</span>
          </div>
          <div className="summary-row total">
            <span>Total:</span>
            <span>₹{(getTotalPrice() + 50.0).toFixed(2)}</span>
          </div>
          <button
            onClick={handleCheckout}
            disabled={loading}
            className="btn btn-primary btn-block"
          >
            {loading ? 'Placing Order...' : 'Place Order'}
          </button>
        </div>
      </div>
    </div>
  );
};

export default Cart;

