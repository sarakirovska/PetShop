import { useEffect, useState } from "react";
import EShopService from "../repository/EShopService";
import "./Cart.css";

const Cart = () => {
    const [cartItems, setCartItems] = useState([]);
    const [isOrderPlaced, setIsOrderPlaced] = useState(false);
    const [totalPrice, setTotalPrice] = useState(0);

    useEffect(() => {
        const userId = localStorage.getItem("userId");
        if (!userId) {
            console.error("No userId found in localStorage");
            return;
        }

        EShopService.getCartItems(userId)
            .then(response => {
                setCartItems(response.data);
                calculateTotal(response.data);
            })
            .catch((error) => {
                console.error("Error fetching cart items: ", error);
            });
    }, []);


    const calculateTotal = (items) => {
        const total = items.reduce((acc, item) => acc + item.product.price * item.quantity, 0);
        setTotalPrice(total);
    };

    const handleRemoveItem = (cartItemId) => {
        const userId = localStorage.getItem("userId");
        if (!userId) {
            alert("Please login");
            return;
        }
        EShopService.removeCartItem(userId, cartItemId)
            .then(() => {

                setCartItems(cartItems.filter(item => item.id !== cartItemId));
                calculateTotal(cartItems.filter(item => item.id !== cartItemId));
            })
            .catch((error) => {
                console.error("Error: ", error);
            });
    };

    const handlePlaceOrder = () => {
        const userId = localStorage.getItem("userId");
        if (!userId) {
            alert("Please log in to place an order!");
            return;
        }

        EShopService.createOrder(userId)
            .then(response => {
                alert("Order placed successfully!");
                setIsOrderPlaced(true);
            })
            .catch((error) => {
                console.error("Error placing order: ", error);
                alert("Failed to place order. Try again!");
            });
    };

    return (
        <div className="cart-container">
            <h2>My Cart🛒</h2>
            {cartItems.length === 0 ? (
                <p className="cart-empty-message">Your cart is empty!</p>
            ) : (
                <table className="cart-table">
                    <thead>
                    <tr>
                        <th>Product</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {cartItems.map(item => (
                        <tr key={item.id}>
                            <td>{item.product.name}</td>
                            <td>${item.product.price}</td>
                            <td>{item.quantity}</td>
                            <td>${item.product.price * item.quantity}</td>
                            <td>
                                <button onClick={() => handleRemoveItem(item.id)}>Remove</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
            <div className="total-price">
                <p><strong>Total: ${totalPrice.toFixed(2)}</strong></p>
            </div>
            <button onClick={handlePlaceOrder} className="place-order-btn" disabled={isOrderPlaced}>
                {isOrderPlaced ? "Order Placed" : "Place Order"}
            </button>
        </div>
    );
};

export default Cart;
