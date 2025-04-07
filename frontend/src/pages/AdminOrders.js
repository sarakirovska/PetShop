import React, { useEffect, useState } from "react";
import EShopService from "../repository/EShopService";
import "./AdminOrders.css"

const AdminOrders = () => {
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        fetchOrders();
    }, []);

    const fetchOrders = () => {
        EShopService.fetchAllOrders()
            .then(response => setOrders(response.data))
            .catch(error => console.error("Error fetching orders:", error));
    };

    const handleStatusChange = (orderId, newStatus) => {
        EShopService.updateOrderStatus(orderId, newStatus)
            .then(() => {
                setOrders(prevOrders => prevOrders.map(order =>
                    order.id === orderId ? { ...order, status: newStatus } : order
                ));
            })
            .catch(error => console.error("Error updating status:", error));
    };

    return (
        <div className="admin-orders">
            <h2>All Orders</h2>
            <table>
                <thead>
                <tr>
                    <th>Order ID</th>

                    <th>Total Price</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody>
                {orders.map(order => (
                    <tr key={order.id}>
                        <td>{order.id}</td>
                        <td>${order.totalPrice.toFixed(2)}</td>
                        <td>{order.status}</td>
                        <td>
                            <select
                                value={order.status}
                                onChange={(e) => handleStatusChange(order.id, e.target.value)}
                            >
                                <option value="PENDING">PENDING</option>
                                <option value="COMPLETED">COMPLETED</option>
                                <option value="CANCELED">CANCELED</option>
                            </select>
                        </td>
                    </tr>
                ))}
                </tbody>

            </table>
        </div>
    );
};

export default AdminOrders;
