import api from "../api/api";

export async function createDeliveryBoy(data) {
    const response = await api.post("/admin/delivery/delivery-boys", data);
    return response.data;
}

export async function updateDeliveryBoy(deliveryBoyId, data) {
    const response = await api.put(`/admin/delivery/delivery-boys/${deliveryBoyId}`, data);
    return response.data;
}

export async function getDeliveryBoys({ page, size }) {
    const response = await api.get("/admin/delivery/delivery-boys", { params: { page, size } });
    return response.data;
}

export async function getAvailableDeliveryBoys({ page, size }) {
    const response = await api.get("/admin/delivery/delivery-boys/available", { params: { page, size } });
    return response.data;
}

export async function assignDeliveryBoy(data) {
    const response = await api.post("/admin/delivery/assign", data);
    return response.data;
}

export async function getDeliveryStatus(orderId) {
    const response = await api.get(`/admin/delivery/orders/${orderId}`);
    return response.data;
}
export async function getDeliveryStatusByStatus({ status, page, size }) {
    const response = await api.get("/admin/delivery/status", { params: { status, page, size } });
    return response.data;
}