import api from "../api/api";

export async function getMedicines({

    page,
    size,
    keyword,
    categoryId,
    status

}) {

    let url = "/admin/medicines";

    if (keyword) {

        url = "/admin/medicines/search";

    }
    else if (categoryId || status) {

        url = "/admin/medicines/filter";

    }

    const response = await api.get(url, {

        params: {

            page,
            size,
            ...(keyword && { keyword }),
            ...(categoryId && { categoryId }),
            ...(status && { status })

        }

    });

    return response.data;
}

export async function createMedicine(data) {

    const response = await api.post("/admin/medicines", data);

    return response.data;
}

export async function updateMedicineStatus(medicineIds, status) {
    const response = await api.patch("/admin/medicines/status", {
        medicineIds,
        status
    });
    return response.data;
}

export async function updateMedicine(medicineId, data) {
    const response = await api.put(`/admin/medicines/${medicineId}`, data);
    return response.data;
}