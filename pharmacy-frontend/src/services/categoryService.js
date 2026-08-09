import api from "../api/api";

export async function getCategories() {
    const response = await api.get("/categories");
    return response.data;

}

export async function createCategory(data) {
    const response = await api.post("/admin/categories", data);
    return response.data;
}

export async function updateCategory(categoryId, data) {
    const response = await api.put(`/admin/categories/${categoryId}`, data);
    return response.data;
}

export async function deleteCategory(categoryId) {
    const response = await api.delete(`/admin/categories/${categoryId}`);
    return response.data;
}
export async function searchCategories(keyword) {
    const response = await api.get(`/categories/search?keyword=${encodeURIComponent(keyword)}`);
    return response.data;
}