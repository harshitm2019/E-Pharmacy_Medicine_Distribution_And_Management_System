import { Box, Button, MenuItem, TextField, ToggleButton, ToggleButtonGroup, Typography } from "@mui/material";
import { useState } from "react";
import toast from "react-hot-toast";

import CreateDeliveryBoyDialog from "../../components/admin/delivery/CreateDeliveryBoyDialog";
import DeliveryBoyTable from "../../components/admin/delivery/DeliveryBoyTable";
import DeliveryStatusDialog from "../../components/admin/delivery/DeliveryStatusDialog";
import DeliveryStatusTable from "../../components/admin/delivery/DeliveryStatusTable";
import UpdateDeliveryBoyDialog from "../../components/admin/delivery/UpdateDeliveryBoyDialog";
import useAdminUsers from "../../hooks/admin/useAdminUsers";
import useAvailableDeliveryBoys from "../../hooks/admin/useAvailableDeliveryBoys";
import useCreateDeliveryBoy from "../../hooks/admin/useCreateDeliveryBoy";
import useDeliveryBoys from "../../hooks/admin/useDeliveryBoys";
import useDeliveryStatusByStatus from "../../hooks/admin/useDeliveryStatusByStatus";
import useUpdateDeliveryBoy from "../../hooks/admin/useUpdateDeliveryBoy";

function DeliveryBoys() {
    const [view, setView] = useState("all");
    const [page, setPage] = useState(0);
    const [size, setSize] = useState(10);
    const [createOpen, setCreateOpen] = useState(false);
    const [updateOpen, setUpdateOpen] = useState(false);
    const [selectedDeliveryBoy, setSelectedDeliveryBoy] = useState(null);

    const [deliveryStatus, setDeliveryStatus] = useState("ASSIGNED");
    const [statusPage, setStatusPage] = useState(0);
    const [statusSize, setStatusSize] = useState(10);
    const [selectedDelivery, setSelectedDelivery] = useState(null);

    const { data: allData, isLoading: allLoading } = useDeliveryBoys({ page, size, enabled: view === "all" });

    const { data: availableData, isLoading: availableLoading } = useAvailableDeliveryBoys({ page, size, enabled: view === "available" });

    const { data: usersData } = useAdminUsers({ role: "DELIVERY_BOY", email: "", page: 0, size: 1000, enabled: createOpen });

    const { data: deliveryStatusData, isLoading: deliveryStatusLoading } = useDeliveryStatusByStatus({
        status: deliveryStatus,
        page: statusPage,
        size: statusSize
    });

    const createMutation = useCreateDeliveryBoy();
    const updateMutation = useUpdateDeliveryBoy();

    const deliveryBoysData = allData?.data?.content ?? [];
    const users = usersData?.data?.content ?? [];

    const existingUserIds = new Set(deliveryBoysData.map(deliveryBoy => deliveryBoy.userId));

    const pendingUsers = users.filter(user => !existingUserIds.has(user.userId));

    const data = view === "all" ? allData : availableData;
    const isLoading = view === "all" ? allLoading : availableLoading;

    function handleViewChange(_, newView) {
        if (!newView) return;
        setView(newView);
        setPage(0);
    }

    function handleEdit(deliveryBoy) {
        setSelectedDeliveryBoy(deliveryBoy);
        setUpdateOpen(true);
    }

    function handleCreate(data) {
        createMutation.mutate(data, {
            onSuccess: response => {
                toast.success(response.message);
                setCreateOpen(false);
            },
            onError: error => {
                toast.error(error.response?.data?.message || "Failed to create delivery information.");
            }
        });
    }

    function handleUpdate(data) {
        updateMutation.mutate(
            { deliveryBoyId: selectedDeliveryBoy.deliveryBoyId, data },
            {
                onSuccess: response => {
                    toast.success(response.message);
                    setUpdateOpen(false);
                    setSelectedDeliveryBoy(null);
                },
                onError: error => {
                    toast.error(error.response?.data?.message || "Failed to update delivery boy.");
                }
            }
        );
    }

    function handleDeliveryStatusChange(event) {
        setDeliveryStatus(event.target.value);
        setStatusPage(0);
    }

    function handleViewDelivery(delivery) {
        setSelectedDelivery(delivery);
    }

    return (
        <Box>
            <Box sx={{ display: "flex", justifyContent: "space-between", alignItems: { xs: "flex-start", sm: "center" }, flexDirection: { xs: "column", sm: "row" }, gap: 2, mb: 3 }}>
                <Typography variant="h4" fontWeight={700}>Delivery Boys</Typography>

                <Button variant="contained" onClick={() => setCreateOpen(true)}>
                    Add Delivery Information
                </Button>
            </Box>

            <ToggleButtonGroup value={view} exclusive onChange={handleViewChange}>
                <ToggleButton value="all">All Delivery Boys</ToggleButton>
                <ToggleButton value="available">Available</ToggleButton>
            </ToggleButtonGroup>

            <Box sx={{ mt: 4 }}>
                <DeliveryBoyTable
                    data={data}
                    isLoading={isLoading}
                    page={page}
                    size={size}
                    setPage={setPage}
                    setSize={setSize}
                    onEdit={handleEdit}
                />
            </Box>

            <CreateDeliveryBoyDialog
                open={createOpen}
                onClose={() => setCreateOpen(false)}
                users={pendingUsers}
                onSubmit={handleCreate}
                loading={createMutation.isPending}
            />

            <UpdateDeliveryBoyDialog
                open={updateOpen}
                onClose={() => {
                    setUpdateOpen(false);
                    setSelectedDeliveryBoy(null);
                }}
                deliveryBoy={selectedDeliveryBoy}
                onSubmit={handleUpdate}
                loading={updateMutation.isPending}
            />

            <Box sx={{ mt: 5 }}>
                <Typography variant="h5" fontWeight={700} sx={{ mb: 2 }}>
                    Delivery Status
                </Typography>

                <TextField
                    select
                    size="small"
                    label="Status"
                    value={deliveryStatus}
                    onChange={handleDeliveryStatusChange}
                    sx={{ minWidth: 220, mb: 3 }}
                >
                    <MenuItem value="ASSIGNED">Assigned</MenuItem>
                    <MenuItem value="OUT_FOR_DELIVERY">Out for Delivery</MenuItem>
                    <MenuItem value="DELIVERED">Delivered</MenuItem>
                </TextField>

                <DeliveryStatusTable
                    data={deliveryStatusData}
                    isLoading={deliveryStatusLoading}
                    page={statusPage}
                    size={statusSize}
                    setPage={setStatusPage}
                    setSize={setStatusSize}
                    onView={handleViewDelivery}
                />

                <DeliveryStatusDialog
                    open={!!selectedDelivery}
                    delivery={selectedDelivery}
                    onClose={() => setSelectedDelivery(null)}
                />
            </Box>
        </Box>
    );
}
export default DeliveryBoys;