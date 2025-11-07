import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../services/api';
import './Restaurants.css';

const Restaurants = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [imageErrors, setImageErrors] = useState({});

  useEffect(() => {
    fetchRestaurants();
  }, []);

  const fetchRestaurants = async () => {
    try {
      console.log('Fetching restaurants from:', api.defaults.baseURL + '/restaurants');
      const response = await api.get('/restaurants');
      console.log('Restaurants response:', response.data);
      setRestaurants(response.data);
    } catch (error) {
      console.error('Error fetching restaurants:', error);
      console.error('Error details:', error.response?.data);
      console.error('Error status:', error.response?.status);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return <div className="container">Loading...</div>;
  }

  return (
    <div className="container">
      <h1>Restaurants</h1>
      <div className="restaurants-grid">
        {restaurants.map((restaurant) => (
          <Link
            key={restaurant.id}
            to={`/restaurants/${restaurant.id}`}
            className="restaurant-card"
          >
            <div className="restaurant-image">
              {restaurant.imageUrl && !imageErrors[restaurant.id] ? (
                <img
                  src={restaurant.imageUrl}
                  alt={restaurant.name}
                  onError={() => setImageErrors(prev => ({ ...prev, [restaurant.id]: true }))}
                />
              ) : (
                <div className="restaurant-image-placeholder">
                  🍽️
                </div>
              )}
            </div>
            <div className="restaurant-info">
              <h3>{restaurant.name}</h3>
              <p className="restaurant-description">{restaurant.description}</p>
              <p className="restaurant-address">📍 {restaurant.address}</p>
              <p className="restaurant-rating">
                ⭐ {restaurant.rating ? restaurant.rating.toFixed(1) : '0.0'} ({restaurant.totalReviews || 0} reviews)
              </p>
            </div>
          </Link>
        ))}
      </div>
      {restaurants.length === 0 && (
        <p className="no-results">No restaurants available at the moment.</p>
      )}
    </div>
  );
};

export default Restaurants;

