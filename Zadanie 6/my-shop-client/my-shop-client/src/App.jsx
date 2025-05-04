import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import { Products } from './components/Products';
import { Cart } from './components/Cart';
import { Payment } from './components/Payment';
import { CartProvider } from './context/CartContext';

function App() {
  return (
    <CartProvider>
      <Router>
        <nav>
          <ul>
            <li><Link to="/">Produkty</Link></li>
            <li><Link to="/cart">Koszyk</Link></li>
            <li><Link to="/payment">Płatności</Link></li>
          </ul>
        </nav>
        <Routes>
          <Route path="/" element={<Products />} />
          <Route path="/cart" element={<Cart />} />
          <Route path="/payment" element={<Payment />} />
        </Routes>
      </Router>
    </CartProvider>
  );
}

export default App;
