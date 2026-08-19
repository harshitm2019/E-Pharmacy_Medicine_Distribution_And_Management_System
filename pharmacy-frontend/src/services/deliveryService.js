import api from "../api/api";

export async function getDeliveryStatus(orderId) {
    const response = await api.get(`/delivery/orders/${orderId}/track`);
    return response.data;
}

export async function getMyDeliveryOrders(status, page = 0, size = 10) {
    const response = await api.get("/delivery/my-orders", {
        params: {status,page, size}
    });
    return response.data;
}
export async function getDeliveryOrderDetails(orderId) {
    const response = await api.get(`/delivery/orders/${orderId}`);
    return response.data;
}
export async function updateDeliveryStatus(orderId,status,cashCollected = false){
    const response = await api.patch(`/delivery/orders/${orderId}/status`,{
        status, cashCollected
  });

    return response.data;
}