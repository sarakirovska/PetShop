import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './ProductCard.css';
import EShopService from "../../repository/EShopService";
import { FaShoppingCart, FaEdit, FaTrash } from 'react-icons/fa';

const ProductCard = ({ product, onDelete = () => {} }) => {
    const navigate = useNavigate();
    const userRole = localStorage.getItem("role");

    const handleDelete = () => {
        if (window.confirm(`Are you sure you want to delete ${product.name}?`)) {
            EShopService.deleteProduct(product.id)
                .then(() => {
                    onDelete(product.id);
                })
                .catch(error => console.error("Error deleting product:", error));
        }
    };

    const handleEdit = () => {
        navigate(`/edit-product/${product.id}`);
    };

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

    return (
        <div className="product-card">
            <Link to={`/product/${product.id}`} className="product-link">
                <img src={product.imageUrl} alt={product.name} className="product-image" />
                <h3>{product.name}</h3>
                <p>Price: ${product.price}</p>
            </Link>
            <div className="product-actions">
                {userRole === "Admin" && (
                    <>
                        <button onClick={handleEdit} className="edit-btn">
                            <FaEdit />
                        </button>
                        <button onClick={handleDelete} className="delete-btn">
                            <FaTrash />
                        </button>
                    </>
                )}
                <button
                    onClick={() => handleAddToCart(product.id, 1)}
                    className="add-to-cart-btn"
                >
                    <FaShoppingCart />
                </button>
            </div>
        </div>
    );
};

export default ProductCard;
