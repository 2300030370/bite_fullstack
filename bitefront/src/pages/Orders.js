import React, { useState, useEffect, useContext } from 'react';
import { Link } from 'react-router-dom';
import api from '../services/api';
import { AuthContext } from '../context/AuthContext';
import './Orders.css';

const Orders = () => {
  const { user } = useContext(AuthContext);
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    try {
      const response = await api.get('/orders');
      setOrders(response.data);
    } catch (error) {
      console.error('Error fetching orders:', error);
    } finally {
      setLoading(false);
    }
  };

  const getStatusColor = (status) => {
    const colors = {
      PENDING: '#ffc107',
      CONFIRMED: '#17a2b8',
      PREPARING: '#007bff',
      OUT_FOR_DELIVERY: '#6f42c1',
      DELIVERED: '#28a745',
      CANCELLED: '#dc3545',
    };
    return colors[status] || '#666';
  };

  if (loading) {
    return <div className="container">Loading...</div>;
  }

  return (
    <div className="container">
      <h1>My Orders</h1>
      {orders.length === 0 ? (
        <div className="no-orders">
          <p>You haven't placed any orders yet.</p>
          <Link to="/restaurants" className="btn btn-primary">
            Browse Restaurants
          </Link>
        </div>
      ) : (
        <div className="orders-list">
          {orders.map((order) => (
            <div key={order.id} className="order-card">
              <div className="order-header">
                <div>
                  <h3>Order #{order.orderNumber}</h3>
                  <p className="order-date">
                    {new Date(order.createdAt).toLocaleString()}
                  </p>
                </div>
                <span
                  className="order-status"
                  style={{ backgroundColor: getStatusColor(order.status) }}
                >
                  {order.status.replace('_', ' ')}
                </span>
              </div>
              <div className="order-details">
                <p>
                  <strong>Restaurant:</strong> {order.restaurant.name}
                </p>
                <p>
                  <strong>Delivery Address:</strong> {order.deliveryAddress}
                </p>
                <p>
                  <strong>Phone:</strong> {order.phone}
                </p>
              </div>
              <div className="order-items">
                <h4>Items:</h4>
                <ul>
                  {order.orderItems.map((item) => (
                    <li key={item.id}>
                      {item.menu.name} x {item.quantity} - ₹{item.price.toFixed(2)} each
                    </li>
                  ))}
                </ul>
              </div>
              <div className="order-footer">
                <span className="order-total">
                  Total: ₹{order.totalAmount.toFixed(2)}
                </span>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default Orders;

