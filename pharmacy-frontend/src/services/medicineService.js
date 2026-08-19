import api from "../api/api";

export async function getMedicinesByCategory({ categoryId, page, size }) {
    const response = await api.get(`/medicines/category/${categoryId}`, {
        params: { page, size }
    });

    return response.data;
}

export async function searchMedicines({ keyword, page, size }) {
    const response = await api.get("/medicines/search", {
        params: { keyword, page, size }
    });

    return response.data;
}
