const express = require('express');
const cors = require('cors');

const app = express();
const PORT = 8080;

// Middleware
app.use(cors());
app.use(express.json());

// Mockowane dane produktów
const products = [
  { id: 1, name: 'Laptop', price: 3000 },
  { id: 2, name: 'Telefon', price: 1500 },
  { id: 3, name: 'Słuchawki', price: 400 },
];

// GET /products – zwraca listę produktów
app.get('/products', (req, res) => {
  res.json(products);
});

// POST /payment – odbiera płatność (np. sumę)
app.post('/payment', (req, res) => {
  const { amount } = req.body;
  console.log(`Otrzymano płatność: ${amount} PLN`);
  res.status(200).json({ message: 'Płatność przyjęta!' });
});

// Start serwera
app.listen(PORT, () => {
  console.log(`Serwer działa na http://localhost:${PORT}`);
});
