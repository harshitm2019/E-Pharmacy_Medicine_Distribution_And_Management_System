import api from "../api/api";

export async function getAllPayments() {
    const response = await api.get("/admin/payments");
    return response.data;
}