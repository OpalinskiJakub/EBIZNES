import { useState } from 'react';
import { api } from '../api.js';

export function Payment() {
  const [amount, setAmount] = useState('');
  const [message, setMessage] = useState('');

  const handlePayment = async () => {
    try {
      await api.post('/payment', { amount: Number(amount) });
      setMessage('Płatność wysłana!');
    } catch (error) {
      console.error('Błąd przy płatności:', error);
      setMessage('Błąd przy płatności.');
    }
  };

  return (
    <div>
      <h2>Płatności</h2>
      <input
        type="number"
        placeholder="Kwota"
        value={amount}
        onChange={(e) => setAmount(e.target.value)}
      />
      <button onClick={handlePayment}>Zapłać</button>
      {message && <p>{message}</p>}
    </div>
  );
}
