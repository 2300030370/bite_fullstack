import React, { useState, useEffect, useContext } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../services/api';
import { AuthContext } from '../context/AuthContext';
import { useCart } from '../context/CartContext';
import './RestaurantDetail.css';

const RestaurantDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useContext(AuthContext);
  const { addToCart } = useCart();
  const [restaurant, setRestaurant] = useState(null);
  const [menus, setMenus] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedCategory, setSelectedCategory] = useState('All');
  const [imageErrors, setImageErrors] = useState({});

  useEffect(() => {
    fetchRestaurant();
    fetchMenus();
  }, [id]);

  const fetchRestaurant = async () => {
    try {
      const response = await api.get(`/restaurants/${id}`);
      setRestaurant(response.data);
    } catch (error) {
      console.error('Error fetching restaurant:', error);
    }
  };

  const fetchMenus = async () => {
    try {
      const response = await api.get(`/restaurants/${id}/menus`);
      setMenus(response.data);
    } catch (error) {
      console.error('Error fetching menus:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleAddToCart = (menu) => {
    if (!user) {
      navigate('/login');
      return;
    }
    addToCart({ ...menu, restaurantId: parseInt(id), restaurantName: restaurant?.name });
  };

  const categories = ['All', ...new Set(menus.map(menu => menu.category))];
  const filteredMenus = selectedCategory === 'All'
    ? menus
    : menus.filter(menu => menu.category === selectedCategory);

  if (loading) {
    return <div className="container">Loading...</div>;
  }

  if (!restaurant) {
    return <div className="container">Restaurant not found</div>;
  }

  return (
    <div className="container">
      <div className="restaurant-header">
        <div className="restaurant-header-image">
          {restaurant.imageUrl && !imageErrors[`restaurant-${restaurant.id}`] ? (
            <img
              src={restaurant.imageUrl}
              alt={restaurant.name}
              onError={() => setImageErrors(prev => ({ ...prev, [`restaurant-${restaurant.id}`]: true }))}
            />
          ) : (
            <div className="restaurant-image-placeholder">
              🍽️
            </div>
          )}
        </div>
        <div className="restaurant-header-content">
          <h1>{restaurant.name}</h1>
          <p className="restaurant-description">{restaurant.description}</p>
          <p className="restaurant-address">📍 {restaurant.address}</p>
          <p className="restaurant-rating">
            ⭐ {restaurant.rating ? restaurant.rating.toFixed(1) : '0.0'} ({restaurant.totalReviews || 0} reviews)
          </p>
        </div>
      </div>

      <div className="category-filters">
        {categories.map(category => (
          <button
            key={category}
            className={`category-btn ${selectedCategory === category ? 'active' : ''}`}
            onClick={() => setSelectedCategory(category)}
          >
            {category}
          </button>
        ))}
      </div>

      <div className="menus-grid">
        {filteredMenus.map((menu) => (
          <div key={menu.id} className="menu-card">
            <div className="menu-image">
              {menu.imageUrl && !imageErrors[`menu-${menu.id}`] ? (
                <img
                  src={menu.imageUrl}
                  alt={menu.name}
                  onError={() => setImageErrors(prev => ({ ...prev, [`menu-${menu.id}`]: true }))}
                />
              ) : (
                <div className="menu-image-placeholder">
                  🍕
                </div>
              )}
            </div>
            <div className="menu-info">
              <h3>{menu.name}</h3>
              <p className="menu-description">{menu.description}</p>
              <div className="menu-footer">
                <span className="menu-price">₹{menu.price.toFixed(2)}</span>
                <button
                  className="btn btn-primary"
                  onClick={() => handleAddToCart(menu)}
                  disabled={!menu.isAvailable}
                >
                  {menu.isAvailable ? 'Add to Cart' : 'Unavailable'}
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>

      {filteredMenus.length === 0 && (
        <p className="no-results">No menu items available in this category.</p>
      )}
    </div>
  );
};

export default RestaurantDetail;

