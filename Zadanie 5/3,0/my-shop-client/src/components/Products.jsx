import { useEffect, useState } from 'react';
import { api } from '../api.js';

export function Products() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    api.get('/products')
      .then(response => {
        setProducts(response.data);
      })
      .catch(error => {
        console.error('Błąd podczas pobierania produktów:', error);
      });
  }, []);

  return (
    <div>
      <h2>Produkty</h2>
      <ul>
        {products.map((product) => (
          <li key={product.id}>
            {product.name} - {product.price} PLN
          </li>
        ))}
      </ul>
    </div>
  );
}
