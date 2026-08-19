import api from "../api/api";

export async function getMyPrescriptions({ page, size }) {
    const response = await api.get("/prescriptions", {
        params: { page, size }
    });

    return response.data;
}

export async function uploadPrescription(file, doctorName) {
    const formData = new FormData();

    formData.append("prescription", file);
    formData.append("doctorName", doctorName);

    const response = await api.post("/prescriptions", formData);

    return response.data;
}

export async function replacePrescription(prescriptionId, file) {
    const formData = new FormData();

    formData.append("prescription", file);

    const response = await api.put(
        `/prescriptions/${prescriptionId}`,
        formData
    );

    return response.data;
}

export async function getPrescriptionById(prescriptionId) {
    const response = await api.get(`/prescriptions/${prescriptionId}`);

    return response.data;
}