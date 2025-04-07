import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import EShopService from "../repository/EShopService";
import './ProductDetails.css';
import { FaShoppingCart } from "react-icons/fa";

const ProductDetails = () => {
    const { id } = useParams();
    const [product, setProduct] = useState(null);

    useEffect(() => {
        EShopService.getProduct(id)
            .then(response => {
                console.log(response.data);
                setProduct(response.data);
            })
            .catch(error => {
                console.error("Error fetching product details:", error);
            });
    }, [id]);

    const handleAddToCart = (productId, quantity) => {
        const userId = localStorage.getItem("userId");

        if (!userId) {
            alert("You need to log in first!");
            return;
        }

        EShopService.addToCart(userId, productId, quantity)
            .then(() => {
                alert("Product added to cart successfully!");
            })
            .catch((error) => {
                console.error("Error adding product to cart: ", error);
                alert("Failed to add product to cart!");
            });
    };

    if (!product) {
        return <p>Loading product details...</p>;
    }

    return (
        <div className="product-details">
            <img
                src={product.imageUrl}
                alt={product.name}
                className="product-details-image"
            />
            <div className="product-details-info">
                <h2 className="product-details-name">{product.name}</h2>
                <p className="product-details-description">{product.description}</p>
                <p className="product-details-price">${product.price}</p>
                <button
                    onClick={() => handleAddToCart(product.id, 1)}
                    className="add-to-cart-btn"
                >
                    <FaShoppingCart className="cart-icon" /> Add to Cart
                </button>
            </div>
        </div>
    );
};

export default ProductDetails;
