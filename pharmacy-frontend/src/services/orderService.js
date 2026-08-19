import api from "../api/api";

export async function getMyOrders() {
    const response = await api.get("/orders");
    return response.data;
}

export async function getOrderById(orderId) {
    const response = await api.get(`/orders/${orderId}`);
    return response.data;
}

export async function cancelOrder(orderId) {
    const response = await api.patch(`/orders/${orderId}/cancel`);
    return response.data;
}

export async function updateOrder(orderId, request = null, prescription = null) {
    const formData = new FormData();

    if (request) {
        formData.append("request", JSON.stringify(request));
    }

    if (prescription) {
        formData.append("prescription", prescription);
    }

    const response = await api.patch(
        `/orders/${orderId}/update`,
        formData
    );

    return response.data;
}

export async function checkoutOrder(request) {
    const response = await api.post(
        "/orders/checkout",
        request
    );

    return response.data;
}