import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Navbar.css";
import EShopService from "../../repository/EShopService";

const Navbar = () => {
    const [categories, setCategories] = useState([]);
    const [dropdown, setDropdown] = useState(false);
    const [newCategory, setNewCategory] = useState("");
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userRole, setUserRole] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem("token");
        setIsLoggedIn(!!token);
        setUserRole(localStorage.getItem("role"));
    }, []);

    useEffect(() => {
        EShopService.fetchCategories()
            .then(response => setCategories(response.data))
            .catch(error => console.error("Error fetching categories:", error));
    }, []);

    const handleLogout = () => {
        localStorage.clear();
        setIsLoggedIn(false);
        navigate("/login");
        window.location.reload();
    };

    const handleAddCategory = () => {
        if (newCategory.trim() !== "") {
            const categoryData = { name: newCategory };
            EShopService.addCategory(categoryData)
                .then(() => {
                    return EShopService.fetchCategories();
                })
                .then(response => {
                    setCategories(response.data);
                    setNewCategory("");
                })
                .catch(error => console.error("Error adding category:", error));
        }
    };

    return (
        <nav className="navbar">
            <div className="nav-logo">
                <Link to="/">
                    <img
                        src="https://png.pngtree.com/png-clipart/20230413/original/pngtree-petshop-logo-with-dog-mascot-png-image_9049305.png"
                        alt="Pet Shop Logo"
                        className="logo-image"
                    />
                </Link>
            </div>
            <ul className="nav-links">
                <li><Link to="/">Home</Link></li>
                <li
                    className="dropdown"
                    onMouseEnter={() => setDropdown(true)}
                    onMouseLeave={() => setDropdown(false)}
                >
                    <span className="dropdown-title">Products ▼</span>
                    {dropdown && (
                        <ul className="dropdown-menu">
                            {categories.map((category) => (
                                <li key={category.id}>
                                    <Link to={`/category/${category.id}`}>{category.name}</Link>
                                </li>
                            ))}
                            {userRole === "Admin" && (
                                <li className="add-category">
                                    <input
                                        type="text"
                                        value={newCategory}
                                        onChange={(e) => setNewCategory(e.target.value)}
                                        placeholder="New Category"
                                        className="new-category-input"
                                    />
                                    <button onClick={handleAddCategory} className="add-category-btn">
                                        Add Category
                                    </button>
                                </li>
                            )}
                        </ul>
                    )}
                </li>
                <li><Link to="/cart">🛒Cart</Link></li>
                {isLoggedIn && userRole === "User" && <li><Link to="/my-orders">My Orders</Link></li>}
                {isLoggedIn && userRole === "Admin" && <li><Link to="/admin/orders">Admin Orders</Link></li>}
                {!isLoggedIn ? (
                    <>
                        <li><Link to="/login">Login</Link></li>
                        <li><Link to="/register">Register</Link></li>
                    </>
                ) : (
                    <li><button onClick={handleLogout}>Logout</button></li>
                )}
            </ul>
        </nav>
    );
};

export default Navbar;