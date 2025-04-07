import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import EShopService from "../repository/EShopService";
import "./EditProduct.css";

const EditProduct = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const [product, setProduct] = useState({
        name: "",
        price: "",
        imageUrl: "",
        description: "",
        category: { id: "" }
    });

    const [categories, setCategories] = useState([]);


    useEffect(() => {
        EShopService.fetchCategories()
            .then(response => setCategories(response.data))
            .catch(error => console.error("Error fetching categories:", error));
    }, []);


    useEffect(() => {
        EShopService.getProduct(id)
            .then(response => setProduct(response.data))
            .catch(error => console.error("Error fetching product:", error));
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === "category") {
            setProduct({ ...product, category: { id: value } });
        } else {
            setProduct({ ...product, [name]: value });
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        EShopService.updateProduct(id, product)
            .then(() => navigate("/"))
            .catch(error => console.error("Error updating product:", error));
    };

    return (
        <div className="edit-product-container">
            <h2>Edit Product</h2>
            <form className="edit-product-form" onSubmit={handleSubmit}>
                <label>
                    Name:
                    <input
                        type="text"
                        name="name"
                        value={product.name}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Price:
                    <input
                        type="number"
                        name="price"
                        value={product.price}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Image URL:
                    <input
                        type="text"
                        name="imageUrl"
                        value={product.imageUrl}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Description:
                    <textarea
                        name="description"
                        value={product.description}
                        onChange={handleChange}
                        required
                    />
                </label>
                <label>
                    Category:
                    <select
                        name="category"
                        value={product.category.id}
                        onChange={handleChange}
                        required
                    >
                        <option value="">Select a category</option>
                        {categories.map(category => (
                            <option key={category.id} value={category.id}>
                                {category.name}
                            </option>
                        ))}
                    </select>
                </label>
                <button type="submit">Save Changes</button>
            </form>
        </div>
    );
};

export default EditProduct;
