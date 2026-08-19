import api from "../api/api";

export async function getMyProfile() {
    const response = await api.get("/users/me");
    return response.data;
}

export async function updateProfile(data) {
    const response = await api.put("/users/profile", data);
    return response.data;
}

export async function changeEmail(data) {
    const response = await api.patch("/users/me/change-email", data);
    return response.data;
}

export async function changePassword(data) {
    const response = await api.patch("/users/me/change-password", data);
    return response.data;
}