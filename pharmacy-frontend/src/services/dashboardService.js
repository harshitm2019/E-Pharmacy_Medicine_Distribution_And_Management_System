import api from "../api/api";

export async function getAdminDashboard() {

    const response = await api.get("/dashboard/admin");

    return response.data;

}