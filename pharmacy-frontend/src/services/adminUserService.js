import api from "../api/api";


export async function createUser(data) {
    const response = await api.post("/admin/users", data);
    return response.data;
}

export async function getUsers({ page, size, role, email }) {
    const response = await api.get("/admin/users", {
        params: { page,size,role: role || undefined,email: email || undefined
        }
    });
    return response.data;
}

export async function updateUserStatus(userId, status) {
    const response = await api.patch(  `/admin/users/${userId}/status`, { status });

    return response.data;
}