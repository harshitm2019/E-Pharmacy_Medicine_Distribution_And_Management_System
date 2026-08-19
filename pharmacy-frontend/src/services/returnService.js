import api from "../api/api";

export async function getReturnsByStatus({ status, page, size }) {
    const response = await api.get("/admin/returns", { params: { status, page, size } });
    return response.data;
}

export async function updateReturnStatus(returnId, returnStatus) {
    const response = await api.patch(`/admin/returns/${returnId}/status`, { returnStatus });
    return response.data;
}

export async function createReturn(request) {
    const response = await api.post("/returns", request);
    return response.data;
}

export async function getMyReturns(page = 0, size = 10) {
    const response = await api.get("/returns", {params: {page,  size}});
    return response.data;
}

