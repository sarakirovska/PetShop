
import axios from "../axios/axios";


const getToken = () => {
    return localStorage.getItem("token");
};


const EShopService = {
    fetchCategories: () => {
        const token = getToken();
        return axios.get("/categories", {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },

    fetchProducts: () => {
        const token = getToken();
        return axios.get("/products", {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    addCategory: (category) => {
        const token = getToken();
        return axios.post("/categories/add", category, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    getProduct: (id) => {
        const token = getToken();
        return axios.get(`/products/${id}`, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    fetchProductsByCategory: (categoryId) => {
        const token = getToken();
        return axios.get(`/products/category/${categoryId}`, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    deleteProduct: (id) => {
        const token = getToken();
        return axios.delete(`/products/delete/${id}`, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    updateProduct: (id, product) => {
        const token = getToken();
        return axios.put(`/products/edit/${id}`, product, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    addProduct: (product) => {
        const token = getToken();
        return axios.post("/products/add", product, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },

    addToCart: (userId, productId, quantity) => {
        const token = localStorage.getItem("token");

        return axios.post(`/cart/add/${userId}/${productId}`, null, {
            params: { quantity },
            headers: token ? { Authorization: `Bearer ${token}` } : {},
        });
    },

    getCartItems: (userId) => {
        const token = getToken();
        return axios.get(`/cart/items/${userId}`, {
            headers: token ? { Authorization: `Bearer ${token}` } : {},
        });
    },
    removeCartItem: (userId, cartItemId) => {
        const token = getToken();
        return axios.delete(`/cart/remove/${userId}/${cartItemId}`, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },

// Authentication
    login: (email, password) => {
        return axios.post("/auth/login", { email, password });
    },
    register: (firstName,lastName,email, password) => {
        return axios.post("/auth/register", {firstName,lastName,email, password });
    },
    createOrder: (userId) => {
        const token = localStorage.getItem("token");
        return axios.post(`/orders/${userId}`, null, {
            headers: token ? { Authorization: `Bearer ${token}` } : {}
        });
    },
    getOrdersByUser: (userId) => {
        const token = localStorage.getItem("token");
        return axios.get(`/orders/user/${userId}`, {
            headers: token ? { Authorization: `Bearer ${token}` } : {},
        });
    },
    fetchAllOrders: () => {
        const token = localStorage.getItem("token");
        return axios.get("/orders", {
            headers: token ? { Authorization: `Bearer ${token}` } : {},
        });
    },

    updateOrderStatus: (orderId, status) => {
        const token = localStorage.getItem("token");
        return axios.put(`/orders/${orderId}`, status, {
            headers: token ? { Authorization: `Bearer ${token}` } : {},
        });
    },




};

export default EShopService;
