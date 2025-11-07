import React from 'react';
import { Link } from 'react-router-dom';
import './Home.css';

const Home = () => {
  return (
    <div className="home">
      <div className="hero">
        <div className="hero-content">
          <h1>Welcome to Bite</h1>
          <p>Order your favorite food from the best restaurants</p>
          <Link to="/restaurants" className="btn btn-primary btn-large">
            Browse Restaurants
          </Link>
        </div>
      </div>
      <div className="features">
        <div className="container">
          <h2>Why Choose Bite?</h2>
          <div className="features-grid">
            <div className="feature-card">
              <div className="feature-icon">🚀</div>
              <h3>Fast Delivery</h3>
              <p>Get your food delivered quickly to your doorstep</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">🍕</div>
              <h3>Wide Selection</h3>
              <p>Choose from hundreds of restaurants and cuisines</p>
            </div>
            <div className="feature-card">
              <div className="feature-icon">💳</div>
              <h3>Easy Payment</h3>
              <p>Secure and convenient payment options</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;

