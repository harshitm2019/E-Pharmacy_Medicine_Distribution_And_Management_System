import api from "../api/api";

export async function getOrders(params) {
    const response = await api.get("/admin/orders", { params });
    return response.data;
}

export async function updatePrescriptionStatus(orderId, prescriptionStatus) {
    const response = await api.patch(`/admin/orders/${orderId}/prescription`, { prescriptionStatus });
    return response.data;
}

export async function updateOrderStatus(orderId, orderStatus) {
    const response = await api.patch(`/admin/orders/${orderId}/status`, { orderStatus });
    return response.data;
}

export async function cancelOrder(orderId) {
    const response = await api.patch(`/admin/orders/${orderId}/cancel`);
    return response.data;
}

export async function getOrderById(orderId) {
    const response = await api.get(`/admin/orders/${orderId}`);
    return response.data;
}

export async function getOrderReport({ startDate, endDate, page, size }) {
    const response = await api.get("/admin/orders/report", {
        params: { startDate, endDate, page, size }
    });
    return response.data;
}
