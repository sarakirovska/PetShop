import React, { useState, useEffect } from "react";
import EShopService from "../repository/EShopService";
import ProductCard from "../components/ProductCard/ProductCard";
import { useNavigate } from "react-router-dom";
import './Home.css';

const Home = () => {
    const [products, setProducts] = useState([]);
    const navigate = useNavigate();
    const userId = localStorage.getItem("userId");
    const userRole = localStorage.getItem("role");

    useEffect(() => {
        loadProducts();
    }, []);

    const loadProducts = () => {
        EShopService.fetchProducts()
            .then(response => setProducts(response.data))
            .catch(error => console.error("Error fetching products:", error));
    };

    const handleAddProduct = () => {
        navigate("/products/add");
    };

    return (
        <div className="home-container">
            <h2 className="home-title">All Products</h2>
            {userRole === "Admin" && (
                <button onClick={handleAddProduct} className="add-product-btn">
                    Add Product
                </button>
            )}
            <div className="product-list">
                {products.map(product => (
                    <ProductCard
                        key={product.id}
                        product={product}
                        userId={userId}
                    />
                ))}
            </div>
        </div>
    );
};

export default Home;
