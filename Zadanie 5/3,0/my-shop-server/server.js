const express = require('express');
const cors = require('cors');
const app = express();

app.use(cors());
app.use(express.json());

const products = [
  { id: 1, name: 'Laptop', price: 3000 },
  { id: 2, name: 'Telefon', price: 1500 },
  { id: 3, name: 'Słuchawki', price: 400 },
];


app.get('/products', (req, res) => {
  res.json(products);
});


app.post('/payment', (req, res) => {
  console.log('Otrzymana płatność:', req.body);
  res.status(200).send('Płatność otrzymana!');
});

const PORT = 8080;
app.listen(PORT, () => {
  console.log(`Serwer działa na http://localhost:${PORT}`);
});
