import { useState } from 'react';
import { useCart } from '../context/CartContext';
import { api } from '../api';

export function Payment() {
  const { cartItems, clearCart } = useCart();
  const [message, setMessage] = useState('');

  const total = cartItems.reduce((sum, item) => sum + item.price, 0);

  const handlePayment = async () => {
    try {
      await api.post('/payment', { amount: total });
      setMessage('Płatność zakończona sukcesem!');
      clearCart();
    } catch (error) {
      console.error('Błąd przy płatności:', error);
      setMessage('Błąd przy płatności.');
    }
  };

  return (
      <div>
        <h2>Płatności</h2>

        {cartItems.length === 0 ? (
            <p>Koszyk jest pusty.</p>
        ) : (
            <>
              <ul>
                {cartItems.map((item, i) => (
                    <li key={i}>
                      {item.name} - {item.price} PLN
                    </li>
                ))}
              </ul>
              <h3>Do zapłaty: {total} PLN</h3>
              <button onClick={handlePayment}>Zapłać</button>
            </>
        )}

        {message && <p>{message}</p>}
      </div>
  );
}
