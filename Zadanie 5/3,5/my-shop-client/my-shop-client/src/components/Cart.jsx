import { useCart } from '../context/CartContext';

export function Cart() {
    const { cartItems, removeFromCart } = useCart();
    const total = cartItems.reduce((sum, item) => sum + item.price, 0);

    return (
        <div>
            <h2>Koszyk</h2>
            {cartItems.length === 0 ? (
                <p>Koszyk jest pusty.</p>
            ) : (
                <ul>
                    {cartItems.map((item, index) => (
                        <li key={index}>
                            {item.name} - {item.price} PLN
                            <button onClick={() => removeFromCart(index)}>Usuń</button>
                        </li>
                    ))}
                </ul>
            )}
            <h3>Łączna kwota: {total} PLN</h3>
        </div>
    );
}
