import { createContext, useState, useContext } from 'react';

const CartContext = createContext();

export function CartProvider({ children }) {
  const [cartItems, setCartItems] = useState([]);

  const addToCart = (product) => {
    setCartItems((prev) => [...prev, product]);
  };

  const removeFromCart = (index) => {
    setCartItems((prev) => prev.filter((_, i) => i !== index));
  };

  const clearCart = () => {
    setCartItems([]);
  };

  return (
      <CartContext.Provider value={{ cartItems, addToCart, removeFromCart, clearCart }}>
        {children}
      </CartContext.Provider>
  );
}

export function useCart() {
  return useContext(CartContext);
}
