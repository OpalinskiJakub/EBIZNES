import { useEffect, useState } from 'react';
import { api } from '../api';
import { useCart } from '../context/CartContext';

export function Products() {
  const [products, setProducts] = useState([]);
  const { addToCart } = useCart();

  useEffect(() => {
    api.get('/products')
      .then(response => setProducts(response.data))
      .catch(error => console.error('Błąd przy pobieraniu produktów:', error));
  }, []);

  return (
    <div>
      <h2>Produkty</h2>
      <ul>
        {products.map(product => (
          <li key={product.id}>
            {product.name} - {product.price} PLN
            <button onClick={() => addToCart(product)}>Dodaj do koszyka</button>
          </li>
        ))}
      </ul>
    </div>
  );
}
