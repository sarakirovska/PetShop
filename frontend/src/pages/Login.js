import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import EShopService from "../repository/EShopService";
import "./Login.css";

const Login = ({ setToken }) => {

    const [formData, setFormData] = useState({
        email: "",
        password: "",
    });
    const [errorMessage, setErrorMessage] = useState("");
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await EShopService.login(formData.email, formData.password);
            const token = response.data.token;
            const userId = response.data.userId;
            const role = response.data.role;

            if (token) {

                localStorage.setItem("token", token);
                localStorage.setItem("userId", userId);
                localStorage.setItem("role", role);

                setToken(token);
                setIsLoggedIn(true);

                alert("Login successful!");
                navigate("/");
            }
        } catch (error) {
            console.error("There was an error logging in!", error);
            setErrorMessage("Login failed! Please check your credentials.");
        }
    };

    return (
        <div className="login-container">
            <h2>Login</h2>
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Email</label>
                    <input
                        type="text"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                    />
                </div>
                <div>
                    <label>Password</label>
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                    />
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default Login;
