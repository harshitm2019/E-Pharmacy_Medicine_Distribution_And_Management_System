import { Box, Typography } from "@mui/material";
import { useState } from "react";
import toast from "react-hot-toast";

import PrescriptionFormDialog from "../../components/customer/prescription/PrescriptionFormDialog";
import PrescriptionTable from "../../components/customer/prescription/PrescriptionTable";
import usePrescriptions from "../../hooks/customer/usePrescriptions";
import useReplacePrescription from "../../hooks/customer/useReplacePrescription";
import useUploadPrescription from "../../hooks/customer/useUploadPrescription";

function Prescriptions() {
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [formOpen, setFormOpen] = useState(false);
    const [formMode, setFormMode] = useState("upload");
    const [selectedPrescription, setSelectedPrescription] = useState(null);

    const { data, isLoading } = usePrescriptions({ page, size });
    const uploadPrescription = useUploadPrescription();
    const replacePrescription = useReplacePrescription();

    const handleOpenUpload = () => { setSelectedPrescription(null); setFormMode("upload"); setFormOpen(true); };
    const handleOpenReplace = (prescription) => { setSelectedPrescription(prescription); setFormMode("replace"); setFormOpen(true); };

    function handleCloseForm() {
        if (uploadPrescription.isPending || replacePrescription.isPending) return;
        setFormOpen(false);
        setSelectedPrescription(null);
    }

    const handleSuccess = (res) => { toast.success(res.message); setFormOpen(false); setSelectedPrescription(null); };
    const handleError = (err, fallback) => toast.error(err.response?.data?.message || fallback);

    function handleSubmit(formData) {
        if (formMode === "upload") {
            uploadPrescription.mutate(formData, {
                onSuccess: handleSuccess,
                onError: (err) => handleError(err, "Failed to upload prescription.")
            });
        } else {
            replacePrescription.mutate(formData, {
                onSuccess: handleSuccess,
                onError: (err) => handleError(err, "Failed to replace prescription.")
            });
        }
    }

    return (
        <Box>
            <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: { xs: "flex-start", sm: "center" }, flexDirection: { xs: "column", sm: "row" }, gap: 2, mb: 3 }}>
                <Box>
                    <Typography variant="h4" fontWeight={700} sx={{ mb: 1 }}>Prescriptions</Typography>
                    <Typography color="text.secondary">Manage your uploaded prescriptions.</Typography>
                </Box>
            </Box>

            <PrescriptionTable
                data={data}
                isLoading={isLoading}
                page={page}
                size={size}
                setPage={setPage}
                setSize={setSize}
                onView={p => window.open(p.prescriptionUrl, "_blank")}
                onReplace={handleOpenReplace}
            />

            <PrescriptionFormDialog
                open={formOpen}
                mode={formMode}
                prescription={selectedPrescription}
                onClose={handleCloseForm}
                onSubmit={handleSubmit}
                loading={uploadPrescription.isPending || replacePrescription.isPending}
            />
        </Box>
    );
}
export default Prescriptions;