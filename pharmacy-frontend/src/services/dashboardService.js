import api from "../api/api";

export async function getAdminDashboard() {

    const response = await api.get("/dashboard/admin");
    return response.data;

}

export async function getCustomerDashboard() {

    const response = await api.get("/dashboard/customer");
    return response.data;
    
}

export async function getDeliveryBoyDashboard() {
    const response = await api.get( "/dashboard/delivery-boy" );
    return response.data;
}