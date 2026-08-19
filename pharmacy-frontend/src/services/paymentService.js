import api from "../api/api";

export async function getMyPayments() {
    const response = await api.get("/payments");
    return response.data;
}

export async function getPayment(paymentId) {
    const response = await api.get(`/payments/${paymentId}`);
    return response.data;
}

export async function processOnlinePayment(orderId, paymentMethod) {
    const response = await api.post("/payments/online", {
        orderId,
        paymentMethod
    });

    return response.data;
}