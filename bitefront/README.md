# Bite Frontend - React Application

## Prerequisites
- Node.js 16+ and npm

## Installation
1. Navigate to the frontend directory:
```bash
cd bitefront
```

2. Install dependencies:
```bash
npm install
```

## Running the Application
Start the development server:
```bash
npm start
```

The frontend will start on `http://localhost:3000`

## Features
- User authentication (Login/Register)
- Browse restaurants
- View restaurant menus
- Add items to cart
- Place orders
- View order history
- Responsive design

## Project Structure
```
bitefront/
├── public/
│   └── index.html
├── src/
│   ├── components/      # Reusable components
│   ├── context/         # React contexts (Auth, Cart)
│   ├── pages/           # Page components
│   ├── services/        # API service
│   ├── App.js           # Main app component
│   └── index.js         # Entry point
└── package.json
```

## Building for Production
```bash
npm run build
```

This creates an optimized production build in the `build` folder.

