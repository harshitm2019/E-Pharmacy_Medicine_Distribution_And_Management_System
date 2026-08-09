import { Box, Typography } from "@mui/material";
import { useState } from "react";
import toast from "react-hot-toast";

import MedicineDetailsDialog from "../../components/admin/medicine/MedicineDetailsDialog";
import MedicineDialog from "../../components/admin/medicine/MedicineDialog";
import MedicineForm from "../../components/admin/medicine/MedicineForm";
import MedicineTable from "../../components/admin/medicine/MedicineTable";
import MedicineToolbar from "../../components/admin/medicine/MedicineToolbar";
import useCreateMedicine from "../../hooks/useCreateMedicine";
import useMedicines from "../../hooks/useMedicines";
import useUpdateMedicine from "../../hooks/useUpdateMedicine";
import useUpdateMedicineStatus from "../../hooks/useUpdateMedicineStatus";


function Medicines() {

    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [keyword, setKeyword] = useState("");
    const [categoryId, setCategoryId] = useState("");
    const [status, setStatus] = useState("");
    const [openDialog, setOpenDialog] = useState(false);
    const [selectedMedicine, setSelectedMedicine] = useState(null);
    const [openDetails, setOpenDetails] = useState(false);
    const [selectedIds, setSelectedIds] = useState([]);
    const [editMedicine, setEditMedicine] = useState(null);

    const { data, isLoading } = useMedicines({ page, size, keyword, categoryId, status });
    const updateStatus = useUpdateMedicineStatus();
    const createMedicine = useCreateMedicine();
    const updateMedicine = useUpdateMedicine();

    function handleSelect(id) {
        setSelectedIds(prev => prev.includes(id) ? prev.filter(item => item !== id) : [...prev, id]);
    }

    function handleSelectAll(ids) {
        setSelectedIds(prev => prev.length === ids.length ? [] : ids);
    }

    function handleStatusChange(status) {
        updateStatus.mutate(
            { medicineIds: selectedIds, status },
            {
                onSuccess: response => {
                    toast.success(response.message);
                    setSelectedIds([]);
                },
                onError: error => {
                    toast.error(error.response?.data?.message || "Failed to update medicine status.");
                }
            }
        );
    }

    function handleEdit(medicine) {
        setEditMedicine(medicine);
        setOpenDialog(true);
    }

    function handleUpdate(data) {
        updateMedicine.mutate(
            { medicineId: editMedicine.medicineId, data },
            {
                onSuccess: response => {
                    toast.success(response.message);
                    setOpenDialog(false);
                    setEditMedicine(null);
                },
                onError: error => toast.error(error.response?.data?.message || "Failed to update medicine.")
            }
        );
    }

    function handleCreate(data) {
        createMedicine.mutate(data, {
            onSuccess: response => {
                toast.success(response.message);
                setOpenDialog(false);
            },
            onError: error => toast.error(error.response?.data?.message || "Failed to create medicine.")
        });
    }

    function handleView(medicine) {
        setSelectedMedicine(medicine);
        setOpenDetails(true);
    }

    function handleCloseDetails() {
        setOpenDetails(false);
        setSelectedMedicine(null);
    }

    return (
        <Box>
            <Typography variant="h4" fontWeight={700} mb={3}>Medicines</Typography>

            <Box sx={{ display: "flex", flexDirection: "column", gap: 4 }}>
                <MedicineToolbar
                    keyword={keyword}
                    setKeyword={setKeyword}
                    categoryId={categoryId}
                    setCategoryId={setCategoryId}
                    status={status}
                    setStatus={setStatus}
                    onAdd={() => setOpenDialog(true)}
                />

                <MedicineTable
                    data={data}
                    isLoading={isLoading}
                    page={page}
                    size={size}
                    setPage={setPage}
                    setSize={setSize}
                    onView={handleView}
                    onEdit={handleEdit}
                    selectedIds={selectedIds}
                    onSelect={handleSelect}
                    onSelectAll={handleSelectAll}
                    onStatusChange={handleStatusChange}
                    isUpdating={updateStatus.isPending}
                />
            </Box>

            <MedicineDetailsDialog
                open={openDetails}
                medicine={selectedMedicine}
                onClose={handleCloseDetails}
            />

            <MedicineDialog
                open={openDialog}
                title={editMedicine ? "Edit Medicine" : "Add Medicine"}
                onClose={() => {
                    setOpenDialog(false);
                    setEditMedicine(null);
                }}
                loading={editMedicine ? updateMedicine.isPending : createMedicine.isPending}
            >
                <MedicineForm
                    medicine={editMedicine}
                    onSubmit={editMedicine ? handleUpdate : handleCreate}
                />
            </MedicineDialog>
        </Box>
    );
}

export default Medicines;