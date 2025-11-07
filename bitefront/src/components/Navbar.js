import React, { useContext } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';
import { useCart } from '../context/CartContext';
import './Navbar.css';

const Navbar = () => {
  const { user, logout } = useContext(AuthContext);
  const { getTotalItems } = useCart();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/');
  };

  const cartItemCount = getTotalItems();

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-logo">
          🍔 Bite
        </Link>
        <div className="navbar-menu">
          <Link to="/" className="navbar-link">Home</Link>
          <Link to="/restaurants" className="navbar-link">Restaurants</Link>
          {user ? (
            <>
              <Link to="/cart" className="navbar-link">
                Cart {cartItemCount > 0 && <span className="cart-badge">{cartItemCount}</span>}
              </Link>
              <Link to="/orders" className="navbar-link">My Orders</Link>
              <span className="navbar-user">Welcome, {user.username}</span>
              <button onClick={handleLogout} className="btn btn-secondary">
                Logout
              </button>
            </>
          ) : (
            <>
              <Link to="/login" className="navbar-link">Login</Link>
              <Link to="/register" className="btn btn-primary">Register</Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;

