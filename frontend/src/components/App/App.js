import { useEffect, useState } from "react";
import { Route, Routes } from "react-router-dom";

import Navbar from "../Navbar/Navbar";
import Home from "../../pages/Home";
import ProductDetails from "../../pages/ProductDetails";
import CategoryProducts from "../../pages/CategoryProducts";
import EditProduct from "../../pages/EditProduct";
import AddProduct from "../../pages/AddProduct";
import Register from "../../pages/Register";
import Login from "../../pages/Login";
import Cart from "../../pages/Cart";
import axios from "axios";
import MyOrders from "../../pages/MyOrders";
import AdminOrders from "../../pages/AdminOrders";


function App() {
    const [token, setToken] = useState(localStorage.getItem("token"));


    useEffect(() => {
        if (token) {
            axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
        } else {
            delete axios.defaults.headers.common["Authorization"];
        }
    }, [token]);

    return (

            <div className="App">
                <Navbar/>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/product/:id" element={<ProductDetails />} />
                    <Route path="/category/:categoryId" element={<CategoryProducts />} />
                    <Route path="/edit-product/:id" element={<EditProduct />} />
                    <Route path="/products/add" element={<AddProduct />} />
                    <Route path="/cart" element={<Cart />} />
                    <Route path="/my-orders" element={<MyOrders />} />
                    <Route path="/admin/orders" element={<AdminOrders />} />

                    <Route path="/login" element={<Login setToken={setToken} />} />
                    <Route path="/register" element={<Register />} />
                </Routes>
            </div>

    );
}

export default App;
